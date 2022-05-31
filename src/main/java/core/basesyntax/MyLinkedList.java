package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, head);
        if (index == 0) {
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        isWrongIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        isWrongIndex(index);
        Node<T> current = getNode(index);
        T oldItem = current.item;
        current.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = getNode(index);
        T removeItem = current.item;
        unlink(current);
        return removeItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int i = 0;
        while (current != null || i < size) {
            if ((current.item == object)
                    || (current.item != null && current.item.equals(object))) {
                unlink(current);
                return true;
            }
            i++;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void isWrongIndex(int index)
            throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        isWrongIndex(index);
        Node<T> current = head;
        int i = 0;
        while (current != null || i < size) {
            if (i == index) {
                return current;
            }
            i++;
            current = current.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = node.next;
            node.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
