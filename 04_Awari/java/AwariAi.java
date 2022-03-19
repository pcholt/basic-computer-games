public interface AwariAi {

    /**
     * Artificial intelligence, core of the decision-making process.
     * @param board the current game board. The game board should be immutable.
     * @return the best possible move
     */
    int getMove(int[] board);
}
