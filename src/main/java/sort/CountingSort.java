package sort;

import java.util.Arrays;

/**
 * 附1 计数排序
 * <p>
 * time; O(n) space: O(n)
 * n - 数组最小元素与最大元素的区间长度
 * 所以数组分布越紧密效率越高
 *
 * @author minwei
 */
public class CountingSort {

    public static void main(String[] args) {
//        int[] arr = new int[]{2, 5, 3, 0, 2, 3, 0, 3};
        int[] arr = new int[]{92, 95, 93, 90, 92, 93, 90, 93};

        int[] result = countingSort(arr);
        System.out.println(Arrays.toString(result));

    }

    /**
     * 计数排序要求待排元素都是0和正整数
     *
     * @param arr 输入数组
     * @return 排好序的数组
     */
    private static int[] countingSort(int[] arr) {

        // 需要先得到数组中最大元素和最小元素
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int ele : arr) {
            min = Math.min(ele, min);
            max = Math.max(ele, max);
        }

        // 方法重载
        return countingSort(arr, min, max + 1);
    }

    /**
     * 加入min 优化空间性能
     *
     * @param arr 输入数组
     * @param p   所有元素大于等于p
     * @param r   所有元素都应小于r
     */
    private static int[] countingSort(int[] arr, int p, int r) {

        // 作为排好序的数组来返回
        int[] result = new int[arr.length];

        // index为临时数组 n个元素都是介于p到r的整数

        // 示例 min=0 max=5 k=6 index需要6格
        // 但也有可能是[90 - 95]区间的数字需要排序
        // 按以前教材的写法 k直接等于r(也就是95) index数组长度为96,此时会浪费90格空间
        int k = r - p;
        int[] index = new int[k];

        // 元素j每出现一次,其对应索引值+1
        for (int ele : arr) {
            // 去掉偏移量P
            index[ele - p]++;
        }

        // index[i]包含小于或等于i的元素个数 2 2 4 7 7 8
        for (int i = 1; i < k; i++) {
            index[i] += index[i - 1];
        }

        // 把每个元素arr[j]放到它在输出数组result的正确位置上

        // 如果所有n个元素都是互异的,那么对于每个arr[j]值来说,
        // index[arr[j]]就是arr[j]在输出数组中的最终正确位置值

        // 因为共有index[arr[j]]个元素小于或等于arr[j]
        for (int j = arr.length - 1; j >= 0; j--) {
            // 注意数组索引是位置值减1
            result[index[arr[j] - p] - 1] = arr[j];
            // 表示这个元素已经被用掉一个,前移一格
            index[arr[j] - p]--;
        }
        return result;
    }
}
