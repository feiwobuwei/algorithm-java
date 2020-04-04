package sort;

import java.util.Arrays;

/**
 * 4 二叉堆排序
 * <p>
 * time: O(n*lgn)
 * space: O(1)
 *
 * @author minwei
 */
public class BinaryHeapSort {

    public static void main(String[] args) {
        int[] input = new int[]{16, 4, 10, 14, 7, 9, 3, 2, 8, 1};
        int[] result = heapSort(input);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 堆排序算法
     * time:  O(n)+(n-1)O(lgn) = O(n*lgn)
     *
     * @param A 输入数组
     */
    private static int[] heapSort(int[] A) {
        // heapSize 初始值为输入数组长度
        int heapSize = A.length;

        // 先通过buildMaxHeap将输入数组建为最大堆
        buildMaxHeap(A);

        // 在A[0...A.length -1]上构造一个新的最大堆
        // 然后不断重复这一过程 直到堆的大小变为1(索引值为0)
        for (int i = A.length - 1; i >= 0; i--) {
            // 每次交换时A[0]是当前二叉堆中的最大元素
            swap(A, 0, i);
            // 最大元素已经就位 要维护性质的数组大小减1
            heapSize--;
            // 而新的根节点可能会违背最大堆的性质。为了维护最大堆的性质,需要再次调用maxHeapify方法
            maxHeapify(A, 0, heapSize);
        }

        return A;
    }

    /**
     * 建立最大堆的辅助方法
     * <p>
     * nonLeaf -- 用作非叶节点的边界, 大于该索引的值都是叶节点
     * 叶节点可以看作只包含一个元素的堆。所以只需要处理小于等于该索引的节点
     *
     * @param A 待排序数组
     */
    private static void buildMaxHeap(int[] A) {
        int nonLeaf = (A.length >> 1) - 1;

        for (int i = nonLeaf; i >= 0; i--) {
            maxHeapify(A, i, A.length);
        }

    }

    /**
     * 维护最大堆性质的辅助方法
     *
     * @param A        输入数组
     * @param i        使以下标i为根节点的所有子树遵循最大堆的性质
     * @param heapSize 要维护最大堆性质的数组大小
     */
    private static void maxHeapify(int[] A, int i, int heapSize) {
        int l = (i << 1) + 1;
        int r = (i << 1) + 2;

        // 记录最大元素的索引
        int largest;

        // 边界限定使用的是 heapSize属性(因为已经排好位置的元素不再参与排序), 而不总是数组长度

        // 找到父,左,右三个节点中的最大值 获取其索引
        // && 自带短路效果 不用担心越界问题
        if (l < heapSize && A[l] > A[i]) {
            largest = l;
        } else {
            largest = i;
        }
        if (r < heapSize && A[r] > A[largest]) {
            largest = r;
        }

        // 如果父节点已经是最大值,不做任何处理
        // 否则把最大值换给父节点 然后递归往下 重新使二叉堆满足性质
        if (largest != i) {
            swap(A, largest, i);
            maxHeapify(A, largest, heapSize);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
