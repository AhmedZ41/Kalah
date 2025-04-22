package kalah;

import java.util.List;
import java.util.Comparator;

/**
 * Alpha-Beta Pruning with Move Ordering.
 * This version adds a heuristic-based sorting of child nodes to improve pruning.
 */
public class AlphaBetaOrdered {

    private static final int MAX_DEPTH = 6;

    public static int nodeCount = 0;

    public static int bestMove(KalahBoard board) {
        nodeCount = 0;

        int bestValue = (board.getCurPlayer() == 'A') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestAction = -1;

        List<KalahBoard> successors = board.possibleActions();

        //  Apply move ordering
        successors.sort(getComparator(board.getCurPlayer()));

        for (KalahBoard child : successors) {
            nodeCount++;
            int value = alphaBeta(child, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            int move = child.getLastPlay();

            if (board.getCurPlayer() == 'A') {
                if (value > bestValue) {
                    bestValue = value;
                    bestAction = move;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestAction = move;
                }
            }
        }

        return bestAction;
    }

    private static int alphaBeta(KalahBoard board, int depth, int alpha, int beta) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }

        List<KalahBoard> successors = board.possibleActions();

        // Apply move ordering again at each level
        successors.sort(getComparator(board.getCurPlayer()));

        if (board.getCurPlayer() == 'A') {
            int maxEval = Integer.MIN_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = alphaBeta(child, depth - 1, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break; // Prune
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = alphaBeta(child, depth - 1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break; // Prune
            }
            return minEval;
        }
    }

    private static int evaluate(KalahBoard board) {
        return board.getAKalah() - board.getBKalah();
    }

    //  This returns a Comparator that sorts children boards based on evaluation
    private static Comparator<KalahBoard> getComparator(char player) {
        return (a, b) -> {
            int evalA = evaluate(a);
            int evalB = evaluate(b);
            return (player == 'A') ? Integer.compare(evalB, evalA) : Integer.compare(evalA, evalB);
        };
    }
}
