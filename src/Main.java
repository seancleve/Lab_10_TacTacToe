import java.util.Scanner;

public class Main {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean playAgain;

        do {
            clearBoard();
            String currentPlayer = PLAYER_X;
            int moveCount = 0;

            while (true) {
                display();
                int row = SafeInput.getRangedInt(console, "Enter row (1-3): ", 1, 3) - 1;
                int col = SafeInput.getRangedInt(console, "Enter col (1-3): ", 1, 3) - 1;

                if (!isValidMove(row, col)) {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }

                board[row][col] = currentPlayer;
                moveCount++;

                if (moveCount >= 5) { // Minimum moves for a win or tie
                    if (isWin(currentPlayer)) {
                        display();
                        System.out.println(currentPlayer + " wins!");
                        break;
                    } else if (isTie()) {
                        display();
                        System.out.println("It's a tie!");
                        break;
                    }
                }

                currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
            }

            playAgain = SafeInput.getYNConfirm(console, "Do you want to play again? (Y/N): ");
        } while (playAgain);

        console.close();
    }

    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = " ";
            }
        }
    }

    private static void display() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + (col < COLS - 1 ? " | " : ""));
            }
            System.out.println();
            if (row < ROWS - 1) System.out.println("---------");
        }
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private static boolean isTie() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].equals(" ")) return false;
            }
        }
        return !isWin(PLAYER_X) && !isWin(PLAYER_O);
    }
}

// SafeInput class for handling input
class SafeInput {
    public static int getRangedInt(Scanner scanner, String prompt, int min, int max) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear invalid input
                System.out.print(prompt);
            }
            input = scanner.nextInt();
            if (input < min || input > max) {
                System.out.println("Input out of range. Enter a number between " + min + " and " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    public static boolean getYNConfirm(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.next().trim().toLowerCase();
            if (!input.equals("y") && !input.equals("n")) {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!input.equals("y") && !input.equals("n"));
        return input.equals("y");
    }
}
