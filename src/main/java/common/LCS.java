package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最长公共子序列  LCS -- longest common subsequence
 * 动态规划问题
 *
 * @author minwei
 */
public class LCS {

    public static void main(String[] args) {

        String[] orgX = new String[]{"A", "B", "C", "B", "D", "A", "B"};
        String[] orgY = new String[]{"B", "D", "C", "A", "B", "A"};

        String[] strX = addHead(orgX);
        String[] strY = addHead(orgY);

        List<String[][]> resultList = lcs(strX, strY);

        // 第一个返回值是二维字符串数组b 记录运行轨迹
        String[][] path = resultList.get(0);

        printStringMatrix(path);

        System.out.println("=====================================================");

        // 第二个返回值是二维字符串数组d 记录LCS长度
        String[][] length = resultList.get(1);

        printStringMatrix(length);

        // 返回值第二个二维数组右下角的值必为LCS长度
        System.out.println("LCS的长度为" + length[length.length - 1][length[0].length - 1]);

        printLCS(path, strX, strX.length - 1, strY.length - 1);
    }

    private static String[] addHead(String[] original) {
        String[] result = new String[original.length + 1];
        // 内部用一个开头加 null (String的默认值 ) 的数组替换
        System.arraycopy(original, 0, result, 1, original.length);
        System.out.println(Arrays.toString(result));
        return result;
    }

    /**
     * LCS算法
     *
     * @param x 要比较的第一个字符串
     * @param y 要比较的第二个字符串
     * @return 返回为1个含2个二维数组的列表，第一个记录运行轨迹 第二个记录LCS长度
     */
    private static List<String[][]> lcs(String[] x, String[] y) {

        int m = x.length;
        int n = y.length;

        List<String[][]> result = new ArrayList<>();

        // 状态方程 记录LCS长度
        int[][] length = new int[m][n];
        // 记录运行轨迹
        String[][] path = new String[m][n];

        // 将字符串矩阵初始化
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                path[i][j] = "空";
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (x[i].equals(y[j])) {
                    // 出现相同元素，标识符加1
                    length[i][j] = length[i - 1][j - 1] + 1;
                    // 标识符
                    path[i][j] = "左上";
                } else {
                    if (length[i - 1][j] >= length[i][j - 1]) {
                        length[i][j] = length[i - 1][j];
                        path[i][j] = "上";
                    } else {
                        length[i][j] = length[i][j - 1];
                        path[i][j] = "左";
                    }
                }
            }
        }
        result.add(path);
        // int 型转 String
        String[][] stringLength = new String[m][n];

        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                // 將int转为String
                stringLength[i][j] = String.valueOf(length[i][j]);
            }
        }
        result.add(stringLength);
        return result;
    }

    /**
     * 打印LCS
     *
     * @param path LCS返回值的表示运行轨迹二维数组
     * @param x    第一个字符串
     * @param i    第一个字符串长度(不含0)初始调用长度减1
     * @param j    第二个字符串长度(不含0)初始调用长度减1
     */
    private static void printLCS(String[][] path, String[] x, int i, int j) {
        // 递归法  也可以使用迭代法
        if (i == 0 || j == 0) {
            return;
        }
        if ("左上".equals(path[i][j])) {
            printLCS(path, x, i - 1, j - 1);
            System.out.print(x[i] + " ");
        } else if ("上".equals(path[i][j])) {
            printLCS(path, x, i - 1, j);
        } else {
            printLCS(path, x, i, j - 1);
        }
    }

    private static void printStringMatrix(String[][] u) {
        for (String[] row : u) {
            for (String element : row) {
                System.out.print(element + "\t\t");
            }
            System.out.println();
        }
    }

}
