package kalah;

import java.util.List;

/**
 * This class implements the Minimax algorithm with a fixed search depth
 * to evaluate the best possible move for a given KalahBoard.
 *
 * The algorithm assumes player 'A' is maximizing and player 'B' is minimizing.
 *
 * @author AhmedZ41
 */
public class MiniMax {

    private static final int MAX_DEPTH = 6;

    // Counter to track number of nodes visited (for performance analysis)
    public static int nodeCount = 0;

    /**
     * Entry point: returns the best move for the current player.
     *
     * @param board current board state
     * @return the best move index (0-5 for A, 7-12 for B)
     */
    public static int bestMove(KalahBoard board) {
        nodeCount = 0; // reset counter

        int bestValue = (board.getCurPlayer() == 'A') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestAction = -1;

        List<KalahBoard> successors = board.possibleActions();
        for (KalahBoard child : successors) {
            nodeCount++;
            int value = minimax(child, MAX_DEPTH - 1);
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

    /**
     * The core minimax algorithm. Returns the evaluation of the given board.
     *
     * @param board current board
     * @param depth remaining search depth
     * @return evaluation score
     */
    private static int minimax(KalahBoard board, int depth) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }

        List<KalahBoard> successors = board.possibleActions();

        if (board.getCurPlayer() == 'A') {
            int maxEval = Integer.MIN_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = minimax(child, depth - 1);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = minimax(child, depth - 1);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    /**
     * Evaluation function for a given board.
     * This version simply returns the difference between the players' Kalahs.
     *
     * @param board the current board
     * @return evaluation value (positive = better for A, negative = better for B)
     */
    private static int evaluate(KalahBoard board) {
        return board.getAKalah() - board.getBKalah();
    }
}
