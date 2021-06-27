package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws GameStopException {
        while (true) {
            try {
                var command = Utils.getMenuCommand();
                new Game().play(command);
            } catch (GameStopException ex) {
                break;
            }
        }
    }
}