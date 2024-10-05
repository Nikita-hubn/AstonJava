package com.company;

import java.util.Comparator;

/**
 * Realization of QuickSort
 *
 * @param <T> the Type of elements in this list
 */
public class QuickSort<T> {
    /**
     * Sorts the list according to the order induced by the comparator.
     *
     *
     * @param comparator - used to compare list elements
     * @throws NullPointerException - if the list contains (@Code null) elements
     */
    public void sort(Object[] array, int size, Comparator<? super T> comparator) {
        QuickSort( array,0, size - 1, comparator);
    }
    /**
     * Realisation of QuickSort.
     *
     * @param low        - bound of array
     * @param high       - bound of array
     * @param comparator - used to compare list elements
     * @throws NullPointerException - if the list contains (@Code null) elements
     */
    private void QuickSort(Object[] array,int low, int high, Comparator comparator) {
        if (low >= high) {
            throw new NullPointerException();
        }
        int middle = low + (high - low) / 2;
        T opor = (T) array[middle];

        int leftBound = low;
        int rightBound = high;
        while (leftBound <= rightBound) {
            while (comparator.compare(array[leftBound], opor) < 0) {
                leftBound++;
            }
            while (comparator.compare(array[rightBound], opor) > 0) {
                rightBound--;
            }
            if (leftBound <= rightBound) {
                T temp = (T) array[leftBound];
                array[leftBound] = array[rightBound];
                array[rightBound] = temp;
                leftBound++;
                rightBound--;
            }
        }
        if (low < rightBound) {
            QuickSort(array,low, rightBound, comparator);
        }
        if (high > leftBound) {
            QuickSort(array,leftBound, high, comparator);
        }
    }
   
}
