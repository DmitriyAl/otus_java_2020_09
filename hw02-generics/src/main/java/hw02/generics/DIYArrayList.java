package hw02.generics;

import java.util.*;

public class DIYArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 2;
    private Object[] elements;
    private int size;

    public DIYArrayList() {
        elements = new Object[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Object[] toArray() {
        Object[] toReturn = new Object[size];
        if (size >= 0) System.arraycopy(elements, 0, toReturn, 0, size);
        return toReturn;
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size++] = e;
        return true;
    }

    private void increaseCapacity() {
        Object[] temp = new Object[(int) (size * 1.5)];
        for (int i = 0; i < elements.length; i++) {
            temp[i] = elements[i];
        }
        elements = temp;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E toReturn = (E) elements[index];
        elements[index] = element;
        return toReturn;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DIYListIterator();
    }

    @Override
    public String toString() {
        if (size > 0) {
            StringBuilder sb = new StringBuilder(elements[0].toString());
            for (int i = 1; i < size; i++) {
                sb.append("; ");
                sb.append(elements[i].toString());
            }
            return sb.toString();
        }
        return "List is empty";
    }

    private class DIYListIterator implements ListIterator<E> {
        int index = -1;

        @Override
        public boolean hasNext() {
            return index + 1 < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) elements[++index];
        }

        @Override
        public void set(E e) {
            if (index < 0) {
                throw new IndexOutOfBoundsException("Iterator didn't return any values");
            }
            DIYArrayList.this.set(index, e);
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
