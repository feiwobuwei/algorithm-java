package unionfind;

/**
 * 测试 数组实现并查集1
 * <p>
 * 元素内数字代表集合号
 *
 * @author minwei
 */
public class UnionFindTest1 {

    /**
     * 数组，表示并查集所有元素
     */
    private int[] id;

    /**
     * 并查集的元素个数
     */
    private int size;

    public static void main(String[] args) {
        int n = 10;
        UnionFindTest1 union = new UnionFindTest1(n);
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

    /**
     * 构造一个新的并查集
     *
     * @param size 初始大小
     */
    public UnionFindTest1(int size) {
        //初始化个数
        this.size = size;
        //初始化数组，每个并查集都指向自己
        id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    /**
     * 查看元素所属于哪个集合
     *
     * @param element 要查看的元素
     * @return element元素所在的集合
     */
    private int find(int element) {
        return id[element];
    }

    /**
     * 判断两个元素是否同属于一个集合
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     * @return <code>boolean</code> 如果是则返回true。
     */
    public boolean isConnected(int firstElement, int secondElement) {
        return find(firstElement) == find(secondElement);
    }

    /**
     * 合并两个元素所在的集合，也就是连接两个元素
     * 把第一个合并到第二个中
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     */
    public void unionElements(int firstElement, int secondElement) {
        //找出firstElement所在的集合
        int firstUnion = find(firstElement);
        //找出secondElement所在的集合
        int secondUnion = find(secondElement);

        //如果这两个不是同一个集合，那么合并。
        if (firstUnion != secondUnion) {
            //遍历数组，使原来的firstUnion、secondUnion合并为secondUnion
            for (int i = 0; i < this.size; i++) {
                if (id[i] == firstUnion) {
                    id[i] = secondUnion;
                }
            }
        }
    }

    /**
     * 本并查集使用数组实现，为了更直观地看清内部数据，采用打印数组
     */
    private void printArr() {
        for (int id : this.id) {
            System.out.print(id + "\t");
        }
        System.out.println();
    }


}
