package utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilsTest {

    // ========== swap ==========

    @Test
    void swap_shouldExchangeTwoElements() {
        String[] arr = {"a", "b", "c"};
        ArrayUtils.swap(arr, 0, 1);
        assertArrayEquals(new String[]{"b", "a", "c"}, arr);
    }

    @Test
    void swap_shouldWorkWithSameIndex() {
        String[] arr = {"a", "b", "c"};
        ArrayUtils.swap(arr, 1, 1);
        assertArrayEquals(new String[]{"a", "b", "c"}, arr);
    }

    @Test
    void swap_shouldWorkWithIntegerArray() {
        Integer[] arr = {10, 20, 30};
        ArrayUtils.swap(arr, 0, 2);
        assertArrayEquals(new Integer[]{30, 20, 10}, arr);
    }

    @Test
    void swap_shouldThrowOnNullArray() {
        String[] arr = null;
        assertThrows(IllegalArgumentException.class, () -> ArrayUtils.swap(arr, 0, 1));
    }

    @Test
    void swap_shouldThrowOnNegativeIndex() {
        String[] arr = {"a", "b"};
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.swap(arr, -1, 0));
    }

    @Test
    void swap_shouldThrowOnIndexOutOfRange() {
        String[] arr = {"a", "b"};
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.swap(arr, 0, 2));
    }

    // ========== safeSwap ==========

    @Test
    void safeSwap_shouldWorkNormally() {
        String[] arr = {"a", "b", "c"};
        ArrayUtils.safeSwap(arr, 0, 2);
        assertArrayEquals(new String[]{"c", "b", "a"}, arr);
    }

    @Test
    void safeSwap_shouldDoNothingWhenIndicesEqual() {
        String[] arr = {"a", "b", "c"};
        ArrayUtils.safeSwap(arr, 1, 1);
        assertArrayEquals(new String[]{"a", "b", "c"}, arr);
    }

    @Test
    void safeSwap_shouldDoNothingOnNullArray() {
        String[] arr = null;
        assertDoesNotThrow(() -> ArrayUtils.safeSwap(arr, 0, 1));
    }

    @Test
    void safeSwap_shouldStillThrowForBadIndices() {
        String[] arr = {"a", "b"};
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.safeSwap(arr, 0, 5));
    }

    // ========== toList ==========

    @Test
    void toList_shouldConvertNonNullArrayToList() {
        String[] arr = {"a", "b", "c"};
        List<String> list = ArrayUtils.toList(arr);
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    void toList_shouldReturnEmptyListForNullArray() {
        List<String> list = ArrayUtils.toList(null);
        assertTrue(list.isEmpty());
    }

    @Test
    void toList_shouldReturnSingleElementList() {
        Integer[] arr = {42};
        List<Integer> list = ArrayUtils.toList(arr);
        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
    }

    @Test
    void toList_shouldReturnMutableList() {
        String[] arr = {"x", "y"};
        List<String> list = ArrayUtils.toList(arr);
        list.add("z");
        assertEquals(3, list.size());
        assertEquals("z", list.get(2));
    }

    @Test
    void toList_shouldReturnIndependentList() {
        String[] arr = {"a", "b"};
        List<String> list = ArrayUtils.toList(arr);
        list.set(0, "modified");
        assertEquals("modified", list.get(0));
        assertEquals("a", arr[0]);
    }
}
