package com.company;

import java.util.ArrayList;

public class GameTree
{
    public static final int INFINITY = 100000;

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


    public int idaStar()
    {
        int threshold = root.getSchedule().evaluate();
        while(true) {
            int temp = idaStarSearch(root, 0, threshold);
            if(temp == Schedule.FEASIBLE_SCHEDULE) {
                return Schedule.FEASIBLE_SCHEDULE;
            }
            if(temp == INFINITY) {
                return -1;
            }
            threshold = temp;
        }
    }

    public int idaStarSearch(TreeNode node, int g, int threshold)
    {
        int evaluation = node.getSchedule().evaluate();
        int f = g + evaluation;

        if(f > threshold) {
            return f;
        }
        if(evaluation == Schedule.FEASIBLE_SCHEDULE) {
            node.getSchedule().printSchedule();
            return Schedule.FEASIBLE_SCHEDULE;
        }
        int min = INFINITY;
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
    public ArrayList<TreeNode> nextNodes(TreeNode parent){
        Schedule child;

        Schedule startingSchedule = parent.getSchedule();

        ArrayList<Activity> remainingActivities = new ArrayList<>();
        for(Activity activity : startingSchedule.getRemainingActivitiesForCurrentTeam()) {
            remainingActivities.add(new Activity(activity));
        }

        ArrayList<TreeNode> nodes = new ArrayList<>();
        for(Activity activity : remainingActivities) {
            if(startingSchedule.isActivityFree(activity)) {
                child = new Schedule(startingSchedule);
                child.addActivity(activity);
                TreeNode childNode = new TreeNode(child, parent);
                parent.children.add(childNode);
                nodes.add(childNode);
            }
        }

        return nodes;
    }

}
