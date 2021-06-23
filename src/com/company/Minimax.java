package com.company;

import java.util.Optional;

public class Minimax {

    public static int minimax(char[] stateArray, int depth, boolean isMaximizing, char turn, char startingTurn) {
        Optional<GameResultType> result = Utils.findTheWinner(stateArray);
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
                if (stateArray[i] == ' ') {
                    stateArray[i] = turn;
                    if (turn == 'X') {
                        score = minimax(stateArray, depth + 1, false, 'O', startingTurn);
                    }
                    if (turn == 'O') {
                        score = minimax(stateArray, depth + 1, false, 'X', startingTurn);
                    }
                    stateArray[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int score = 0;
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                // Is the spot available?
                if (stateArray[i] == ' ') {
                    stateArray[i] = turn;
                    if (turn == 'X') {
                        score = minimax(stateArray, depth + 1, true, 'O', startingTurn);
                    }
                    if (turn == 'O') {
                        score = minimax(stateArray, depth + 1, true, 'X', startingTurn);
                    }
                    stateArray[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }
}
