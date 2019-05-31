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
		frequencyTable = new double[256];
		
		for(byte b : data) {
			frequencyTable[b + 128]++;
		}
		for(double d : frequencyTable) {
			d /= data.length;
		}
	}
	
	/**
	 * Build a tree that defines the huffman code.
	 */
	public void buildHuffmanTree() {
		PriorityQueue<TreeNode> prioBytes = new PriorityQueue<TreeNode>();
		for(short i=0; i < frequencyTable.length; i++) {
			TreeNode tn = new TreeNode();
			tn.frequency = frequencyTable[i];
			tn.value = (byte) (i - 128);
			prioBytes.add(tn);
		}
		while(prioBytes.size() > 1) {
			TreeNode first = prioBytes.remove();
			TreeNode second = prioBytes.remove();
			
			TreeNode newParent = new TreeNode(first, second, null, first.frequency + second.frequency, (byte) 0);
			prioBytes.add(newParent);
		}
		huffmanTreeRoot = prioBytes.remove();
	}
	
	/**
	 * Build a table that gives the huffman code for each byte. Write that table to codeTable.
	 */
	public void buildHuffmanTable() {
		codeTable = new ArrayList[256];
		buildHuffmanTable(huffmanTreeRoot, new ArrayList<Integer>());
	}
	
	private void buildHuffmanTable(TreeNode tn, ArrayList<Integer> path) {
		if(tn.left == null && tn.right == null)
			codeTable[tn.value + 128] = (ArrayList<Integer>) path.clone();
		else {
			if(tn.left != null) {
				path.add(0);
				buildHuffmanTable(tn.left, path);
				path.remove(path.size() -1);
			}
			if(tn.right != null) {
				path.add(1);
				buildHuffmanTable(tn.right, path);
				path.remove(path.size() -1);
			}
		}
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
