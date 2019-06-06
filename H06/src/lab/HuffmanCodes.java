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
		frequencyTable = new double[256]; // offset 128 (In Arrays there are no negativ indezes)
		
		for(byte b : data) {
			frequencyTable[b + 128]++;	// offset 128 (Byte: between -128 and 127)
		}
		for(int i=0; i<frequencyTable.length; i++) {
			frequencyTable[i] /= data.length;
		}
	}
	
	/**
	 * Build a tree that defines the huffman code.
	 */
	public void buildHuffmanTree() {
		PriorityQueue<TreeNode> prioBytes = new PriorityQueue<TreeNode>(256);
		for(short i=0; i < frequencyTable.length; i++) {
			TreeNode tn = new TreeNode();
			tn.frequency = frequencyTable[i];
			tn.value = (byte) (i - 128);		// offset 128 (Byte: between -128 and 127)
			prioBytes.add(tn);
		}
		
		while(prioBytes.size() > 1) {
			TreeNode first = prioBytes.remove();	// entfernen wir die zwei zu diesem Zeitpunkt 
			TreeNode second = prioBytes.remove();	// kleinsten Knoten aus der Priority-Queue
			
			TreeNode newParent = new TreeNode(first, second, null, first.frequency + second.frequency, (byte) 0);	 
			first.p = newParent;		// erstellen einen neuen Knoten mit der Summe der Schlüssel der Kinder,
			second.p = newParent;		// der die beiden entfernten Knoten als Kinder hat,
			prioBytes.add(newParent);	// und fügen diesen wieder in die Priority-Queue ein.
		}
		huffmanTreeRoot = prioBytes.remove();		// ist nur ein Element in der Priority-Queue,
		huffmanTreeRoot.left.p = huffmanTreeRoot;	// ist dieses die Wurzel unseres Baumes
		huffmanTreeRoot.right.p = huffmanTreeRoot;
	}
	
	/**
	 * @param leaf possible leaf
	 * @return whether the TreeNode leaf is a leaf in the tree
	 */
	private boolean isLeaf(TreeNode leaf) {
		return (leaf.left == null);	// every TreeNode has two or zero children
	}
	
	/**
	 * Build a table that gives the huffman code for each byte. Write that table to codeTable.
	 */
	public void buildHuffmanTable() {
		codeTable = new ArrayList[256];
		buildHuffmanTable(huffmanTreeRoot, new ArrayList<Integer>());
	}
	
	/**
	 * @param tn der aktuelle Knoten
	 * @param path die Bitfolge, welche den Weg zum aktuellen Knoten darstellt
	 */
	private void buildHuffmanTable(TreeNode tn, ArrayList<Integer> path) {
		if(isLeaf(tn))
			codeTable[tn.value + 128] = (ArrayList<Integer>) path.clone(); // without cloning all ArrayLists would be empty
		else {
			// Falls wir im Pfad den linken Kindknoten auswählen, steht an dieser Stelle eine 0
			path.add(0);	
			buildHuffmanTable(tn.left, path);
			path.remove(path.size() -1); // the ArrayList of each Byte has to be individually
			
			// wenn wir den rechten Kindknoten auswählen, eine 1
			path.add(1);	
			buildHuffmanTable(tn.right, path);
			path.remove(path.size() -1); // the ArrayList of each Byte has to be individually
		}
	}
	
	/**
	 * Compress the inputStream using codeTable.
	 */
	public void compress(ByteArrayInputStream inputStream, BitOutputStream outputStream) {
		int b;
		while((b = inputStream.read()) != -1) {
			for(Integer bit : codeTable[b]) { 	// ersetzt das Byte mit seiner Bitfolge im HuffmanTable 
				outputStream.write(bit);		// schreibt die einzelnen Bit aus dem HuffmanTable
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
			if(b == 0) {	// Falls wir im Pfad den linken Kindknoten auswählen, steht an dieser Stelle eine 0
				tnBit = tnBit.left;
			} else {		// wenn wir den rechten Kindknoten auswählen, eine 1
				tnBit = tnBit.right;
			}
			if(isLeaf(tnBit)) {
				outputStream.write(tnBit.value);	// ersetzt Bitfolge durch das vollständige Byte
				tnBit = huffmanTreeRoot;			// die nächste Bitfolge beginnt bei der Wurzel
			}	
		}
	}
	
	/**
	 * Save the Huffman tree as bitstream.
	 */
	public void saveHuffmanTree(BitOutputStream stream) {
		treeToStream(stream, huffmanTreeRoot);	// beginnen wir von Wurzel aus den Baum zu lesen
	}
	
	/**
	 * @see https://stackoverflow.com/questions/759707/efficient-way-of-storing-huffman-tree#answer-759766
	 * @param stream tree as bitstream
	 * @param tnBit the current TreeNode to read/convert
	 */
	private void treeToStream(BitOutputStream stream, TreeNode tnBit) {
		if(isLeaf(tnBit)) {		// a leaf contains the value of the byte
			stream.write(1);	// a 1 representents a following data byte
			stream.writeByte(tnBit.value);	// the following data byte
		} else {
			stream.write(0);	// each step/node in the tree is represented as a 0
			treeToStream(stream, tnBit.left);	// walking through the tree to all left
			treeToStream(stream, tnBit.right);	// and right nodes
		}
	}
	
	/**
	 * Load the Huffman tree from the bitstream.
	 */
	public void loadHuffmanTree(BitInputStream stream) {
		huffmanTreeRoot = streamToTree(stream); // der im höhsten Rekursionsschritt erstellte Knoten ist die Wurzel
	}
	
	/**
	 * @see https://stackoverflow.com/questions/759707/efficient-way-of-storing-huffman-tree#answer-759766
	 * @param stream tree as bitstream
	 * @return den Kindknoten des im übergeordneten Rekursionsschrittes erstellten Knoten
	 */
	private TreeNode streamToTree(BitInputStream stream) {
		int b = stream.read();
		TreeNode newNode = new TreeNode();	// creating a new TreeNode for every Bit
		
		if(b == 1) {	// a 1 representents a following data byte 
			newNode.value = stream.readByte();	// a leaf contains the value of the byte
		} else {
			newNode.left = streamToTree(stream);	// recreating the tree with all left
			newNode.left.p = newNode;
			
			newNode.right = streamToTree(stream);	// and right nodes
			newNode.right.p = newNode;
		}
		return newNode;	// dieses Knoten ist ein Kind des im übergeordneten Rekursionsschrittes erstellten Knoten
	}
	
}
