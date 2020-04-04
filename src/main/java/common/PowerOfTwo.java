package common;

/**
 * 找出不大于N的最大的2的幂
 *
 * @author minwei
 */
public class PowerOfTwo {

    public static void main(String[] args) {


        System.out.println(findPower(15));
        System.out.println(findPower(17));
        System.out.println(findPower2(15));
        System.out.println(findPower2(17));

        System.out.println("==================");

        System.out.println(findPower(1073741823));
        System.out.println(findPower2(1073741823));

    }


    /**
     * 时间复杂度是 O(logN)
     */
    private static int findPower(int n) {
        int sum = 1;
        while (true) {
            if (sum * 2 > n) {
                return sum;
            }
            sum = sum * 2;
        }
    }

    /**
     * HashMap中的算法
     * <p>
     * 例如 N = 19，那么转换成二进制就是 00010011（为了方便，采用8位的二进制来表示）。
     * 那么要找的数就是，把二进制中最左边的 1 保留，后面的 1 全部变为 0。即目标数是 00010000。
     * <p>
     * 获取步骤如下
     * 1、找到最左边的 1，然后把它右边的所有 0 变成 1 (也即 n |= n >> 1/2/4/8/16; 这五行代码的作用)
     * 2、把得到的数值加 1，可以得到 00100000。 即 00011111 + 1 = 00100000。
     * 3、把 得到的 00100000 向右移动一位，即可得到 00010000，即 00100000 >> 1 = 00010000。
     * <p>
     * 时间复杂度近似 O(1)
     */
    private static int findPower2(int n) {
        // n = n | (n >> 1) 整型数 32 位
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return (n + 1) >> 1;
    }


}
