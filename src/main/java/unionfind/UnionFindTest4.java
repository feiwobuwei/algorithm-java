package unionfind;

/**
 * 测试 数组实现并查集4
 * 快速Union，快速Find
 * <p>
 * 增加高度(基于秩)
 *
 * @author minwei
 */
public class UnionFindTest4 {

    private int[] parent;
    private int[] height;
    int size;

    public UnionFindTest4(int size) {
        this.size = size;
        this.parent = new int[size];
        this.height = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    public int find(int element) {
        while (element != parent[element]) {
            element = parent[element];
        }
        return element;
    }

    public boolean isConnected(int firstElement, int secondElement) {
        return find(firstElement) == find(secondElement);
    }

    /**
     * 两个集合的高度不一样的时候，对它们进行合并，
     * 新集合高度肯定等于高度大的那个集合的高度。所以高度不用调整。
     * <p>
     * 如果要合并的两个集合高度一样，那么可以随意选一个作为根
     * 这里选的是让secondRoot作为新集合的根。
     * 然后secondRoot高度高了一层，所以+1
     *
     * @param firstElement  要合并的第一个元素
     * @param secondElement 要合并的第二个元素
     */
    public void unionElements(int firstElement, int secondElement) {
        int firstRoot = find(firstElement);
        int secondRoot = find(secondElement);

        // 如果第一颗树高度小于第二颗树 那么让第一颗树连接到第二颗树
        if (height[firstRoot] < height[secondRoot]) {
            parent[firstRoot] = secondRoot;
        } else if (height[firstRoot] > height[secondRoot]) {
            parent[secondRoot] = firstRoot;
        } else {
            parent[firstRoot] = secondRoot;
            height[secondRoot] += 1;
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
        UnionFindTest4 union = new UnionFindTest4(n);

        System.out.println("初始parent：");
        union.printArr(union.parent);
        System.out.println("初始height：");
        union.printArr(union.height);

        System.out.println("连接了5 6 之后的parent：");
        union.unionElements(5, 6);
        union.printArr(union.parent);
        System.out.println("连接了5 6 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 2 之后的parent：");
        union.unionElements(1, 2);
        union.printArr(union.parent);
        System.out.println("连接了1 2 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了2 3 之后的parent：");
        union.unionElements(2, 3);
        union.printArr(union.parent);
        System.out.println("连接了2 3 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 4 之后的parent：");
        union.unionElements(1, 4);
        union.printArr(union.parent);
        System.out.println("连接了1 4 之后的height：");
        union.printArr(union.height);

        System.out.println("连接了1 5 之后的parent：");
        union.unionElements(1, 5);
        union.printArr(union.parent);
        System.out.println("连接了1 5 之后的height：");
        union.printArr(union.height);

        System.out.println("1  6 是否连接：" + union.isConnected(1, 6));

        System.out.println("1  8 是否连接：" + union.isConnected(1, 8));
    }
}
