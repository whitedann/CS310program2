package edu.sdsu.cs.datastructures;

import java.util.*;

public class BalancedMap<K extends Comparable<K>,V> implements IMap<K,V> {

    private TreeMap<K,V> contents;

    public BalancedMap(IMap<K,V> source){
        this();
        contents = new TreeMap<>();
        for(K key : source.keyset()){
            this.contents.put(key, source.getValue(key));
        }
    }

    public BalancedMap(){
        contents = new TreeMap<>();
    }

    @Override
    public boolean contains(K key) {
        return contents.containsKey(key);
    }

    @Override
    public boolean add(K key, V value) {
        if(contents.containsKey(key))
            return false;
        else{
            contents.put(key, value);
            return true;
        }
    }

    @Override
    public V delete(K key) {
        if(contents.containsKey(key))
            return contents.remove(key);
        else{
            return null;
        }
    }

    @Override
    public V getValue(K key) {
        if(contents.containsKey(key)){
            return contents.get(key);
        }
        else
            return null;
    }

    @Override
    public K getKey(V value) {
        for(K key : contents.keySet()){
            if(contents.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        Collection<K> keys = new LinkedList<>();
        for(K key : contents.keySet()){
            if(contents.get(key).equals(value)){
                keys.add(key);
            }
        }
        return keys;
    }

    @Override
    public int size() {
        return contents.size();
    }

    @Override
    public boolean isEmpty() {
        if(contents.isEmpty())
            return true;
        return false;
    }

    @Override
    public void clear() {
        contents = new TreeMap<>();
    }

    @Override
    public Iterable<K> keyset() {
        Iterable<K> out = contents.keySet();
        return out;
    }

    @Override
    public Iterable<V> values() {
        Collection<V> values = new LinkedList<>();
        for(K key : contents.keySet()){
            values.add(contents.get(key));
        }
        return values;
    }
}
