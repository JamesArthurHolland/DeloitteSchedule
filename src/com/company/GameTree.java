package com.company;

import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;

public class GameTree
{
    public TreeNode root;

    public GameTree (Schedule rootState) {
        this.root = new TreeNode(rootState, null);
        this.root.state = rootState;
        this.root.children = new ArrayList<TreeNode>();
    }

    public TreeNode addNode (Schedule state, TreeNode parent) {
        TreeNode newNode = new TreeNode(state, parent);
        parent.children.add(newNode);
        return newNode;
    }

    public static class TreeNode {
        Schedule state;
        TreeNode parent;
        ArrayList <TreeNode> children;
        int evaluationValue;

        public TreeNode(Schedule state, TreeNode parent)
        {
            this.state = state;
            this.parent = parent;
            this.children = new ArrayList<>();
            this.evaluationValue = 0;
        }

        public Schedule getSchedule()
        {
            return state;
        }
    }

//    public static GameTree generateTree(Schedule initialSchedule, int searchDepth)
//    {
//        int reachedDepth = 0;
//
//        GameTree tree = new GameTree(initialSchedule);
//
//        // list of leaf nodes that need to be expanded into new sub-trees
//        Stack<TreeNode> leafNodes = new LinkedList<>();
//
//        // swap buffer for repopulating leafNodes
//        Stack<GameTree.TreeNode> buffer = new LinkedList<>();
//
//        leafNodes.add(tree.root);
//
//        while(reachedDepth < searchDepth)
//        {
//            // for each leaf node, grow its children and append them
//            while(leafNodes.peek() != null)
//            {
//                growTree(leafNodes.getFirst());
//
//                for(GameTree.TreeNode i : leafNodes.getFirst().children)
//                {
//                    GameTree.TreeNode temp = new GameTree.TreeNode();
//
//                    // needed in order to correctly display parent moves
//                    temp.children = i.children;
//                    temp.parent = i.parent;
//                    temp.state = i.state.clone();
//                    temp.state.parentMove.clear();
//
//                    // these generated children are going to be the new leaf nodes to grow
//                    buffer.addLast(temp);
//                }
//
//                leafNodes.removeFirst();
//            }
//
//            // populate the next generation of leaf nodes
//            for(GameTree.TreeNode i : buffer)
//            {
//                leafNodes.addLast(i);
//            }
//
//            buffer.clear();
//
//            reachedDepth++;
//        }
//
//        return tree;
//    }
//
//    // Given a node, generate its next "generation" of childred
//    public static void growTree(GameTree.TreeNode root)
//    {
//        Deque<Schedule> resultingSchedules = generateFutureSchedules(root.state);
//
//        GameTree tempTree = new GameTree(root.state);
//
//        for (Schedule i : resultingSchedules) {
//            tempTree.addNode(i, root);
//        }
//
//        root = tempTree.root;
//    }


    public static int idaStarSearch(TreeNode node, int g, int threshold)
    {
        int evaluation = node.getSchedule().evaluate();
        int f = g + evaluation;

        if(f > threshold) {
            return f;
        }
        if(evaluation == Schedule.FEASIBLE_SCHEDULE) {
            return Schedule.FEASIBLE_SCHEDULE;
        }
        int min = Integer.MAX_VALUE;
        for(TreeNode tempNode : nextNodes(node)) {
            int temp = idaStarSearch(tempNode, g, threshold);
            if(temp == Schedule.FEASIBLE_SCHEDULE) {
                return Schedule.FEASIBLE_SCHEDULE;
            }
            if(temp < min) {
                min = temp;
            }
        }
        return min;
    }

    // given a board layout, generate all the possible moves in a given round for it
    public static ArrayList<TreeNode> nextNodes(TreeNode parent){
        Schedule child;

        Schedule startingSchedule = parent.getSchedule();

        ArrayList<Activity> remainingActivities = startingSchedule.getRemainingActivitiesForCurrentTeam();

        ArrayList<TreeNode> nodes = new ArrayList<>();
        for(Activity activity : remainingActivities) {
            if(startingSchedule.isActivityFree(activity)) {
                child = new Schedule(startingSchedule);
                child.addActivity(activity);
                nodes.add(new TreeNode(startingSchedule, parent));
            }
        }

        return nodes;
    }
}
