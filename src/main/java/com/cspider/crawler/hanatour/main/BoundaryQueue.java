package com.cspider.crawler.hanatour.main;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BoundaryQueue<E>  implements Queue<E> {

    private LinkedList<E> _elements = new LinkedList<E>();
    private int limit;

    public BoundaryQueue(int limit) {
        if (limit <= 0){
            throw new IllegalArgumentException("limit must be positive number");
        }
        this.limit = limit;
    }

    public int size() {
        return _elements.size();
    }

    @Override
    public int hashCode() {
        return _elements.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof  BoundaryQueue) ){
            return false;
        }else {
            return _elements.equals(((BoundaryQueue)obj)._elements);
        }
    }

    public boolean isEmpty() {
        return size() == 0 ? true : false;
    }

    public boolean contains(Object o) {
        return _elements.contains(o);
    }

    public Iterator<E> iterator() {
        return _elements.iterator();
    }

    public Object[] toArray() {
        return _elements.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return _elements.toArray(a);
    }

    public boolean add(E e) {
        if (size() == limit) {
            _elements.removeFirst();
        }
        _elements.add(e);
        return true;
    }

    public boolean remove(Object o) {
        return _elements.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return _elements.contains(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            return false;
        }else {
            for (E e : c) {
                add(e);
            }
            return true;
        }
    }

    public boolean removeAll(Collection<?> c) {
        return _elements.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return _elements.retainAll(c);
    }

    public void clear() {
        _elements.clear();
    }

    public boolean offer(E e) {
        if (size() == limit){
            _elements.removeFirst();
        }
        return _elements.offer(e);
    }

    public E remove() {
        return _elements.remove();
    }

    public E poll() {
        return _elements.poll();
    }

    @Deprecated
    public E element() {
        return _elements.element();
    }

    public E peek() {
        return _elements.peek();
    }
}
