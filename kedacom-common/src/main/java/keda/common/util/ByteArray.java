package keda.common.util;

public class ByteArray {
	private byte[] buffer;
	private int size = 0;
	private int capacityIncrement;
	
	public ByteArray(int initialCapacity, int capacityIncrement) {
		buffer = new byte[initialCapacity];
		this.capacityIncrement = capacityIncrement;
	}
	
	private void prepareBuffer(int extendSize) {
		if(size + extendSize > buffer.length) {
			byte[] newBuffer = new byte[buffer.length + capacityIncrement];
			System.arraycopy(buffer, 0, newBuffer, 0, size);
			buffer = newBuffer;
		}
	}
	
	public void append(byte data) {
		prepareBuffer(1);
		buffer[size++] = data;
	}
	
	public void append(byte [] data, int length) {
		prepareBuffer(length);
		System.arraycopy(data, 0, buffer, size, length);
		size += length;
	}
	
	public byte[] toArray() {
		byte [] res = new byte[size];
		System.arraycopy(buffer, 0, res, 0, size);
		
		return res;
	}
	
	public void reset() {
		size = 0;
	}
}
