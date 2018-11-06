/** Daniel White (CSSC0721) & Mario Shamhon (CSSC0781)**/
/** CS 310, Fall 2018, Shawn Healey **/

package edu.sdsu.cs.datastructures;

import java.util.Iterator;
import java.util.LinkedList;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K,V>{

    private int currentSize;
    private Node root;

    public UnbalancedMap(IMap<K,V> source){
        IteratorHelper builder = new IteratorHelper(source);
        while(builder.hasNext()){
            builder.makeNode();
        }
    }

    public UnbalancedMap(){
        this.root = null;
        this.currentSize = 0;
    }

    private class IteratorHelper{

        private Iterator<K> kiter;
        private Iterator<V> viter;

        public IteratorHelper(IMap<K,V> source){
            kiter = source.keyset().iterator();
            viter = source.values().iterator();
        }

        public boolean hasNext(){
            return kiter.hasNext() && viter.hasNext();
        }

        public void makeNode(){
            add(kiter.next(), viter.next());
        }

    }

    private class Node {
        public K key;
        public V value;
        public Node left, right, parent;

        public Node(K key, V val, Node parent) {
            this.key = key;
            this.value = val;
            this.parent = parent;
        }
    }

    //Finds node of the given key
    private Node findNode(K key){
        Node p = root;

        while(p != null){
            int cmp = key.compareTo(p.key);
            if(cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    //Finds the sucessory node to target node
    private Node predecessor(Node e){
        if(e == null)
            return null;
        else if(e.left != null){
            Node p = e.left;
            while(p.right != null)
                p = p.right;
            return p;
        }
        else {
            Node parent = e.parent;
            Node child = e;
            while(parent != null && child == parent.left){
                child = parent;
                if(parent.parent != null)
                    parent = parent.parent;
                else parent = root;
            }
            return parent;
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
            root = new Node(key, value, null);
            currentSize++;
        }
        Node currentNode = root;
        int compareResult;
        while(currentNode != null){
            compareResult = key.compareTo(currentNode.key);
            if(compareResult < 0) {
                if(currentNode.left == null) {
                    currentNode.left = new Node(key, value, currentNode);
                    currentSize++;
                    return true;
                }
                else
                    currentNode = currentNode.left;
            }
            else if(compareResult > 0) {
                if(currentNode.right == null) {
                    currentNode.right = new Node(key, value, currentNode);
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
        //Find Node to remove using key
        Node toRemove = findNode(key);
        if(toRemove == null){
            return null;
        }
        else
            currentSize--;
        V tempVal = toRemove.value;

        //Node has 0 children
        if(toRemove.right == null && toRemove.left == null) {
            if(toRemove == root){
                root = null;
                toRemove.parent = null;
            }
            else if(toRemove.parent.left == toRemove)
                toRemove.parent.left = null;
            else
                toRemove.parent.right = null;
            return tempVal;
        }
        //Node has 1 child
        else if(toRemove.left != null ^ toRemove.right != null){
            if(toRemove.left != null){
                 //link grandchild to grandparent
                 toRemove.left.parent = toRemove.parent;
                 //link grandparent to grandchild
                if(toRemove == root){
                    root = toRemove.left;
                    toRemove.parent = null;
                }
                else if(toRemove.parent.left == toRemove) {
                    toRemove.parent.left = toRemove.left;
                }
                else {
                    toRemove.parent.right = toRemove.left;
                }
            }
            else{
                toRemove.right.parent = toRemove.parent;
                 if(toRemove == root){
                     root = toRemove.right;
                     toRemove.parent = null;
                 }
                 else if(toRemove.parent.left == toRemove) {
                     toRemove.parent.left = toRemove.right;
                 }
                 else {
                     toRemove.parent.right = toRemove.right;
                 }
            }
             return tempVal;
        }
        /** The below section I got help from the following link:
         * * https://stackoverflow.com/questions/29871949/delete-a-node-from-a-
         * binary-search-tree
         *
         */
        //Node has 2 children
        else if(toRemove.left != null && toRemove.right != null){
            Node predecessor = predecessor(toRemove);
            predecessor.parent.left = null;
            predecessor.parent = null;
            Node parentOfTheNodeToDelete = toRemove.parent;

            predecessor.parent = parentOfTheNodeToDelete;
            Node rightOfNodeToDelete = toRemove.right;
            Node leftOfNodeToDelete = toRemove.left;

            if(parentOfTheNodeToDelete == null){
                root = predecessor;
            } else {
                if (parentOfTheNodeToDelete.right.key
                        .compareTo(toRemove.key) == 0) {
                    parentOfTheNodeToDelete.right = predecessor;

                } else if (parentOfTheNodeToDelete.left.key
                        .compareTo(toRemove.key) == 0) {
                    parentOfTheNodeToDelete.left = predecessor;
                }

            }
            if(predecessor.right != predecessor)
                predecessor.right = rightOfNodeToDelete;
            if(predecessor.left != predecessor)
                predecessor.left = leftOfNodeToDelete;
        }
        return tempVal;
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
        if(currentSize == 0)
            return null;
        LinkedList<K> list = new LinkedList<>();
        getFirstNodeFromValue(root, list, value);
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    private void getFirstNodeFromValue(Node e,LinkedList list, V value){
        if( e != null){
            if(e.value.equals(value)) {
                list.add(e.key);
                return;
            }
            getFirstNodeFromValue(e.left, list, value);
            getFirstNodeFromValue(e.right, list, value);
        }
    }

    @Override
    public Iterable<K> getKeys(V value) {
        LinkedList<K> list = new LinkedList<>();
        retrieveKeys(root, list, value);
        if(list.size() == 0){
            return null;
        }
        return list;
    }

    private void retrieveKeys(Node e, LinkedList list, V value){
        //recursively retrieves keys associated with value, adds them to list
        if( e != null) {
            retrieveKeys(e.left, list, value);
            if (e.value.equals(value))
                list.add(e.key);
            retrieveKeys(e.right, list, value);
        }
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
        LinkedList<K> list = new LinkedList();
        inOrderTraverse(root, list);
        return list;
    }

    private void inOrderTraverse(Node e, LinkedList list){
        //recursively finds all the keys in tree, adds them to list
        if(e != null) {
            inOrderTraverse(e.left, list);
            list.add(e.key);
            inOrderTraverse(e.right, list);
        }
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> list = new LinkedList<>();
        inOrderTraverseVals(root, list);
        return list;
    }

    private void inOrderTraverseVals(Node e, LinkedList list){
        //recursively finds all the values in the tree, adds them to list
        if(e != null){
            inOrderTraverseVals(e.left, list);
            list.add(e.value);
            inOrderTraverseVals(e.right, list);
        }
    }

}
