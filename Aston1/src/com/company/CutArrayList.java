package com.company;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Resizable list with QuickSort based on array.
 * <p>
 * The array is used to place elements in the list.
 * The size of the array can increase by <i>MULTIPLIER</i> if the limit of its capacity is reached.
 * <p>
 *
 * @param <T> the Type of elements in this list
 * @author Potapov Nikita
 */
public class CutArrayList<T> {
    private T[] array;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MULTIPLIER = 2;
    private int lastPosition = 0;

    /**
     * Constructs an empty list with an initial capacity of DEFAULT_CAPACITY.
     */
    public CutArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs an empty list with an initial capacity of capacity.
     *
     * @param capacity the initial capacity of the list
     * @throws IllegalArgumentException - if the specified initial capacity is wrong
     */
    public CutArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity is wrong: " + capacity);
        }
        this.array = (T[]) new Object[capacity];
    }

    /**
     * Adds the specified element to the end of this list.
     *
     * @param element - to be appended to list.
     */
    public void add(T element) {
        if (lastPosition >= array.length) {
            growArray();
        }
        array[lastPosition] = element;
        lastPosition++;
    }

    /**
     * Inserts an item into the specified position in the list according to the index.
     * Shifts the element currently at that position to the right.
     * If insert in last position of the list. Adding new element to end of list.
     *
     * @param index   - position to insertion
     * @param element - to be inserted
     * @throws IndexOutOfBoundsException - if index is wrong
     */
    public void addToIndex(int index, T element) {
        if (index == lastPosition) {
            add(element);
            return;
        }
        checkBounds(index);
        if (lastPosition + 1 >= array.length) {
            growArray();
        }
        System.arraycopy(array, index, array, index + 1, lastPosition - index);
        array[index] = element;
        lastPosition++;
    }

    /**
     * Replaces the element at the position in this list with the new element.
     *
     * @param index   - position of element
     * @param element - the new element
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException - if index is wrong
     */
    public T set(int index, T element) {
        checkBounds(index);
        T oldElement = array[index];
        array[index] = element;
        return oldElement;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts all the following elements to the left.
     *
     * @param index - of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException - if index is wrong
     */
    public T remove(int index) {
        checkBounds(index);
        T element = array[index];
        System.arraycopy(array, index + 1, array, index, lastPosition - index - 1);
        lastPosition--;
        array[lastPosition] = null;
        return element;
    }



    /**
     * Returns the element at the position in this list.
     *
     * @param index - of the element
     * @return - the element at the position in this list
     * @throws IndexOutOfBoundsException - if index is wrong
     */
    public T get(int index) {
        checkBounds(index);
        return array[index];
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        Arrays.fill(array, null);
        lastPosition = 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return - the number of elements in this list.
     */
    public int size() {
        return lastPosition;
    }

    /**
     * Sorts the list according to the order induced by the comparator.
     *
     * @param comparator - used to compare list elements
     */
    public void sort(Comparator<? super T> comparator) {
        QuickSort<T> quickSort = new QuickSort<>();
        quickSort.sort(array, lastPosition, comparator);
    }



    /**
     * Resize base array.
     * Create the new array with new capacity. Copy all elements to the new array.
     */
    private void growArray() {
        long newCapacity = array.length * MULTIPLIER;

        if (newCapacity > Integer.MAX_VALUE) {
            newCapacity = Integer.MAX_VALUE;
        }

        T[] newArray = (T[]) new Object[(int) newCapacity];
        System.arraycopy(this.array, 0, newArray, 0, array.length);
        this.array = newArray;
    }

    /**
     *checks if element have go out of borders
     *
     * @param index - of element which checks
     */
    private void checkBounds(int index) {
        if (index < 0 || index >= lastPosition) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, lastPosition));
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, 0, lastPosition));
    }
}