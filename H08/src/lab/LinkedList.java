package lab;

/**
 * Aufgabe H1 a)
 * 
 * @author Emre Berber 2957148
 * @author Christoph Berst 2743394
 * @author Jan Braun 2768531
 */

import frame.ListNode;
import frame.TableEntry;

public class LinkedList {
	
	private ListNode _head;
	private ListNode _nil;
	
	public LinkedList() {
		_nil = new ListNode(null, null, null);
		_nil.setNext(_nil);
		_nil.setPrev(_nil);
		_head = _nil;
	}
	
	public ListNode head() {
		return _head;
	}
	
	public ListNode nil() {
		return _nil;
	}
	
	/**
	 * Return the number of elements in the linked list.
	 */
	public int length() {
		int count = 0;
		ListNode x = _head;
		while(x != _nil) {	// nil is the end of the list
			count++;		// counting the ListNodes
			x = x.next();	// walking through the list
		}
		return count;
	}
	
	/**
	 * Insert an entry into the linked list at the position before the given node.
	 */
	public void insertBefore(TableEntry entry, ListNode node) {
		ListNode newNode = new ListNode(entry, node.prev(), node);	// creating new Node
		node.prev().setNext(newNode);	// Aus der Sicht des vorangehenden Elements bekannt machen
		node.setPrev(newNode);			// Aus der Sicht des folgenden Elements bekannt machen
		if(node == head())				// Wenn wir vor dem head einfügen,
			_head = newNode;			// ist der neue Knoten der head. 
	}
	
	/**
	 * Append an entry to the end of the list.
	 */
	public void append(TableEntry entry) {
		if(head() == nil()) {							// no head?
			_head = new ListNode(entry, _nil, _nil);	// then make one
			return;
		}
		ListNode iter = head();
		while(iter.next() != _nil) {	// das letzte Element suchen
			iter = iter.next();
		}
		iter.setNext(new ListNode(entry, iter, _nil));	// an das letzte Element hinten dran hängen
	}
	
	/**
	 * Delete the given node from the linked list.
	 */
	public void delete(ListNode node) {
		node.prev().setNext(node.next());	// Aus der Sicht des vorangehenden Elements löschen
		node.next().setPrev(node.prev());	// Aus der Sicht des folgenden Elements löschen
		if(node == _head)					// if the head gets deleted
			_head = node.next();			// it's following node has to take the place
	}
}
