package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V>{

    private Node root;
    private int size = 0;

    private class Node<K, V> implements Comparable<K> {
        public K key;
        public V val;
        public Node left;
        public Node right;
        public Node() {
            key = null;
            val = null;
            left = null;
            right = null;
        }
        public Node(K _key, V _val) {
            key = _key;
            val = _val;
            left = null;
            right = null;
        }

        @Override
        public int compareTo(K o) {
            return key.toString().compareTo(o.toString());
        }
    }

    public BSTMap() {
        size = 0;
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        Node p = root;
        while (p != null) {
            if (p.compareTo(key) > 0) {
                p = p.left;
            } else if (p.compareTo(key) < 0) {
                p = p.right;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        Node p = root;
        while (p != null) {
            if (p.compareTo(key) > 0) {
                p = p.left;
            } else if (p.compareTo(key) < 0) {
                p = p.right;
            } else {
                return (V) p.val;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            size = 1;
            return;
        }
        Node p = root;
        while (root != null) {
            if (root.compareTo(key) > 0) {
                if (root.left == null) {
                    root.left = new Node<>(key, value);
                    break;
                }
                root = root.left;
            } else if (root.compareTo(key) < 0) {
                if (root.right == null) {
                    root.right = new Node<>(key, value);
                    break;
                }
                root = root.right;
            } else {
                root.val = value;
                return;
            }
        }
        root = p;
        size += 1;
    }

    @Override
    public Set<K> keySet() {
        Iterator<K> it = iterator();
        Set<K> set = new HashSet<>();
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }

    @Override
    public V remove(K key) {
        V val = null;
        Node cur, pre;
        Node p = root;
        while (p != null) {
            if (p.compareTo(key) > 0) {
                p = p.left;
            } else if (p.compareTo(key) < 0) {
                p = p.right;
            } else {
                if (p.left != null) {
                    cur = p.left;
                    if(cur.right == null) {
                        cur.right = p.right;
                    } else {
                        pre = cur;
                        cur = cur.right;
                        while (cur.right != null) {
                            pre = cur;
                            cur = cur.right;
                        }
                        if (cur.left != null) {
                            pre.right = cur.left;
                        }
                        cur.left = p.left;
                        cur.right = p.right;
                    }
                    val = (V)p.val;
                    if (root == p) {
                        root = cur;
                    } else {
                        p = cur;
                    }
                } else if (p.right != null) {
                    cur = p.right;
                    if(cur.left == null) {
                        cur.left = p.left;
                    } else {
                        pre = cur;
                        cur = cur.left;
                        while (cur.left != null) {
                            pre = cur;
                            cur = cur.left;
                        }
                        if (cur.right != null) {
                            pre.left = cur.right;
                        }
                        cur.left = p.left;
                        cur.right = p.right;
                    }
                    val = (V) p.val;
                    if (root == p) {
                        root = cur;
                    } else {
                        p = cur;
                    }
                } else {
                    val = (V) p.val;
                    if (root == p) {
                        root = null;
                    } else {
                        p = null;
                    }
                }
                size -= 1;
                break;
            }
        }
        return val;
    }

    @Override
    public V remove(K key, V value) {
        V val = null;
        Node cur, pre;
        Node p = root;
        while (p != null) {
            if (p.compareTo(key) > 0) {
                p = p.left;
            } else if (p.compareTo(key) < 0) {
                p = p.right;
            } else {
                if (p.val.equals(val)) {
                    if (p.left != null) {
                        cur = p.left;
                        if(cur.right == null) {
                            cur.right = p.right;
                        } else {
                            pre = cur;
                            cur = cur.right;
                            while (cur.right != null) {
                                pre = cur;
                                cur = cur.right;
                            }
                            if (cur.left != null) {
                                pre.right = cur.left;
                            }
                            cur.left = p.left;
                            cur.right = p.right;
                        }
                        val = (V) p.val;
                        if (root == p) {
                            root = cur;
                        } else {
                            p = cur;
                        }
                    } else if (p.right != null) {
                        cur = p.right;
                        if(cur.left == null) {
                            cur.left = p.left;
                        } else {
                            pre = cur;
                            cur = cur.left;
                            while (cur.left != null) {
                                pre = cur;
                                cur = cur.left;
                            }
                            if (cur.right != null) {
                                pre.left = cur.right;
                            }
                            cur.left = p.left;
                            cur.right = p.right;
                        }
                        val = (V) p.val;
                        if (root == p) {
                            root = cur;
                        } else {
                            p = cur;
                        }
                    } else {
                        val = (V) p.val;
                        if (root == p) {
                            root = null;
                        } else {
                            p = null;
                        }
                    }
                    size -= 1;
                    break;
                }
            }
        }
        return val;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Node cur;
        private Stack<Node> stack;
        public BSTMapIterator() {
             stack = new Stack<>();
             stack.push(root);
        }
        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public K next() {
            cur = stack.pop();
            K key = (K)cur.key;
            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
            return key;
        }
    }

    public void printInOrder() {
        getVal(root);
    }
    private void getVal (Node n) {
        if (n == null) {
            return ;
        }
        getVal(n.left);
        System.out.print(n.key + " ");
        getVal(n.right);

    }
}
