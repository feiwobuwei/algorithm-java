package sort;

import java.util.Arrays;

/**
 * 2 归并排序
 * <p>
 * time: O(n*lgn)
 * space: O(n)
 *
 * @author minwei
 */
public class MergeSort {

    public static void main(String[] args) {
        // 9个元素
        int[] arr = {8, 2, 3, 4, 5, 6, 1, 7, 9};
        // int[] test = {5, 2, 4, 7, 1, 3, 2, 6};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * @param arr   输入数组
     * @param start 头部指针
     * @param end   尾部指针
     */
    private static void mergeSort(int[] arr, int start, int end) {

        // 一直二分 直到头部指针和尾部指针相遇
        if (start < end) {
            int mid = (end - start) / 2 + start;
            // 前者包含⌈n/2⌉个元素 (>= n/2) 后者包含 ⌊n/2⌋ 个元素 因为索引从0开始
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);

            merge(arr, start, mid, end);
        }
    }

    /**
     * 辅助排序过程
     * p <= q < r
     *
     * @param arr   输入数组
     * @param start 待排序段的首指针
     * @param mid   待排序段的分割点
     * @param end   待排序段的尾指针
     */
    private static void merge(int[] arr, int start, int mid, int end) {

        // 左边区间元素的个数
        int n1 = mid - start + 1;
        // 右边区间元素的个数
        int n2 = end - mid;

        // 辅助排序数组
        int[] leftArr = new int[n1 + 1];
        int[] rightArr = new int[n2 + 1];

//        for (int i = 0; i < n1; i++) {
//            L[i] = A[p + i];
//        }

        // 和上面等效
        System.arraycopy(arr, start, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        // 让数组最后一个元素为无穷大
        leftArr[n1] = Integer.MAX_VALUE;
        rightArr[n2] = Integer.MAX_VALUE;

        // 左区间和右区间各自的游标
        int leftCursor = 0;
        int rightCursor = 0;

        while (start <= end) {
            // 局部优化: 如果某一边数组已经到了尾部，
            // 则可以直接将另一边剩余的元素拷贝到原数组剩余的位置中，不需要再一个个的比较
            // 例如一边 1 2 3 4 。另一边 5 6 7 8 这样的情况
            if (leftArr[leftCursor] == Integer.MAX_VALUE) {
                // rightArr.length - 1 是为了去掉尾部的哨兵 Integer.MAX_VALUE
                // 左数组已经到了哨兵处，把右数组的所有元素拷贝回原数组现在剩余的区间
                System.arraycopy(rightArr, rightCursor, arr, start, rightArr.length - 1 - rightCursor);
                break;
            } else if (rightArr[rightCursor] == Integer.MAX_VALUE){
                // 镜像处理
                System.arraycopy(leftArr, leftCursor, arr, start, leftArr.length - 1 - leftCursor);
                break;
            }

            // 左右临时数组都没有到哨兵处 正常归并
            if (leftArr[leftCursor] <= rightArr[rightCursor]) {
                arr[start++] = leftArr[leftCursor++];
            } else {
                arr[start++] = rightArr[rightCursor++];
            }
        }

    }
}