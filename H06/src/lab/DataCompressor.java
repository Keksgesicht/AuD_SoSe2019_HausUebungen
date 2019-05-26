package lab;

/**
 * Aufgabe H1
 * 
 * Abgabe von: <name>, <name> und <name>
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import frame.BitInputStream;
import frame.BitOutputStream;
import frame.EncodingException;

public class DataCompressor {
	
	/**
	 * Compress the inputData using HuffmanCodes and write the output to the outputStream.
	 */
	public void compress(byte[] inputData, ByteArrayOutputStream outputStream) {
		HuffmanCodes huffman = new HuffmanCodes();
		// TODO
	}
	/**
	 * Decompress the inputStream and write the result to the outputStream.
	 */
	public void decompress(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
		HuffmanCodes huffman = new HuffmanCodes();
		// TODO
	}
}
