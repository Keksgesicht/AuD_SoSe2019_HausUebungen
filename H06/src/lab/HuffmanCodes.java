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
			first.p = newParent;
			second.p = newParent;
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
	
	/**
	 * @param leaf possible leaf
	 * @return whether the TreeNode leaf is a leaf in the tree
	 */
	private boolean isLeaf(TreeNode leaf) {
		return leaf.left == null && leaf.right == null;
	}
	
	private void buildHuffmanTable(TreeNode tn, ArrayList<Integer> path) {
		if(isLeaf(tn))
			codeTable[tn.value + 128] = (ArrayList<Integer>) path.clone(); // the ArrayList of each Byte has to be individually
		else {
			// turn left
			path.add(0);
			buildHuffmanTable(tn.left, path);
			path.remove(path.size() -1);		// without cloning all ArrayLists would be empty
			// turn right
			path.add(1);
			buildHuffmanTable(tn.right, path);
			path.remove(path.size() -1);		// without cloning all ArrayLists would be empty
		}
	}
	
	/**
	 * Compress the inputStream using codeTable.
	 */
	public void compress(ByteArrayInputStream inputStream, BitOutputStream outputStream) {
		int b;
		while((b = inputStream.read()) != -1) {
			for(Integer bit : codeTable[b]) {
				outputStream.write(bit);
			}
		}
	}
	
	/**
	 * Decompress the bytesToRead many bytes from the inputStream. Use the Huffman tree for decoding.
	 */
	public void decompress(BitInputStream inputStream, ByteArrayOutputStream outputStream, int bytesToRead) {
		TreeNode tnBit = huffmanTreeRoot;
		int b;
		while((b = inputStream.read()) != -1) {
			if(b == 0) {
				tnBit = tnBit.left;
			} else {
				tnBit = tnBit.right;
			}
			if(isLeaf(tnBit)) {
				outputStream.write(tnBit.value);
				tnBit = huffmanTreeRoot;
			}	
		}
	}
	
	/**
	 * Save the Huffman tree as bitstream.
	 */
	public void saveHuffmanTree(BitOutputStream stream) {
		treeToStream(stream, huffmanTreeRoot);
	}
	
	/**
	 * @see https://stackoverflow.com/questions/759707/efficient-way-of-storing-huffman-tree#answer-759766
	 * @param stream
	 * @param bit
	 */
	private void treeToStream(BitOutputStream stream, TreeNode tnBit) {
		if(isLeaf(tnBit)) {
			stream.write(1);	// a 1 
			stream.writeByte(tnBit.value);
		} else {
			stream.write(0);
			treeToStream(stream, tnBit.left);
			treeToStream(stream, tnBit.right);
		}
	}
	
	/**
	 * Load the Huffman tree from the bitstream.
	 */
	public void loadHuffmanTree(BitInputStream stream) {
		huffmanTreeRoot = streamToTree(stream);
	}
	
	/**
	 * @see https://stackoverflow.com/questions/759707/efficient-way-of-storing-huffman-tree#answer-759766
	 * @param stream
	 * @return
	 */
	private TreeNode streamToTree(BitInputStream stream) {
		int b = stream.read();
		TreeNode newNode = new TreeNode();
		
		if(b == 1) {
			newNode.value = stream.readByte();
		} else {
			newNode.left = streamToTree(stream);
			newNode.left.p = newNode;
			
			newNode.right = streamToTree(stream);
			newNode.right.p = newNode;
		}
		return newNode;
	}
	
}
