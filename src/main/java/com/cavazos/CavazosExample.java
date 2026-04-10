package com.cavazos;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class CavazosExample {

    public static void main(String[] args) {
        // Load the list of commands from the JSON file
        String[] commands = JSONFile.readCommandsFromResource("/commands.json");

        if (commands == null) {
            System.out.println("Error loading commands.json");
            return;
        }

        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        
        // Using stacks to handle the undo/redo history
        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();

        char choice = ' ';

        while (choice != 'q') {
            printMenu();
            System.out.print("Enter a command: ");

            String line = input.nextLine().trim().toLowerCase();
            if (line.isEmpty()) continue;

            choice = line.charAt(0);
            System.out.println();

            switch (choice) {
                case 'i':
                    // Pick a random command and add to undo stack
                    String cmd = commands[rand.nextInt(commands.length)];
                    System.out.println("[COMMAND ISSUED]: General Cavazos orders the troops to do: " + cmd);
                    undoStack.push(cmd);
                    redoStack.clear(); // Clear redo whenever a new command is issued
                    break;

                case 'l':
                    // Print the full list of available commands
                    System.out.println("Number  Command");
                    System.out.println("------  --------------------");
                    for (int i = 0; i < commands.length; i++) {
                        System.out.printf("%02d      %s%n", (i + 1), commands[i]);
                    }
                    break;

                case 'u':
                    // Move the last command from undo to redo
                    if (undoStack.isEmpty()) {
                        System.out.println("ERROR: There are no commands to undo. Please issue or redo a command");
                    } else {
                        String last = undoStack.pop();
                        redoStack.push(last);
                        System.out.println("[UNDO COMMAND ISSUED]: General Cavazos orders the troops to undo: " + last);
                    }
                    break;

                case 'r':
                    // Move the last undone command back to undo stack
                    if (redoStack.isEmpty()) {
                        System.out.println("ERROR: There are no commands to redo.");
                    } else {
                        String redo = redoStack.pop();
                        undoStack.push(redo);
                        System.out.println("[REDO COMMAND ISSUED]: General Cavazos orders the troops to redo: " + redo);
                    }
                    break;

                case 'q':
                    System.out.println("Thank You General Cavazos");
                    break;
            }
        }
        input.close();
    }

    public static void printMenu() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("General Cavazos Commander App");
        System.out.println("----------------------------------------------------------------");
        System.out.println("i    Issue a command");
        System.out.println("l    List all of the commands");
        System.out.println("u    Undo the last command that was issued");
        System.out.println("r    Redo the last command that was issued");
        System.out.println("q    Quit");
        System.out.println("----------------------------------------------------------------");
    }
}