package system;

import java.util.Scanner;

public class MainManager {

    private final static String EXIT_KEYWORD = "exit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            System.out.print("Type 'exit' to terminate the program: ");
            userInput = scanner.next();
        } while (!userInput.equals(EXIT_KEYWORD));
        System.out.println("User terminated the programm");
    }
}
