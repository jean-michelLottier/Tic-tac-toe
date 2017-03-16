/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jean-michel
 */
public class TicTacToe {

    private final char[][] board;
    private final Map<String, List<String[]>> winningMoves;
    private final String player1;
    private final String player2;
    private static final char ELEMENT_X = 'X';
    private static final char ELEMENT_O = 'O';

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-2]");

    public TicTacToe() {
        this.board = new char[][]{
            new char[]{'.', '.', '.'},
            new char[]{'.', '.', '.'},
            new char[]{'.', '.', '.'}};

        this.winningMoves = new HashMap<>();
        List<String[]> values = new ArrayList<>();
        values.add(new String[]{"0,1", "0,2"});
        values.add(new String[]{"1,0", "2,0"});
        values.add(new String[]{"1,1", "2,2"});
        this.winningMoves.put("0,0", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"0,0", "0,2"});
        values.add(new String[]{"1,1", "2,1"});
        this.winningMoves.put("0,1", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"0,0", "0,1"});
        values.add(new String[]{"1,2", "2,2"});
        values.add(new String[]{"2,0", "1,1"});
        this.winningMoves.put("0,2", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"1,1", "1,2"});
        values.add(new String[]{"0,0", "2,0"});
        this.winningMoves.put("1,0", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"2,1", "2,2"});
        values.add(new String[]{"0,0", "1,0"});
        values.add(new String[]{"1,1", "0,2"});
        this.winningMoves.put("2,0", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"1,0", "1,2"});
        values.add(new String[]{"0,1", "2,1"});
        values.add(new String[]{"0,0", "2,2"});
        values.add(new String[]{"2,0", "0,2"});
        this.winningMoves.put("1,1", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"2,0", "2,1"});
        values.add(new String[]{"0,2", "1,2"});
        values.add(new String[]{"0,0", "1,1"});
        this.winningMoves.put("2,2", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"2,0", "2,2"});
        values.add(new String[]{"0,1", "1,1"});
        this.winningMoves.put("2,1", new ArrayList<>(values));
        values.clear();
        values.add(new String[]{"1,0", "1,1"});
        values.add(new String[]{"0,2", "2,2"});
        this.winningMoves.put("1,2", new ArrayList<>(values));

        this.player1 = "Player 1";
        this.player2 = "Player 2";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.startGame();

        System.out.println("Bye!");
    }

    public void startGame() {
        System.out.println("- Starting Tic-Tac-Toe game -");
        this.showBoard();
        byte c = 0;
        int[] coordinates;
        do {
            if (c == 0) {
                coordinates = this.setMove(player1);
            } else {
                coordinates = this.setMove(player2);
            }

            this.showBoard();
            if (this.isWinningMove(coordinates)) {
                if (c == 0) {
                    System.out.println(this.player1 + " won the game!!");
                } else {
                    System.out.println(this.player2 + " won the game!!");
                }
                break;
            }

            c++;
            if (c > 1) {
                c = 0;
            }
        } while (!this.isEndGame());

        System.out.println("- End game -");
    }

    private boolean isWinningMove(int[] coordinates) {
        String sCoord = coordinates[0] + "," + coordinates[1];
        System.out.println("coordinates: " + sCoord);
        for (String[] moves : this.winningMoves.get(sCoord)) {
            System.out.println("move: " + Arrays.toString(moves));
            int c = 1;
            for (String val : moves) {
                String[] coord = val.split(",");
                if (this.board[coordinates[0]][coordinates[1]] == this.board[Integer.valueOf(coord[0])][Integer.valueOf(coord[1])]) {
                    c++;
                }
            }

            if (c == 3) {
                return true;
            }
        }

        return false;
    }

    private boolean isEndGame() {
        for (char[] line : this.board) {
            for (char c : line) {
                if (c == '.') {
                    return false;
                }
            }
        }

        return true;
    }

    private int[] setMove(String player) {
        Scanner scanner = new Scanner(System.in);
        String move = null;
        do {
            System.out.println(player + ":");
            move = scanner.next();
        } while (!this.isValidMove(move));

        String[] coordinates = move.split(",");
        if (this.player1.equalsIgnoreCase(player)) {
            this.board[Integer.valueOf(coordinates[0])][Integer.valueOf(coordinates[1])] = TicTacToe.ELEMENT_X;
        } else {
            this.board[Integer.valueOf(coordinates[0])][Integer.valueOf(coordinates[1])] = TicTacToe.ELEMENT_O;
        }

        return new int[]{Integer.valueOf(coordinates[0]), Integer.valueOf(coordinates[1])};
    }

    private boolean isValidMove(String move) {
        if (move == null || move.isEmpty()) {
            return false;
        }

        String[] coordinates = move.split(",");
        if (coordinates.length != 2) {
            System.out.println("Input must be passed in the form of coordinates separated by a comma. "
                    + "For example: \"1,1\"");
            return false;
        }

        Matcher matcher0 = NUMBER_PATTERN.matcher(coordinates[0]);
        Matcher matcher1 = NUMBER_PATTERN.matcher(coordinates[1]);
        if (!matcher0.matches() || !matcher1.matches()) {
            System.out.println("Coordinates must be values between 0 to 2.");
            return false;
        }

        char target = this.board[Integer.valueOf(coordinates[0])][Integer.valueOf(coordinates[1])];
        if (target != '.') {
            System.out.println("Coordinates already used. Select another space");
            return false;
        }

        return true;
    }

    private void showBoard() {
        for (char[] board1 : this.board) {
            System.out.println(board1);
        }
    }
}
