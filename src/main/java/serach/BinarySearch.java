package serach;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 折半查找的前提条件是需要有序顺序存储，对于静态查找表，一次排序后不再变化，折半查找能得到不错的效率。
 * 但对于需要频繁执行插入或删除操作的数据集来说，维护有序的排序会带来不小的工作量，不建议使用。
 *
 * @author minwei
 */
public class BinarySearch {

    @Test
    public void test() {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int result = binarySearch(arr, 1000);
        System.out.println(result);
    }

    @Test
    public void test2() {
        int[] arr = {1, 8, 10, 89, 1000, 1000, 1000, 1234};
        List<Integer> list = binarySearch2(arr, 0, arr.length - 1, 1000);
        // ArrayList 不会自动排序
        System.out.println(list);
        System.out.println("==========================");
        list.stream().sorted().forEach((i) -> System.out.print(i + " "));
    }

    @Test
    public void test3() {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int result = binarySearch3(arr, 1000);
        System.out.println(result);
    }

    private static int binarySearch(int[] arr, int value) {
        return binarySearchRecursive(arr, 0, arr.length - 1, value);
    }

    /**
     * 二分法-递归法
     * <p>
     * time O(lgn)
     *
     * @param arr   待查找数组
     * @param left  左边索引
     * @param right 右边索引
     * @param value 待查找值
     * @return 找到了就返回对应的下标 否则-1
     */
    private static int binarySearchRecursive(int[] arr, int left, int right, int value) {

        if (left > right) {
            return -1;
        }

        int mid = (right - left) / 2 + left;
        int midVal = arr[mid];

        if (value > midVal) {
            // 向右递归
            return binarySearchRecursive(arr, mid + 1, right, value);
        } else if (value < midVal) {
            // 向左递归
            return binarySearchRecursive(arr, left, mid - 1, value);
        } else {
            // 正好找到了
            return mid;
        }

    }

    /**
     * 尽量返回接口而非实际的类型，
     * 如返回List而非ArrayList，
     *
     * @param arr   待查找数组
     * @param left  左边索引
     * @param right 右边索引
     * @param value 待查找值
     * @return 找到了返回所有满足条件的索引
     */
    private static List<Integer> binarySearch2(int[] arr, int left, int right, int value) {

        if (left > right) {
            // 如果没有目标值 就返回一个空数组
            return new ArrayList<>();
        }

        int mid = (right - left) / 2 + left;
        int midVal = arr[mid];

        if (value > midVal) {
            // 向右递归
            return binarySearch2(arr, mid + 1, right, value);
        } else if (value < midVal) {
            // 向左递归
            return binarySearch2(arr, left, mid - 1, value);
        } else {
            List<Integer> result = new ArrayList<>();
            result.add(mid);

            int temp = mid - 1;

            // 一直循环到该值左边的值与目标值不同
            while (temp >= 0 && arr[temp] == value) {
                // 自动装箱
                result.add(temp);
                temp -= 1;
            }

            temp = mid + 1;

            // 一直循环到该值右边的值与目标值不同
            while (temp <= arr.length - 1 && arr[temp] == value) {
                // 自动装箱
                result.add(temp);
                temp += 1;
            }

            // 正好找到了
            return result;
        }

    }

    /**
     * 二分法-迭代法  时间复杂度 O（log(n)）
     *
     * @param arr   待查找数组
     * @param value 待查找值
     * @return 找到了就返回对应的下标 否则-1
     */
    private static int binarySearch3(int[] arr, int value) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] > value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
