import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Board {
    private final int MAX_ROWS;
    private final int MAX_COLS;
    private final Pieces[][] gameBoard;

    public Board(int max_rows, int max_cols) {
        this.MAX_ROWS = max_rows;
        this.MAX_COLS = max_cols;

        this.gameBoard = new Pieces[this.MAX_ROWS][this.MAX_COLS];
    }
    public int getMaxRows() {
        return this.MAX_ROWS;
    }

    public int getMaxCols() {
        return this.MAX_COLS;
    }

    public void printBoard(){
        printBoard("");
    }

    public void printBoard(String message){
        System.out.println("\n\n\n\n");
        String colHeader = "T | 1 | 2 | 3 ";
        System.out.println(colHeader);
        System.out.println("-".repeat(colHeader.length()));
        for (int row = 0; row < this.MAX_ROWS; row++) {
            StringBuilder printStr = new StringBuilder();
            for (int col = 0; col < this.MAX_COLS; col++) {
                char playerName;
                if (this.gameBoard[row][col] == null) {
                    playerName = ' ';
                } else {
                    playerName = this.gameBoard[row][col].getName();
                }
                printStr.append("| ").append(playerName).append(" ");
            }
            System.out.println((row + 1) + " " + printStr.substring(1));
            if (row < (this.MAX_ROWS - 1)) {
                System.out.println("-".repeat(printStr.length() + 1));
            }
        }
        System.out.println("\n" + message);
    }
    private boolean isSpaceFree(int row, int col){
        if (row >= this.MAX_ROWS || row >= this.MAX_COLS) {
            return false;
        }
        return this.gameBoard[row][col] == null;
    }
    public boolean play(char player, int row, int col){
        if (this.isSpaceFree(row, col)) {
            this.gameBoard[row][col] = new Pieces(player, row, col);
            Main.plays.add(this.gameBoard[row][col]);
            return true;
        }
        return false;
    }

    private boolean isSpaceAvailable(){
        boolean isAvailable = false;
        check:
        for (int row = 0; row < this.MAX_ROWS; row++) {
            for (int col = 0; col < this.MAX_COLS; col++) {
                if (this.isSpaceFree(row, col)) {
                    isAvailable = true;
                    break check;
                }
            }
        }
        return isAvailable;
    }

    public boolean autoPlay(char player) {
        boolean played = false;
        check:
        for (int row = 0; row < this.MAX_ROWS; row++) {
            for (int col = 0; col < this.MAX_COLS; col++) {
                if (play(player, row, col)) {
                    played = true;
                    break check;
                }
            }
        }
        return played;
    }

    public boolean randomAutoPlay(char player) {
        boolean played;
        if (!this.isSpaceAvailable()) return false;
        Random random = new Random();
        int row;
        int col;
        do {
            row = random.nextInt(this.MAX_ROWS);
            col = random.nextInt(this.MAX_COLS);
            played = play(player, row, col);
        } while (!played);
        return true;
    }

    public boolean haveAWinner() {
        boolean winner = false;
        for (int row = 0; row < this.MAX_ROWS; row++) {
            if (Arrays.stream(this.gameBoard[row]).noneMatch(Objects::isNull)) {
                winner = Arrays.stream(this.gameBoard[row]).map(Player::getName).distinct().count() <= 1;
            }
        }

        if (!winner) {
            for (int col = 0; col < this.MAX_COLS; col++) {
                Pieces[] column = new Pieces[this.MAX_ROWS];
                for (int row = 0; row < this.MAX_ROWS; row++) {
                    column[row] = this.gameBoard[row][col];
                }
                if (Arrays.stream(column).noneMatch(Objects::isNull)) {
                    winner = Arrays.stream(column).map(Player::getName).distinct().count() <= 1;
                    if (winner) {
                        break;
                    }
                }
            }
        }

        if (!winner) {
            Pieces[] diagonals = new Pieces[this.MAX_ROWS];
            for (int diag = 0; diag < this.MAX_ROWS; diag++) {
                diagonals[diag] = this.gameBoard[diag][diag];
            }
            if (Arrays.stream(diagonals).noneMatch(Objects::isNull)) {
                winner = Arrays.stream(diagonals).map(Player::getName).distinct().count() <= 1;
            }
        }
        return winner;
    }
    public boolean spaceAvailable(){
        return Arrays.stream(this.gameBoard).flatMap(Arrays::stream).anyMatch(Objects::isNull);
    }
}
