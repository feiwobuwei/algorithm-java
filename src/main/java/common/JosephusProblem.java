package common;

import org.junit.Test;

public class JosephusProblem {

    @Test
    public void test() {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5); // 加入5个小孩节点
        circleSingleLinkedList.showBoy();
    }

    @Test
    public void test2() {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5); // 加入5个小孩节点

        circleSingleLinkedList.countBoy(1, 2, 5);

        System.out.println("===========================");

        CircleSingleLinkedList circleSingleLinkedList2 = new CircleSingleLinkedList();
        circleSingleLinkedList2.addBoy(6); // 加入5个小孩节点

        circleSingleLinkedList2.countBoy(1, 5, 6);

    }


}

// 创建单向环形链表
class CircleSingleLinkedList {

    private Boy first; // 初始节点

    // nums 需要添加的节点数
    public void addBoy(int nums) {

        if (nums < 1) {
            System.out.println("nums必须是大于1的正整数");
            return;
        }

        Boy cur = null; // 辅助指针
        for (int i = 1; i <= nums; i++) {
            // 根据编号创建小孩节点
            Boy boy = new Boy(i);

            // 如果是第一个小孩
            if (i == 1) {
                first = boy;
                first.setNext(first);
                cur = first;
            } else {
                cur.setNext(boy);
                boy.setNext(first);
                cur = boy;
            }
        }

    }

    // 遍历环形链表
    public void showBoy() {

        if (first == null) {
            System.out.println("链表为空");
            return;
        }

        Boy cur = first; // 辅助指针

        while (true) {

            System.out.printf("小孩的编号%d \n", cur.getNo());

            if (cur.getNext() == first) {
                break; // 结束条件cur == first
            }

            cur = cur.getNext(); // 指针后移

        }
    }

    /**
     * @param startNo  表示从第几个小孩开始报数 即k 不一定总是从1号开始
     * @param countNum 表示数几下 即m
     * @param nums     表示最初有多少个小孩在圈中
     */
    public void countBoy(int startNo, int countNum, int nums) {

        // 数据校验 不能起始编号大于m
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("起始编号必须为大于等于1的整数,且起始编号不能大于nums");
            return;
        }

        Boy helper = first;

        // 辅助指针helper 事先指向环形链表的的最后这个节点 即 1-2-3-4-5-1 的 5
        while (helper.getNext() != first) {
            helper = helper.getNext();
        }

        // 先让first 和 helper 移动 k-1 次 如果 k=1 该代码块直接跳过
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        // 只剩一个节点 结束循环
        while (helper != first) {
            // 当小孩报数时,让first 和 helper指针同时移动 m-1次，然后出圈
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            System.out.printf("小孩%d出圈\n", first.getNo());

            first = first.getNext();
            helper.setNext(first);
        }

        System.out.printf("最后留在圈中的小孩编号为%d \n", first.getNo());
    }

}


// Boy类 表示节点
class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
