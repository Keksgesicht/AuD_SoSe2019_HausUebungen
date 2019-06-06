package lab;

/**
 * Aufgabe H1
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
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
		huffman.buildFrequencyTable(inputData);			// FrequencyTable mit input Data erstellen
		huffman.buildHuffmanTree();						// HuffmanTree wird generiert
		huffman.buildHuffmanTable();					// HuffmanTree wird in HuffmanTable umgewandelt

		outputStream.write('A');						// Schreibe den ASCII code für A in den outputStream
		outputStream.write('U');						// Schreibe den ASCII code für U in den outputStream
		outputStream.write('D');						// Schreibe den ASCII code für D in den outputStream
		
		BitOutputStream saver = new BitOutputStream(outputStream);
		huffman.saveHuffmanTree(saver);					// Schreibe Repräsentation des HuffmanTrees in den outputStream
		saver.close();
		
		outputStream.write(inputData.length);			// Schreibe die Anzahl der zu komprimierenden Bytes in den outputStream
		
		ByteArrayInputStream input = new ByteArrayInputStream(inputData);
		BitOutputStream output = new BitOutputStream(outputStream);
		huffman.compress(input, output);				// Durchführung der Komprimierung, dabei wird die komprimierte Datei in den outputStream geschrieben
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
		if(a == 'A' && u == 'U' && d == 'D') {			// Überprüfen ob die Datei mit "AUD" beginnt
			BitInputStream loader = new BitInputStream(inputStream);
			huffman.loadHuffmanTree(loader);			// wandelt stream gespeicherten HuffmanTrees in Tree um
			loader.close();
			
			int bytesToRead = (int) inputStream.read();	// Liest die Anzahl der komprimierten Bytes aus dem inputStream
			if(bytesToRead == -1) throw new EncodingException("inputStream has suddenly ended!");
			
			BitInputStream compressedData = new BitInputStream(inputStream);
			huffman.decompress(compressedData, outputStream, bytesToRead);	// Durchführung der Dekomprimierung, dabei wird die dekomprimierte Datei in den outputStream geschrieben
			compressedData.close();
		}
		else throw new EncodingException("wrong format!");
	}
}
