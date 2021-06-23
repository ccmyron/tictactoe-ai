package com.company;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Game {

    char[] stateArray = new char[9];

    public void printBoard(char[] stateArray) {

        System.out.print ("---------\n");
        for (int i = 0; i < 3; ++i){
            System.out.print("| ");
            for (int j = 0; j < 3; ++j){
                System.out.print(stateArray[i * 3 + j] + " ");
            }
            System.out.println("|");
        }
        System.out.print ("---------\n");
    }

    public static Optional<GameResultType> findTheWinner(char[] stateArray) {
        int[][] winConditionArray = {
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
            if (stateArray[winConditionArray[i][0]] == stateArray[winConditionArray[i][1]] &&
                    stateArray[winConditionArray[i][1]] == stateArray[winConditionArray[i][2]] &&
                    stateArray[winConditionArray[i][0]] != ' ') {
                if (stateArray[winConditionArray[i][0]] == 'X') {
                    return Optional.of(GameResultType.X_WON);
                } else if (stateArray[winConditionArray[i][0]] == 'O') {
                    return Optional.of(GameResultType.O_WON);
                }
            }
        }

        boolean gameTied = true;
        for (int i = 0; i < 9; ++i) {
            if (stateArray[i] == 0) {
                gameTied = false;
                break;
            }
        }

        if (gameTied) {
            return Optional.of(GameResultType.DRAW);
        }
        return Optional.empty();
    }

    public static void outputWinner (int winner) {
        switch (winner) {
            case -1:
                System.out.println("Draw!");
                break;
            case 1:
                System.out.println("X wins");
                break;
            case 2:
                System.out.println("O wins");
                break;
        }
    }

    public static void userMove(char[] stateArray, char turn) {
        Scanner sc = new Scanner(System.in);
        boolean isInputCorrect = false;
        int x = 0;
        int y = 0;
        String coordinates = "";

        while (!isInputCorrect) {
            System.out.print("Enter the coordinates: ");

            // validate the input
            coordinates = sc.nextLine();
            if (coordinates.length() != 3
                    || !Character.isDigit(coordinates.charAt(0))
                    || !Character.isDigit(coordinates.charAt(2)))
            {
                System.out.println("You should enter numbers!");
                continue;
            }

            x = coordinates.charAt(0) - '0';
            y = coordinates.charAt(2) - '0';


            // check if the coordinates are from 1 to 3
            if (x < 1 || x >= 4 || y < 1 || y >= 4) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            // check if the cell is occupied
            if (stateArray[(x - 1) * 3 + y - 1] != 0) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            isInputCorrect = true;
        }

        stateArray[(x - 1) * 3 + y - 1] = turn;
    }

    public static void randomMove(char[] stateArray, char turn, String mode) {
        Random randomizer = new Random();
        int position = 0;
        boolean isInputCorrect = false;

        while (!isInputCorrect) {
            position = randomizer.nextInt(9);

            if (stateArray[position] != 0) {
                continue;
            }

            isInputCorrect = true;
        }

        System.out.format("Making move level \"%s\"\n", mode);
        stateArray[position] = turn;
    }

    public static void hardMove(int[] stateArray, int startingTurn, String mode) {
        int bestScore = Integer.MIN_VALUE;
        int position = 0;
        for (int i = 0; i < 9; i++) {
            // Is the spot available?
            int score = 0;
            int turn = startingTurn;
            if (stateArray[i] == 0) {
                stateArray[i] = turn;
                if (turn == 1) {
                    turn = 2;
                } else {
                    turn = 1;
                }

                score = Minimax.minimax(stateArray, 0, false, turn, startingTurn);
                stateArray[i] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    position = i;
                }
            }
        }
        System.out.format("Making move level \"%s\"\n", mode);
        stateArray[position] = startingTurn;
    }

    /*-----------------------------------------------Game process-----------------------------------------------*/

    public static void playUserVSUser() {
        // create the state array for the game logic
        int[] stateArray = new int[9];
        int moves;
        Optional<GameResultType> winner;

        moves = 1;
        winner = Optional.empty();
        while (moves < 9) {
            printBoard(stateArray);
            if (moves % 2 == 1) {
                userMove(stateArray, 1);
            }
            if (moves % 2 == 0) {
                userMove(stateArray, 2);
            }
            if (moves >= 5) {
                winner = findTheWinner(stateArray);
            }
            if (Optional.isEmpty()) {
                printBoard(stateArray);
                break;
            }
            moves++;
        }

        outputWinner(winner);
    }

//    public static void playUserVSEasy(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                userMove(stateArray, 1);
//            }
//            if (moves % 2 == 0) {
//                randomMove(stateArray, 2, "easy");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playUserVSMedium(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                userMove(stateArray, 1);
//            }
//            if (moves < 4) {
//                randomMove(stateArray, 2, "medium");
//            } else {
//                hardMove(stateArray, 2, "medium");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playUserVSHard(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                userMove(stateArray, 1);
//            } else {
//                hardMove(stateArray, 2, "hard");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playEasyVSUser(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                randomMove(stateArray, 1, "easy");
//            }
//            if (moves % 2 == 0) {
//                userMove(stateArray, 2);
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playEasyVSEasy(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                randomMove(stateArray, 1, "easy");
//            }
//            if (moves % 2 == 0) {
//                randomMove(stateArray, 2, "easy");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playEasyVSMedium(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                randomMove(stateArray, 1, "easy");
//            }
//            if (moves % 2 == 0) {
//                if (moves < 4) {
//                    randomMove(stateArray, 2, "medium");
//                } else {
//                    hardMove(stateArray, 2, "medium");
//                }
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playEasyVSHard(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                randomMove(stateArray, 1, "easy");
//            } else {
//                hardMove(stateArray, 2, "hard");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playMediumVSUser(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                if (moves < 5) {
//                    randomMove(stateArray, 1, "medium");
//                } else {
//                    hardMove(stateArray, 1, "medium");
//                }
//            }
//            if (moves % 2 == 0) {
//                userMove(stateArray, 2);
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playMediumVSEasy(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                if (moves < 5) {
//                    randomMove(stateArray, 1, "medium");
//                } else {
//                    hardMove(stateArray, 1, "medium");
//                }
//            }
//            if (moves % 2 == 0) {
//                randomMove(stateArray, 2, "easy");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playMediumVSMedium(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                if (moves < 5) {
//                    randomMove(stateArray, 1, "medium");
//                } else {
//                    hardMove(stateArray, 1, "medium");
//                }
//            }
//            if (moves % 2 == 0) {
//                if (moves < 4) {
//                    randomMove(stateArray, 2, "medium");
//                } else {
//                    hardMove(stateArray, 2, "medium");
//                }
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playMediumVSHard(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                if (moves < 5) {
//                    randomMove(stateArray, 1, "medium");
//                } else {
//                    hardMove(stateArray, 1, "medium");
//                }
//            }
//            if (moves % 2 == 0) {
//                hardMove(stateArray, 2, "hard");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playHardVSUser(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                hardMove(stateArray, 1, "hard");
//            }
//            if (moves % 2 == 0) {
//                userMove(stateArray, 2);
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playHardVSEasy(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                hardMove(stateArray, 1, "hard");
//            }
//            if (moves % 2 == 0) {
//                randomMove(stateArray, 2, "easy");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playHardVSMedium(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                hardMove(stateArray, 1, "hard");
//            }
//            if (moves % 2 == 0) {
//                if (moves < 4) {
//                    randomMove(stateArray, 2, "medium");
//                } else {
//                    hardMove(stateArray, 2, "medium");
//                }
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
//
//    public static void playHardVSHard(){
//        // create the state array for the game logic
//        int[] stateArray = new int[9];
//        int moves;
//        int winner;
//
//        moves = 1;
//        winner = 0;
//        while (moves < 10) {
//            printBoard(stateArray);
//            if (moves % 2 == 1) {
//                hardMove(stateArray, 1, "hard");
//            }
//            if (moves % 2 == 0) {
//                hardMove(stateArray, 2, "hard");
//            }
//            if (moves >= 5) {
//                winner = findTheWinner(stateArray);
//            }
//            if (winner != 0) {
//                printBoard(stateArray);
//                break;
//            }
//            moves++;
//        }
//
//        outputWinner(winner);
//    }
}
