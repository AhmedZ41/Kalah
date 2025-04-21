package kalah;

import java.util.List;

/**
 * Implements the Minimax algorithm with Alpha-Beta Pruning
 * for the Kalah board game.
 *
 * Player A is maximizing, Player B is minimizing.
 */
public class AlphaBeta {

    private static final int MAX_DEPTH = 6;

    // Count of nodes evaluated (for analysis)
    public static int nodeCount = 0;

    /**
     * Entry point for choosing the best move for the current player.
     *
     * @param board current board state
     * @return best move index (0–5 for A, 7–12 for B)
     */
    public static int bestMove(KalahBoard board) {
        nodeCount = 0;

        int bestValue = (board.getCurPlayer() == 'A') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestAction = -1;

        List<KalahBoard> successors = board.possibleActions();
        for (KalahBoard child : successors) {
            nodeCount++;
            int value = alphabeta(child, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
     * Recursive Minimax function with Alpha-Beta pruning.
     *
     * @param board current game state
     * @param depth remaining depth
     * @param alpha best value found so far for maximizer
     * @param beta  best value found so far for minimizer
     * @return evaluation value
     */
    private static int alphabeta(KalahBoard board, int depth, int alpha, int beta) {
        if (depth == 0 || board.isFinished()) {
            return evaluate(board);
        }

        List<KalahBoard> successors = board.possibleActions();

        if (board.getCurPlayer() == 'A') {
            int maxEval = Integer.MIN_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = alphabeta(child, depth - 1, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break; // Beta cutoff
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (KalahBoard child : successors) {
                nodeCount++;
                int eval = alphabeta(child, depth - 1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break; // Alpha cutoff
            }
            return minEval;
        }
    }

    /**
     * Simple evaluation function: difference between A's and B's kalahs.
     *
     * @param board game board
     * @return evaluation score
     */
    private static int evaluate(KalahBoard board) {
        return board.getAKalah() - board.getBKalah();
    }
}
