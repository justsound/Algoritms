import hw4.BinaryTree.Node;

public class hw4 {
    public class BinaryTree {
        Node Root;

        private enum Color {
            Red, Black
        }

        private class Node {
            int Value;
            Node Left;
            Node Right;
            Color color;
        }

        public boolean find(int Value) {
            Node current = Root;
            while (current != null) {
                if (current.Value == Value)
                    return true;

                if (Value < current.Value) {
                    current = current.Left;
                } else {
                    current = current.Right;
                }
            }
            return false;
        }

        private boolean push(int Value) {
            if (Root == null) {
                Root = new Node();
                Root.Value = Value;
                return true;
            } else {
                boolean result = addNewNode(Root, Value);
                rebalance(Root);
                return result;
            }
        }

        private boolean addNewNode(Node Node, int Value) {
            if (Node.Value > Value) {
                if (Node.Left == null) {
                    Node.Left.Value = Value;
                    Node.Left.color = Color.Red;
                    rebalance(Node.Left);
                    return true;
                } else {
                    boolean result = addNewNode(Node.Left, Value);
                    return result;
                }
            } else if (Node.Value < Value) {
                if (Node.Right == null) {
                    Node.Right.Value = Value;
                    Node.Right.color = Color.Red;
                    rebalance(Node.Right);
                    return true;
                } else {
                    boolean result = addNewNode(Node.Right, Value);
                    return result;
                }
            } else
                return false;
        }

        public Node rebalance(Node node) {
            Node result = node;
            boolean needRebalance;
            do {
                needRebalance = false;
                if (result.Right != null && result.Right.color == Color.Red &&
                        (result.Left == null || result.Left.color == Color.Black)) {
                    needRebalance = true;
                    result = rightSwap(result);
                }
                if (result.Left != null && result.Left.color == Color.Red &&
                        result.Left.Left != null && result.Left.Left.color == Color.Red) {
                    needRebalance = true;
                    result = leftSwap(result);
                }
                if (result.Left != null && result.Left.color == Color.Red &&
                        result.Right != null && result.Right.color == Color.Red) {
                    needRebalance = true;
                    colorSwap(result);
                }
            } while (needRebalance);
            return result;
        }

        public void printTree() {
            printTree(Root);
        }

        private void printTree(Node node) {
            if (node == null)
                return;
            System.out.printf("%d %c ", node.Value, node.color == Color.Red ? 'R' : 'B');
            if (node.Left != null)
                System.out.printf("%d %c ", node.Left.Value, node.Left.color == Color.Red ? 'R' : 'B');
            if (node.Right != null)
                System.out.printf("%d %c ", node.Right.Value, node.Right.color == Color.Red ? 'R' : 'B');

            System.out.println();
            printTree(node.Left);
            printTree(node.Right);
        }

        public Node rightSwap(Node node) {
            Node Right = node.Right;
            Node betweenChild = Right.Left;
            Right.Left = node;
            node.Right = betweenChild;
            Right.color = node.color;
            node.color = Color.Red;
            return Right;
        }

        public Node leftSwap(Node node) {
            Node Left = node.Left;
            Node betweenChild = Left.Right;
            Left.Right = node;
            node.Left = betweenChild;
            Left.color = node.color;
            node.color = Color.Red;
            return Left;
        }

        private void colorSwap(Node node) {
            node.Right.color = Color.Black;
            node.Left.color = Color.Black;
            node.color = Color.Red;
        }

    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        for (int i = 1; i <= 15; i++) {
            tree.push(i);
        }

        tree.printTree();

    }
}
