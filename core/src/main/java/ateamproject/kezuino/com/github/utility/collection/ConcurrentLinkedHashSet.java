/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.utility.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * Preservers insert order in a thread-safe manner.
 *
 * @author Kez and Jules
 */
public class ConcurrentLinkedHashSet<E extends Object> extends LinkedHashSet<E> {
    public ConcurrentLinkedHashSet(int i, float f) {
        super(i, f);
    }

    public ConcurrentLinkedHashSet(int i) {
        super(i);
    }

    public ConcurrentLinkedHashSet() {
        super();
    }

    public ConcurrentLinkedHashSet(Collection<? extends E> collection) {
        super(collection);
    }

    public ConcurrentLinkedHashSet(E[] array) {
        super(Arrays.asList(array));
    }

    @Override
    public synchronized boolean add(E e) {
        return super.add(e);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    /**
     * Thread-safe foreach iteration.
     *
     * @param consumer {@link Consumer} to run for each {@link E}.
     */
    public synchronized void safeForEach(Consumer<E> consumer) {
        for (int i = this.size() - 1; i >= 0; i--) {
            E e = this.get(i);
            consumer.accept(e);
        }
    }

    public synchronized E get(int i) {
        return super.stream().filter(x -> this.indexOf(x) == i).findFirst().orElse(null);
    }

    /**
     * Finds the index of {@link E} in this {@link ConcurrentLinkedHashSet}. Returns the index if found, -1 if not.
     *
     * @param e {@link E} to search in this {@link ConcurrentLinkedHashSet}.
     * @return Index if found, -1 if not.
     */
    public synchronized int indexOf(E e) {
        int index = 0;

        for (Object x : super.toArray()) {
            if (x.equals(e)) {
                return index;
            }
            index++;
        }

        return -1;
    }
}
