package binarytree;

import java.util.HashMap;
import java.util.LinkedList;

class BinaryTree {

    // 树的根节点
    private BinaryNode<Integer> root;

    BinaryNode<Integer> getRoot() {
        return root;
    }

    /**
     * @param array 输入数组
     * @param b     true 代表需要加入parent关系
     */
    BinaryTree(int[] array, boolean b) {
        HashMap<Integer, BinaryNode<Integer>> map = new HashMap<>();
        if (b) {
            root = createBinaryTreeByArray2(array, 0, map);
            // root = createBinaryTreeByArray2(array);
        } else {
            root = createBinaryTreeByArray(array, 0);
        }
    }

    BinaryTree(int[] array, int index) {
        // 创建非平衡二叉搜索树
        if (index == 0) {
            root = createTreeByInsert(array);
        }
    }

    /**
     * 根据数组元素的插入顺序创建平衡二叉查找树
     *
     * @param array 输入数组
     * @return 返回根节点
     */
    private BinaryNode<Integer> createTreeByInsert(int[] array) {

        root = new BinaryNode<>(array[0]); // 第一个元素规定为根

        for (int i = 1; i < array.length; i++) {
            BinaryNode<Integer> bn = new BinaryNode<>(array[i]);
//            insert(bn, root);
            addAVL(bn, root);
        }

        return root;
    }

    // 返回以入参节点为根节点的树的高度
    // 用于后面计算平衡二叉树的左子树与右子树的高度差
    int height(BinaryNode<Integer> t) {
        if (t == null) {
            return 0;
        }
        return Math.max(height(t.left), height(t.right)) + 1;
    }

    // 返回一个节点左子树的高度
    int leftHeight(BinaryNode<Integer> t) {
        if (t.left == null) {
            return 0;
        } else {
            return height(t.left);
        }
    }

    // 返回一个节点右子树的高度
    int rightHeight(BinaryNode<Integer> t) {
        if (t.right == null) {
            return 0;
        } else {
            return height(t.right);
        }
    }

    // 构建 AVL树(平衡二叉树的) 的 左旋(不涉及parent指针的操作方式)
    // HashMap里面的方法叫 rotateLeft
    private void leftRotate(BinaryNode<Integer> tempNode) {

        // 1 创建一个新的节点newNode 值等于旋转中心节点(亦作为当前节点)的值
        BinaryNode<Integer> newNode = new BinaryNode<>(tempNode.element);

        // 2 把新节点的左子树设置为当前节点的左子树
        newNode.left = tempNode.left;

        // 3 把新节点的右子树设置为当前节点的右子树的左子树
        newNode.right = tempNode.right.left;

        // 4 把当前节点的值换为当前节点的右子树的值
        tempNode.element = tempNode.right.element;

        // 5 把当前节点的右子树设置成右子树的右子树
        tempNode.right = tempNode.right.right;

        // 6 把当前节点的左子树设置为新的节点
        tempNode.left = newNode;

    }

    // 右旋
    private void rightRotate(BinaryNode<Integer> tempNode) {

        // 1 创建一个新的节点newNode 值等于旋转中心节点(亦作为当前节点)的值
        BinaryNode<Integer> newNode = new BinaryNode<>(tempNode.element);

        // 2 把新节点的右子树设置为当前节点的右子树
        newNode.right = tempNode.right;

        // 3 把新节点的左子树设置为当前节点的左子树的右子树
        newNode.left = tempNode.left.right;

        // 4 把当前节点的值换为当前节点的左子树的值
        tempNode.element = tempNode.left.element;

        // 5 把当前节点的左子树设置成左子树的左子树
        tempNode.left = tempNode.left.left;

        // 6 把当前节点的右子树设置为新的节点
        tempNode.right = newNode;

    }


    /**
     * add,平衡二叉树的添加节点方法
     * 当左右子树高度差大于1时 进行左旋或右旋操作
     *
     * @param x 需要插入的元素
     * @param t t初始为root
     */
    void addAVL(BinaryNode<Integer> x, BinaryNode<Integer> t) {

        int compareResult = x.element.compareTo(t.element);

        if (compareResult < 0) {
            if (t.left != null) {
                addAVL(x, t.left);
            } else {
                // 将x设置为找到位置的左节点
                t.left = x;
            }
        } else if (compareResult > 0) {
            if (t.right != null) {
                addAVL(x, t.right);
            } else {
                // 将x设置为找到位置的右节点
                t.right = x;
            }
        }

        // 插入元素就是当前元素

        // 当符合左旋的条件时 还要先查看旋转中心的右节点( 记为 tempRight )
        // 若 tempRight 的右子树大于或等于左子树 不作处理 (参考前面左旋案例)
        // 若 tempRight 的右子树高度小于左子树 必须先以 tempRight 作为旋转中心进行右旋

        // 现在判断条件改为对每个节点
        if (rightHeight(root) - leftHeight(root) > 1) {

            if (root.right != null && rightHeight(root.right) < leftHeight(root.right)) {
                rightRotate(root.right);
            } else {
                leftRotate(root);
            }

            return; // 立即结束 不然左右横跳

        }

        // 当符合右旋的条件时 还要先查看旋转中心的左节点( 记为 tempLeft)
        // 若 tempLeft 的左子树高度大于或等于右子树 不作处理 (参考前面右旋案例)
        // 若 tempLeft 的左子树高度小于右子树 必须先以 tempLeft 作为旋转中心进行左旋
        if (leftHeight(root) - rightHeight(root) > 1) {

            if (root.left != null && leftHeight(root.left) < rightHeight(root.left)) {
                leftRotate(root.left);
            } else {
                rightRotate(root);
            }
        }
    }


    /**
     * 根据数组创建二叉树(非二叉搜索树)
     * 无空节点情况
     *
     * @param array 输入数组
     * @param index 数组索引
     * @return 每次返回当前节点 最终返回根节点
     */
    private BinaryNode<Integer> createBinaryTreeByArray(int[] array, int index) {

        BinaryNode<Integer> tn;

        if (index < array.length) {
            int value = array[index];
            tn = new BinaryNode<>(value);
            tn.left = createBinaryTreeByArray(array, 2 * index + 1);
            tn.right = createBinaryTreeByArray(array, 2 * index + 2);
            return tn;
        }
        return null;
    }

    /**
     * 对于前驱 后继 插入等操作 还需要加入parent关系
     * parent
     * (i - 1) >> 1
     * left
     * (i << 1) + 1
     * right
     * (i << 1) + 2
     *
     * @param array 输入数组
     * @param index 当前递归节点的索引
     * @return 最终生成的树的根节点
     */
    private BinaryNode<Integer> createBinaryTreeByArray2(int[] array, int index,
                                                         HashMap<Integer, BinaryNode<Integer>> map) {
        if (array[0] == -1) {
            return null;
        }

        BinaryNode<Integer> bn;
        if (index < array.length) {
            if (array[index] != -1) {
                int value = array[index];
                bn = new BinaryNode<>(value);
                map.put(index, bn);

                // 只有根节点的时候父节点为空
                if (index <= 0) {
                    bn.parent = null;
                } else {
                    bn.parent = map.get((index - 1) >> 1);
                }

                bn.left = createBinaryTreeByArray2(array, 2 * index + 1, map);
                bn.right = createBinaryTreeByArray2(array, 2 * index + 2, map);

                return bn;
            }
        }
        return null;
    }


    // 自上而下的 层次遍历 直接打印 或者 返回 动态数组
    LinkedList<BinaryNode<Integer>> levelTraversal(BinaryNode<Integer> t) {
        if (t == null) {
            return null;
        }
        LinkedList<BinaryNode<Integer>> list = new LinkedList<>();

        LinkedList<BinaryNode<Integer>> result = new LinkedList<>();

        list.add(t);
        result.add(t);

        BinaryNode<Integer> currentNode;

        while (!list.isEmpty()) {
            currentNode = list.poll();

            // System.out.println(currentNode.val);

            if (currentNode.left != null) {
                list.add(currentNode.left);
                result.add(currentNode.left);
            }
            if (currentNode.right != null) {
                list.add(currentNode.right);
                result.add(currentNode.right);
            }
        }
        return result;
    }

    private void printNode(BinaryNode<Integer> node) {
        System.out.print(node.element + " ");
    }

    // 先序遍历
    void preorderTravesal(BinaryNode<Integer> root) {
        printNode(root);
        if (root.left != null) {
            preorderTravesal(root.left);
        }
        if (root.right != null) {
            preorderTravesal(root.right);
        }
    }

    // 中序遍历
    void inorderTravesal(BinaryNode<Integer> root) {
        if (root.left != null) {
            inorderTravesal(root.left);
        }
        printNode(root);
        if (root.right != null) {
            inorderTravesal(root.right);
        }
    }

    // 后序遍历
    void postorderTravesal(BinaryNode<Integer> root) {
        if (root.left != null) {
            postorderTravesal(root.left);
        }
        if (root.right != null) {
            postorderTravesal(root.right);
        }
        printNode(root);
    }

    // search方法,调用时从根节点开始查找，如果找到了就返回一个指向该值的节点(类)
    BinaryNode<Integer> search(Integer x, BinaryNode<Integer> t) {
        if (t == null || x.equals(t.element)) {
            return t;
        }
        if (x < t.element) {
            return search(x, t.left);
        } else {
            return search(x, t.right);
        }
    }

    // 获取二叉树的总节点数，调用时参数为根节点
    int getNodeNumbers(BinaryNode<Integer> t) {
        if (t == null) {
            return 0;
        } else {
            return 1 + getNodeNumbers(t.left) + getNodeNumbers(t.right);
        }
    }

    // 获取二叉树的最大高度，调用时参数为根节点
    int maxDepth(BinaryNode<Integer> t) {
        if (t == null) {
            return 0;
        }
        return Math.max(maxDepth(t.left), maxDepth(t.right)) + 1;
    }

    // contains方法,调用时从根节点开始比较
    boolean contains(Integer x, BinaryNode<Integer> t) {
        if (t == null) {
            return false;
        }

        int compareResult = x.compareTo(t.element); // 没重写就是默认方法，属性值相减

        if (compareResult < 0) {
            return contains(x, t.left);
        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else {
            return true; // match
        }
    }

    // findMin方法的递归实现,调用时从根节点开始,一路往左必最小
    BinaryNode<Integer> findMin(BinaryNode<Integer> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    // findMax方法的非递归实现(迭代),调用时从根节点开始,一路往右必最大
    BinaryNode<Integer> findMax(BinaryNode<Integer> t) {
        if (t == null) {
            return t;
        }
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    // successor方法，找到给定节点的后继元素
    // 如果该节点右子树非空，则是右子树的最左节点；如果右子树为空，则往上迭代至最合适的父亲。
    BinaryNode<Integer> successor(BinaryNode<Integer> t) {
        if (t.right != null) {
            return findMin(t.right);
        }
        // 处理右子树空的情况
        BinaryNode<Integer> y = t.parent;
        // 循环直到t不是y的右子树才结束，这样找到的就是比它大的最小值，也即为后继元素
        while (y != null && t == y.right) {
            t = y;
            y = y.parent;
        }
        return y;
    }

    // predecessor方法，找到给定节点的前驱元素，与后继元素方法完全对称
    // 如果该节点左子树非空，则是左子树的最右节点；如果左子树为空，则往上迭代至最合适的父亲。
    BinaryNode<Integer> predecessor(BinaryNode<Integer> t) {
        if (t.left != null) {
            return findMax(t.left);
        }
        // 处理左子树空的情况
        BinaryNode<Integer> y = t.parent;
        // 循环直到t不是y的左子树才结束，这样找到的就是比它小的最大值，也即为前驱元素
        while (y != null && t == y.left) {
            t = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * insert方法,调用时从根节点开始比较,类似contains，只不过最后操作不同,
     *
     * @param x 需要插入的元素
     * @param t t初始为root
     */
    void insert(BinaryNode<Integer> x, BinaryNode<Integer> t) {
        int compareResult = x.element.compareTo(t.element);

        if (compareResult < 0) {
            if (t.left != null) {
                insert(x, t.left);
            } else {
                t.left = x; // 将x设置为找到位置的左节点
                x.parent = t; // 并把此时的t设置为x的父亲
            }
        } else if (compareResult > 0) {
            if (t.right != null) {
                insert(x, t.right);
            } else {
                t.right = x; // 将x设置为找到位置的右节点
                x.parent = t;// 并把此时的t设置为x的父亲
            }
        }
    }

    /**
     * 一个子辅助过程transplant(移植即替换) 用一颗根为v的子树替换原来一颗以u为根的子树，
     * 最后v成为u父节点的新孩子
     * delete的辅助过程
     *
     * @param t 二叉搜索树
     * @param u 旧的子树根节点
     * @param v 新的子树根节点
     */
    void transplant(BinaryTree t, BinaryNode<Integer> u, BinaryNode<Integer> v) {
        if (u.parent == null) {
            t.root = v; // 当u是根节点的时候，直接用v替换 只是指针指向了新节点v
        } else if (u == u.parent.left) {
            u.parent.left = v; // 当u是一个左孩子，更新其双亲的左孩子为v (父到子的链接)
        } else {
            u.parent.right = v; // 否则更新其双亲的右孩子为v (父到子的链接)
        }
        if (v != null) {
            v.parent = u.parent; // 最后当v非空时，补充子到父的链接
        }
    }

    // 如果一颗二叉搜索树的一个节点有两个孩子，那么它的后继没有左孩子，它的前驱没有右孩子。
    // 因为若后继有左孩子 该左孩子将取代该后继元素 作为新的后继 (大于等于该节点的最小值) ceiling
    // 因为若前驱有右孩子 该右孩子将取代该前驱元素 作为新的前驱 (小于等于该节点的最大值) floor

    /**
     * delete方法,稍复杂，
     * 分情况讨论:
     * 1.如果要删除的节点x没有子节点，则将其删除并且修改它的父节点，用null做子节点替换z。
     * 2.如果只有一个孩子，那么将这个孩子提升到树中x的位置，用x的孩子替换x。
     * 3.如果有两个孩子:
     * 当x后继y是x的右孩子时，用y替换掉x，并仅留下y的右孩子 (y必定没有左子树,不必担心丢失数据) ；
     * 当x后继y不是x的右孩子，用y的右孩子替换掉y，再用y替换掉x.补充x的原右子树与y的父子关系
     * 最后补充x的原左子树与y的父子关系
     *
     * @param t 二叉搜索树
     * @param x 要删除的节点
     */
    void delete(BinaryTree t, BinaryNode<Integer> x) {
        BinaryNode<Integer> y;
        if (x.left == null) {
            transplant(t, x, x.right); // 处理x没有左子树的情况，直接以x的右子树替换自身
        } else if (x.right == null) {
            // (经过了上面的if，则说明有左子树)处理x没有右子树的情况，直接以x的左子树替换自身
            transplant(t, x, x.left);
        } else {
            y = t.findMin(x.right); // x有左右孩子,y是x的后继
            if (y.parent != x) { // y是x的后继，且不为直接父子关系
                transplant(t, y, y.right); // 直接用y的右孩子替换掉y (y必定没有左子树)
                y.right = x.right; // 将x的原右子树给y，父到子的链接
                y.right.parent = y;// 子到父的链接，补充父子关系
            }
            // y是x的后继，且为直接父子关系
            transplant(t, x, y); // 用y替换x，此时y替换x成为x父节点的新儿子
            y.left = x.left;// 将x的原左子树给y，父到子的链接
            y.left.parent = y;// 子到父的链接，补充父子关系
        }
    }

}
