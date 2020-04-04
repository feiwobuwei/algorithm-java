package sort;

import java.util.Arrays;

/**
 * 8 希尔排序
 * <p>
 * time: 平均 time: O(n*log(n)) 最好O(n^1.3) 最坏O(n^2)
 * space: O(1)
 *
 * @author minwei
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] test = new int[]{8, 2, 3, 4, 5, 6, 9, 7, 1};
        shellSort(test);

        System.out.println(Arrays.toString(test));
        System.out.println("========================");

        int[] test2 = new int[]{8, 2, 3, 4, 12, 5, 6, 10, 9, 7, 1, 11};
        shellSort2(test2);
        System.out.println(Arrays.toString(test2));

    }

    /**
     * 原始的希尔排序 区间排序用的是插入排序
     */
    private static void shellSort(int[] array) {
        int n = array.length;

        // h希尔增量 每次减半
        // 直到最后一次增量h变为1 此时变为了插入排序
        for (int h = n >> 1; h > 0; h = h >> 1) {
            for (int j = h; j < n; j++) {
                int key = array[j];
                int i = j - h;
                while (i >= 0 && array[i] > key) {
                    array[i + h] = array[i];
                    i = i - h;
                }
                array[i + h] = key;
            }
        }
    }

    /**
     * 区间排序 用冒泡排序
     */
    private static void shellSort2(int[] a) {
        int n = a.length;

        // h希尔增量 每次减半
        // 直到最后一次增量h变为1 此时变为了冒泡排序
        for (int h = n >> 1; h > 0; h = h >> 1) {
            for (int i = h; i < n; i++) {
                // 将a[i]与a[i-h],a[i-2h],a[i-3h]进行交换
                for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
                    swap(a, j, j - h);
                }
            }
        }
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

}
