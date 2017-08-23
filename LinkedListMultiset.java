import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
    /** Reference to head of list. */
    protected Node<T> mHead;
    /** Reference to tail of list. */
    protected Node<T> mTail;

    /** Length of list. */
    protected int mLength;

    public LinkedListMultiset() {
        mHead = null;
        mTail = null;
        mLength = 0;
    } // end of LinkedListMultiset()


    public void add(T item) {
        Node<T> newNode = new Node<T>(item);

        // If head is empty, then list is empty and head reference need to be initialised.
        if (mHead == null) {
            mHead = newNode;
            mTail = newNode;
        }
        // otherwise, add node to the head of list.
        else {
            mTail.setNext(newNode);
            newNode.setPrev(mTail);
        }
        mTail = newNode;

        mLength++;
    } // end of add()


    public int search(T item) {
        int count = 0;
        Node<T> currNode = mHead;
        for (int i = 0; i < mLength; ++i) {
            if (currNode.getValue().toString().compareTo(item.toString()) == 0) {
                count++;
            }
            currNode = currNode.getNext();
        }
        return count;
    } // end of search()


    public void removeOne(T item) {
        Node<T> currNode = mHead;
        int count = 0;
        for (int i = 0; i < mLength; ++i) {
            // if the value of the current node equals to value item
            if (currNode.getValue().toString().compareTo(item.toString()) == 0) {
                // first node
                if (currNode.getPrev() == null) {
                    mHead = currNode.getNext();
                    currNode.getNext().setPrev(null);
                }
                // last node
                else if (currNode.getNext() == null) {
                    mTail = currNode.getPrev();
                    currNode.getPrev().setNext(null);
                } else {
                    currNode.getPrev().setNext(currNode.getNext());
                    currNode.getNext().setPrev(currNode.getPrev());
                }
                count = 1;
            }
            if (count == 1) {
                mLength -= count;
                return;
            }
            currNode = currNode.getNext();
        }
    } // end of removeOne()


    public void removeAll(T item) {
        Node<T> currNode = mHead;
        int count = 0;
        for (int i = 0; i < mLength; ++i) {
            // if the value of the current node equals to value item
            if (currNode.getValue().toString().compareTo(item.toString()) == 0) {
                // first node
                if (currNode.getPrev() == null) {
                    mHead = currNode.getNext();
                    currNode.getNext().setPrev(null);
                }
                // last node
                else if (currNode.getNext() == null) {
                    mTail = currNode.getPrev();
                    currNode.getPrev().setNext(null);
                } else {
                    currNode.getPrev().setNext(currNode.getNext());
                    currNode.getNext().setPrev(currNode.getPrev());
                }
                count += 1;
            }
            currNode = currNode.getNext();
        }
        mLength -= count;
    } // end of removeAll()

//    public void print(PrintStream out) {
//        Node currNode = mHead;
//        if (mHead != null) {
//            for (int i = 0; i < mLength; ++i) {
//                System.out.print(currNode.getValue() + " ");
//                if (currNode.getNext() != null) {
//                    currNode = currNode.getNext();
//                }
//            }
//            System.out.println();
//        }
//    } // end of print()

    public void print(PrintStream out) {
        Node<T> currNode = mHead;
        Node<T> pointerNode = mHead;
        boolean canPrint = true;
        if (mHead != null) {
            for (int i = 0; i < mLength; ++i) {
                pointerNode = mHead;
                canPrint = true;
                for (int j = 0; j < i; ++j) {
                    if (currNode.getValue().toString().compareTo(pointerNode.getValue().toString()) == 0) {
                        canPrint = false;
                    }
                    pointerNode = pointerNode.getNext();
                }
                if (canPrint == true) {
                    out.println(currNode.getValue() + printDelim + search(currNode.getValue()));
                    //System.out.println(currNode.getValue() + printDelim + search(currNode.getValue()));
                }
                if (currNode.getNext() != null) {
                    currNode = currNode.getNext();
                }
            }
        }
    } // end of print()

    /**
     * Node type, inner private class.
     */
    private class Node<T> {
        /** Stored value of node. */
        protected T mValue;
        /** Reference to next node. */
        protected Node<T> mNext;
        /** Reference to previous node. */
        protected Node<T> mPrev;

        public Node(T value) {
            mValue = value;
            mPrev = null;
            mNext = null;
        }

        public T getValue() {
            return mValue;
        }

        public Node<T> getPrev() { return mPrev; }
        public Node<T> getNext() { return mNext; }

        public void setValue(T value) { mValue = value; }

        public void setPrev(Node<T> prev) { mPrev = prev; }
        public void setNext(Node<T> next) { mNext = next; }
    } // end of inner class Node

} // end of class LinkedListMultiset