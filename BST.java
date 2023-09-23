import javax.lang.model.type.NullType;
import java.security.KeyStore;

/**
 * A minimal implementation of a binary search tree. See the python version for
 * additional documentation.
 * You can also see Chapter 6 of <a href="https://www.teach.cs.toronto.edu/~csc148h/winter/notes/">CSC148 Course Notes</a>
 * if you want a refresher on BSTs, but it is required to complete this assignment.
 * @param <T>
 */
public class BST<T extends Comparable<T>> {
    //Note: the extends Comparable<T> above means we require T to implement the Comparable<T> interface,
    //      since a BST requires that we can compare its elements to determine the ordering.
    private T root;

    private BST<T> left;
    private BST<T> right;

    public BST(T root) {
        if (root != null) { // check to ensure we don't accidentally try to store null at the root!
            this.root = root;
            this.left = new BST<>();
            this.right = new BST<>();
        }
        // Note: each of the attributes will default to null
    }

    /**
     * Alternate constructor, so we don't have to explicitly pass in null.
     */
    public BST() {
        this(null);
    }


    // Task 2: Implement the BST methods.

    public boolean isEmpty() {
        // implement me!
        return root == null;
    }

    public boolean contains(T item) {
        // provided
        if (this.isEmpty()) {
            return false;
        } else if (item.equals(this.root)) { // we need to use .equals and not == to properly compare values
            return true;
        } else if (item.compareTo(this.root) < 0) {
            return this.left.contains(item);
        }
        return this.right.contains(item);

    }


    public void insert(T item) {
        // implement me!
        if (this.root == null) {
            this.root = item;
            this.left = new BST<>();
            this.right = new BST<>();
        } else if (item.compareTo(this.root) < 0) {
            this.left.insert(item);
        }
        else {
            this.right.insert(item);
        }
    }


    public void delete(T item) {
        // implement me!
        if (item.equals(this.root)) {
            this.deleteRoot();
        }
        else if (item.compareTo(this.root) < 0) {
            this.left.delete(item);

        }
        else {
            this.right.delete(item);
        }
    }

    private void deleteRoot() {
        // implement me!
        if (this.left.isEmpty() && this.right.isEmpty()) {
            this.root = null;
            this.left = null;
            this.right = null;
        }
        else if (this.left.isEmpty()) {
            BST<T> new_root = this.right;
            this.root = new_root.root;
            this.left = new_root.left;
            this.right = new_root.right;
        }
        else if (this.right.isEmpty()) {
            BST<T> new_root = this.left;
            this.root = new_root.root;
            this.left = new_root.left;
            this.right = new_root.right;

        }
        else {
            this.root = this.left.extractMax();
        }
    }


    private T extractMax() {
        // implement me!
        if (this.right.isEmpty()) {
            BST<T> max_item = this;
            BST<T> new_root = this.left;
            this.root = new_root.root;
            this.left = new_root.left;
            this.right = new_root.right;
            return max_item.root;
        }
        else {
            return this.right.extractMax();
        }
        // dummy code; replace with correct code when you implement this.
    }

    public int height() {
        // implement me!
        if (this.root == null){
            return 0;
        }
        else {
            int left_height = this.left.height();
            int right_height = this.right.height();

            if (left_height > right_height) {
                return (left_height + 1);
            }
            else {
                return (right_height + 1);
            }
        }
    }


    public int count(T item) {
        // implement me!
        if (item.equals(this.root)) {
            if (item.equals(this.left.root) && item.equals(this.right.root)) {
                return 1 + this.left.count(item) + this.right.count(item);
            } else if (item.equals(this.left.root)) {
                return 1 + this.left.count(item);
            } else if (item.equals(this.right.root)) {
                return 1 + this.right.count(item);
            }
            return 1;
        }
        else if (item.compareTo(this.root) < 0) {
            return this.left.count(item);
        }
        else {
            return this.right.count(item);
            }
    }

    public int getLength() {
        // implement me!
        if (this.isEmpty()) {
            return 0;
        } else if (this.left.isEmpty() && this.right.isEmpty()) {
            return 1;
        } else {
            int length = 1;
            while (!this.left.isEmpty() && !this.right.isEmpty()) {
                length += this.left.getLength() + this.right.getLength();
            }
            return length;
        }
    }


        public static void main (String[]args){
            // you can write any code you want here and run this file to confirm that
            //      your code is working as it should. We will not run this when testing your code.
            BST<T> tree = new BST<>();
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);

            System.out.println("The binary tree is empty: "
                    + tree.isEmpty());
            System.out.println("The length of binary tree is : "
                    + tree.getLength());
            System.out.println("The maximum value of the binary tree is : "
                    + tree.extractMax());
            System.out.println("The height of the binary tree is : "
                    + tree.height());
            System.out.println("The times the number 1 appears in the binary tree is : "
                    + tree.count(1));

            tree.delete(5);
            System.out.println("The length of binary tree is : "
                    + tree.getLength());
        }


    }