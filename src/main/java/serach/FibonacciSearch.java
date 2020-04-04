package serach;


import java.util.Arrays;

/**
 * 斐波那契查找
 */
public class FibonacciSearch {

    public static void main(String[] args) {

        int[] arr = {1, 8, 10, 89, 1000, 1234};

//        System.out.println(Arrays.toString(fib(10)));

        int result = fibonacciSearch(arr, 1234);
        System.out.println(result);

        System.out.println("================");

        int[] arr1 = new int[100];
        for (int i = 0; i < 100; i++) {
            arr1[i] = i + 1;
        }

        int result1 = fibonacciSearch(arr1, 80);
        System.out.println(result1);

    }

    // 获取对应于k的斐波那契数列 迭代法 输入参数必须大于2
    private static int[] fib(int k) {

        if (k < 2) {
            throw new RuntimeException("输入参数必须必须大于等于2");
        }

        int[] f = new int[k];

        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < k; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f;

    }

    // 迭代法
    private static int fibonacciSearch(int[] arr, int value) {

        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int mid;

        int count = 2;
        // 获取大于 count 的最小的斐波那契数(-1)的数
        while (n > fib(count)[count - 1] - 1) {
            count++;
        }

        // 1 1 2 3 5 8 count = 6

        // 本次排序使用范围内的斐波那契数列
        int[] F = fib(count);

        int k = count - 1; // k 斐波那契数列最大下标

        // 新数组的长度
        int newLength = F[k] - 1;

        // 扩容数组 没有数值的部分先用0填充
        int[] temp = Arrays.copyOf(arr, newLength);

        /// 将不满的数值补全，使长度为F[k] - 1
        for (int i = high + 1; i < newLength; i++) {
            temp[i] = arr[high];
        }

        System.out.println(Arrays.toString(temp));

        while (low <= high) {
            mid = low + F[k - 1] - 1;
            if (value > temp[mid]) {
                low = mid + 1;
                k = k - 2; // mid右边的个数，即F[k-2]个
            } else if (value < temp[mid]) {
                high = mid - 1;
                k = k - 1; // mid左边的个数
            } else {  // 值相等
                if (mid < n - 1) {
                    return mid;
                } else {
                    return n - 1; // 找到的是原数组的最后一个元素
                }
            }
        }

        return -1;
    }

}
