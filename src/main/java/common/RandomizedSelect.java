package common;

import java.util.Random;

/**
 * 线性时间选择  平均时间复杂度O(n)  最坏 O(n^2)
 * <p>
 * 给定线性序集中 n 个元素和一个整数 k, 1 <= k <= n, 要求找出这 n 个元素中第 k 小的元素。
 * 即如果将这 n 个元素依其线性序排列时，排在第 k 个位置的元素即为要找的元素。
 * <p>
 * 基于快速排序的 分治算法
 *
 * @author minwei
 */
public class RandomizedSelect {

    public static void main(String[] args) {

        int[] a = new int[]{1, 3, 2, 6, 5, 8, 4, 9, 7, 0};

        // 选出第四大的数
        int t = select(a, 0, a.length - 1, 4);
        System.out.println(t);
    }


    /**
     * @param a      待查询数组
     * @param low    左游标
     * @param high   右游标
     * @param target 待查询目标
     * @return 第target小的数
     */
    private static int select(int[] a, int low, int high, int target) {

        // 递归终止条件:区间大小为1(左右游标相遇)
        if (low == high) {
            return a[low];
        }

        // 将数组以i为基准分为两部分，左边的都小于i，右边的都大于i
        // 此处会用到快速排序算法中的划分方法来找基准
        // 最坏情况每次随机产生的主元都是当前的最大值 而我们要找的是第1小或第2小的元素
        int i = randPart(a, low, high);

        //数组左半部分的长度
        int length = i - low + 1;
        // 如果第target小的数小于等于左半部分的长度，则这个数在此部分内
        if (target <= length) {
            return select(a, low, i, target);
        }
        // 如果第target小的数大于左半部分的长度，则这个数在右半部分内，
        // 且左半部分的数都小于第target小的数 所以现在是找第 target - length 小 的数
        else {
            return select(a, i + 1, high, target - length);
        }
    }


    /**
     * 划分寻找基准-随机化优化
     *
     * @param a    待查询数组
     * @param low  左游标
     * @param high 右游标
     * @return 主元
     */
    private static int randPart(int[] a, int low, int high) {

        Random r = new Random();
        // 随机产生一个 low 到 high 的整数
        int flag = low + r.nextInt(high - low + 1);

        // 记录主元的值
        int pivot = a[flag];

        // 交换a[low]与a[flag]的位置
        a[flag] = a[low];
        a[low] = pivot;

        // 循环直到左右游标相遇(low=high)
        // 此时相遇点左边的数都小于等于pivot 右边的数都大于pivot
        while (low < high) {
            // 1、从右往左找当前主元右边第一个比基准小或等的数。
            // 右边每有一个比基准大的数,就把右游标左移一位
            while (low < high && a[high] > pivot) {
                high--;
            }
            // 如果左右游标还未相遇,用当前左游标的刻度记录右游标的值
            // 例如左游标为2,则说明刻度0,1都是比基准小的数。现在右边的第一个比基准小的数就放在2的位置
            if (low < high) {
                a[low] = a[high];
            }
            // 2、然后继续从左往右找比当前主元左边第一个比基准大的数。(例如现在就是从2开始)
            // 左边每有一个比基准小的数,就把左游标右移一位
            while (low < high && a[low] <= pivot) {
                low++;
            }
            // 如果左右游标还未相遇 用右游标的刻度记录左游标的值
            // 例如右游标为8,则说明刻度9,10都是比基准大的数.现在左边的第一个比基准大的数就放在8的位置
            if (low < high) {
                a[high] = a[low];
            }
        }

        // 将pivot放到low和high相遇的地方(此处也可以是a[low])
        a[high] = pivot;

        return high;
    }
}
