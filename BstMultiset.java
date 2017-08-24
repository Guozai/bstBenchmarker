import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T extends Comparable<T>> extends Multiset<T>
{
    /** Reference to root node of tree. */
    protected Node<T> mRoot;
    private int size = 0;

    public BstMultiset() {
        mRoot = null;
    } // end of BstMultiset()

    public void add(T item) {
        mRoot = add(item, mRoot);
    } // end of add()

    /**
     * Recursive add() method
     */
    private Node<T> add(T data, Node<T> node) {
        if(node != null) {
            if(data != null){
                if(data.toString().compareTo(node.getValue().toString()) < 0){
                    node.mLeft = add(data, node.mLeft);
                }
                if(data.toString().compareTo(node.getValue().toString()) >= 0){
                    node.mRight = add(data, node.mRight);
                }
            }
        }
        else{
            node = new Node<T>(data);
            size++;
        }
        return node;
    }

    public int search(T item) {
        if (mRoot == null) {
            return 0;
        } else {
            return getCount(mRoot, item);
        }
    } // end of search()

    /**
     * Recursive Method Helping search()
     */
    public int getCount(Node<T> root, T data) {
        if (root == null) {
            return 0;
        }
        if (root.getValue().toString().compareTo(data.toString()) == 0) {
            return 1 + getCount(root.getLeft(), data) + getCount(root.getRight(), data);
        }
        if (root.getValue().toString().compareTo(data.toString()) < 0) {
            return getCount(root.getRight(), data);
        } else { // if (root.getValue().toString().compareTo(data.toString()) > 0)
            return getCount(root.getLeft(), data);
        }
    }

    /**
     * Recursive Method Helping search()
     */
//	 public int  countDuplicates (Node root, Node prev) {
//            //Basic edge case .. root==null
//            if (root == null) {
//				return 0;
//			}
//            //else
//            int count=0;
//            if (root.getValue().toString().compareTo(prev.getValue().toString() == 0) {
//                count++;
//			}
//            return count+countDuplicates(root.getLeft(),root)+countDuplicates(root.getRight(),root);
//	}

//    private int count = 0;
//    private int lookup(T data, Node<T> node) {
//        if (data != null) {
//            if (data.toString().compareTo(node.getValue().toString()) == 0) {
//                count++;
//            } else {
//                if (data.toString().compareTo(node.getValue().toString()) < 0) {
//                    if (node.getLeft() != null) {
//                        return lookup(data, node.getLeft());
//                    }
//                } else if (data.toString().compareTo(node.getValue().toString()) > 0) {
//                    if (node.getRight() != null) {
//                        return lookup(data, node.getRight());
//                    }
//                }
//            }
//        }
//        return count;
//    }

    /**
     * Removes a data entry from the BST
     */
    public void removeOne(T item) { mRoot = remove(item, mRoot); } // end of removeOne()

    public void removeAll(T item) {
        while (search(item) > 0) {
            mRoot = remove(item, mRoot);
        }
    } // end of removeAll()

    private Node<T> remove(T data, Node<T> node) {
        // System.out.println("Program has arrived beginning of Node<T>.");
        if (node != null) {
            if (data == null) {
                node.setRight(remove(data, node.getRight()));
                if (node.getValue() == null) {
                    node = removeNode(node);
                }
            } else {
                if (data.toString().compareTo(node.getValue().toString()) < 0) {
                    node.setLeft(remove(data, node.getLeft()));
                } else if (data.toString().compareTo(node.getValue().toString()) > 0) {
                    node.setRight(remove(data, node.getRight()));
                } else {
                    node = removeNode(node);
                }
            }
        }
        return node;
    }

    private Node<T> removeNode(Node<T> node) {
        if (node.getLeft() == null) {
            node = node.getRight();
        } else if (node.getRight() == null) {
            node = node.getLeft();
        } else {
            Node<T> big = node.getLeft();
            Node<T> last = null;
            while (big.getRight() != null) {
                last = big;
                big = big.getRight();
            }
            node.setValue(big.getValue());
            if (last == null) {
                node.setLeft(big.getLeft());
            } else {
                last.setRight(big.getLeft());
            }
        }
        return node;
    }

    public void print(PrintStream out) {
        setNumber(mRoot);
        List<T> aList = reversePreOrder();
        boolean canPrint = true;
        if (!aList.isEmpty()) {
            for (int i = 0; i < aList.size(); ++i) {
                canPrint = true;
                for (int j = 0; j < i; ++j) {
                    if (aList.get(i).compareTo(aList.get(j)) == 0) {
                        canPrint = false;
                    }
                }
                if (canPrint == true) {
                    System.out.println(aList.get(i).toString() + printDelim + search(aList.get(i)));
                }
            }
        }
    } // end of print()

    /**
     * Recursive Method Helping print()
     */
    public void setNumber(Node<T> node) {
        if (node != null) {
            if (node.getLeft() != null) {
                setNumber(node.getLeft());
            }
            node.setNum(search(node.getValue()));
            if (node.getRight() != null) {
                setNumber(node.getRight());
            }
        }
    }

    /**
     * BST Node implementation, inner private class.
     */
    private class Node<T extends Comparable<T>> {
        /** Stored value of node. */
        protected T mValue;
        /** Reference to the left node. */
        protected Node<T> mLeft;
        /** Reference to the right node. */
        protected Node<T> mRight;
        /** Save the count of value */
        protected int num;

        public Node(T value) { mValue = value; }

        public T getValue() { return mValue; }
        public Node<T> getLeft() { return mLeft; }
        public Node<T> getRight() { return mRight; }
        public int getNum() { return num; }

        public void setValue(T value) { mValue = value; }
        public void setLeft(Node<T> left) { mLeft = left; }
        public void setRight(Node<T> right) { mRight = right; }
        public void setNum(int num) { this.num = num; }
    } // end of inner class Node

    /**
     * The Pre-order traversal of the BST
     */
    public List<T> reversePreOrder() {
        List<T> list = new ArrayList<T>();
        reversePreOrder(mRoot, list);
        return list;
    }

    public void reversePreOrder(Node<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getValue());
            reversePreOrder(node.getRight(), list);
            reversePreOrder(node.getLeft(), list);
        }
    }

    /**
     //	 * Non-recursive Method Helping print()
     //	 */
//	private void MorrisTraversal(Node<T> root) {
//        Node<T> current, pre;
//
//        if (root == null) {
//            return;
//		}
//
//        current = root;
//        while (current != null)
//        {
//            if (current.getLeft() == null)
//            {
//                System.out.println(current.getValue() + printDelim + current.getNum());
//                current = current.getRight();
//            }
//            else
//            {
//                // Find the inorder predecessor of current
//                pre = current.getLeft();
//                while (pre.getRight() != null && pre.getRight() != current) {
//                    pre = pre.getRight();
//                }
//
//                // Make current as right child of its inorder predecessor
//                if (pre.getRight() == null)
//                {
//                    pre.setRight(current);
//                    current = current.getLeft();
//                }
//
//                // Revert the changes made in if part to restore the
//                // original tree i.e.,fix the right child of predecssor
//                else {
//                    pre.setRight(null);
//					if (current.getValue().toString().compareTo(current.getRight().getValue().toString()) != 0) {
//                        System.out.println(current.getValue() + printDelim + current.getNum());
//                        current = current.getRight();
//                    }
//                }   //End of if condition pre->right == null
//            } // End of if condition current->left == null
//        } // End of while
//	}

} // end of class BstMultiset
