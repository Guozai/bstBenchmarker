import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
    /** Reference to head of list. */
    protected Node<T> mHead;
    /** Reference to tail of list. */
    protected Node<T> mTail;

    /** Length of list. */
    protected int mLength;

    public SortedLinkedListMultiset() {
        mHead = null;
        mTail = null;
        mLength = 0;
    } // end of SortedLinkedListMultiset()

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
    } // end of add()


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
	
	
/*	public void print(PrintStream out) {
		Node<T> currNode = mHead;
        if (mHead != null) {
        	sort(mHead);
            for (int i = 0; i < mLength; ++i) {
                System.out.print(currNode.getValue() + " ");
                if (currNode.getNext() != null) {
                    currNode = currNode.getNext();
                }
            }
            System.out.println();
        }
	} // end of print() 
*/

    public void print(PrintStream out) {
        Node<T> currNode = mHead;
        Node<T> pointerNode = mHead;
        boolean canPrint = true;
        if (mHead != null) {
            sort(mHead);
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
                }
                if (currNode.getNext() != null) {
                    currNode = currNode.getNext();
                }
            }
        }
    } // end of print()

    public void sort(Node<T> head) {
        Node<T> first = head;
        Node<T> last = getLast(head);
        quickSort(first, last);
    }

    public Node<T> getLast(Node<T> node) {
        while (!Objects.isNull(node.getNext())) {
            node = node.getNext();
        }
        return node;
    }

    private void quickSort(Node<T> low, Node<T> high) {
        /**
         * Partioning till
         *  - high is out of boundary and
         *  - low not equal high means no element and
         *  - has more than one element
         */
        if (!Objects.isNull(high) &&
                low != high && low != high.getNext()) {

            Node<T> pivot = partition(low, high);
            quickSort(low, pivot.getPrev());
            quickSort(pivot.getNext(), high);
        }
    }

    private Node<T> partition(Node<T> low, Node<T> high) {
        Node<T> i = null,
                j = low,
                pivot = high;
        T temp;

        for (; j != high; j = j.getNext()) {
            if (pivot.getValue().toString().compareTo(j.getValue().toString()) > 0) {
                i = (Objects.isNull(i)) ? low : i.getNext();
                temp = i.getValue();
                i.setValue(j.getValue());
                j.setValue(temp);
            }
        }

        i = (Objects.isNull(i)) ? low : i.getNext();
        temp = i.getValue();
        i.setValue(pivot.getValue());
        pivot.setValue(temp);
        return i;
    }

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
    } // end of inner class Node<T>

} // end of class SortedLinkedListMultiset