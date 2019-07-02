package com.bless.java8;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by wangxi on 2019/6/27.
 */
@Slf4j
public class TreeDemo {

    public static void main(String[] args) {

       List<String > ss = Lists.newArrayList("a","b","c","d","e","f","g","h","i","j","k","l"
               ,"m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6");

        BinaryTree binaryTree = new BinaryTree(ss);
        log.info("binaryTree的 深度:" + binaryTree.getDeep());
        log.info(JSONObject.toJSONString(binaryTree.getNode(),true));


        List<String > numbers = Lists.newArrayList("3","4","5","6","7");
        Node root = new Node();
        root.setName("4");
        root.setValue(4);

        BinaryTree binaryTree2 = new BinaryTree(root);

        for (String number : numbers) {
            Node child = new Node();
            child.setValue(Integer.parseInt(number));
            child.setName(number);
            root.addNode(child);
        }


        log.info(JSONObject.toJSONString(root,true));
        log.info("深度:   "  + binaryTree2.getDeep());
    }

    @Data
    public static class BinaryTree{
        public Node node;
        private Integer deep;

        BinaryTree(List<String> names) {
            node = createDemo(Lists.newArrayList(),names,0);
        }

        public BinaryTree(Node node) {
            this.node = node;
        }

        //按层次初始化
        private Node createDemo(List<Node> nodes,List<String> names,Integer nameIndex){

            if (nodes.size() == 0 && nameIndex == 0){
                node = new Node();
                node.setName(names.get(nameIndex));
                nodes.add(node);
            }
            List<Node> nextNodes = Lists.newArrayList();
            for (Node node: nodes){
                ChildObject childObject = createLRNode(node,names,nameIndex);
                nextNodes.addAll(childObject.getChildren());
                nameIndex = childObject.getNameIndex();
            }

            if (nextNodes.size() != 0){
                createDemo(nextNodes,names,nameIndex);
            }


            return node;
        }

        private ChildObject createLRNode(Node node,List<String> names,Integer nameIndex){
            List<Node> childNodes = Lists.newArrayList();

            if (node.getLeftChild() == null && ++nameIndex < names.size()){

                Node leftNode = new Node();
                leftNode.setName(names.get(nameIndex));
                leftNode.setIsRight(false);
                node.setLeftChild(leftNode);
                childNodes.add(leftNode);
            }

            if (node.getRightChild() == null && ++nameIndex < names.size()){
                Node rightNode = new Node();
                rightNode.setName(names.get(nameIndex));
                rightNode.setIsRight(true);
                node.setRightChild(rightNode);
                childNodes.add(rightNode);
            }


            return ChildObject.of(childNodes,nameIndex);
        }

        @Data
        @AllArgsConstructor(staticName = "of")
        @NoArgsConstructor
        private static class ChildObject {
            private List<Node> children;
            private Integer nameIndex;
        }


        /**
         * 获取整颗树的深度
         * @return
         */
        public Integer getDeep() {
            return deep(Lists.newArrayList(node),1);
        }

        /**
         * TODO 查找深度
         * @param
         * @param maxCount
         * @return
         */
        private Integer deep(List<Node> nodes,int maxCount){

            List<Node> nextNodes = Lists.newArrayList();
            Boolean hasNext = false;
            for (Node node:nodes) {
                if (node.getLeftChild() != null){
                    hasNext = true;
                    nextNodes.add(node.getLeftChild());
                }

                if (node.getRightChild() != null){
                    hasNext = true;
                    nextNodes.add(node.getRightChild());
                }
            }

            if (hasNext){
                maxCount++;
                maxCount = deep(nextNodes,maxCount);
            }

           return maxCount;
        }
    }


    @Data
    @NoArgsConstructor
    public static class Node {
        //父节点
//        private Node parent;

        private Boolean isRight;
        //左子节点
        private Node leftChild;
        //右子节点
        private Node rightChild;
        //深度
        private Integer deep;
        //当前节点的度 即 子节点个数
        private Integer degree;
        //节点名字
        private String name;

        //节点值
        private Integer value;

        public Integer getDegree() {
            int count = 0;
            if (leftChild != null) count++;
            if (rightChild != null) count++;
            return count;
        }

        public void addNode(Node node){
            //如果加入的值 小于 当前树的值
            if (node.value < value){
                //判断左边有树没
                if (leftChild != null){
                    leftChild.addNode(node);
                }else {
                    leftChild = node;
                }

            }else {
                //值比当前的大
                if (rightChild == null){
                    rightChild = node;
                }else {
                    rightChild.addNode(node);
                }
            }
        }


    }


}
