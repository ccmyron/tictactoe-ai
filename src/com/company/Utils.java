package com.company;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Utils {
    /**
     * Find the winner based on 3x3 rules
     * <p>
     * Goes through each possible winning combination.
     * Returns an empty optional if there isn't any combinations on the board.
     *
     * @param board the game board
     * @see Optional
     */

    public static Optional<GameResultType> findTheWinner(char[] board) {
        /* Game winning combinations */
        int[][] winArray = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        /* Go through all combinations */
        for (int i = 0; i < 8; i++) {
            if (board[winArray[i][0]] == board[winArray[i][1]] &&
                    board[winArray[i][1]] == board[winArray[i][2]] &&
                    board[winArray[i][0]] != ' ') {
                if (board[winArray[i][0]] == 'X') {
                    return Optional.of(GameResultType.X_WON);
                } else if (board[winArray[i][0]] == 'O') {
                    return Optional.of(GameResultType.O_WON);
                }
            }
        }

        /* Check if the game is tied */
        boolean gameTied = true;
        for (int i = 0; i < 9; ++i) {
            if (board[i] == ' ') {
                gameTied = false;
                break;
            }
        }
        if (gameTied) {
            return Optional.of(GameResultType.DRAW);
        }

        return Optional.empty();
    }


    /**
     * Find the 'potential' winner based on 3x3 rules
     * <p>
     * Goes through each possible winning combination, and checks if there's
     * one missing move that could complete the game.
     * Used for mediumMove in the Game class
     *
     * @param board the game board
     * @see Optional
     */

    public static Optional<Integer> findTheWinnerMove(char[] board) {
        int[][] winArray = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        for (int i = 0; i < 8; i++) {
            if (board[winArray[i][0]] == board[winArray[i][1]]
                    && board[winArray[i][0]] != ' '
                    && board[winArray[i][2]] == ' '
            ) {
                return Optional.of(winArray[i][2]);
            } else if (board[winArray[i][0]] == board[winArray[i][2]]
                    && board[winArray[i][0]] != ' '
                    && board[winArray[i][1]] == ' '
            ) {
                return Optional.of(winArray[i][1]);
            } else if (board[winArray[i][1]] == board[winArray[i][2]]
                    && board[winArray[i][1]] != ' '
                    && board[winArray[i][0]] == ' '
            ) {
                return Optional.of(winArray[i][0]);
            }
        }

        return Optional.empty();
    }


    /**
     * Return a GameRule instance with both players set, based on user input
     *
     * @see GameRule
     * @see GameStopException
     */

    public static GameRule getGameRule() throws GameStopException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String command = sc.nextLine();
            String[] tokens = command.split(" ");

            switch (tokens[0]) {
                case "start":
                    try {
                        PlayerType firstPlayer = PlayerType.from(tokens[1]);
                        PlayerType secondPlayer = PlayerType.from(tokens[2]);
                        return new GameRule(firstPlayer, secondPlayer);
                    } catch (RuntimeException ex) {
                        System.out.println("Bad parameters");
                    }
                    break;

                case "exit":
                    throw new GameStopException("Exiting...");

                default:
                    System.out.println("Bad parameters!");
                    break;
            }
        }
    }
}
