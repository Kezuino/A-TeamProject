/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.utility.collection;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
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

    public ConcurrentLinkedHashSet(Collection<? extends E> clctn) {
        super(clctn);
    }
    
    @Override
    public synchronized boolean add(E e) {
        return super.add(e);
    }
    
    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }
    
    public synchronized E get(int i) {
        return super.stream().filter(x -> this.indexOf(x) == i).findFirst().orElse(null);
    }
    
    public synchronized int indexOf(E e) {
        int index = 0;
        
        for(Object x : super.toArray()) {
            if(x.equals(e)) {
                return index;
            }
            index++;
        }
        
        return -1;
    }
}
