package common;


/**
 * 汉诺塔问题
 * <p>
 * 数学求解
 * <p>
 * 递推公式： H（k）=2H（k-1）+1。
 * 通项公式： H（k）=2^k-1。
 * <p>
 * 首先被移动到C盘的必定是最大的盘子，否则必定违反“在移动过程中始终保持小盘在大盘之上”的规定。
 * 既然要将最大盘移动到C，此时最大盘之上必定没有任何盘子，亦即它独自在一根柱子上，
 * 要做到这点最优做法当然是先把较小的n-1 个盘子由A移动到B，剩下最大盘独自在A。
 * 将n-1个盘由A移动到B花费的最少次数为H（n-1）。此时再将最大盘由A移动到C，
 * 此时移动总次数为H（n-1）+1。接着把剩下的n-1个盘由B移动到C，
 * 花费的最少次数当然也是H（n-1）。于是得到总移动次数2H（n-1）+1.证得H（k）=2H（k-1）+1。
 * <p>
 * 推导通项公式。由H（k）=2H（k-1）+1得H(k)+1=2(H(k-1)+1)，
 * 于是{H(k)+1}是首项为H(1)=1，公比为2的等比数列，求得H(k)+1 = 2^k，所以H(k) = 2^k-1
 * <p>
 * 代码流程
 * 1 如果是一个盘 A->C
 * 2 如果n>=2 总是看作两个盘 一个是最下边的盘 一个是除了最下边的盘的所有盘
 * - 先把除了最下边的盘的所有盘 A -> B
 * - 把最下面的盘 A -> C
 * - 把B的所有盘 从 B -> C
 *
 * @author minwei
 */
public class TowerOfHanoi {

    public static void main(String[] args) {

        hanoiTower(4, 'A', 'B', 'C');
        System.out.println("总共需要移动的次数 " + count);

    }

    private static int count;

    /**
     * @param num 要移动的盘数
     * @param a   起始柱子
     * @param b   过渡柱子
     * @param c   终止柱子
     */
    private static void hanoiTower(int num, char a, char b, char c) {

        // 如果是一个盘 直接移动即可
        if (num == 1) {
            System.out.println("第1个盘从 " + a + "->" + c);
            count++;
        } else {
            // 如果n>=2 总是看作两个盘 一个是最下边的盘 一个是除了最下边的盘的所有盘

            // 1 先把除了最下边的盘的所有盘 A -> B
            hanoiTower(num - 1, a, c, b);

            // 2 把最下面的盘 A -> C
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            count++;

            // 3 把B的所有盘 从 B -> C
            hanoiTower(num - 1, b, a, c);

        }
    }


}
