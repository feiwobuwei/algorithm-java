package common;

import java.util.ArrayList;
import java.util.List;

/**
 * 算法导论 矩阵分解
 */
public class MatrixDecomposition {

    public static void main(String[] args) {

        // 矩阵本身就是二维数组
        System.out.println("矩阵乘法测试：");
        double[][] instance_A = new double[][]{{2, 3, 4}, {3, 4, 5}};
        double[][] instance_B = new double[][]{{1, 4}, {5, 3}, {1, 6}};

        double[][] instance_C = multiplyMatrix(instance_A, instance_B);
        printMatrix(instance_C);

        System.out.println("高斯消元法,求LU矩阵：");

        // 矩阵本身就是二维数组
        double[][] Matrix_A = new double[][]{{1, 2, 0}, {3, 4, 4}, {5, 6, 3}};
        double[] b = new double[]{3, 7, 8};
        System.out.println("A矩阵：");
        printMatrix(Matrix_A);

        int n = Matrix_A.length; // 行数
        // 两个辅助矩阵
        double[][] Matrix_L = new double[n][n];
        double[][] Matrix_U = new double[n][n];

        // P是对角矩阵
        int[] pi = new int[]{3, 1, 2}; // 任意给的吗？用来交换行
        double[][] Matrix_P = new double[n][n];

        for (int i = 0; i < Matrix_P.length; i++) {
            for (int j = 0; j < Matrix_P.length; j++) {
                if (j == pi[i] - 1) {
                    Matrix_P[i][j] = 1;
                } else {
                    Matrix_P[i][j] = 0;
                }
            }
        }

        System.out.println("P矩阵：");
        printMatrix(Matrix_P);

        Matrix_A = multiplyMatrix(Matrix_P, Matrix_A);
        System.out.println("处理后的A矩阵：");
        printMatrix(Matrix_A);

        // LU分解，返回一个含有2个二维数组的列表
        List<double[][]> list_LU = LU_Decomposition(Matrix_A, Matrix_L, Matrix_U);
        Matrix_L = list_LU.get(0);
        Matrix_U = list_LU.get(1);

        System.out.println("L矩阵：");
        printMatrix(Matrix_L);

        System.out.println("U矩阵：");
        printMatrix(Matrix_U);

        // 创建单位矩阵
        double[][] Matrix_E = createUnitMatrix(n);
        // L需要加上单位矩阵
        Matrix_L = addMatrix(Matrix_L, Matrix_E);
        System.out.println("加上单位矩阵的L矩阵：");
        printMatrix(Matrix_L);

        System.out.println("最终的计算结果：");
        /*
         * 定理 1 当 m≦n时，一个 m×n 的(0,1) 矩阵P为置换矩阵的充要条件是P的每一行恰有一个 1，每一列恰有一个 1。
         * LUP_Solve(Matrix_L, Matrix_U, pi, b);
         */

        double[] result;
        result = solve_LUP(Matrix_L, Matrix_U, pi, b);

        // 第一种格式化输出方法：
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

        for (double x : result) {
            System.out.println(df.format(x));
        }
    }

    // 矩阵基础加法
    private static double[][] addMatrix(double[][] A, double[][] B) {
        // 不能直接实例化泛型数组
        double[][] C = new double[A.length][A[0].length];

        if (A.length != B.length || A[0].length != B[0].length) {
            // System.out.println("无法相加");
            // return null;
            throw new RuntimeException("行列数不一致");
        } else {
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    C[i][j] = A[i][j] + B[i][j];
                }
            }
            return C;
        }
    }

    // 矩阵基础乘法
    private static double[][] multiplyMatrix(double[][] A, double[][] B) {

        double[][] C = new double[A.length][B[0].length]; // 第一个的行，第二个的列

        if (A.length != B[0].length) {
            throw new RuntimeException("A行数与B列数不一致");
        } else {
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    C[i][j] = 0.0; // 归零
                    for (int k = 0; k < A[0].length; k++) {
                        C[i][j] = C[i][j] + A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }
    }

    // 打印矩阵
    private static void printMatrix(double[][] matrix) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        for (double[] matrix1 : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(df.format(matrix1[j]) + "\t"); // 以制表符对输出格式化
            }
            System.out.println(); // 换行
        }
    }

    /**
     * LU分解法
     *
     * @param A 方阵
     * @param L 下三角矩阵
     * @param U 上三角矩阵
     * @return 将L, U矩阵加入列表可以作为多个返回值
     */
    private static List<double[][]> LU_Decomposition(double[][] A, double[][] L, double[][] U) {
        int n = A.length; // A方阵
        List<double[][]> list = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            U[k][k] = A[k][k]; // 主元

            for (int i = k + 1; i < n; i++) {
                L[i][k] = A[i][k] / U[k][k]; // 下三角矩阵,除以主元
                U[k][i] = A[k][i]; // 上三角矩阵,保持原值
            }

            for (int i = k + 1; i < n; i++) {
                for (int j = k + 1; j < n; j++) {
                    A[i][j] = A[i][j] - L[i][k] * U[k][j]; // 迭代法计算
                }
            }
        }
        list.add(L);
        list.add(U);

        return list;
    }

    /**
     * LUP分解法,主方法
     *
     * @param L  下三角矩阵
     * @param U  上三角矩阵
     * @param pi pi={ 3, 1, 2 }; 任意给的吗？用来交换行
     * @param b  b ={ 3, 7, 8 };
     * @return x 方程的解
     */
    private static double[] solve_LUP(double[][] L, double[][] U, int[] pi, double[] b) {
        int n = L.length;
        double[] y = new double[n];
        double[] x = new double[n];
        double k; // 辅助求和参数
        double q; // 辅助求和参数

        // 正向替换 求y
        for (int i = 0; i < n; i++) {
            k = 0.0;
            for (int j = 0; j <= i - 1; j++) {
                k = k + L[i][j] * y[j];
            }
            y[i] = b[pi[i] - 1] - k;

        }

        // 逆向替换 求x
        for (int i = n - 1; i >= 0; i--) {
            q = 0.0;
            for (int j = i; j <= n - 1; j++) {
                q = q + U[i][j] * x[j];
            }
            x[i] = (y[i] - q) / U[i][i];
        }

        return x;
    }

    // 创建n阶单位矩阵
    private static double[][] createUnitMatrix(int n) {
        double[][] Matrix_E = new double[n][n];
        for (int i = 0; i < Matrix_E.length; i++) {
            for (int j = 0; j < Matrix_E[0].length; j++) {
                if (i == j) {
                    Matrix_E[i][j] = 1;
                } else {
                    Matrix_E[i][j] = 0;
                }
            }

        }
        return Matrix_E;
    }

}
