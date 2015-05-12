package com.monolc.spire.program;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.spire.window.Event;
import com.monolc.spire.window.Window;

public class Program {
	public static void main(String[] args) {
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		Window w = new Window();
		w.setGLMajor(4);
		w.setGLMinor(0);
		w.setTitle("Test");
		w.create();
		while (!w.shouldClose()) {
			w.update();
			while (w.eventsToQuery()) {
				Event e = w.queryEvent();
				switch (e.getType()){
					case Event.KEY_PRESS:
						System.out.println(e.getKey()+" was pressed.");
						break;
					case Event.KEY_RELEASE:
						break;
					case Event.KEY_REPEAT:
						break;
					case Event.MOUSE_CLICK:
						break;
					case Event.MOUSE_RELEASE:
						break;
					case Event.MOUSE_SCROLL:
						break;
				}
			}
			//game loop
		}
		w.destroy();
	}
}
