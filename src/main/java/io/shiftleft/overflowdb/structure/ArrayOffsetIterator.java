package io.shiftleft.overflowdb.structure;

import java.util.Iterator;

class ArrayOffsetIterator<T> implements Iterator<T> {
  private final Object[] array;
  private int current;
  private final int exclusiveEnd;
  private final int strideSize;

  ArrayOffsetIterator(Object[] array, int begin, int exclusiveEnd, int strideSize) {
    this.array = array;
    this.current = begin;
    this.exclusiveEnd = exclusiveEnd;
    this.strideSize = strideSize;
  }

  @Override
  public boolean hasNext() {
    return current < exclusiveEnd;
  }

  @Override
  public T next() {
    T element = (T) array[current];
    current += strideSize;
    return element;
  }
}
