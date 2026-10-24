package utils;

import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    /**
     * Меняет местами два элемента в массиве ссылочного типа.
     *
     * @param array массив, в котором нужно поменять элементы
     * @param j     индекс первого элемента
     * @param k     индекс второго элемента
     * @param <T>   тип элементов массива (только ссылочные типы)
     * @throws IllegalArgumentException если массив равен null
     * @throws IndexOutOfBoundsException если индексы вне диапазона
     */
    public static  <T>  void swap (T[] array,int j, int k){

        if(array==null){

            throw  new IllegalArgumentException("Массив не может быть null.");
        }

        if(j<0||j>=array.length||k<0||k>= array.length){
            throw new IndexOutOfBoundsException(
                    String.format("Индексы должны быть в диапазоне [0, %d]. Получено: j=%d, k=%d",
                            array.length-1,j,k)
            );
        }
        T temp=array[j];
        array[j]=array[k];
        array[k]=temp;
    }

    /**
     * Метод: оставляет элементы на своих местах если переданые индексы одинаковые.
     */
    public static <T> void safeSwap(T[] array,int j,int k){
        if(j==k||array==null){
            return;
        }
        swap(array,j,k);
    }


    /**
     * Преобразует массив ссылочного типа в список.
     * Возвращаемый список фиксирован по размеру и поддерживается исходным массивом
     *
     * @param arr массив для преобразования
     * @param <T> тип элементов массива
     * @return список, содержащий элементы массива
     */
    public static <T> List<T> toList(T[] arr) {
        if (arr == null) {
            return List.of();
        }
        return Arrays.asList(arr);
    }




}
