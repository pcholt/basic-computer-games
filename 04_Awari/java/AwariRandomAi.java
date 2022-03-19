import java.util.Random;

public record AwariRandomAi(int computerPits) implements AwariAi {

    @Override
    public int getMove(int[] board) {
        Random random = new Random();
        int move = random.nextInt(6) + computerPits;
        while (board[move] == 0) {
            move = random.nextInt(6) + computerPits;
        }
        return move;
    }

}
