package sort;

import java.util.Arrays;

/**
 * 1 插入排序
 * <p>
 * time: O(n^2)
 * space: O(1)
 *
 * @author minwei
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] test = new int[]{8, 2, 3, 4, 5, 6, 1, 7};
        insertionSort(test);
        System.out.println(Arrays.toString(test));
    }

    private static void insertionSort(int[] array) {
        // 从第2个元素开始排
        for (int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j - 1;
            // 将该牌与其左边位置的牌逐个比较。左边的牌若比该牌大,就集体右移，直到腾出合适的位置
            while (i >= 0 && array[i] > key) {
                // 逐个右移,给更小的元素让位
                array[i + 1] = array[i];
                i = i - 1;
            }
            array[i + 1] = key;

        }
    }
}
