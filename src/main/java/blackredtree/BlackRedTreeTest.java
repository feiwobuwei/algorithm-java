package blackredtree;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 红黑树测试
 *
 * @author minwei
 */
public class BlackRedTreeTest {


    /**
     * 根据层次遍历建立二叉树1
     * 用于test1 test2
     */
    private int[] arrays = {7, 4, 11, 3, 6, 9, 18, 2, -1, -1, -1, -1, -1, 14, 19};
    private BlackRedTree tree = new BlackRedTree(arrays);
    private BlackRedNode<Integer> root = tree.getRoot();

    /**
     * 根据层次遍历建立二叉树1 2
     * 用于test3 test5
     */
    private int[] arrays2 = {7, 5, 11, 3, -1, 9, 14, 1, 2, -1, -1, 8, 10, 12, 15};
    private BlackRedTree tree2 = new BlackRedTree(arrays2);
    private BlackRedNode<Integer> root2 = tree2.getRoot();

    /**
     * 建颗空树
     * 用于test4
     */
    private int[] arrayNil = {-1};
    private BlackRedTree nullTree = new BlackRedTree(arrayNil);

    @Before
    public void ready() {

        // 准备工作 额外再添加一些节点
        BlackRedNode<Integer> brn1 = new BlackRedNode<>(12);
        BlackRedNode<Integer> brn2 = new BlackRedNode<>(17);
        BlackRedNode<Integer> brn3 = new BlackRedNode<>(22);
        BlackRedNode<Integer> brn4 = new BlackRedNode<>(20);
        tree.insert1(brn1, root);
        tree.insert1(brn2, root);
        tree.insert1(brn3, root);
        tree.insert1(brn4, root);

        // 给一些节点上色
        BlackRedNode<Integer> search1 = tree2.search(7, root2);
        BlackRedNode<Integer> search2 = tree2.search(5, root2);
        BlackRedNode<Integer> search3 = tree2.search(3, root2);
        BlackRedNode<Integer> search4 = tree2.search(9, root2);
        BlackRedNode<Integer> search5 = tree2.search(14, root2);
        BlackRedNode<Integer> search6 = tree2.search(8, root2);
        BlackRedNode<Integer> search7 = tree2.search(10, root2);
        BlackRedNode<Integer> search8 = tree2.search(12, root2);
        BlackRedNode<Integer> search9 = tree2.search(15, root2);

        List<BlackRedNode<Integer>> list = new ArrayList<>();

        list.add(search1);
        list.add(search2);
        list.add(search3);
        list.add(search4);
        list.add(search5);
        list.add(search6);
        list.add(search7);
        list.add(search8);
        list.add(search9);

        // 一起涂色
        tree2.blacking(list);
    }

    @Test
    public void test1() {

        System.out.println("中序遍历");
        tree.inorderTraversal(root);

        System.out.println();
        System.out.println("================");

        tree.traverseByLevel(tree);
    }


    @Test
    public void test2() {

        // 左旋右旋测试

        BlackRedNode<Integer> search = tree.search(11, root);

        LinkedList<BlackRedNode<Integer>> blackRedNodes = tree.levelTraversal(root);
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

        System.out.println();

        System.out.println("左旋后的层次遍历");
        tree.leftRotate(tree, search);

        // 重新层次遍历
        blackRedNodes = tree.levelTraversal(root);
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

        System.out.println();

        BlackRedNode<Integer> search2 = tree.search(18, root);
        System.out.println("右旋后的层次遍历");
        tree.rightRotate(tree, search2);

        // 重新层次遍历
        blackRedNodes = tree.levelTraversal(root);
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));
    }


    @Test
    public void test3() {

        // 插入测试

        // 先查看下颜色是否染正确
        LinkedList<BlackRedNode<Integer>> blackRedNodes = tree2.levelTraversal(root2);
        blackRedNodeForEach(blackRedNodes);

        BlackRedNode<Integer> brn = new BlackRedNode<>(4);

        // 返回新的根节点
        BlackRedNode<Integer> newRoot = tree2.rbInsert(tree2, brn);

        System.out.println("插入节点4以后");

        blackRedNodes = tree2.levelTraversal(newRoot);
        blackRedNodeForEach(blackRedNodes);

        System.out.println("查看数值");
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));
    }


    @Test
    public void test4() {

        // 13.3-2测试

        //  int[] array = {41，38, 31, 12, 19, 8};


        /*
  					    41(B)
  					   /
  					  38(B)
  					 /
  					31(R)
  				   /
  				 12(B)
                /  \
              8(R) 19(R）
  	    */

        // int[] array = {11, 2, 14, 1, 7, 15, 5, 8, 4};

        int[] array = {6, 2, 8, 1, 4, 3};

        for (int i : array) {
            BlackRedNode<Integer> brn0 = new BlackRedNode<>(i);
            nullTree.rbInsert(nullTree, brn0);
        }

        BlackRedNode<Integer> newRoot = nullTree.getRoot();

        LinkedList<BlackRedNode<Integer>> blackRedNodes = nullTree.levelTraversal(newRoot);
        blackRedNodeForEach(blackRedNodes);

        System.out.println("查看数值");
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

        BlackRedNode<Integer> search = nullTree.search(2, nullTree.getRoot());

        System.out.println();
        System.out.println("===============");

        // 删除测试
        System.out.println("删除节点2");
        nullTree.rbDelete(nullTree, search);

        System.out.println("继续删除节点3");
        BlackRedNode<Integer> search2 = nullTree.search(3, nullTree.getRoot());
        nullTree.rbDelete(nullTree, search2);

        blackRedNodes = nullTree.levelTraversal(newRoot);
        blackRedNodeForEach(blackRedNodes);

        System.out.println("查看数值");
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

    }


    @Test
    public void test5() {

        // 完整的删除测试

        BlackRedNode<Integer> newRoot = root2;

        LinkedList<BlackRedNode<Integer>> blackRedNodes = tree2.levelTraversal(newRoot);
        blackRedNodeForEach(blackRedNodes);

        System.out.println("查看数值");
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

        System.out.println();
        System.out.println("删除节点5");
        BlackRedNode<Integer> search = tree2.search(5, tree2.getRoot());

        tree2.rbDelete(tree2, search);

        newRoot = tree2.getRoot();

        blackRedNodes = tree2.levelTraversal(newRoot);
        blackRedNodeForEach(blackRedNodes);

        System.out.println("查看数值");
        blackRedNodes.forEach((i) -> System.out.print(i.element + ","));

        System.out.println();

    }

    private void blackRedNodeForEach(LinkedList<BlackRedNode<Integer>> blackRedNodes) {
        blackRedNodes.forEach((i) -> {
            if (i.color) {
                System.out.print("B" + ",");
            } else {
                System.out.print("R" + ",");
            }
        });

        System.out.println();
    }
}
