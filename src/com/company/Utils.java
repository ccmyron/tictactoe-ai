package com.company;

import java.util.Optional;
import java.util.Scanner;

public class Utils {

    private static boolean isStringPresent(String[] arr, String s) {
        for (String element : arr) {
            if (element.equals(s)) {
                return true;
            }
        }

        return false;
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
            if (stateArray[i] == ' ') {
                gameTied = false;
                break;
            }
        }

        if (gameTied) {
            return Optional.of(GameResultType.DRAW);
        }
        return Optional.empty();
    }

    public static int getMenuCommand() {
        Scanner sc = new Scanner(System.in);
        String[] acceptedTokens = {"user", "easy", "medium", "hard"};

        while (true) {
            String command = sc.nextLine();
            String[] tokens = command.split(" ");

            switch (tokens[0]) {
                case "start":
                    if (tokens.length != 3
                            || !isStringPresent(acceptedTokens, tokens[1])
                            || !isStringPresent(acceptedTokens, tokens[2])) {
                        System.out.println("Bad parameters!");
                        continue;
                    } else {
                        if (tokens[1].equals("user")) {
                            if (tokens[2].equals("user")) {
                                return 1; // start user user
                            }
                            if (tokens[2].equals("easy")) {
                                return 2; // start user easy
                            }
                            if (tokens[2].equals("medium")) {
                                return 3; // start user medium
                            }
                            if (tokens[2].equals("hard")) {
                                return 4; // start user hard
                            }
                        }
                        if (tokens[1].equals("easy")) {
                            if (tokens[2].equals("user")) {
                                return 5; // start easy user
                            }
                            if (tokens[2].equals("easy")) {
                                return 6; // start easy easy
                            }
                            if (tokens[2].equals("medium")) {
                                return 7; // start easy medium
                            }
                            if (tokens[2].equals("hard")) {
                                return 8; // start easy hard
                            }
                        }
                        if (tokens[1].equals("medium")) {
                            if (tokens[2].equals("user")) {
                                return 9; // start medium user
                            }
                            if (tokens[2].equals("easy")) {
                                return 10; // start medium easy
                            }
                            if (tokens[2].equals("medium")) {
                                return 11; // start medium medium
                            }
                            if (tokens[2].equals("hard")) {
                                return 12; // start medium hard
                            }
                        }
                        if (tokens[1].equals("hard")) {
                            if (tokens[2].equals("user")) {
                                return 13; // start hard user
                            }
                            if (tokens[2].equals("easy")) {
                                return 14; // start hard easy
                            }
                            if (tokens[2].equals("medium")) {
                                return 15; // start hard medium
                            }
                            if (tokens[2].equals("hard")) {
                                return 16; // start hard hard
                            }
                        }
                    }

                case "exit":
                    return -1;

                default:
                    System.out.println("Bad parameters!");
                    break;
            }
        }
    }
}
