package com.monolc.spire.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;

public class VAO {
	int id;
	public VAO() {
		id = GL30.glGenVertexArrays();
		bind();
	}
	public void bind() {
		GL30.glBindVertexArray(id);
	}
}
