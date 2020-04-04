package unionfind;

/**
 * 测试 数组实现并查集5
 * <p>
 * 路径压缩
 * <p>
 * 路径压缩就是处理并查集中的深的结点。实现方法很简单，
 * 就是在find函数里加上一句 parent[element] = parent[parent[element]];就好了，
 * 就是让当前结点指向自己父亲的父亲，减少深度，
 * 同时还没有改变根结点的weight(非根节点的weight改变了无所谓)。
 * <p>
 * 注：只能在基于重量的并查集上改find函数，而不能在基于高度的并查集上采用这种路径压缩。
 * 因为路径压缩后根的重量不变，但高度会变，然而高度改变后又不方便重新计算。
 *
 * @author minwei
 */
public class UnionFindTest5 {

    private int[] parent;
    private int[] weight;
    private int size;

    public UnionFindTest5(int size) {
        this.parent = new int[size];
        this.weight = new int[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            this.parent[i] = i;
            this.weight[i] = 1;
        }
    }

    public int find(int element) {
        while (element != parent[element]) {
            parent[element] = parent[parent[element]]; // 唯一需要添加的一行代码
            element = parent[element];
        }
        return element;
    }

    public boolean isConnected(int firstElement, int secondElement) {
        return find(firstElement) == find(secondElement);
    }

    public void unionElements(int firstElement, int secondElement) {
        int firstRoot = find(firstElement);
        int secondRoot = find(secondElement);

        //如果已经属于同一个集合了，就不用再合并了。
        if (firstRoot == secondRoot) {
            return;
        }

        if (weight[firstRoot] > weight[secondRoot]) {
            parent[secondRoot] = firstRoot;
            weight[firstRoot] += weight[secondRoot];
        } else {
            //weight[firstRoot] <= weight[secondRoot]
            parent[firstRoot] = secondRoot;
            weight[secondRoot] += weight[firstRoot];
        }
    }

    private void printArr(int[] arr) {
        for (int p : arr) {
            System.out.print(p + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        UnionFindTest5 union = new UnionFindTest5(n);

        System.out.println("初始parent：");
        union.printArr(union.parent);
        System.out.println("初始weight：");
        union.printArr(union.weight);

        System.out.println("连接了5 6 之后的parent：");
        union.unionElements(5, 6);
        union.printArr(union.parent);
        System.out.println("连接了5 6 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 2 之后的parent：");
        union.unionElements(1, 2);
        union.printArr(union.parent);
        System.out.println("连接了1 2 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了2 3 之后的parent：");
        union.unionElements(2, 3);
        union.printArr(union.parent);
        System.out.println("连接了2 3 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 4 之后的parent：");
        union.unionElements(1, 4);
        union.printArr(union.parent);
        System.out.println("连接了1 4 之后的weight：");
        union.printArr(union.weight);

        System.out.println("连接了1 5 之后的parent：");
        union.unionElements(1, 5);
        union.printArr(union.parent);
        System.out.println("连接了1 5 之后的weight：");
        union.printArr(union.weight);

        System.out.println("1  6 是否连接：" + union.isConnected(1, 6));

        System.out.println("1  8 是否连接：" + union.isConnected(1, 8));
    }
}
