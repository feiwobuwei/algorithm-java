package blackredtree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 算法导论 - 红黑树的JAVA实现
 */
class BlackRedTree {

    /**
     * 哨兵节点
     */
    private BlackRedNode<Integer> sentry;

    /**
     * 根节点
     */
    private BlackRedNode<Integer> root;

    BlackRedNode<Integer> getRoot() {
        return root;
    }

    BlackRedTree(int[] array) {

        HashMap<Integer, BlackRedNode<Integer>> map = new HashMap<>();
        root = createBinaryRedTreeByArray(array, 0, map);
        // 节点均尚未涂色
        sentry = null;

    }

    /**
     * 将红节点涂黑 black为true red为false
     *
     * @param brns 红黑树节点列表
     */
    void blacking(List<BlackRedNode<Integer>> brns) {
        for (BlackRedNode<Integer> brn : brns) {
            brn.color = true;
        }
    }

    private BlackRedNode<Integer> createBinaryRedTreeByArray(int[] array, int index,
                                                             HashMap<Integer, BlackRedNode<Integer>> map) {
        // 返回一颗空树
        if (array[0] == -1) {
            return null;
        }

        BlackRedNode<Integer> brn;
        if (index < array.length) {
            if (array[index] != -1) {
                int value = array[index];
                brn = new BlackRedNode<>(value);
                map.put(index, brn);

                // 只有根节点的时候父节点为空
                if (index <= 0) {
                    brn.parent = null;
                } else {
                    brn.parent = map.get((index - 1) >> 1);
                }

                brn.left = createBinaryRedTreeByArray(array, 2 * index + 1, map);
                brn.right = createBinaryRedTreeByArray(array, 2 * index + 2, map);

                return brn;
            }
        }
        return null;
    }

    /**
     * search方法,调用时从根结点开始查找，如果找到了就返回一个指向该值的结点(类)
     *
     * @param x 待查找节点的值
     * @param t 根节点
     * @return 待查找节点
     */
    BlackRedNode<Integer> search(Integer x, BlackRedNode<Integer> t) {
        if (t == null || x.equals(t.element)) {
            return t;
        }
        if (x < t.element) {
            return search(x, t.left);
        } else {
            return search(x, t.right);
        }
    }

    private void printNode(BlackRedNode<Integer> node) {
        System.out.print(node.element + " ");
    }

    // 中序遍历

    void inorderTraversal(BlackRedNode<Integer> root) {
        if (root.left != null) {
            inorderTraversal(root.left);
        }
        printNode(root);
        if (root.right != null) {
            inorderTraversal(root.right);
        }
    }

    /**
     * insert方法,调用时从根结点开始比较,类似contains，只不过最后操作不同,
     * 初始的插入二叉节点 非红黑数的插入操作
     *
     * @param x 需要插入的元素
     * @param t t初始为root
     */
    void insert1(BlackRedNode<Integer> x, BlackRedNode<Integer> t) {
        int compareResult = x.element.compareTo(t.element);

        if (compareResult < 0) {
            if (t.left != null) {
                insert1(x, t.left);
            } else {
                t.left = x; // 将x设置为找到位置的左结点
                x.parent = t; // 并把此时的t设置为x的父亲
            }
        } else if (compareResult > 0) {
            if (t.right != null) {
                insert1(x, t.right);
            } else {
                t.right = x; // 将x设置为找到位置的右结点
                x.parent = t;// 并把此时的t设置为x的父亲
            }
        }
    }

    // 自上而下的 层次遍历 直接打印 或者 返回一个 动态数组
    LinkedList<BlackRedNode<Integer>> levelTraversal(BlackRedNode<Integer> t) {

        if (t == null) {
            return null;
        }

        LinkedList<BlackRedNode<Integer>> list = new LinkedList<>();

        LinkedList<BlackRedNode<Integer>> result = new LinkedList<>();

        list.add(t);
        result.add(t);

        BlackRedNode<Integer> currentNode;

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

    void traverseByLevel(BlackRedTree t) {

        LinkedList<BlackRedNode<Integer>> binaryNodes = t.levelTraversal(root);
        for (BlackRedNode<Integer> bn : binaryNodes) {

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

            System.out.println("=======================");
        }
    }

    // 左旋操作
    void leftRotate(BlackRedTree tree, BlackRedNode<Integer> x) {

        BlackRedNode<Integer> y = x.right; // 前提：这里假设x的右孩子为y。下面开始正式操作

        x.right = y.left; // 将 “y的左孩子” 设为 “x的右孩子”，即 将β设为x的右孩子

        if (y.left != tree.sentry) {
            y.left.parent = x;  // 将 “x” 设为 “y的左孩子的父亲”，即 将β的父亲设为x
        }

        y.parent = x.parent; // 将 “x的父亲” 设为 “y的父亲”

        if (x.parent == tree.sentry) {
            root = y; // 情况1：如果 “x的父亲” 是空节点，则将y设为根节点
        } else if (x == x.parent.left) {
            x.parent.left = y; // 情况2：如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        } else {
            x.parent.right = y; // 情况3：(x是它父节点的右孩子) 将y设为“x的父节点的右孩子”
        }

        y.left = x;    // 将 “x” 设为 “y的左孩子”
        x.parent = y;  // 将 “x的父节点” 设为 “y”
    }

    // 右旋操作(操作对称可逆)
    void rightRotate(BlackRedTree tree, BlackRedNode<Integer> y) {
        BlackRedNode<Integer> x = y.left; // 前提：这里假设y的左孩子为x。下面开始正式操作

        y.left = x.right;  // 将 “x的右孩子” 设为 “y的左孩子”，即 将β设为y的左孩子

        if (x.right != tree.sentry) {
            x.right.parent = y;   // 将 “y” 设为 “x的右孩子的父亲”，即 将β的父亲设为y
        }

        x.parent = y.parent;    // 将 “y的父亲” 设为 “x的父亲”

        if (y.parent == tree.sentry) {
            tree.root = x;   // 情况1：如果 “y的父亲” 是空节点，则将x设为根节点
        } else if (y == y.parent.right) {
            y.parent.right = x;   // 情况2：如果 y是它父节点的右孩子，则将x设为“y的父节点的左孩子”
        } else {
            y.parent.left = x;  // 情况3：(y是它父节点的左孩子) 将x设为“y的父节点的左孩子”
        }

        x.right = y; // 将 “y” 设为 “x的右孩子”
        y.parent = x;   // 将 “y的父节点” 设为 “x”
    }

    // 红黑树的插入
    BlackRedNode<Integer> rbInsert(BlackRedTree tree, BlackRedNode<Integer> z) {

        BlackRedNode<Integer> y = tree.sentry; // 新建节点“y”，将y设为空节点。
        BlackRedNode<Integer> x = tree.root;  // 设“红黑树T”的根节点为“x”

        while (x != tree.sentry) {
            y = x;          // 找出要插入的节点“z”在二叉树T中的位置“y” 循环停下来的时候 y就是正确位置
            if (z.element < x.element) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;   // 设置 “z的父亲” 为 “y”

        if (y == tree.sentry) {
            tree.root = z;  // 情况1：若y是空节点，则将z设为根
        } else if (z.element < y.element) {
            y.left = z;   // 情况2：若“z所包含的值” < “y所包含的值”，则将z设为“y的左孩子”
        } else {
            y.right = z;   // 情况3：(“z所包含的值” >= “y所包含的值”)将z设为“y的右孩子”
        }

        z.left = tree.sentry;  // z的左孩子设为空
        z.right = tree.sentry; // z的右孩子设为空

        // 以上已经完成将“节点z插入到二叉树”中了。

        z.color = false;    // 将z着色为“红色”
        rbInsertFixup(tree, z); // 通过RB-INSERT-FIXUP对红黑树的节点进行颜色修改以及旋转，让树T仍然是一颗红黑树

        // 操作完毕后,若根节点改变了 以原根节点的指针的父节点作为新的根节点
        return tree.root;
    }

    // 辅助修复方法
    private void rbInsertFixup(BlackRedTree tree, BlackRedNode<Integer> z) {
        // 若“当前节点(z)的父节点是红色”，则进入循环。 此时 z.parent.color 表示false
        // 注意空指针异常。 当插入根节点的左右孩子时 因为根节点肯定是黑色的 所以一定不会进入循环体
        while (z.parent != null && !z.parent.color) {
            if (z.parent == z.parent.parent.left) { // 若“z的父节点”是“z的祖父节点的左孩子”，则进行以下处理。

                BlackRedNode<Integer> y = z.parent.parent.right;  // 将y设置为“z的叔叔节点(z的祖父节点的右孩子)”
                // 注意空指针异常
                if (y == null || !y.color) {        // Case1 条件：(叔节点为空或者)叔叔是红色
                    z.parent.color = true;          //  (01) 将“父节点”设为黑色。
                    if (y != null) {
                        y.color = true;                 //  (02) 将“叔叔节点”设为黑色。
                    }
                    z.parent.parent.color = false;  //  (03) 将“祖父节点”设为“红色”。
                    z = z.parent.parent;            //  (04) 将“祖父节点”设为“当前节点”(红色节点)

                } else if (z == z.parent.right) {   // Case2 条件：叔叔是黑色，且当前节点是右孩子
                    z = z.parent;                   //  (01) 将“父节点”作为“新的当前节点”。
                    leftRotate(tree, z);            //  (02) 以“新的当前节点”为支点进行左旋。
                    // 情况二经过上面的操作就变为了情况三
                } else {                            // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                    z.parent.color = true;          // (01) 将“父节点”设为“黑色”。
                    z.parent.parent.color = false;  // (02) 将“祖父节点”设为“红色”。
                    rightRotate(tree, z.parent.parent); // (03) 以“祖父节点”为支点进行右旋。
                }
            } else {    // 若“z的父节点”是“z的祖父节点的右孩子”，将上面的操作中“right”和“left”交换位置，然后依次执行。

                // 对称性
                BlackRedNode<Integer> y = z.parent.parent.left;  // 将y设置为“z的叔叔节点(z的祖父节点的左孩子)”

                if (y == null || !y.color) {        // Case4 条件：叔叔是红色
                    z.parent.color = true;          //  (01) 将“父节点”设为黑色。
                    if (y != null) {
                        y.color = true;  //  (02) 将“叔叔节点”设为黑色。
                    }
                    z.parent.parent.color = false;  //  (03) 将“祖父节点”设为“红色”。
                    z = z.parent.parent;            //  (04) 将“祖父节点”设为“当前节点”(红色节点)

                } else if (z == z.parent.left) {    // Case5 条件：叔叔是黑色，且当前节点是左孩子
                    z = z.parent;                   //  (01) 将“父节点”作为“新的当前节点”。
                    rightRotate(tree, z);           //  (02) 以“新的当前节点”为支点进行右旋。
                    // 情况二经过上面的操作就变为了情况三
                } else {                            // Case6 条件：叔叔是黑色，且当前节点是右孩子。
                    z.parent.color = true;          // (01) 将“父节点”设为“黑色”。
                    z.parent.parent.color = false;  // (02) 将“祖父节点”设为“红色”。
                    leftRotate(tree, z.parent.parent); // (03) 以“祖父节点”为支点进行左旋。

                }
            }
        }
        tree.root.color = true; // 最后,将根节点设为黑色。
    }

    // findMin方法的递归实现,调用时从根结点开始,一路往左必最小
    BlackRedNode<Integer> findMin(BlackRedNode<Integer> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    // findMax方法的非递归实现(迭代),调用时从根结点开始,一路往右必最大
    BlackRedNode<Integer> findMax(BlackRedNode<Integer> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }


    /**
     * 一个子辅助过程transplant(移植即替换) 用一颗根为v的子树替换原来一颗以u为根的子树，
     * 最后v成为u父节点的新孩子
     * delete的辅助过程
     *
     * @param t 二叉搜索树
     * @param u 旧的子树根结点
     * @param v 新的子树根结点
     */
    void rbTransplant(BlackRedTree t, BlackRedNode<Integer> u, BlackRedNode<Integer> v) {
        if (u.parent == t.sentry) {
            t.root = v; // 当u是根结点的时候，直接用v替换 只是指针指向了新节点v
        } else if (u == u.parent.left) {
            u.parent.left = v; // 当u是一个左孩子，更新其双亲的左孩子为v (父到子的链接)
        } else {
            u.parent.right = v; // 否则更新其双亲的右孩子为v (父到子的链接)
        }

        if (v != null) {
            v.parent = u.parent; // 最后当v非空时，补充子到父的链接
        }
    }

    void rbDelete(BlackRedTree t, BlackRedNode<Integer> z) {

        BlackRedNode<Integer> x;      // x总是作为y的左节点或是右节点
        BlackRedNode<Integer> y = z;  // 将“z”赋值给 “y”; y要么是从树中删除的节点(情况1,2) 要么是移至树内的节点(情况3)
        boolean yOriginalColor = y.color;  // 记录y节点原来的颜色

        if (z.left == t.sentry) {
            x = z.right;                  // 若“z的左孩子” 为空，则将“z的右孩子” 赋值给 “x”；(此时y=z)
            rbTransplant(t, z, z.right);        // 处理z没有左子树的情况，以z的右子树替换自身

        } else if (z.right == null) { // (经过了上面的if，则说明有左子树)
            x = z.left;  // 若“z的右孩子” 为空，则将“z的左孩子” 赋值给 “x”; (此时y=z)
            rbTransplant(t, z, z.left);  // 处理z没有右子树的情况，以z的左子树替换自身

        } else {
            y = t.findMin(z.right); // z 有左右孩子, y是z的后继
            yOriginalColor = y.color;  // 因为y被改变,重新记录y节点原来的颜色

            x = y.right; // 将“y的右孩子” 赋值给 “x”； z的后继y一定没有左孩子，

            // 和binaryTre 当时的判断条件互反 所以y节点自身的替换和节点上升操作在else块里
            if (y.parent == z) { // y是z的后继，且为直接父子关系(且一定是右子节点)
                if (x != t.sentry) {
                    x.parent = y;

                    // 将x的父节点改为y 因为下面的操作rbTransplant(t, z, y); z将被删除 所以如果写z.parent则空指针异常
                    // 这样最终效果依然等效于 x.parent 总是被设置指向树中y父节点的原始位置
                    // if (v != null) v.parent = u.parent; 此处v总是x(移植方法中的第三个参数) u就是z p184
                }
            } else {   // y是z的后继，且不为直接父子关系
                rbTransplant(t, y, y.right); // 用y的右孩子替换掉y
                y.right = z.right;           // 将z的原右子树给y，父到子的链接
                y.right.parent = y;          // 子到父的链接，补充父子关系
            }

            rbTransplant(t, z, y); // 用y替换z，此时y替换z成为z父节点的新儿子
            y.left = z.left;// 将z的原左子树给y，父到子的链接
            y.left.parent = y;// 子到父的链接，补充父子关系

            // 即便z是根节点(原黑) 也没有破坏红黑树性质(移过去的y也染黑)
            y.color = z.color;  // y的颜色改为z的颜色 保证z的祖先路径上的黑高不会改变
        }

        // 因为若y原来是红色。上面三种情况都不会改变黑高 也就是不会影响红黑树的性质 可以直接结束
        // 如果y原来节点的颜色为black(也就是true) 执行修复操作(黑高会发生变化) 否则删除结束
        if (yOriginalColor) {
            rbDeleteFixup(t, x); // 以x为基准进行修复 x总是y的左节点或是右节点
        }
    }

    // y 要么是从树中删除的节点(情况1,2) 要么是移至树内的节点(情况3)原位置也会被有右孩子替代
    // 因为 x 会移动到 y 原来的位置(三种情况都是) 所以 属性x.parent 总是被设置指向树中y父节点的原始位置(即使x为空)

    // 循环开始前的注意事项如上所示
    private void rbDeleteFixup(BlackRedTree t, BlackRedNode<Integer> x) {
        BlackRedNode<Integer> w; // w总是作为兄弟节点

        // 初始时 x可能是红黑节点 直接结束 可能是双黑节点
        // 循环终止条件1: x指向红黑节点
        // 循环终止条件2: x指向根节点

        // 否则进入循环体 则x总是指向一个具有双重黑色的非根节点
        while (x != t.sentry && x != t.root && x.color) {

            if (x == x.parent.left) {   // x是左子节点
                w = x.parent.right;   // w是x的兄弟节点

                // 如果兄弟节点颜色为红 将情况1转换为2 3 4 以便后续处理
                if (!w.color) {
                    w.color = true;                         // case 1 将兄弟节点颜色染黑    为了不改变黑高
                    x.parent.color = false;                 // case 1 将x的父节点颜色染红   为了不改变黑高
                    leftRotate(t, x.parent);                // case 1 以x的父节点为支点右旋 不改变红黑树性质
                    w = x.parent.right;                     // case 1 w为x的新兄弟节点
                }

                // 经过情况1处理 现在兄弟节点颜色必定为黑 根据w节点的颜色不同分为情况2 3 4
                // 2 3 4 只能进入一种分支

                // w 不可能是叶子节点 p185
                // (w.right == t.sentry && w.left == t.sentry) ||
                // 如果兄弟是叶节点 或者 双子节点都能存在且为黑
                if (w.right.color && w.left.color) {
                    w.color = false;                         // case 2 将兄弟节点颜色染红
                    x = x.parent;                            // case 2 将x指向x的父节点

                    // 否则(经过第一层if 已经判断左不是黑了 若能进入循环则)左孩子为红 右节点为黑
                } else if (w.right.color) {
                    w.left.color = true;                     // case 3 将兄弟左子节点颜色染黑(如果存在的话)
                    w.color = false;                         // case 3 兄弟颜色染红
                    rightRotate(t, w);                       // case 3 以兄弟节点为基准右旋
                    w = x.parent.right;                      // case 3 w为x的新兄弟节点

                    // 否则(经过第一层if 已经判断不存在左右同黑 第二层不存在左红右黑) 右节点存在且为红 (不关心左子节点颜色)
                } else {
                    w.color = x.parent.color;                // case 4 将兄弟的颜色染为x父节点的颜色
                    x.parent.color = true;                   // case 4 将x父节点的颜色染黑
                    w.right.color = true;                    // case 4 将w节点的右节点染黑
                    leftRotate(t, x.parent);                 // case 4 以父节点为基准进行左旋
                    x = t.root;                              // case 4 将x设为树的根(循环将结束)
                }

            } else {   // 否则x是右子节点  // 交换if块里面的left和right

                w = x.parent.left;   // w是x的兄弟节点

                // 如果兄弟节点颜色为红
                if (!w.color) {
                    w.color = true;                 // case 1   将兄弟节点颜色染黑
                    x.parent.color = false;         // case 1   将x的父节点颜色染红
                    rightRotate(t, x.parent);       // case 1   以x的父节点为支点右旋
                    w = x.parent.left;              // case 1   w为x的新兄弟节点
                }

                // 经过情况1处理 现在兄弟节点颜色必定为黑
                // 如果兄弟是叶节点或者双子节点都能存在且为黑
                // (w.right == t.sentry && w.left == t.sentry) ||
                if (w.right.color && w.left.color) {
                    w.color = false;                // case 2   将兄弟节点颜色染红
                    x = x.parent;                   // case 2   将x指向x的父节点

                } else if (w.left.color) {          // 兄弟左子节点存在且为黑

                    w.right.color = true;           // case 3   将兄弟右子节点颜色染红(如果存在的话)
                    w.color = false;                // case 3   兄弟颜色染红
                    leftRotate(t, w);               // case 3   以兄弟节点为基准左旋
                    w = x.parent.right;             // case 3   w为x的新兄弟节点

                } else {                     // 兄弟仅右子节点存在且为黑
                    w.color = x.parent.color;                      // case 4 将兄弟的颜色染为x父节点的颜色
                    x.parent.color = true;                         // case 4 将x父节点的颜色染黑
                    w.left.color = true;   // case 4 将w节点的左节点染黑
                    rightRotate(t, x.parent);                      // case 4 以父节点为基准进行右旋
                    x = t.root;                                    // case 4 将x设为树的根(循环将结束)
                }
            }
        }
        // 循环结束
        if (x != t.sentry) {
            x.color = true;  //把x涂黑
        }
    }
}
