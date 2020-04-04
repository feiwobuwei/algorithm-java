package sort;

import java.util.Arrays;

/**
 * 3 冒泡排序
 * <p>
 * time: O(n^2)
 * space: O(1)
 *
 * @author minwei
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 5, 6, 7, 8, 1};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 正序排列，小值往上浮。每轮最小的元素浮到该轮的起点位置
     * 反复交换相邻的未按次序排列的元素
     *
     * @param arr 待排序数组
     */
    private static void bubbleSort(int[] arr) {
        boolean flag = false;
        for (int i = 1; i < arr.length; i++) {
            for (int j = arr.length - 1; j >= i; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    flag = true;
                }
            }
            // 如果某轮一次交换也没进行,提前结束
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
