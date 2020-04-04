package unionfind;

/**
 * 测试 数组实现并查集2
 * 快速Union，慢Find
 * <p>
 * 数组内的元素表示父亲的索引，相当于指针。
 *
 * @author minwei
 */
public class UnionFindTest2 {

    private int[] parent;
    private int size;

    public UnionFindTest2(int size) {
        this.size = size;
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            // 初始情况 表示自己就是自己大哥,不从属于任何人
            parent[i] = i;
        }
    }

    public int find(int element) {
        // O(n)操作 迭代寻找根 (只有根的大哥是自己)
        while (element != parent[element]) {
            element = parent[element];
        }
        return element;
    }

    public boolean isConnected(int firstElement, int secondElement) {
        // 调用find函数 看两者的根是否相同
        return find(firstElement) == find(secondElement);
    }

    public void unionElements(int firstElement, int secondElement) {
        int firstRoot = find(firstElement);
        int secondRoot = find(secondElement);
        if (firstRoot == secondRoot) {
            return;
        }
        // 认新大哥 O(1)操作
        parent[firstRoot] = secondRoot;
    }

    /**
     * 本并查集使用数组实现，为了更直观地看清内部数据，采用打印数组
     */
    private void printArr() {
        for (int parent : this.parent) {
            System.out.print(parent + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        UnionFindTest2 union = new UnionFindTest2(n);
        System.out.println("初始：");
        union.printArr();

        System.out.println("连接了5 6");
        union.unionElements(5, 6);
        union.printArr();

        System.out.println("连接了1 2");
        union.unionElements(1, 2);
        union.printArr();

        System.out.println("连接了2 3");
        union.unionElements(2, 3);
        union.printArr();

        System.out.println("连接了1 4");
        union.unionElements(1, 4);
        union.printArr();

        System.out.println("连接了1 5");
        union.unionElements(1, 5);
        union.printArr();

        System.out.println("1  6 是否连接：" + union.isConnected(1, 6));

        System.out.println("1  8 是否连接：" + union.isConnected(1, 8));
    }

}
