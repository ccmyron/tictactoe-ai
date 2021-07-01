package com.company;

import java.util.Optional;
import java.util.function.IntBinaryOperator;

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
            return step(board, depth, isMaximizing, turn, startingTurn, Math::max, Integer.MIN_VALUE);
        } else {
            return step(board, depth, isMaximizing, turn, startingTurn, Math::min, Integer.MAX_VALUE);
        }
    }

    private static int step(
            char[] board,
            int depth,
            boolean isMaximizing,
            char turn,
            char startingTurn,
            IntBinaryOperator operator,
            int initialBestScore) {

        int score = 0;
        int bestScore = initialBestScore;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = turn;

                score = minimax(board, depth + 1, !isMaximizing, switchTurn(turn), startingTurn);
                bestScore = operator.applyAsInt(score, bestScore);

                board[i] = ' ';
            }
        }

        return bestScore;
    }

    private static char switchTurn(char turn) {
        return (turn == 'X') ? 'O' : 'X';
    }
}
