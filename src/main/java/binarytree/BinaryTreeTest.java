package binarytree;

import org.junit.Test;

import java.util.LinkedList;

/**
 * 二叉搜索树测试
 *
 * @author minwei
 */
public class BinaryTreeTest {

    /**
     * 根据层次遍历建立测试用二叉树
     */
    private int[] arrays = {6, 2, 8, 1, 4, -1, -1, -1, -1, 3};
    private BinaryTree tree = new BinaryTree(arrays, true);
    private BinaryNode<Integer> root = tree.getRoot();

    /**
     * 双旋演示
     */
    private int[] arrays2 = {10, 11, 7, 6, 8, 9};
    private BinaryTree tree2 = new BinaryTree(arrays2, 0);
    private BinaryNode<Integer> root2 = tree2.getRoot();

    @Test
    public void test1() {

        System.out.println("================== 遍历测试 ==================");

        LinkedList<BinaryNode<Integer>> binaryNodes = tree.levelTraversal(root);

        System.out.println("层次遍历");
        binaryNodes.forEach(treeNode -> System.out.print(treeNode.element + " "));

        System.out.println();

        System.out.println("先序遍历");
        tree.preorderTravesal(root);
        System.out.println();
        System.out.println("中序遍历");
        tree.inorderTravesal(root);
        System.out.println();
        System.out.println("后序遍历");
        tree.postorderTravesal(root);
        System.out.println();

        if (tree.contains(5, root)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println("树的高度为: " + tree.maxDepth(root));

    }

    @Test
    public void test2() {

        System.out.println("================== 遍历操作2 ==================");

        LinkedList<BinaryNode<Integer>> binaryNodes = tree.levelTraversal(root);
        for (BinaryNode<Integer> bn : binaryNodes) {

            System.out.println("当前节点值: " + bn.element);

            if (bn.parent != null) {
                System.out.println("当前节点的父节点的值: " + bn.parent.element);
            } else {
                System.out.println("当前节点父节点为null");
            }

            if (bn.left != null) {
                System.out.println("当前节点的左子节点值: " + bn.left.element);
            } else {
                System.out.println("当前节点的左子节点为null");
            }

            if (bn.right != null) {
                System.out.println("当前节点的右子节点值: " + bn.right.element);
            } else {
                System.out.println("当前节点的右子节点为null");
            }

            System.out.println("===============================");
        }
    }

    @Test
    public void test3() {

        System.out.println("================== 最大 最小 前驱 后继 ==================");

        System.out.println("最小值:" + tree.findMin(root).element);
        System.out.println("最大值:" + tree.findMax(root).element);
        System.out.println("结点总数:" + tree.getNodeNumbers(root));

        // 在层次遍历结果列表中 根据元素值 找到其对应节点
        BinaryNode<Integer> search1 = tree.search(4, root);

        //  search方法,调用时从根结点开始查找，如果找到了就返回一个指向该值的结点(类) 根据参数不同重载
        BinaryNode<Integer> search2 = tree.search(3, root);

        System.out.println("4的后继元素是" + tree.successor(search1).element);
        System.out.println("3的前驱元素是" + tree.predecessor(search2).element);

    }

    @Test
    public void test4() {

        System.out.println("================== 插入 移植 和 删除 ==================");

        tree.insert(new BinaryNode<>(5), root);
        System.out.println("先序遍历");
        tree.preorderTravesal(root);

        tree.insert(new BinaryNode<>(7), root);
        System.out.println();
        System.out.println("插入两个点后的结点总数:" + tree.getNodeNumbers(root));

        // 二叉查找树的中序遍历一定是严格升序输出
        System.out.println("中序遍历");
        tree.inorderTravesal(root);

        BinaryNode<Integer> node9 = new BinaryNode<>(9);
        System.out.println();

        System.out.println("插入节点9");
        tree.insert(node9, root);

        System.out.println("后序遍历");
        tree.postorderTravesal(root);

        System.out.println();
        System.out.println("======================");

        BinaryNode<Integer> node10 = new BinaryNode<>(10);
        System.out.println("替换新加的节点9为10");
        tree.transplant(tree, node9, node10);
        System.out.println("中序遍历");
        tree.inorderTravesal(root);

        System.out.println();

        // 若是替换4，此时3，5两个子节点会被空节点替代
        System.out.println("替换原来的节点1为0");

        BinaryNode<Integer> node0 = new BinaryNode<>(0);
        BinaryNode<Integer> search = tree.search(1, root);

        tree.transplant(tree, search, node0);
        System.out.println("中序遍历");
        tree.inorderTravesal(root);

        // 最复杂的一种情况
        BinaryNode<Integer> search2 = tree.search(2, root);

        System.out.println();
        System.out.println("删除值为2的结点");

        tree.delete(tree, search2);

        System.out.println("重新中序遍历");
        tree.inorderTravesal(root);
        System.out.println();

    }

    // 平衡二叉搜索树 左旋右旋测试

    @Test
    public void test5() {

        System.out.println("================ 平衡二叉搜索树 ==============");

        System.out.println("树的深度: " + tree2.maxDepth(root2));

        LinkedList<BinaryNode<Integer>> binaryNodes = tree2.levelTraversal(root2);

        System.out.println("先序遍历");
        tree2.preorderTravesal(root2);

        System.out.println();

        System.out.println("层次遍历");
        binaryNodes.forEach(treeNode -> System.out.print(treeNode.element + " "));

        System.out.println();

        System.out.println("根节点左子树高度: " + tree2.leftHeight(root2));
        System.out.println("根节点右子树高度: " + tree2.rightHeight(root2));

    }


}
