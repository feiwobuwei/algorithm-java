package binarytree;

class BinaryNode<T> {

    T element;
    BinaryNode<T> left;
    BinaryNode<T> right;
    BinaryNode<T> parent;

    BinaryNode(T element) {
        this.element = element;
        this.left = null;
        this.right = null;
    }

}
