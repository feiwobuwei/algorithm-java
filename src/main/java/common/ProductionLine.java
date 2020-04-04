package common;


/**
 * 算法导论第二版的 动态规划问题
 * 第三版替换为 切割钢条问题
 */
public class ProductionLine {

    public static void main(String[] args) {
        // 一维数组
        int[] instance_e = new int[]{2, 4};
        int[] instance_x = new int[]{3, 2};

        // int n = 6; //6个装配站
        // 固定长度的矩阵数组 2行6列
        int[][] instance_a = new int[][]{{7, 9, 3, 4, 8, 4}, {8, 5, 6, 4, 5, 7}};

        // 固定长度的矩阵数组 2行5列
        int[][] instance_t = new int[][]{{2, 3, 1, 3, 4}, {2, 1, 2, 2, 1}};

        // 返回最小时间和最短路线编号，以一维数组形式
        int[] instance_min = fastestWay(instance_a, instance_t, instance_e, instance_x, 6);

        System.out.println("最小时间：" + instance_min[0]);
        System.out.println("最短路线编号：" + instance_min[1]);

        // 返回记录的站点，以二维数组形式
        int[][] instance_l = fastestWay2(instance_a, instance_t, instance_e, instance_x, 6);

        // 二维数组 遍历输出
        for (int[] ints : instance_l) {
            for (int j = 0; j < instance_l[0].length; j++) {
                System.out.println(ints[j] + " ");
            }
            System.out.println();
        }

        System.out.println("路线的倒序输出:");
        reversePrintStations(instance_l, instance_min[1], 6);
        System.out.println("路线的正序输出:");
        forwardPrintStations(instance_l, instance_min[1], 6);

    }

    // 返回最小值和最短路线
    private static int[] fastestWay(int[][] a, int[][] t, int[] e, int[] x, int n) {
        int[][] f = new int[2][6];
        int[][] l = new int[2][5];
        int[] min = new int[2]; // 最小值

        f[0][0] = e[0] + a[0][0];
        f[1][0] = e[1] + a[0][0];

        for (int j = 1; j < n; j++) {
            if (f[0][j - 1] + a[0][j] <= f[1][j - 1] + t[1][j - 1] + a[0][j]) {
                f[0][j] = f[0][j - 1] + a[0][j];
                l[0][j - 1] = 1;

            } else {
                f[0][j] = f[1][j - 1] + t[1][j - 1] + a[0][j];
                l[0][j - 1] = 2;
            }

            if (f[1][j - 1] + a[1][j] <= f[0][j - 1] + t[0][j - 1] + a[1][j]) {
                f[1][j] = f[1][j - 1] + a[1][j];
                l[1][j - 1] = 2;

            } else {
                f[1][j] = f[0][j - 1] + t[0][j - 1] + a[1][j];
                l[1][j - 1] = 1;
            }

        }

        // 其实可以说明有等于，即路线不只一条
        if (f[0][n - 1] + x[0] <= f[1][n - 1] + x[1]) {
            min[0] = f[0][n - 1] + x[0];
            min[1] = 1;
        } else {
            min[0] = f[1][n - 1] + x[1];
            min[1] = 2;
        }

        return min;

    }

    // 返回最短路线二维数组
    private static int[][] fastestWay2(int[][] a, int[][] t, int[] e, int[] x, int n) {
        int[][] f = new int[2][6];
        int[][] l = new int[2][5];
        int[] min = new int[2]; // 最小值

        f[0][0] = e[0] + a[0][0];
        f[1][0] = e[1] + a[1][0];
        for (int j = 1; j < n; j++) {
            if (f[0][j - 1] + a[0][j] <= f[1][j - 1] + t[1][j - 1] + a[0][j]) {
                f[0][j] = f[0][j - 1] + a[0][j];
                l[0][j - 1] = 1;

            } else {
                f[0][j] = f[1][j - 1] + t[1][j - 1] + a[0][j];
                l[0][j - 1] = 2;
            }

            if (f[1][j - 1] + a[1][j] <= f[0][j - 1] + t[0][j - 1] + a[1][j]) {
                f[1][j] = f[1][j - 1] + a[1][j];
                l[1][j - 1] = 2;

            } else {
                f[1][j] = f[0][j - 1] + t[0][j - 1] + a[1][j];
                l[1][j - 1] = 1;
            }

        }

        if (f[0][n - 1] + x[0] <= f[1][n - 1] + x[1]) {
            min[0] = f[0][n - 1] + x[0];
            min[1] = 1;
        } else {
            min[0] = f[1][n - 1] + x[1];
            min[1] = 2;
        }

        return l;
    }

    // 输出需要行走的站名,倒序
    private static void reversePrintStations(int[][] l, int l_n, int n) {
        int i = l_n;
        System.out.println("line " + i + "," + "station " + n);

        for (int j = n - 1; j > 0; j--) {
            i = l[i - 1][j - 1]; // 倒推过程
            System.out.println("line " + i + "," + "station " + j);
        }
    }

    // 输出需要行走的站名,正序
    private static void forwardPrintStations(int[][] l, int l_n, int n) {
        printStations(l, l_n, n); // 采用递归
        System.out.print("line " + l_n + "," + "station " + n);
    }

    // 递归调用
    private static void printStations(int[][] l, int l_n, int n) {
        int i = l_n;
        int j = n - 1; // 下标处理

        if (j > 0) {
            i = l[i - 1][j - 1]; // 递归过程
            printStations(l, i, n - 1);
            System.out.println("line " + i + "," + "station " + j);
        }
    }

}
