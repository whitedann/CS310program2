package edu.sdsu.cs.datastructures;

import javax.sound.sampled.Line;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K,V>{

    private int currentSize;
    private Node root;
    private Keys keySet;
    private LinkedList<K> keys;

    public UnbalancedMap(IMap<K,V> source){
        //TODO
        this.root = null;
        this.currentSize = 0;
    }

    public UnbalancedMap(){
        this.root = null;
        this.currentSize = 0;
        this.keySet = new Keys();
        this.keys = new LinkedList<>();
    }

    private class Keys{
        public LinkedList<K> list = new LinkedList<>();
        public Iterable<K> keys = list;

        public K add(K key){
            list.add(key);
            keys = list;
            return key;
        }

        public Iterable<K> getKeys(){
            return this.keys;
        }

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
        Node parentNodeToDelete = findNodeToDelete(key);

        /** No Children **/
        if(parentNodeToDelete.left == null && parentNodeToDelete.right == null){
            if(parentNodeToDelete.left.key == key)
                parentNodeToDelete.left = null;
            if(parentNodeToDelete.right.key == key)
                parentNodeToDelete.right = null;
        }
        /** One Child **/
        if(parentNodeToDelete.left != null || parentNodeToDelete.right != null){
            if(parentNodeToDelete.left != null){
                parentNodeToDelete.left
            }
        }

        return null;
    }

    private Node findNodeToDelete(K key){
        Node nodeToDelete = root;
        if(nodeToDelete == null){
            return null;
        }

        while(nodeToDelete != null) {
            if(nodeToDelete.left != null) {
                if(nodeToDelete.left.key.compareTo(key) == 0)
                    return nodeToDelete;
            }
            if(nodeToDelete.right != null){
                if(nodeToDelete.right.key.compareTo(key) == 0)
                    return nodeToDelete;
            }
            if(nodeToDelete.key.compareTo(key) < 0){
                nodeToDelete = nodeToDelete.right;
            }
            if(nodeToDelete.key.compareTo(key) > 0){
                nodeToDelete = nodeToDelete.left;
            }
        }
        return nodeToDelete;

    }

    @Override
    public V getValue(K key) {
        Node currentNode = root;
        if(currentNode == null) {
            return null;
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
        //Todo
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        //Todo
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
        Collections.sort(keys);
        return this.keys;
       /**
       preOrderTraverse(root);
       return keySet.getKeys();
        **/
    }

    public void preOrderTraverse(Node e){
        if(e != null){
            keySet.add(e.key);
            preOrderTraverse(e.left);
            preOrderTraverse(e.right);
        }
    }

    @Override
    public Iterable<V> values() {
        //Todo
        return null;
    }
}
