package com.myking520.github.message;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class SimpleMessage implements IResponseMessage {
	private short actionId;
	// 消息状态
	private short state;
	private byte[] data = new byte[0];
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();

	public SimpleMessage(short actionId, short state) {
		super();
		this.actionId = actionId;
		this.state = state;
	}

	public short getActionId() {
		return actionId;
	}

	public short getState() {
		return state;
	}

	public byte[] getData() {
		return data;
	}

	public static void main(String[] args) {
		for (int i = 10000000; i < 10000000+100 ; i++) {
			byte[] b = int2bytes(i);
			long f = bytes2int(b);
			System.out.println(f);
		}
		System.out.println("========================");
		for (int i = -10000000; i < -10000000+ 100; i++) {
			byte[] b = int2bytes(i);
			long f = bytes2int(b);
			System.out.println(f);
		}
	}

	// value=53480640->000000011 00110000 00001100 11000000
	// 0xFF ->11111111
	// value>>0&0xFF->(000000011 00110000 00001100 11000000)&11111111
	// =>bytes[0]=11000000
	// value>>8&0xFF->(00000000 000000011 00110000 00001100)&11111111
	// =>bytes[1]=00001100
	// value>>16&0xFF->(00000000 00000000 000000011 00110000)&11111111
	// =>bytes[2]=00110000
	// value>>24&0xFF->(00000000 00000000 00000000 000000011)&11111111
	// =>bytes[3]=000000011
	private static byte[] int2bytes(int intv) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) ((intv >> (i * 8)) & 0xFF);
		}
		return bytes;
	}
	// bytes=[11000000,00001100,00110000,000000011]
	// value=00000000 00000000 00000000 00000000
	// value|=(bytes[0])&0xFF ->(00000000 00000000 00000000 00000000)|11000000 =>00000000 00000000 00000000 11000000
	// value|=(bytes[1]<<8)&0xFF00 ->(00000000 00000000 00000000 11000000)|00001100  00000000 =>00000000 00000000 00001100 11000000
	// value|=(bytes[2]<<16)&0xFF0000 ->(00000000 00000000 00001100 11000000)|00110000 00000000 00000000 =>00000000 00110000 00001100 11000000
	// value|=(bytes[3]<<24)&0xFF000000 ->(00000000 00110000 00001100 11000000)|000000011 00000000 00000000 =>000000011 00110000 00001100 11000000
	// value=000000011 00110000 00001100 11000000
	private static long[] tf = { 0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L,0xFF00000000L,0xFF0000000000L,0xFF000000000000L,0xFF00000000000000L };
	private static int bytes2int(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			value |= ((bytes[i] << (i * 8) & tf[i]));
		}
		return value;
	}
	
}
