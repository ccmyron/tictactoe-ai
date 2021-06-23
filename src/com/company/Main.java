package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws GameStopException {
        while (true) {
            Game game = new Game();
            switch (Utils.getMenuCommand()) {
                case -1:
                    throw new GameStopException("Quitting the game...");
                case 1:
                    game.playUserVSUser();
                    break;
//                case 2:
//                    Game.playUserVSEasy();
//                    break;
//                case 3:
//                    Game.playUserVSMedium();
//                    break;
                case 4:
                    game.playUserVSHard();
                    break;
//                case 5:
//                    Game.playEasyVSUser();
//                    break;
//                case 6:
//                    Game.playEasyVSEasy();
//                    break;
//                case 7:
//                    Game.playEasyVSMedium();
//                    break;
//                case 8:
//                    Game.playEasyVSHard();
//                    break;
//                case 9:
//                    Game.playMediumVSUser();
//                    break;
//                case 10:
//                    Game.playMediumVSEasy();
//                    break;
//                case 11:
//                    Game.playMediumVSMedium();
//                    break;
//                case 12:
//                    Game.playMediumVSHard();
//                    break;
//                case 13:
//                    Game.playHardVSUser();
//                    break;
//                case 14:
//                    Game.playHardVSEasy();
//                    break;
//                case 15:
//                    Game.playHardVSMedium();
//                    break;
//                case 16:
//                    Game.playHardVSHard();
//                    break;
//            }
            }
        }
    }
}