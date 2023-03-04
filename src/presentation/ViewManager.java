package presentation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewManager {
    private Scanner scanner;

    public ViewManager() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println();
        System.out.println("    1) Character creation");
        System.out.println("    2) List characters");
        System.out.println("    3) Create an adventure");
        System.out.println("    4) Start an adventure");
        System.out.println("    5) Exit");
        System.out.println();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That's not a valid integer, try again!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public double askForDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("That's not a valid double, try again!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public void showTabulatedList(String[] messages) {
        for (String message : messages) {
            showTabulatedMessage(message);
        }
    }

    public void showTabulatedMessage(String message) {
        System.out.println("\t" + message);
    }

    public void spacing() {
        System.out.println();
    }

    // This should probably be decoupled. This approach was taken to keep the exercise simple.
    public LocalDate askForDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("That's not a valid date, try again!");
            }
        }
    }

    public void DAOMenu() {
        System.out.println();
        System.out.println("Do you want to use your local or cloud data?");
        System.out.println("    1) Local data");
        System.out.println("    2) Cloud data");
        System.out.println();
    }
}
