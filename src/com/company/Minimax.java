package com.company;

import java.util.Optional;

public class Minimax {

    /**
     * Returns the best score for the move that was made.
     *
     * Recursively makes all the possible moves and ranks their scores.
     *
     * @param board the game board
     * @param depth of the recursive tree made by this function's calls
     * @param isMaximizing boolean
     * @param turn player's turn
     * @param startingTurn the first player's turn that was given to this function
     *
     * @see "https://www.youtube.com/watch?v=trKjYdBASyQ"
     */

    public static int minimax(char[] board, int depth, boolean isMaximizing, char turn, char startingTurn) {
        Optional<GameResultType> result = Utils.findTheWinner(board);
        if (result.isPresent()) {
            if (result.get() == GameResultType.X_WON && startingTurn == 'X'
                    || result.get() == GameResultType.O_WON && startingTurn == 'O') {
                return 10;
            }
            if (result.get() == GameResultType.X_WON && startingTurn == 'O'
                    || result.get() == GameResultType.O_WON && startingTurn == 'X') {
                return -10;
            }
            if (result.get() == GameResultType.DRAW) {
                return 0;
            }
        }

        if (isMaximizing) {
            int score = 0;
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                // Is the spot available?
                if (board[i] == ' ') {
                    board[i] = turn;
                    if (turn == 'X') {
                        score = minimax(board, depth + 1, false, 'O', startingTurn);
                    }
                    if (turn == 'O') {
                        score = minimax(board, depth + 1, false, 'X', startingTurn);
                    }
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int score = 0;
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                // Is the spot available?
                if (board[i] == ' ') {
                    board[i] = turn;
                    if (turn == 'X') {
                        score = minimax(board, depth + 1, true, 'O', startingTurn);
                    }
                    if (turn == 'O') {
                        score = minimax(board, depth + 1, true, 'X', startingTurn);
                    }
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }
}
