class NodeRedBlackTree {
    int data;
    NodeRedBlackTree parent;
    NodeRedBlackTree left;
    NodeRedBlackTree right;
    int color;
}

public class RedBlackTree {

    private NodeRedBlackTree root;
    private NodeRedBlackTree TNULL;

    private void preOrderHelper(NodeRedBlackTree nodeRedBlackTree) {
        if (nodeRedBlackTree != TNULL) {
            System.out.print(nodeRedBlackTree.data + " ");
            preOrderHelper(nodeRedBlackTree.left);
            preOrderHelper(nodeRedBlackTree.right);
        }
    }

    private void inOrderHelper(NodeRedBlackTree nodeRedBlackTree) {
        if (nodeRedBlackTree != TNULL) {
            inOrderHelper(nodeRedBlackTree.left);
            System.out.print(nodeRedBlackTree.data + " ");
            inOrderHelper(nodeRedBlackTree.right);
        }
    }

    private void postOrderHelper(NodeRedBlackTree nodeRedBlackTree) {
        if (nodeRedBlackTree != TNULL) {
            postOrderHelper(nodeRedBlackTree.left);
            postOrderHelper(nodeRedBlackTree.right);
            System.out.print(nodeRedBlackTree.data + " ");
        }
    }

    private NodeRedBlackTree searchTreeHelper(NodeRedBlackTree nodeRedBlackTree, int key) {
        if (nodeRedBlackTree == TNULL || key == nodeRedBlackTree.data) {
            return nodeRedBlackTree;
        }

        if (key < nodeRedBlackTree.data) {
            return searchTreeHelper(nodeRedBlackTree.left, key);
        }
        return searchTreeHelper(nodeRedBlackTree.right, key);
    }

    
    private void fixDelete(NodeRedBlackTree x) {
        NodeRedBlackTree s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }

                    
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }

                    
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }


    private void rbTransplant(NodeRedBlackTree u, NodeRedBlackTree v){
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void findNodeHelper(NodeRedBlackTree nodeRedBlackTree, int key){
        
        NodeRedBlackTree z = TNULL;
        NodeRedBlackTree x, y;
        while (nodeRedBlackTree != TNULL){
            if (nodeRedBlackTree.data == key) {
                z = nodeRedBlackTree;
            }

            if (nodeRedBlackTree.data <= key) {
                nodeRedBlackTree = nodeRedBlackTree.right;
            } else {
                nodeRedBlackTree = nodeRedBlackTree.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }
        System.out.println("Found");
    }

    public int find(int key){
        findNodeHelper(this.root,key);
        return key;
    }

    private void deleteNodeHelper(NodeRedBlackTree nodeRedBlackTree, int key) {
        
        NodeRedBlackTree z = TNULL;
        NodeRedBlackTree x, y;
        while (nodeRedBlackTree != TNULL){
            if (nodeRedBlackTree.data == key) {
                z = nodeRedBlackTree;
            }

            if (nodeRedBlackTree.data <= key) {
                nodeRedBlackTree = nodeRedBlackTree.right;
            } else {
                nodeRedBlackTree = nodeRedBlackTree.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0){
            fixDelete(x);
        }
    }

    
    private void fixInsert(NodeRedBlackTree k){
        NodeRedBlackTree u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; 
                if (u.color == 1) {
                    
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        
                        k = k.parent;
                        rightRotate(k);
                    }
                    
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; 

                if (u.color == 1) {
                    
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        
                        k = k.parent;
                        leftRotate(k);
                    }
                    
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    public RedBlackTree() {
        TNULL = new NodeRedBlackTree();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    
    
    public void preorder() {
        preOrderHelper(this.root);
    }

    
    
    public void inorder() {
        inOrderHelper(this.root);
    }

    
    
    public void postorder() {
        postOrderHelper(this.root);
    }

    
    
    public NodeRedBlackTree searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    
    public NodeRedBlackTree minimum(NodeRedBlackTree nodeRedBlackTree) {
        while (nodeRedBlackTree.left != TNULL) {
            nodeRedBlackTree = nodeRedBlackTree.left;
        }
        return nodeRedBlackTree;
    }

    
    public NodeRedBlackTree maximum(NodeRedBlackTree nodeRedBlackTree) {
        while (nodeRedBlackTree.right != TNULL) {
            nodeRedBlackTree = nodeRedBlackTree.right;
        }
        return nodeRedBlackTree;
    }

    

    
    public void leftRotate(NodeRedBlackTree x) {
        NodeRedBlackTree y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    
    public void rightRotate(NodeRedBlackTree x) {
        NodeRedBlackTree y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    
    
    public void insert(int key) {
        
        NodeRedBlackTree nodeRedBlackTree = new NodeRedBlackTree();
        nodeRedBlackTree.parent = null;
        nodeRedBlackTree.data = key;
        nodeRedBlackTree.left = TNULL;
        nodeRedBlackTree.right = TNULL;
        nodeRedBlackTree.color = 1; 

        NodeRedBlackTree y = null;
        NodeRedBlackTree x = this.root;

        while (x != TNULL) {
            y = x;
            if (nodeRedBlackTree.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        
        nodeRedBlackTree.parent = y;
        if (y == null) {
            root = nodeRedBlackTree;
        } else if (nodeRedBlackTree.data < y.data) {
            y.left = nodeRedBlackTree;
        } else {
            y.right = nodeRedBlackTree;
        }

        
        if (nodeRedBlackTree.parent == null){
            nodeRedBlackTree.color = 0;
            return;
        }

        
        if (nodeRedBlackTree.parent.parent == null) {
            return;
        }

        
        fixInsert(nodeRedBlackTree);
    }

    public NodeRedBlackTree getRoot(){
        return this.root;
    }

    
    public void deleteNode(int data) {
        deleteNodeHelper(this.root, data);
    }

    
    public void prettyPrint() {
        printRedBlackTree(this.root,0);
    }

    public void printRedBlackTree(NodeRedBlackTree root, int level){
        int i;
        if (root!=null){
           printRedBlackTree(root.right,level+1);
           for (i=0;i<=level;i++){
               System.out.print("   ");
           }
           char color;
           if (root.color==1){
               color='r';
           }else{
               color='b';
           }
            System.out.println("("+color+")"+root.data);
            printRedBlackTree(root.left,level+1);
        }
    }
}

import java.util.*;

class BinaryTree
{
    private NodeBinaryTree root; 
    
    public BinaryTree() 
    { root = null; } 
    
    public NodeBinaryTree find(int key) 
    { 
        NodeBinaryTree current = root; 
        while(current.iData != key) 
        {
            if(key < current.iData) 
                current = current.leftChild;
            else 

            current = current.rightChild;
            if(current == null) 
                return null; 
        }
        return current; 
    }
    
    public void insert(int id)
    {
        NodeBinaryTree newNodeBinaryTree = new NodeBinaryTree(); 
        newNodeBinaryTree.iData = id; 
        if(root==null) 
            root = newNodeBinaryTree;
        else 
        {
            NodeBinaryTree current = root; 
            NodeBinaryTree parent;
            while(true) 
            {
                parent = current;
                if(id < current.iData) 
                {
                    current = current.leftChild;
                    if(current == null) 
                    { 
                        parent.leftChild = newNodeBinaryTree;
                        return;
                    }
                }
                else 
                {
                    current = current.rightChild;
                    if(current == null) 
                    { 
                        parent.rightChild = newNodeBinaryTree;
                        return;
                    }
                }
            }
        }
    }
    
    public boolean delete(int key) 
    { 
        NodeBinaryTree current = root;
        NodeBinaryTree parent = root;
        boolean isLeftChild = true;
        while (current.iData != key) 
        {
            parent = current;
            if (key < current.iData) 
            {
                isLeftChild = true;
                current = current.leftChild;
            } else 
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) 
                return false; 
        }
        
        
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) 
                root = null; 
            else if (isLeftChild)
                parent.leftChild = null; 
            else 
                parent.rightChild = null;
        }
        
        else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;
            
        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;
        else
        {
            NodeBinaryTree successor = getSuccessor(current);
            if (current == root){
                successor.leftChild=root.leftChild;
                root = successor;
            }

            else if (isLeftChild){
                successor.leftChild=parent.leftChild.leftChild;
                parent.leftChild = successor;
            }
            else{
                successor.leftChild=parent.rightChild.leftChild;
                parent.rightChild = successor;
            }
            return true;
        }
        return true;
    }


        
        
        
        private NodeBinaryTree getSuccessor(NodeBinaryTree delNodeBinaryTree)
        {
            NodeBinaryTree successorParent = delNodeBinaryTree;
            NodeBinaryTree successor = delNodeBinaryTree;
            NodeBinaryTree current = delNodeBinaryTree.rightChild; 
            while(current != null) 
            {
                successorParent = successor;
                successor = current;
                current = current.leftChild; 
            }
            
            if(successor != delNodeBinaryTree.rightChild) 
            { 
                successorParent.leftChild = successor.rightChild;
                successor.rightChild = delNodeBinaryTree.rightChild;
            }
            return successor;
        }



        private void preOrder(NodeBinaryTree localRoot)
        {
            if(localRoot != null)
            {
                System.out.print(localRoot.iData + " ");
                preOrder(localRoot.leftChild);
                preOrder(localRoot.rightChild);
            }
        }

        private void inOrder(NodeBinaryTree localRoot)
        {
            if(localRoot != null)
            {
                inOrder(localRoot.leftChild);
                System.out.print(localRoot.iData + " ");
                inOrder(localRoot.rightChild);
            }
        }

        private void postOrder(NodeBinaryTree localRoot)
        {
            if(localRoot != null)
            {
                postOrder(localRoot.leftChild);
                postOrder(localRoot.rightChild);
                System.out.print(localRoot.iData + " ");
            }
        }


    public void prettyBinaryPrint() {
        printBinaryTree(this.root,0);
    }

    public void printBinaryTree(NodeBinaryTree root, int level){
        int i;
        if (root!=null){
            printBinaryTree(root.rightChild,level+1);
            for (i=0;i<=level;i++){
                System.out.print("   ");
            }
            System.out.println(root.iData);
            printBinaryTree(root.leftChild,level+1);
        }
    }


    }
    
    class NodeBinaryTree
{
    public int iData; 
    public NodeBinaryTree leftChild; 
    public NodeBinaryTree rightChild; 
    public void displayNode() 
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print("} ");
    }
}

public class LAB5 {
    public static void main(String[] args) throws IOException {
        int value;

        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(1);
        redBlackTree.insert(2);
        redBlackTree.insert(3);
        redBlackTree.insert(4);
        redBlackTree.insert(5);


        BinaryTree theBinaryTree = new BinaryTree();
        theBinaryTree.insert(50);
        theBinaryTree.insert(25);
        theBinaryTree.insert(75);
        theBinaryTree.insert(12);
        theBinaryTree.insert(37);
        theBinaryTree.insert(47);
        theBinaryTree.insert(74);
        theBinaryTree.insert(56);


       label: while (true){
            System.out.print("Enter first letter of show, ");
            System.out.print("red-black trees, binary trees, exit: ");
            int choice = getChar();
            switch (choice){
                case 'r':
                    while (true){
                        System.out.print("Enter first letter of show, ");
                        System.out.print("insert, find, delete, return to main menu, exit: ");
                        choice=getChar();
                        switch (choice)
                        {
                            case 's':
                                redBlackTree.prettyPrint();
                                break;
                            case 'i':
                                System.out.print("Enter value to insert: ");
                                value = getInt();
                                redBlackTree.insert(value);
                                break;
                            case 'f':
                                System.out.print("Enter value to find: ");
                                value = getInt();
                                int res=redBlackTree.find(value);
                                System.out.println(res);
                            case 'd':
                                System.out.print("Enter value to delete: ");
                                value=getInt();
                                redBlackTree.deleteNode(value);
                                break;
                            case 'r':
                                continue label;
                            case 'e':
                                return;
                        }
                    }

                case 'b':
                    while (true)
                    {
                        System.out.print("Enter first letter of show, ");
                        System.out.print("insert, find, delete, return to main menu, exit: ");
                        choice = getChar();
                        switch (choice)
                        {
                            case 's':
                                theBinaryTree.prettyBinaryPrint();
                                break;
                            case 'i':
                                System.out.print("Enter value to insert: ");
                                value = getInt();
                                theBinaryTree.insert(value);
                                break;
                            case 'f':
                                System.out.print("Enter value to find: ");
                                value = getInt();
                                NodeBinaryTree found = theBinaryTree.find(value);
                                if (found != null) {
                                    System.out.print("Found: ");
                                    found.displayNode();
                                    System.out.print("\n");
                                }
                            case 'd':
                                System.out.print("Enter value to delete: ");
                                value=getInt();
                                theBinaryTree.delete(value);
                                break;
                            case 'r':
                                continue label;
                            case 'e':
                                return;
                        }
                    }
                case 'e':
                    return;

            }
        }

    }


    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    // -------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }

}
    
