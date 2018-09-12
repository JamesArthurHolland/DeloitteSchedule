package com.company;

public class Pruner
{
    public static final int INFINITY = 100000;

    /*
     * Assign values to the given tree nodes in MinMax manner.
     *
     */
    public static int MinMax(GameTree.TreeNode node, int alpha, int beta, boolean maximizingPlayer) {
        if (node.children.isEmpty()) {
            return node.evaluationValue;
        }
        if (maximizingPlayer == true) {
            node.evaluationValue = -1 * INFINITY;
            for (GameTree.TreeNode child : node.children) {
                node.evaluationValue = Math.max(node.evaluationValue, MinMax(child, alpha, beta, false));
                alpha = Math.max(alpha, node.evaluationValue);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        else {
            node.evaluationValue = INFINITY;
            for(GameTree.TreeNode child : node.children) {
                node.evaluationValue = Math.min(node.evaluationValue, MinMax(child, alpha, beta, true));
                beta = Math.min(beta, node.evaluationValue);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return node.evaluationValue;
    }
}
