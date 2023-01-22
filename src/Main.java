import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Pieces> plays = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Hello world!\nWelcome to this Tic-Ta-Toe game");
        char player1 = 'X';
        char player2 = 'Y';
        Scanner in = new Scanner(System.in);
        Board gameBoard = new Board(3, 3);
        gameBoard.printBoard();

        // playing
        char turn = player1;
        boolean stop = false;
        String winner = "";
        gameBoard.printBoard();
        while (!stop) {
            boolean goodPlay = false;
            if (turn == player1) {
                if (gameBoard.spaceAvailable()) {
                    do {
//                        System.out.print("Please enter row col:");
//                        int row = in.nextInt();
//                        int col = in.nextInt();
//                        goodPlay = gameBoard.play(turn, row - 1, col - 1);
//                        goodPlay = gameBoard.autoPlay(turn);
                        goodPlay = gameBoard.randomAutoPlay(turn);
                    } while (!goodPlay);
                }
            } else {
//                goodPlay = gameBoard.autoPlay(turn);
                goodPlay = gameBoard.randomAutoPlay(turn);
            }
            if (goodPlay) {
                if (gameBoard.haveAWinner()) {
                    winner = "Player " + turn + " is our winner!!!";
                    stop = true;
                } else {
                    turn = (turn == player1) ? player2 : player1;
                }
            } else {
                winner = "We do not have a winner :-(";
                stop = true;
            }
            gameBoard.printBoard();
        }
        System.out.println(winner);
        gameBoard.printBoard(winner);

        System.out.println("\n*** Plays Made ***");
        plays.forEach(data -> System.out.println(data));
    }
}