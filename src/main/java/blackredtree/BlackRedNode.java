package blackredtree;

/**
 * 红黑树节点
 *
 * @param <T> 泛型
 * @author minwei
 */
public class BlackRedNode<T> {

    /**
     * 泛型元素
     */
    T element;

    BlackRedNode<T> left;
    BlackRedNode<T> right;
    BlackRedNode<T> parent;

    boolean color;

    BlackRedNode(T element) {
        // 添加的节点 均先涂红色
        this.color = false;
        this.element = element;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

}
