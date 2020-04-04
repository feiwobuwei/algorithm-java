package sort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 附2 桶排序
 * <p>
 * time O(n+m+n(lgn-lgm)
 * space O(m+n)
 * n -- 数组长度 元素个数  m -- 分成 m 个桶
 * 理想情况下 当元素分布均匀时time O(n)
 * 最坏情况下 当元素分布极不均匀 time O(n^2)
 *
 * @author minwei
 */
public class BucketSort {

    public static void main(String[] args) {

        int[] test = new int[]{21, 8, 6, 11, 36, 50, 27, 42, 0, 12};
        int[] ints = bucketSort(test);
        System.out.println(Arrays.toString(ints));

        System.out.println();
        System.out.println("==================================");

        int[] test2 = new int[]{2, 5, 3, 0, 2, 3, 0, 3};
        int[] ints1 = bucketSort(test2);
        System.out.println(Arrays.toString(ints1));

        System.out.println();
        System.out.println("==================================");

        double[] arr = new double[]{4.5, 0.84, 3.25, 2.18, 0.5};
        double[] doubles = bucketSort(arr);
        System.out.println(Arrays.toString(doubles));

    }

    /**
     * double型数组
     *
     * @param arr 待排序数组
     */
    private static double[] bucketSort(double[] arr) {

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (double i1 : arr) {
            max = Math.max(max, i1);
            min = Math.min(min, i1);
        }

        // 非整形的数组 不能使用下面的公式来计算桶数
//        int bucketNum = (int) ((max - min) / arr.length + 1);

        // 1 先获取区间差值
        double span = max - min;

        // 用桶数再确定区间跨度
        int bucketNum = arr.length;

        // 2 初始化桶.因为桶大小经常改变 所以使用动态数组
        ArrayList<ArrayList<Double>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<>());
        }

        // 3 将每个元素放入到自己对应的桶中(区间)
        for (double v : arr) {
            // 插值公式
            int num = (int) ((v - min) * (bucketNum - 1) / span);
            bucketArr.get(num).add(v);
        }

        // 4 对每个桶内部进行排序
        for (ArrayList<Double> doubles : bucketArr) {
            // 桶内的排序使用任意一个原址排序即可,这里使用选择排序
            selectSort2(doubles);
        }

        // 5 将桶中元素合并到输出数组中
        double[] sortedArray = new double[arr.length];
        int index = 0;

        for (ArrayList<Double> doubles : bucketArr) {
            for (double ele : doubles) {
                sortedArray[index++] = ele;
            }
        }

        System.out.println(bucketArr);

        return sortedArray;
    }

    private static void selectSort2(ArrayList<Double> arr) {

        for (int i = 0; i < arr.size() - 1; i++) {
            // 先假定当前数是最小的
            int min = i;
            for (int j = i + 1; j < arr.size(); j++) {
                // 设置成泛型T 就不能比较大小了
                if (arr.get(j) < arr.get(min)) {
                    min = j;
                }
            }
            if (i != min) {
                swap(arr, i, min);
            }
        }
    }

    // ====================== 分界线 ======================== //

    /**
     * int型数组
     *
     * @param arr 待排序数组
     */
    private static int[] bucketSort(int[] arr) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i1 : arr) {
            max = Math.max(max, i1);
            min = Math.min(min, i1);
        }

        // 1 先获取桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);

        // 2 初始化桶.因为桶大小经常改变 所以使用动态数组
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<>());
        }

        // 3 将每个元素放入到自己对应的桶中(区间)
        for (int ele : arr) {
            int num = (ele - min) / (arr.length);
            bucketArr.get(num).add(ele);
        }

        // 4 对每个桶内部进行排序
        for (ArrayList<Integer> integers : bucketArr) {
            // 桶内的排序使用任意一个原址排序即可,这里使用选择排序
            selectSort(integers);
        }

        // 5 将桶中元素合并到输出数组中
        int[] sortedArray = new int[arr.length];
        int index = 0;

        for (ArrayList<Integer> integers : bucketArr) {
            for (int ele : integers) {
                sortedArray[index++] = ele;
            }
        }

        System.out.println(bucketArr);

        return sortedArray;
    }

    private static void selectSort(ArrayList<Integer> arr) {

        for (int i = 0; i < arr.size() - 1; i++) {
            // 先假定当前数是最小的
            int min = i;
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(min)) {
                    min = j;
                }
            }
            if (i != min) {
                swap(arr, i, min);
            }
        }
    }

    private static <T> void swap(ArrayList<T> arr, int i, int j) {
        T t1 = arr.get(i);
        T t2 = arr.get(j);
        arr.set(i, t2);
        arr.set(j, t1);
    }

}
