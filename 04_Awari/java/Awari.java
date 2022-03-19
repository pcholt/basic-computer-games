import java.util.Scanner;
import java.util.logging.Logger;

public class Awari {
    int[] board;
    private final int playerPits;
    private final int computerPits;
    private final int playerHome;
    private final int computerHome;
    Scanner input;
    int sumPlayer;
    int sumComputer;
    Logger logger = Logger.getAnonymousLogger();
    private final AwariAi awariAi;

    Awari(AwariAi awariAi) {
        this.awariAi = awariAi;
        input = new Scanner(System.in);
        playerPits = 0;
        computerPits = 7;
        playerHome = 6;
        computerHome = 13;
        sumPlayer = 18;
        sumComputer = 18;
        board = new int[14];
        for (int i = 0; i < 6; i++) {
            board[playerPits + i] = 3;
            board[computerPits + i] = 3;
        }
        System.out.println("		 AWARI");
        System.out.println("CREATIVE COMPUTING MORRISTOWN, NEW JERSEY");
        printBoard();
        playerMove(true);
    }

    private void printBoard() {
        System.out.print("\n    ");
        for (int i = 0; i < 6; i++) {
            System.out.printf("%2d", board[12 - i]);
            System.out.print("  ");
        }
        System.out.println();
        System.out.printf("%2d", board[computerHome]);
        System.out.print("                          ");
        System.out.printf("%2d%n", board[playerHome]);
        System.out.print("    ");
        for (int i = 0; i < 6; i++) {
            System.out.printf("%2d", board[playerPits + i]);
            System.out.print("  ");
        }
        System.out.println();
    }

    private void playerMove(boolean isInitialMove) {
        logger.info("ComputerSum=" + sumComputer + " PlayerSum=" + sumPlayer);
        if (isInitialMove)
            System.out.print("YOUR MOVE? ");
        else
            System.out.print("AGAIN? ");
        int move = input.nextInt();
        while (move < 1 || move > 6 || board[move - 1] == 0) {
            System.out.print("INVALID MOVE!!! TRY AGAIN  ");
            move = input.nextInt();
        }
        int seeds = board[move - 1];
        board[move - 1] = 0;
        sumPlayer -= seeds;
        int last_pos = distribute(seeds, move);
        if (last_pos == playerHome) {
            printBoard();
            if (isGameOver(true)) {
                System.exit(0);
            }
            playerMove(false);
        } else if (board[last_pos] == 1 && last_pos != computerHome) {
            captureOpposite(last_pos);
            printBoard();
            if (isGameOver(false)) {
                System.exit(0);
            }
            computerMove(true);
        } else {
            printBoard();
            if (isGameOver(false)) {
                System.exit(0);
            }
            computerMove(true);
        }
    }

    private void captureOpposite(int last_pos) {
        int opp = calculateOpposite(last_pos);
        if (last_pos < 6) {
            sumPlayer += board[opp];
            sumComputer -= board[opp];
        } else {
            sumComputer += board[opp];
            sumPlayer -= board[opp];
        }
        board[last_pos] += board[opp];
        board[opp] = 0;
    }

    private void computerMove(boolean value) {
        int val = -1;
        logger.info("ComputerSum=" + sumComputer + " PlayerSum=" + sumPlayer);
        for (int i = 0; i < 6; i++) {
            if (6 - i == board[computerPits + i])
                val = i;
        }
        if (val == -1) {
            int move = awariAi.getMove(board);
            if (value)
                System.out.printf("MY MOVE IS %d %n", move - computerPits + 1);
            else
                System.out.printf(",%d%n", move - computerPits + 1);
            int seeds = board[move];
            board[move] = 0;
            sumComputer -= seeds;
            int last_pos = distribute(seeds, move + 1);
            if (board[last_pos] == 1 && last_pos != playerHome) {
                captureOpposite(last_pos);
            }
            printBoard();
            if (isGameOver(false)) {
                System.exit(0);
            }
            playerMove(true);
        } else {
            int move = val + computerPits;
            if (value)
                System.out.printf("MY MOVE IS %d", move - computerPits + 1);
            else
                System.out.printf(",%d", move - computerPits + 1);
            int seeds = board[move];
            board[move] = 0;
            sumComputer -= seeds;
            int last_pos = distribute(seeds, move + 1);
            if (last_pos == computerHome) {
                if (isGameOver(true)) {
                    System.exit(0);
                }
                computerMove(false);
            }
        }
    }

    private int distribute(int seeds, int pos) {
        while (seeds != 0) {
            if (pos == 14)
                pos = 0;
            if (pos < 6)
                sumPlayer++;
            else if (pos > 6 && pos < 13)
                sumComputer++;
            board[pos]++;
            pos++;
            seeds--;
        }
        return pos - 1;
    }

    private int calculateOpposite(int pos) {
        return 12 - pos;
    }

    private boolean isGameOver(boolean show) {
        if (sumPlayer == 0 || sumComputer == 0) {
            if (show)
                printBoard();
            System.out.println("GAME OVER");
            if (board[playerHome] > board[computerHome]) {
                System.out.printf("YOU WIN BY %d POINTS%n", board[playerHome] - board[computerHome]);
            } else if (board[playerHome] < board[computerHome]) {
                System.out.printf("YOU LOSE BY %d POINTS%n", board[computerHome] - board[playerHome]);
            } else {
                System.out.println("DRAW");
            }
            return true;
        }
        return false;
    }


}
