package tests;

public class Stack {

    private final String[] elements;
    private int size;

    public Stack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Ёмкость должна быть больше 0");
        }
        this.elements = new String[capacity];
        this.size = 0;
    }

    public void push(String item) {
        if (size == elements.length) {
            throw new IllegalStateException("Стек переполнен");
        }
        elements[size++] = item;
    }

    public String pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        String item = elements[--size];
        elements[size] = null;
        return item;
    }

    public String peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        return elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
}
