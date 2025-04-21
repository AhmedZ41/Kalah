package kalah;


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
        testMiniMaxAndAlphaBetaWithGivenBoard();
        //testHumanMiniMax();
        //testHumanMiniMaxAndAlphaBeta();
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
                //action = MiniMax.bestMove(kalahBd); //  MiniMax decides
                //action = AlphaBeta.bestMove(kalahBd);
                action = AlphaBetaOrdered.bestMove(kalahBd);
                move = AlphaBetaOrdered.bestMove(kalahBd);

                System.out.println("AI (A) plays: " + action);
                //System.out.println("MiniMax node count: " + MiniMax.nodeCount);
                //System.out.println("AlphaBeta nodeCount: " + AlphaBeta.nodeCount);
                System.out.println("AlphaBetaOrdered chooses: " + move + " (Nodes: " + AlphaBetaOrdered.nodeCount + ")");

            } else {
                action = kalahBd.readAction(); // Human plays B
            }

            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }
}
