package sort;

import java.util.Arrays;

/**
 * 7 选择排序
 * <p>
 * time:O(n^2) space: O(1)
 *
 * @author minwei
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {8, 2, 3, 4, 5, 6, 1, 7};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 第i轮找到第i小的元素
     *
     * @param arr 输入数组
     */
    private static void selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            // 先假定当前数是最小的
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 有很多无用的比较
                if (arr[j] < arr[min]) {
                    //记下目前找到的最小值所在的位置
                    min = j;
                }
            }
            // 找到本轮循环的最小的数进行交换
            // i对应元素已经是本轮最小 则不进入if语句块
            if (i != min) {
                swap(arr, i, min);
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
