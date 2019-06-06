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
		huffman.buildFrequencyTable(inputData);
		huffman.buildHuffmanTree();
		huffman.buildHuffmanTable();

		outputStream.write('A');
		outputStream.write('U');
		outputStream.write('D');
		
		BitOutputStream saver = new BitOutputStream(outputStream);
		huffman.saveHuffmanTree(saver);
		saver.close();
		
		outputStream.write(inputData.length);
		
		ByteArrayInputStream input = new ByteArrayInputStream(inputData);
		
		BitOutputStream output = new BitOutputStream(outputStream);
		huffman.compress(input, output);
		output.close();
	}
	/**
	 * Decompress the inputStream and write the result to the outputStream.
	 */
	public void decompress(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
		HuffmanCodes huffman = new HuffmanCodes();
		char a = (char) inputStream.read();
		char u = (char) inputStream.read();
		char d = (char) inputStream.read();
		if(a == 'A' && u == 'U' && d == 'D') {
			BitInputStream loader = new BitInputStream(inputStream);
			huffman.loadHuffmanTree(loader);
			loader.close();
			
			int bytesToRead = (int) inputStream.read();
			if(bytesToRead == -1) throw new EncodingException("inputStream has suddenly ended!");
			
			BitInputStream compressedData = new BitInputStream(inputStream);
			huffman.decompress(compressedData, outputStream, bytesToRead);
			compressedData.close();
		}
		else throw new EncodingException("wrong format!");
	}
}
