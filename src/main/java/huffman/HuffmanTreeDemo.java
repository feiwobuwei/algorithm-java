package huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTreeDemo {

    public static void main(String[] args) {

        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        System.out.println(root.value);

        preOrder(root);

    }

    // 最后返回赫夫曼树的根节点 其值为最小WPL
    private static Node createHuffmanTree(int[] arr) {

        List<Node> nodes = new ArrayList<>();

        for (int value : arr) {
            nodes.add(new Node(value));
        }

        // Collections
        // This class consists exclusively of static methods that operate on or return collections.
        // 该类完全由操作集合或返回集合的静态方法组成。

        // 1-5步是一个循环过程 当nodes里面只有一个节点的时候 循环结束
        while (nodes.size() > 1) {
            // 1 从小到大排序，将每一个数据都可以看成是一颗最简单的二叉树
            Collections.sort(nodes);

            System.out.println("处理前 nodes: " + nodes);

            // 2 取出根节点权值最小的两颗二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            // 3 组成一颗新树
            Node parentNode = new Node(leftNode.value + rightNode.value); // 根节点
            parentNode.left = leftNode;
            parentNode.right = rightNode;

            // 4 删除掉已经处理过的节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            // 5 添加新的成员
            nodes.add(parentNode);

            Collections.sort(nodes);
            System.out.println("处理后 nodes: " + nodes);
            System.out.println("===========================================================");
        }

        return nodes.get(0);
    }

    // 使用前序遍历 验证树的形状
    private static void preOrder(Node root) {

        // 只输出叶节点的值
        if (root.left == null && root.right == null) {
            System.out.print(root.value + " ");
        }

        if (root.left != null) {
            preOrder(root.left);
        }

        if (root.right != null) {
            preOrder(root.right);
        }
    }

}


// 节点类
class Node implements Comparable<Node> {

    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }


    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.value, o.value); // 从小到大排序
    }

    //  Integer.compare
    //
    //  public static int compare(int x, int y) {
    //        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    //    }
}
