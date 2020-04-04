package common;


import java.util.Arrays;

/**
 * 八皇后问题
 */
public class EightQueenProblem {

    private static int max = 8;

    // 保存最终摆放位置的一种结果 即每一行皇后的列位置 (肯定不会置于同一行)
    private static int[] array = new int[max];
    // 记录最终的解法数目
    private static int count = 0;
    // 统计冲突次数
    private static int conflictCount = 0;

    public static void main(String[] args) {

        EightQueenProblem test = new EightQueenProblem();
        test.check(0); // 初始调用
        System.out.println("总共解法数: " + count);
        System.out.println("总共判断冲突次数: " + conflictCount);
    }

    // 编写一个方法 放置第n个皇冠
    private void check(int n) {
        // 递归终止条件
        if (n == max) {
            // 索引到了8 其实就是第9个棋子 此时代表已经找到了一个解
            count++;

            // 打印这个解
            Arrays.stream(array).forEach((i) -> System.out.print((i + 1) + " "));
            System.out.println();
            return;
        }

        for (int i = 0; i < max; i++) {
            // 每次都先把当前这个皇后 n 放到该行的第一列
            array[n] = i;
            // 判断是否冲突
            if (!isConflict(n)) {
                check(n + 1);
            }
            // 如果冲突 回溯到上一层 i++ 向右移动一列
        }
    }

    // 判断是否冲突 即是 同一列 同一斜线 (默认不置于同一行)
    // n表示第n个皇后 和前面n-1个皇后 比较
    private boolean isConflict(int n) {
        conflictCount++;

        for (int i = 0; i < n; i++) {
            // 横向间距 Math.abs(array[n] - array[i]) 等于纵向间距 Math.abs(n - i) 即表示在同一斜线
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return true; // 冲突
            }
        }
        return false; // 不冲突
    }

}

