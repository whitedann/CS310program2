package edu.sdsu.cs.datastructures;

import java.util.LinkedList;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K,V>{

    private int currentSize;
    private Node root;

    public UnbalancedMap(IMap<K,V> source){
        this.root = null;
        this.currentSize = 0;
    }

    public UnbalancedMap(){
        this.root = null;
        this.currentSize = 0;
    }

    private class Node{
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V val){
            this.key = key;
            this.value = val;
        }

        public int compareTo(Node otherNode) {
            return ((Comparable<K>)key).compareTo((K)otherNode.key);
        }
    }

    @Override
    public boolean contains(K key){
        if(root == null){
            return false;
        }

        Node currentNode = root;
        while(currentNode != null){
            if(currentNode.key.compareTo(key) == 0)
                return true;
            else if(currentNode.key.compareTo(key) > 0)
                currentNode = currentNode.left;
            else if(currentNode.key.compareTo(key) < 0)
                currentNode = currentNode.right;
        }
        return false;
    }

    @Override
    public boolean add(K key, V value) {
        if(root == null){
            root = new Node(key, value);
            currentSize++;
        }
        Node currentNode = root;
        int compareResult;
        while(currentNode != null){
            compareResult = key.compareTo(currentNode.key);
            if(compareResult < 0) {
                if(currentNode.left == null) {
                    currentNode.left = new Node(key, value);
                    currentSize++;
                    return true;
                }
                else
                    currentNode = currentNode.left;
            }
            else if(compareResult > 0) {
                if(currentNode.right == null) {
                    currentNode.right = new Node(key, value);
                    currentSize++;
                    return true;
                }
                else
                    currentNode = currentNode.right;
            }
            else if(compareResult == 0)
                return false;
        }
        return false;
    }

    @Override
    public V delete(K key) {
        //TODO needs to work on this
        return null;
    }

    @Override
    public V getValue(K key) {
        Node currentNode = root;
        if(currentNode == null) {
            System.out.println("0");
        }

        while(true){
            if(currentNode.key.compareTo(key) == 0)
                return currentNode.value;
            else if(currentNode.key.compareTo(key) > 0) {
                if(currentNode.left == null) {
                    return null;
                }
                else
                    currentNode = currentNode.left;
            }
            else if(currentNode.key.compareTo(key) < 0) {
                if(currentNode.right == null) {
                    return null;
                }
                else
                    currentNode = currentNode.right;
            }
        }
    }

    @Override
    public K getKey(V value) {
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        return null;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        if(currentSize == 0)
            return true;
        else
            return false;
    }

    @Override
    public void clear() {
        this.root = null;
        this.currentSize = 0;
    }

    @Override
    public Iterable<K> keyset() {
        LinkedList<K> keyset = new LinkedList<>();
        //preOrderTraverse(root, keyset);
        return keyset;
    }

    public void preOrderTraverse(Node e, LinkedList<K> list){
        if(e != null){
            preOrderTraverse(e.left, list);
            list.add(e.key);
            preOrderTraverse(e.right, list);
        }
    }

    @Override
    public Iterable<V> values() {
        return null;
    }
}
