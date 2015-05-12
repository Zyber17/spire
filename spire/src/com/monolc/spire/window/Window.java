package com.monolc.spire.window;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	long id;
	int width;
	int height;
	String title;
	KeyHandler kh;
	MouseHandler mh;
	ScrollHandler sh;
	ArrayList<Event> events;
	double timeOfInitialization;
	public Window() {
		id = 0;
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		title = "Window";
		width = 800;
		height = 600;
	}
	public void setTitle(String t) {
		title = t;
		if (id != 0) {
			GLFW.glfwSetWindowTitle(id, title);
		}
	}
	public void setWidth(int w) {
		width = w;
		if (id != 0) {
			GLFW.glfwSetWindowSize(id, width, height);
		}
	}
	public void setHeight(int h) {
		height = h;
		if (id != 0) {
			GLFW.glfwSetWindowSize(id, width, height);
		}
	}
	public void setGLMajor(int GLMaj) {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, GLMaj);
	}
	public void setGLMinor(int GLMin) {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, GLMin);
	}
	public void setVisible(boolean visible) {
		glfwWindowHint(GLFW_VISIBLE, visible ? GL_TRUE : GL_FALSE);
	}
	public void setResizable(boolean resizable) {
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);
	}
	public void setFloating(boolean floating) {
		glfwWindowHint(GLFW_FLOATING, floating ? GL_TRUE : GL_FALSE);
	}
	public void setDecorated(boolean decorated) {
		glfwWindowHint(GLFW_DECORATED, decorated ? GL_TRUE : GL_FALSE);
	}
	public void create() {
		events = new ArrayList<Event>();
		id = glfwCreateWindow(width, height, title, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		this.bindKeyCallback();
		this.bindMouseCallback();
		this.bindScrollCallback();
		this.createContext();
		timeOfInitialization = glfwGetTime();
	}
	public double getSecondsSinceInitialization() {
		return glfwGetTime() - timeOfInitialization;
	}
	public Event queryEvent() {
		return events.remove(0);
	}
	public boolean eventsToQuery() {
		return events.size() > 0;
	}
	private void bindKeyCallback() {
		kh = new KeyHandler(this);
		glfwSetKeyCallback(id, kh);
	}
	private void bindMouseCallback() {
		mh = new MouseHandler(this);
		glfwSetMouseButtonCallback(id, mh);
	}
	private void bindScrollCallback() {
		sh = new ScrollHandler(this);
		glfwSetScrollCallback(id, sh);
	}
	private void createContext() {
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
		System.out.println("OpenGL V" + GL11.glGetInteger(GL30.GL_MAJOR_VERSION) + "." + GL11.glGetInteger(GL30.GL_MINOR_VERSION) + " Initialized");
		glEnable(GL11.GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	public int getWidth() {
		IntBuffer width = IntBuffer.allocate(1);
		IntBuffer height = IntBuffer.allocate(1);
		GLFW.glfwGetWindowSize(id, width, height);
		return width.get();
	}
	public int getHeight() {
		IntBuffer width = IntBuffer.allocate(1);
		IntBuffer height = IntBuffer.allocate(1);
		GLFW.glfwGetWindowSize(id, width, height);
		return height.get();
	}
	public void setPosition(int x, int y) {
		GLFW.glfwSetWindowPos(id, x, y);
	}
	public void keyPress(int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS || action == GLFW_RELEASE || action == GLFW_REPEAT) {
			events.add(new Event(key, action, Event.KEYBOARD));
		}
	}
	public void mouseClick(int button, int action, int mods) {
		if (action == GLFW_PRESS) {
			events.add(new Event(button, action, Event.MOUSE_BUTTON));
		}
	}
	public void mouseScroll(double xoffset, double yoffset) {
		events.add(new Event(xoffset, yoffset));
	}
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(id) == GL_TRUE;
	}
	public void update() {
		glfwSwapBuffers(id);
		glfwPollEvents();
	}
	public void destroy() {
		glfwSetWindowShouldClose(id, GL_TRUE);
		glfwDestroyWindow(id);
		kh.release();
		mh.release();
		sh.release();
	}
}
