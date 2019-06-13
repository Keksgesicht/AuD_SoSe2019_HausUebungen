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
		while(x != _nil) {
			count++;
			x = x.next();
		}
		return count;
	}
	
	/**
	 * Insert an entry into the linked list at the position before the given node.
	 */
	public void insertBefore(TableEntry entry, ListNode node) {
		ListNode newNode = new ListNode(entry, node.prev(), node);
		node.prev().setNext(newNode);
		node.setPrev(newNode);
		if(node == head())
			_head = newNode;		
	}
	
	/**
	 * Append an entry to the end of the list.
	 */
	public void append(TableEntry entry) {
		if(head() == nil()) {
			_head = new ListNode(entry, _nil, _nil);
			return;
		}
		ListNode iter = head();
		while(iter.next() != _nil) {
			iter = iter.next();
		}
		iter.setNext(new ListNode(entry, iter, _nil));
	}
	
	/**
	 * Delete the given node from the linked list.
	 */
	public void delete(ListNode node) {
		node.prev().setNext(node.next());
		node.next().setPrev(node.prev());
		if(node == _head)
			_head = node.next();
	}
}
