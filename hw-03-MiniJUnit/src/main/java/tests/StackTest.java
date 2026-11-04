package tests;

import annotation.Test;
import annotation.Before;
import annotation.After;

public class StackTest {

    private Stack stack;

    @Before
    public void setUp() {
        stack = new Stack(5);
        System.out.println("  [setUp] Создан стек ёмкостью 5");
    }

    @After
    public void tearDown() {
        stack = null;
        System.out.println("  [tearDown] Очистка");
    }

    @Test
    public void pushAndPop_singleElement() {
        stack.push("first");
        String result = stack.pop();
        if (!"first".equals(result)) {
            throw new AssertionError("Ожидалось 'first', получено: " + result);
        }
        System.out.println("  push('first') -> pop() = 'first' ✓");
    }

    @Test
    public void pushAndPop_multipleElements() {
        stack.push("one");
        stack.push("two");
        stack.push("three");
        String r1 = stack.pop();
        String r2 = stack.pop();
        String r3 = stack.pop();
        if (!"three".equals(r1) || !"two".equals(r2) || !"one".equals(r3)) {
            throw new AssertionError("Нарушен порядок LIFO: " + r1 + ", " + r2 + ", " + r3);
        }
        System.out.println("  push(1,2,3) -> pop() = 3,2,1 (LIFO) ✓");
    }

    @Test
    public void peek_shouldNotRemoveElement() {
        stack.push("top");
        String first = stack.peek();
        String second = stack.peek();
        if (!"top".equals(first) || !"top".equals(second)) {
            throw new AssertionError("peek() должен возвращать элемент без удаления");
        }
        if (stack.size() != 1) {
            throw new AssertionError("Размер должен остаться 1");
        }
        System.out.println("  peek() не удаляет элемент ✓");
    }

    @Test
    public void isEmpty_newStackShouldBeEmpty() {
        if (!stack.isEmpty()) {
            throw new AssertionError("Новый стек должен быть пустым");
        }
        System.out.println("  isEmpty() для нового стека = true ✓");
    }

    @Test
    public void isEmpty_notEmptyAfterPush() {
        stack.push("item");
        if (stack.isEmpty()) {
            throw new AssertionError("Стек после push не должен быть пустым");
        }
        System.out.println("  isEmpty() после push = false ✓");
    }

    @Test
    public void size_tracksCorrectly() {
        if (stack.size() != 0) {
            throw new AssertionError("Новый стек должен иметь размер 0");
        }
        stack.push("a");
        stack.push("b");
        if (stack.size() != 2) {
            throw new AssertionError("Ожидалось 2, получено: " + stack.size());
        }
        stack.pop();
        if (stack.size() != 1) {
            throw new AssertionError("Ожидалось 1, получено: " + stack.size());
        }
        System.out.println("  size() отслеживает элементы ✓");
    }

    @Test
    public void popEmptyStack_shouldThrowException() {
        try {
            stack.pop();
            throw new AssertionError("Ожидалось IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("  pop() пустого стека бросает IllegalStateException ✓");
        }
    }

    @Test
    public void pushToFullStack_shouldThrowException() {
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        try {
            stack.push("overflow");
            throw new AssertionError("Ожидалось IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("  push() переполненного стека бросает IllegalStateException ✓");
        }
    }

    @Test
    public void peekEmptyStack_shouldThrowException() {
        try {
            stack.peek();
            throw new AssertionError("Ожидалось IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("  peek() пустого стека бросает IllegalStateException ✓");
        }
    }

    @Test
    public void clear_shouldEmptyStack() {
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.clear();
        if (!stack.isEmpty()) {
            throw new AssertionError("Стек после clear должен быть пустым");
        }
        if (stack.size() != 0) {
            throw new AssertionError("Размер после clear должен быть 0");
        }
        System.out.println("  clear() очищает стек ✓");
    }

    @Test
    public void negativeCapacity_shouldThrowException() {
        try {
            new Stack(-1);
            throw new AssertionError("Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("  Stack(-1) бросает IllegalArgumentException ✓");
        }
    }

    @Test
    public void zeroCapacity_shouldThrowException() {
        try {
            new Stack(0);
            throw new AssertionError("Ожидалось IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("  Stack(0) бросает IllegalArgumentException ✓");
        }
    }
}
