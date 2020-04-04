package common;

import org.junit.Test;

/**
 * 0-1 背包问题
 */
public class KnapsackProblem {

    /**
     * 物品的重量
     */
    private int[] w = {1, 4, 3};

    /**
     * 物品的价值
     */
    private int[] v = {1500, 3000, 2000};

    @Test
    public void test1() {
        // 背包的重量
        int m = 4;
        int value = maxValue(w, v, m);
        System.out.println("maxValue: " + value);

    }

    @Test
    public void test2() {
        int m = 5;
        int value = maxValue(w, v, m);
        System.out.println("maxValue: " + value);
    }

    @Test
    public void test3() {
        int m = 7;
        int value = maxValue(w, v, m);
        System.out.println("maxValue: " + value);
    }

    /**
     * 一 状态定义: u[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
     * 二 边界条件: u[0][0] = 0 ;
     * 三 状态转移方程
     * 1. u[i][0]=u[0][j]=0  -- 没有选择过物品 或 背包容量为0
     * 2. 当w[i]>j时，有u[i][j]=u[i-1][j] -- 若物品超出背包容量 不可能放入 价值不变
     * 3. 当w[i]<=j时，有u[i][j] = max{u[i-1][j],u[i-1][j-w[i]]+v[i]}
     * 上式说明
     * 第一项是前一次的取得的最大价值
     * 第二项是先扔掉前面等于该物品重量的物品背包里剩余物品的价值 与 新加入的物品的价值之和
     * <p>
     * 第三种情况的 JAVA 代码格式
     * u[i][j] = Math.max(u[i - 1][j], u[i - 1][j - w[i - 1]] + v[i - 1]);
     *
     * @param w w[i] 第i个物品的重量
     * @param v v[i] 第i个商品的价值
     * @param m 背包重量
     * @return 最大价值
     */
    private static int maxValue(int[] w, int[] v, int m) {
        // 物品的个数
        int n = w.length;
        int[][] u = new int[n + 1][m + 1];

        // 记录放置情况(可以不需要)
        int[][] path = new int[n + 1][m + 1];

        // 控制行 从上往下
        for (int i = 1; i < u.length; i++) {
            // 控制列 从左往右
            for (int j = 1; j < u[i].length; j++) {
                // 注意下标
                if (w[i - 1] > j) {
                    u[i][j] = u[i - 1][j];
                } else {
                    if (u[i - 1][j] < u[i - 1][j - w[i - 1]] + v[i - 1]) {
                        u[i][j] = u[i - 1][j - w[i - 1]] + v[i - 1];
                        // 策略有更新 路径也更新
                        path[i][j] = 1;
                    } else {
                        // 否则延续以前的策略
                        u[i][j] = u[i - 1][j];
                    }
                }
            }
        }

        printMatrix(u);
        System.out.println("===============================");
        printPath(path, w);

        return u[n][m];
    }

    /**
     * 打印矩阵的辅助方法
     *
     * @param u 待打印矩阵
     */
    private static void printMatrix(int[][] u) {
        for (int[] row : u) {
            for (int element : row) {
                System.out.printf("%-8d", element);
                // “-”表示左对齐，在数字宽度前面加上“-”号即可。
                // 说明：数字宽度为8，如果要打印的位数小于8，则在后面补足空格；
                // 如果要打印的位数大于8，则打印所有的数字，不会截断。
            }
            System.out.println();
        }
    }

    /**
     * 应该反向输出 因为最后一次得到的结果才是最大的
     *
     * @param path 路径矩阵
     * @param w 重量数组
     */
    private static void printPath(int[][] path, int[] w) {

        printMatrix(path);

        // 行的最大下标
        int i = path.length - 1;
        // 列的最大下标
        int j = path[0].length - 1;

        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包中\n", i);
                // 减去这个物品的重量 注意下标
                j = j - w[i - 1];
            }
            i--;
        }

    }
}
