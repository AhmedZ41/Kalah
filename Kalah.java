package kalah;


import java.util.Random;

import static kalah.MiniMax.bestMove;

/**
 * Hauptprogramm für KalahMuster.
 *
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {

    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     *
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {

        //testExample();
        //testHHGame();
        //testMiniMaxAndAlphaBetaWithGivenBoard();
        //testHumanMiniMax();
        //testHumanMiniMaxAndAlphaBeta();
        compareNodeCounts();
    }

    /**
     * Beispiel von https://de.wikipedia.org/wiki/Kalaha
     */
    public static void testExample() {
        KalahBoard kalahBd = new KalahBoard(new int[]{5, 3, 2, 1, 2, 0, 0, 4, 3, 0, 1, 2, 2, 0}, 'B');
        kalahBd.print();

        System.out.println("B spielt Mulde 11");
        kalahBd.move(11);
        kalahBd.print();

        System.out.println("B darf nochmals ziehen und spielt Mulde 7");
        kalahBd.move(7);
        kalahBd.print();
    }

    /**
     * Mensch gegen Mensch
     */
    public static void testHHGame() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action = kalahBd.readAction();
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testMiniMaxAndAlphaBetaWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-aml hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            int move;
            if (kalahBd.getCurPlayer() == 'A') {
                action = MiniMax.bestMove(kalahBd); //  MiniMax decides
                //action = AlphaBeta.bestMove(kalahBd);
                //action = AlphaBetaOrdered.bestMove(kalahBd);
                //move = AlphaBetaOrdered.bestMove(kalahBd);

                System.out.println("AI (A) plays: " + action);
                System.out.println("MiniMax node count: " + MiniMax.nodeCount);
                //System.out.println("AlphaBeta nodeCount: " + AlphaBeta.nodeCount);
                //System.out.println("AlphaBetaOrdered chooses: " + move + " (Nodes: " + AlphaBetaOrdered.nodeCount + ")");

            } else {
                action = kalahBd.readAction(); // Human plays B
            }

            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }
    public static void compareNodeCounts() {
        int testCases = 10;

        int totalMiniMax = 0;
        int totalAlphaBeta = 0;
        int totalAlphaBetaOrdered = 0;

        for (int i = 1; i <= testCases; i++) {
            KalahBoard board = generateRandomBoard('A');

            System.out.println("Test Case #" + i);
            board.print();

            // MiniMax
            MiniMax.bestMove(board);
            System.out.println("MiniMax nodes: " + MiniMax.nodeCount);
            totalMiniMax += MiniMax.nodeCount;

            // AlphaBeta
            AlphaBeta.bestMove(board);
            System.out.println("AlphaBeta nodes: " + AlphaBeta.nodeCount);
            totalAlphaBeta += AlphaBeta.nodeCount;

            // AlphaBetaOrdered
            AlphaBetaOrdered.bestMove(board);
            System.out.println("AlphaBetaOrdered nodes: " + AlphaBetaOrdered.nodeCount);
            totalAlphaBetaOrdered += AlphaBetaOrdered.nodeCount;

            System.out.println("---------------------------------------------------\n");
        }

        // Averages
        System.out.println("=== Average Node Expansions over " + testCases + " test cases ===");
        System.out.println("MiniMax:           " + (totalMiniMax / testCases));
        System.out.println("AlphaBeta:         " + (totalAlphaBeta / testCases));
        System.out.println("AlphaBetaOrdered:  " + (totalAlphaBetaOrdered / testCases));
    }
    private static KalahBoard generateRandomBoard(char startingPlayer) {
        Random rand = new Random();
        int[] board = new int[14];

        // Fill pits with a small random number of stones ( 0 to 6)
        for (int i = 0; i < 14; i++) {
            if (i == 6 || i == 13) {
                board[i] = 0; // Start Kalahs empty
            } else {
                board[i] = rand.nextInt(4); // Keep it reasonable for performance
            }
        }

        return new KalahBoard(board, startingPlayer);
    }

}
