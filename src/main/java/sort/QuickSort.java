package sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * 5 快速排序
 * <p>
 * time 最坏O(n2^)，平均O(n*lgn)
 * space O(n*lgn)
 *
 * @author minwei
 */
public class QuickSort {

    @Test
    public void test() {
        int[] test = {8, 2, 3, 4, 5, 6, 1, 7};
        quickSort(test);
        System.out.println(Arrays.toString(test));
    }

    @Test
    public void test2() {
        int[] test = {8, 2, 3, 4, 5, 6, 1, 7};
        // 测试双路快排
        quickSort2(test);
        System.out.println(Arrays.toString(test));
    }

    private static void quickSort(int[] A) {
        // 方法重载 方便调用
        quickSort(A, 0, A.length - 1);
    }

    /**
     * @param A 待排序数组
     * @param p 头指针
     * @param r 尾指针
     */
    private static void quickSort(int[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }

    /**
     * 快速排序-数组划分函数
     *
     * @param A 输入数组
     * @param p 头指针
     * @param r 尾指针
     * @return 主元下标
     */
    private static int partition(int[] A, int p, int r) {

        // 优化方案是随机选一个该区间元素作为主元
        int random = new Random().nextInt(r - p + 1) + p;
        // 将该元素换到末尾
        swap(A, random, r);

        // 选择末尾元素作为主元
        int x = A[r];

        // i总是位于待确定区间的左边
        int i = p - 1;

        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                // 每有一个小于等于主元的元素 游标向右移动一位
                i++;
                swap(A, i, j);
            }
        }
        // 得知有i个数小于主元 故主元应放在i+1的位置
        swap(A, i + 1, r);
        // 返回主元素的位置 此时A[i+1]号位置的元素就已经就位(也是最终位置)
        return i + 1;
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // ==================== 双路快排 =================== //

    /**
     * 双路快排
     *
     * @param array 待排序数组
     */
    public static void quickSort2(int[] array) {
        // 方法重载 方便调用
        quick2(array, 0, array.length - 1);
    }

    /**
     * 处理有大量重复元素的情况
     *
     * @param array 待排序数组
     * @param start 头指针
     * @param end   尾指针
     */
    private static void quick2(int[] array, int start, int end) {
        if (start < end) {
            int key = selectionKey2(array, start, end);
            quick2(array, start, key - 1);
            quick2(array, key + 1, end);
        }
    }

    private static int selectionKey2(int[] array, int start, int end) {
        // 随机选一个该区间元素作为主元
        int pivot = (int) (Math.random() * (end - start + 1) + start);
        // 把主元放到开始的位置
        swap(array, pivot, start);
        // 记录主元指针对应的值
        int pivotValue = array[start];
        // 从头开始往后
        int left = start + 1;
        // 从尾开始往前
        int right = end;

        // 结束循环时 left==right
        while (true) {
            while (left <= end && array[left] < pivotValue) {
                left++;
            }
            while (right >= start + 1 && array[right] > pivotValue) {
                right--;
            }
            // 已经是正确位置
            if (left > right) {
                break;
            }
            // 交换后两个指针都移动一步
            swap(array, left, right);
            left++;
            right--;
        }

        // 将主元安放在正确的位置 right 也可以用 left
        swap(array, start, right);
        return right;
    }
}
