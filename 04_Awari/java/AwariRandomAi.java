import java.util.Random;

public record AwariRandomAi(int computerPits) implements AwariAi {

    static Random random = new Random();

    /**
     * {@inheritDoc}
     * @param board the current game board. The game board should be immutable.
     * @return a move chosen at random from the available legal moves.
     */
    @Override
    public int getMove(int[] board) {
        int move;
        do {
            move = randomMove(random);
        }
        while (board[move] == 0);
        return move;
    }

    private int randomMove(Random random) {
        return random.nextInt(6) + computerPits;
    }

}
