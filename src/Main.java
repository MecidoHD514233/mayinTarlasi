import java.util.Random;
import java.util.Scanner;

class MineSweeper {
    private final int rows;
    private final int cols;
    private final int mines;
    private final int[][] board;
    private final boolean[][] revealed;
    private boolean gameover;

    public MineSweeper(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.board = new int[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.gameover = false;
        placeMines();
        calculateNumbers();
    }

    private void placeMines() {
        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < mines) {
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);
            if (board[row][col] != -1) {
                board[row][col] = -1;
                placedMines++;
            }
        }
    }

    private void calculateNumbers() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == -1) {
                    continue;
                }

                int mineCount = 0;
                for (int i = 0; i < 8; i++) {
                    int newRow = row + dx[i];
                    int newCol = col + dy[i];
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && board[newRow][newCol] == -1) {
                        mineCount++;
                    }
                }
                board[row][col] = mineCount;
            }
        }
    }

    public void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col]) {
            return;
        }
        revealed[row][col] = true;

        if (board[row][col] == -1) {
            gameover = true;
        } else if (board[row][col] == 0) {
            int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int i = 0; i < 8; i++) {
                revealCell(row + dx[i], col + dy[i]);
            }
        }
    }

    public boolean isGameOver() {
        return gameover;
    }

    public void printBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!revealed[row][col]) {
                    System.out.print("■ ");
                } else if (board[row][col] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(board[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public void printFullBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(board[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MineSweeper game = new MineSweeper(5, 5, 5);

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.print("Enter row and column to reveal: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            game.revealCell(row, col);
        }

        System.out.println("Game Over!");
        game.printFullBoard();
        scanner.close();
    }
}
