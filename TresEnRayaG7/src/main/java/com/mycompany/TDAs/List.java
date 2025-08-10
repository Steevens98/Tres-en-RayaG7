package com.mycompany.TDAs;

import java.util.Comparator;

/**
 *
 * @author Usuario
 */
public interface List<E> extends Iterable<E> {

    boolean addFirst(E element);

    boolean addLast(E element);

    boolean removeFirst();

    boolean removeLast();
    
    E getFirst();

    E getLast();

    boolean insert(int index, E element);

    boolean contains(E element, Comparator<E> cmp);

    E get(int index);

    int indexOf(E element, Comparator<E> cmp);

    boolean isEmpty();

    E remove(int index);

    boolean remove(E element, Comparator<E> cmp);

    E set(int index, E element);

    int size();    

}
