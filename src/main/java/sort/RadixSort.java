package sort;

import java.util.Arrays;

/**
 * 6 基数排序
 * <p>
 * time: O(d(r+n))
 * space: O(rn)
 * r -- 代表关键字基数(即radix 十进制就是10)，
 * d -- 代表最大数字长度(如百位数是3)
 * n -- 代表数组长度
 *
 * @author minwei
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 通用的桶排序算法
     *
     * @param arr 输入数组
     */
    private static void radixSort(int[] arr) {

        // 需要先得到数组中最大元素的位数
        int max = 0;
        for (int ele : arr) {
            if (ele > max) {
                max = ele;
            }
        }

//        // Stream流 与上面等效
//        OptionalInt optionalInt = Arrays.stream(arr).max();
//        // 防止NPE
//        if (optionalInt.isPresent()) {
//            max = optionalInt.getAsInt();
//        }

        // 变为字符串 获取其长度
        int maxLength = (max + "").length();

        // 十进制下 从左往右摆10个桶 表示0~9(横向)
        // 纵向都取为数组长度 是防止出现 4 14 24 34 这样的极端情况
        int[][] bucket = new int[arr.length][10];
        // 10个游标 每个桶一个 用于标识当前桶里有几个数
        int[] cursor = new int[10];

        // 外部循环同时维持两个变量 d次循环
        // n的步长为每次乘以10
        for (int k = 0, n = 1; k < maxLength; k++, n *= 10) {
            // n次循环
            for (int ele : arr) {
                int digit = (ele / n) % 10;
                // 第一次53取得的是3 放入第4个桶 cursor[3]是0(表示第3个桶中还没有数),放入第一格中
                // 第二次3取得的又是3 仍放入第4个桶中 cursor[3]现在是1(表示第3个桶中有1个数),放入第二格中
                bucket[cursor[digit]][digit] = ele;
                // 对应桶的游标下移一位
                cursor[digit]++;
            }

            // 原数组的指针
            int index = 0;
            // 从左往右查看桶中是否有元素。十进制 r次循环
            for (int i = 0; i < 10; i++) {
                // 如果桶中有数据,按置入桶的顺序取出桶里的数据放到原来的数组中
                if (cursor[i] != 0) {
                    // 游标刻度值
                    int scale = 0;
                    while (scale < cursor[i]) {
                        arr[index++] = bucket[scale++][i];
                    }
                    // 清空原来的桶只需要将游标复位即可
                    cursor[i] = 0;
                }
            }
        }
    }
}
