package com.gmail.npnster.first_project;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class RailsApiQue {
	private LinkedList<RailsApiQueEntry<?,?>> mQue;
	
	public RailsApiQue() {
		mQue = new LinkedList<RailsApiQueEntry<?,?>>();
	}

	public Iterator<RailsApiQueEntry<?, ?>> iterator() {
		return mQue.iterator();
	}

	public boolean containsAll(Collection<?> collection) {
		return mQue.containsAll(collection);
	}

	public boolean isEmpty() {
		return mQue.isEmpty();
	}

	public void add(int location, RailsApiQueEntry<?, ?> object) {
		mQue.add(location, object);
	}

	public boolean add(RailsApiQueEntry<?, ?> object) {
		return mQue.add(object);
	}

	public boolean removeAll(Collection<?> collection) {
		return mQue.removeAll(collection);
	}

	public boolean addAll(int location, Collection<? extends RailsApiQueEntry<?, ?>> collection) {
		return mQue.addAll(location, collection);
	}

	public boolean retainAll(Collection<?> collection) {
		return mQue.retainAll(collection);
	}

	public boolean addAll(Collection<? extends RailsApiQueEntry<?, ?>> collection) {
		return mQue.addAll(collection);
	}

	public void addFirst(RailsApiQueEntry<?, ?> object) {
		mQue.addFirst(object);
	}

	public void addLast(RailsApiQueEntry<?, ?> object) {
		mQue.addLast(object);
	}

	public void clear() {
		mQue.clear();
	}

	public Object clone() {
		return mQue.clone();
	}

	public String toString() {
		return mQue.toString();
	}

	public boolean contains(Object object) {
		return mQue.contains(object);
	}

	public boolean equals(Object object) {
		return mQue.equals(object);
	}

	public RailsApiQueEntry<?, ?> get(int location) {
		return mQue.get(location);
	}

	public RailsApiQueEntry<?, ?> getFirst() {
		return mQue.getFirst();
	}

	public int hashCode() {
		return mQue.hashCode();
	}

	public RailsApiQueEntry<?, ?> getLast() {
		return mQue.getLast();
	}

	public int indexOf(Object object) {
		return mQue.indexOf(object);
	}

	public int lastIndexOf(Object object) {
		return mQue.lastIndexOf(object);
	}

	public ListIterator<RailsApiQueEntry<?, ?>> listIterator(int location) {
		return mQue.listIterator(location);
	}

	public ListIterator<RailsApiQueEntry<?, ?>> listIterator() {
		return mQue.listIterator();
	}

	public RailsApiQueEntry<?, ?> remove(int location) {
		return mQue.remove(location);
	}

	public boolean remove(Object object) {
		return mQue.remove(object);
	}

	public RailsApiQueEntry<?, ?> removeFirst() {
		return mQue.removeFirst();
	}

	public RailsApiQueEntry<?, ?> removeLast() {
		return mQue.removeLast();
	}

	public Iterator<RailsApiQueEntry<?, ?>> descendingIterator() {
		return mQue.descendingIterator();
	}

	public List<RailsApiQueEntry<?, ?>> subList(int start, int end) {
		return mQue.subList(start, end);
	}

	public boolean offerFirst(RailsApiQueEntry<?, ?> e) {
		return mQue.offerFirst(e);
	}

	public boolean offerLast(RailsApiQueEntry<?, ?> e) {
		return mQue.offerLast(e);
	}

	public RailsApiQueEntry<?, ?> peekFirst() {
		return mQue.peekFirst();
	}

	public RailsApiQueEntry<?, ?> peekLast() {
		return mQue.peekLast();
	}

	public RailsApiQueEntry<?, ?> pollFirst() {
		return mQue.pollFirst();
	}

	public RailsApiQueEntry<?, ?> pollLast() {
		return mQue.pollLast();
	}

	public RailsApiQueEntry<?, ?> pop() {
		return mQue.pop();
	}

	public void push(RailsApiQueEntry<?, ?> e) {
		mQue.push(e);
	}

	public boolean removeFirstOccurrence(Object o) {
		return mQue.removeFirstOccurrence(o);
	}

	public boolean removeLastOccurrence(Object o) {
		return mQue.removeLastOccurrence(o);
	}

	public RailsApiQueEntry<?, ?> set(int location, RailsApiQueEntry<?, ?> object) {
		return mQue.set(location, object);
	}

	public int size() {
		return mQue.size();
	}

	public boolean offer(RailsApiQueEntry<?, ?> o) {
		return mQue.offer(o);
	}

	public RailsApiQueEntry<?, ?> poll() {
		return mQue.poll();
	}

	public RailsApiQueEntry<?, ?> remove() {
		return mQue.remove();
	}

	public RailsApiQueEntry<?, ?> peek() {
		return mQue.peek();
	}

	public RailsApiQueEntry<?, ?> element() {
		return mQue.element();
	}

	public Object[] toArray() {
		return mQue.toArray();
	}

	public <T> T[] toArray(T[] contents) {
		return mQue.toArray(contents);
	}

}