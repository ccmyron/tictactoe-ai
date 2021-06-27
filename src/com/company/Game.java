package com.company;

//import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private char[] board = new char[9];
    char currentTurn;

    public Game () {
        Arrays.fill(board, ' ');
        currentTurn = 'X';
    }

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

    public void outputWinner (GameResultType result) {
        switch (result) {
            case DRAW -> System.out.println("Draw!");
            case X_WON -> System.out.println("X wins");
            case O_WON -> System.out.println("O wins");
        }
    }

    public void play(GameCommand command) {
        var firstPlayerAction = getActionForPlayer(command.getFirstPlayer());
        var secondPlayerAction = getActionForPlayer(command.getSecondPlayer());


        if (moves % 2 == 1) {
            firstPlayerAction.run();
        } else {
            secondPlayerAction.run();
        }
    }

    private Runnable getActionForPlayer(PlayerType playerType) {
        switch (playerType) {
            case USER -> {
                return this::userMove;
            }
            case BOT_EASY ->{
                return this::easyMove;
            }
            case BOT_MEDIUM ->{
                return this::mediumMove;
            }
            case BOT_HARD ->{
                return this::hardMove;
            }
        }
    }

    public void userMove(char turn) {
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
            if (this.board[(x - 1) * 3 + y - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            isInputCorrect = true;
        }

        this.board[(x - 1) * 3 + y - 1] = turn;
    }

    public void randomMove(char turn, String mode) {
        Random randomizer = new Random();
        int position = 0;
        boolean isInputCorrect = false;

        while (!isInputCorrect) {
            position = randomizer.nextInt(9);

            if (this.board[position] != 0) {
                continue;
            }

            isInputCorrect = true;
        }

        System.out.format("Making move level \"%s\"\n", mode);
        this.board[position] = turn;
    }

    public void hardMove(char startingTurn, String mode) {
        int bestScore = Integer.MIN_VALUE;
        int position = 0;
        for (int i = 0; i < 9; i++) {
            int score = 0;
            char turn = startingTurn;
            // Is the spot available?
            if (this.board[i] == ' ') {
                this.board[i] = turn;
                // Switch the player
                if (turn == 'X') {
                    turn = 'O';
                } else {
                    turn = 'X';
                }

                score = Minimax.minimax(this.board, 0, false, turn, startingTurn);
                this.board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    position = i;
                }
            }
        }
        System.out.format("Making move level \"%s\"\n", mode);
        this.board[position] = startingTurn;
    }

    /*-----------------------------------------------Game process-----------------------------------------------*/

    public void playUserVSUser() {
        int moves = 1;
        Optional<GameResultType> winner = Optional.empty();

        while (moves < 10) {
            printBoard();
            if (moves % 2 == 1) {
                userMove('X');
            }
            if (moves % 2 == 0) {
                userMove('O');
            }
            if (moves >= 5) {
                winner = Utils.findTheWinner(this.board);
            }
            if (winner.isPresent()) {
                printBoard();
                break;
            }
            moves++;
        }

        outputWinner(winner.get());
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
    public void playUserVSHard(){
        int moves = 1;
        Optional<GameResultType> winner = Optional.empty();
        while (moves < 10) {
            printBoard();
            if (moves % 2 == 1) {
                userMove('X');
            } else {
                hardMove('O', "hard");
            }
            if (moves >= 5) {
                winner = Utils.findTheWinner(this.board);
            }
            if (winner.isPresent()) {
                printBoard();
                break;
            }
            moves++;
        }

        outputWinner(winner.get());
    }
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
