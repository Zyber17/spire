package com.monolc.spire.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

public class VBO {
	int id;
	public VBO() {
		id = GL15.glGenBuffers();
		bind();
	}
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
	}
	public void setData(float[] data) {
		bind();
		FloatBuffer dataBuf = (ByteBuffer.allocateDirect(data.length * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer());
		dataBuf.put(data).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,  dataBuf, GL15.GL_STATIC_DRAW);
	}
	public void destroy(){
		GL15.glDeleteBuffers(id);
	}
}
