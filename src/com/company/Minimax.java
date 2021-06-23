package com.company;

public class Minimax {

    public static int minimax(int[] stateArray, int depth, boolean isMaximizing, int turn, int startingTurn) {
        int result = Game.findTheWinner(stateArray);
        if (result != 0) {
            if (result == 1 && startingTurn == 1 || result == 2 && startingTurn == 2) {
                return 10;
            }
            if (result == 1 && startingTurn == 2 || result == 2 && startingTurn == 1) {
                return -10;
            }
            if (result == -1) {
                return 0;
            }
        }

        if (isMaximizing) {
            int score = 0;
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                // Is the spot available?
                if (stateArray[i] == 0) {
                    stateArray[i] = turn;
                    if (turn == 1) {
                        score = minimax(stateArray, depth + 1, false, 2, startingTurn);
                    }
                    if (turn == 2) {
                        score = minimax(stateArray, depth + 1, false, 1, startingTurn);
                    }
                    stateArray[i] = 0;
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int score = 0;
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                // Is the spot available?
                if (stateArray[i] == 0) {
                    stateArray[i] = turn;
                    if (turn == 1) {
                        score = minimax(stateArray, depth + 1, true, 2, startingTurn);
                    }
                    if (turn == 2) {
                        score = minimax(stateArray, depth + 1, true, 1, startingTurn);
                    }
                    stateArray[i] = 0;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

}
