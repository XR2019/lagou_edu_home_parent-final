package com.lagou;
/**
 * 实现要求：
 * 1、根据已有的代码片段，创建二叉搜索树；
 * 2、用中序遍历输出排序结果，结果形如：0，1，2 ，3 ，4， 5， 6， 7， 8， 9，
 * 3、使用递归、非递归二种方式实现遍历；
 * 4、注意编写代码注释。
 */
public class BinaryTree {
    public static void main(String[] args) {
        final int[] values ={1, 3, -4, 5, 2, 8, 6, 7, 9, 0};
        System.out.println("构建二叉树：");
        Node root = createBinaryTree(values);
        System.out.println("对Node节点进行中序遍历：");
        middleGet(root);


    }
    //构建二叉树
    public static Node createBinaryTree(int[] values) {
        Node root = null;
        //利用for循环构建二叉树
        for (int i=0;i<values.length;i++) {
           root = insert(root,values[i]);
        }
        return root;
    }
    //插入方法
    public static Node insert(Node node,int data) {
        //如果当前节点为空则插入第一节点
        if (node == null) {
            return new Node(data); //将数组中第一个节点作为root
        }
        //插入左边
       else if (data < node.data) {
            node.leftChild = insert(node.leftChild,data);
        }
        //插入右边
        else if (data > node.data) {
           node.rightChild = insert(node.rightChild,data);
        } else {
            node.data = data; //对当前节点进行覆盖操作
        }
        return node;
    }
    //使用递归实现中序遍历：左-->根节点-->右
    public static void middleGet(Node node) {
        if (node == null) {
            return;
        } else {
            //递归实现
            middleGet(node.leftChild);
            System.out.println(node.data); //输出根节点数据
            middleGet(node.rightChild);
        }
    }
}
class Node{
    //需要新创建一个类Node节点类，里面包含的数据有：data数据部分、左孩子节点、右孩子节点
    public int data; //节点的数据部分
    public Node leftChild; //左孩子
    public Node rightChild; //右孩子
    //创建包含data数据的构造器
    public Node(int data) {
        this.data = data;
    }
}
