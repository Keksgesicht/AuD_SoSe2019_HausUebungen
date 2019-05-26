package lab;

/**
 * Aufgabe H1
 * 
 * Abgabe von: <name>, <name> und <name>
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

import frame.BitInputStream;
import frame.BitOutputStream;
import frame.TreeNode;

public class HuffmanCodes {
	
	private double[] frequencyTable;
	private ArrayList<Integer>[] codeTable;
	private TreeNode huffmanTreeRoot;
	private TreeNode huffmanTreeNil;
	
	public HuffmanCodes() {
		huffmanTreeNil = new TreeNode();
		huffmanTreeRoot = huffmanTreeNil;
	}
	
	public double[] getFrequencyTable() {
		return frequencyTable;
	}
	
	public ArrayList<Integer>[] getHuffmanTable() {
		return codeTable;
	}
	
	/**
	 * Write the frequency of every byte in data into frequencyTable.
	 */
	public void buildFrequencyTable(byte[] data) {
		// TODO
	}
	
	/**
	 * Build a tree that defines the huffman code.
	 */
	public void buildHuffmanTree() {
		// TODO
	}
	
	/**
	 * Build a table that gives the huffman code for each byte. Write that table to codeTable.
	 */
	public void buildHuffmanTable() {
		// TODO
	}
	
	/**
	 * Compress the inputStream using codeTable.
	 */
	public void compress(ByteArrayInputStream inputStream, BitOutputStream outputStream) {
		// TODO
	}
	
	/**
	 * Decompress the bytesToRead many bytes from the inputStream. Use the Huffman tree for decoding.
	 */
	public void decompress(BitInputStream inputStream, ByteArrayOutputStream outputStream, int bytesToRead) {
		// TODO
	}
	
	/**
	 * Save the Huffman tree as bitstream.
	 */
	public void saveHuffmanTree(BitOutputStream stream) {
		// TODO
	}
	
	/**
	 * Load the Huffman tree from the bitstream.
	 */
	public void loadHuffmanTree(BitInputStream stream) {
		// TODO
	}
	
}
