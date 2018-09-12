package com.company;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class GameTree
{
    public TreeNode root;

    public GameTree (Schedule rootState) {
        this.root = new TreeNode();
        this.root.state = rootState;
        this.root.children = new ArrayList<TreeNode>();
    }

    public TreeNode addNode (Schedule state, TreeNode parent) {
        TreeNode newNode = new TreeNode();
        newNode.state = state;
        newNode.parent = parent;
        newNode.children = new ArrayList<TreeNode>();
        newNode.evaluationValue = 0;
        parent.children.add(newNode);
        return newNode;
    }

    public static class TreeNode {
        Schedule state;
        TreeNode parent;
        ArrayList <TreeNode> children;
        int evaluationValue;
    }

    public static GameTree generateTree(Schedule initialSchedule, int searchDepth)
    {
        int reachedDepth = 0;

        GameTree tree = new GameTree(initialSchedule);

        // list of leaf nodes that need to be expanded into new sub-trees
        Deque<TreeNode> leafNodes = new LinkedList<>();

        // swap buffer for repopulating leafNodes
        Deque<GameTree.TreeNode> buffer = new LinkedList<>();

        leafNodes.add(tree.root);

        while(reachedDepth < searchDepth)
        {
            // for each leaf node, grow its children and append them
            while(leafNodes.peek() != null)
            {
                growTree(leafNodes.getFirst());

                for(GameTree.TreeNode i : leafNodes.getFirst().children)
                {
                    GameTree.TreeNode temp = new GameTree.TreeNode();

                    // needed in order to correctly display parent moves
                    temp.children = i.children;
                    temp.parent = i.parent;
                    temp.state = i.state.clone();
                    temp.state.parentMove.clear();

                    // these generated children are going to be the new leaf nodes to grow
                    buffer.addLast(temp);
                }

                leafNodes.removeFirst();
            }

            // populate the next generation of leaf nodes
            for(GameTree.TreeNode i : buffer)
            {
                leafNodes.addLast(i);
            }

            buffer.clear();

            reachedDepth++;
        }

        return tree;
    }

    // Given a node, generate its next "generation" of childred
    public static void growTree(GameTree.TreeNode root)
    {
        Deque<Schedule> resultingSchedules = generateFutureSchedules(root.state);

        GameTree tempTree = new GameTree(root.state);

        for (Schedule i : resultingSchedules) {
            tempTree.addNode(i, root);
        }

        root = tempTree.root;
    }

    // given a board layout, generate all the possible moves in a given round for it
    public static Deque<Board> generateFutureSchedules(Schedule startingSchedule){
        Board child;

        Deque<Board> layouts = new LinkedList<>();

        for(int i = 0; i<7; i++) {
            child = startingSchedule.clone();

            if(child.makeMove(i) != -1){
                {
                    child.parentMove.add(i);
                    if(child.playerToMove == startingSchedule.playerToMove){
                        Deque<Board> temp = generateFutureLayouts(child);
                        for(Board b : temp)
                            layouts.add(b);
                    }
                    else
                        layouts.add(child);
                }
            }
        }

        return layouts;
    }
}
