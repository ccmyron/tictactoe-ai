package com.company;

//import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final char[] board = new char[9];
    private char currentTurn;
    private int moves;


    /**
     * Game constructor
     *
     * Initializes the game: sets the board to be empty;
     * Sets the currentTurn to be 'X', as it's the first to pla;
     * Sets the moves to 0.
     */

    public Game() {
        Arrays.fill(this.board, ' ');
        this.currentTurn = 'X';
        this.moves = 0;
    }


    /**
     * Outputs the board
     */

    public void printBoard() {

        System.out.print ("---------\n");
        for (int i = 0; i < 3; ++i) {
            System.out.print("| ");
            for (int j = 0; j < 3; ++j) {
                System.out.print(this.board[i * 3 + j] + " ");
            }
            System.out.println("|");
        }
        System.out.print ("---------\n");
    }


    /**
     * Outputs the winner
     *
     * @param result enum value of game result
     * @see GameResultType
     */

    public void outputWinner(GameResultType result) {
        switch (result) {
            case DRAW -> System.out.println("Draw!");
            case X_WON -> System.out.println("X wins");
            case O_WON -> System.out.println("O wins");
        }
    }


    /**
     * Switches the player's turn
     */

    public void switchTurn() {
        if (this.currentTurn == 'X') {
            this.currentTurn = 'O';
        } else {
            this.currentTurn = 'X';
        }
    }


    /**
     * Makes a move depending on the type of the player.
     *
     * @param playerType enum for declaring type of player.
     * @see PlayerType
     * @see Runnable
     */

    private Runnable getPlayerMove(PlayerType playerType) {
        switch (playerType) {
            case USER -> {
                return this::userMove;
            }
            case BOT_EASY -> {
                return this::easyMove;
            }
            case BOT_MEDIUM -> {
                return this::mediumMove;
            }
            case BOT_HARD -> {
                return this::hardMove;
            }
        }

        return null;
    }


    /**
     * Plays the game from start to finish
     *
     * Basic Tic-Tac-Toe flow, 9 moves, 'X' starts, the player is switched on each move.
     * From the 5th move it is checked for a winner.
     *
     * @param command GameCommand instance which defines the game rules
     * @see GameRule
     * @see Optional
     */

    public void play(GameRule command) {
        var firstPlayerAction = getPlayerMove(command.getFirstPlayer());
        var secondPlayerAction = getPlayerMove(command.getSecondPlayer());

        Optional<GameResultType> winner = Optional.empty();

        while (this.moves < 9) {
            printBoard();

            if (this.moves % 2 == 0) {
                firstPlayerAction.run();
            } else {
                secondPlayerAction.run();
            }
            switchTurn();

            if (moves >= 5) {
                winner = Utils.findTheWinner(this.board);
            }

            if (winner.isPresent()) {
                printBoard();
                break;
            }

            this.moves++;
        }

        outputWinner(winner.get());
    }


    /**
     * Makes a move dictated by the user.
     *
     * Reads the input in form of 2 digits, both from 1 to 3,
     * validates it and saves the move.
     *
     * @Examples: "1 3", "2 2"
     */

    private void userMove() {
        Scanner sc = new Scanner(System.in);
        boolean isInputCorrect = false;
        int x = 0;
        int y = 0;

        while (!isInputCorrect) {
            System.out.print("Enter the coordinates: ");

            // Validate the input
            String coordinates = sc.nextLine();
            if (coordinates.length() != 3
                    || coordinates.charAt(1) != ' '
                    || !Character.isDigit(coordinates.charAt(0))
                    || !Character.isDigit(coordinates.charAt(2))
            ) {
                System.out.println("You should enter numbers!");
                continue;
            }

            x = coordinates.charAt(0) - '0';
            y = coordinates.charAt(2) - '0';


            // Check if the coordinates are from 1 to 3
            if (x < 1 || x >= 4 || y < 1 || y >= 4) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            // Check if the cell is free
            if (this.board[(x - 1) * 3 + y - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            isInputCorrect = true;
        }

        this.board[(x - 1) * 3 + y - 1] = this.currentTurn;
    }


    /**
     * Makes a random move.
     *
     * Generates a random position until the position is free
     * in the game grid and then saves the move.
     */

    private void easyMove() {
        Random randomizer = new Random();
        int position = 0;

        while (true) {
            position = randomizer.nextInt(9);

            if (this.board[position] == ' ') {
                break;
            }
        }

        System.out.println("Making move level \"easy\"");
        this.board[position] = this.currentTurn;
    }


    /**
     * Makes a move of medium difficulty.
     *
     * Medium means that if there's a move that can complete the game,
     * the AI will make it, otherwise the move will be random.
     */

    private void mediumMove() {

        Optional<Integer> bestPosition = Utils.findTheWinnerMove(this.board);

        if (bestPosition.isPresent()) {
            System.out.println("Making move level \"medium\"");
            this.board[bestPosition.get()] = this.currentTurn;
        } else {
            Random randomizer = new Random();
            int position = 0;

            while (true) {
                position = randomizer.nextInt(9);

                if (this.board[position] == ' ') {
                    break;
                }
            }

            System.out.println("Making move level \"medium\"");
            this.board[position] = this.currentTurn;
        }
    }


    /**
     * Makes a move of hard difficulty.
     *
     * Uses the minimax algorithm to find out the
     * best possible move for each stage.
     */

    private void hardMove() {
        int bestScore = Integer.MIN_VALUE;
        int position = 0;
        char startingTurn = this.currentTurn;
        for (int i = 0; i < 9; i++) {
            int score;
            char turn = startingTurn;
            /* Is the cell available? */
            if (this.board[i] == ' ') {
                /* Fill the cell */
                this.board[i] = this.currentTurn;

                /* Switch the player to find the minimized score */
                if (turn == 'X') {
                    turn = 'O';
                } else {
                    turn = 'X';
                }

                /* Find the score for this move */
                score = Minimax.minimax(this.board, 0, false, turn, startingTurn);

                /* Empty the cell */
                this.board[i] = ' ';

                /* Save the position with the best score */
                if (score > bestScore) {
                    bestScore = score;
                    position = i;
                }
            }
        }

        System.out.println("Making move level \"hard\"");
        this.board[position] = startingTurn;
    }
}
