package common;

import org.junit.Test;

/**
 * 最大公约数 greatest common divisor(gcd)
 *
 * @author minwei
 */
public class GCD {

    private long m = 1000, n = 300;

    @Test
    public void test() {
        long result = gcd(m, n);
        System.out.println(result);
    }

    @Test
    public void test2() {
        long result = gcd2(m, n);
        System.out.println(result);
    }

    @Test
    public void test3() {
        long result = gcd3(m, n);
        System.out.println(result);
    }

    /*
        辗转相除法
        当2个整型数较大时 取模运算性能比较低
     */

    /**
     * 辗转相除法 -- 迭代形式
     * <p>
     * 如果N>M，第一次迭代将他们互相交换
     *
     * @param m dividend
     * @param n  divisor
     * @return gcd
     */
    private long gcd(long m, long n) {
        while (n != 0) {
            long rem = m % n;
            m = n;
            n = rem;
        }
        return m;
    }

    /**
     * 辗转相除法 -- 递归形式
     * <p>
     * 入口方法
     *
     * @param m dividend
     * @param n  divisor
     * @return gcd
     */
    private long gcd2(long m, long n) {
        // 编译后自动初始化了?
        long result;
        if (m < n) {
            result = gcdRec(n, m);
        } else {
            result = gcdRec(m, n);
        }
        return result;
    }

    private long gcdRec(long a, long b) {
        if (a % b == 0) {
            return b;
        } else {
            return gcdRec(b, a % b);
        }
    }

     /*
        更相减损术
        算法不稳定 当两数相差悬殊时 递归次数较大 例如 10000 和 1
     */

    /**
     * gcd(a,b)=gcd(a-b,b)
     */
    private long gcd3(long m, long n) {
        if (m == n) {
            return m;
        }

        if (m < n) {
            return gcd3(n - m, m);
        } else {
            return gcd3(m - n, n);
        }
    }


}
