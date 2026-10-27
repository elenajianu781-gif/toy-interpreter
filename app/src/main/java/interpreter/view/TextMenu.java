package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.List;
import java.util.Scanner;

public class TextMenu {

    private final List<IStmt> programs;
    private int selectedProgram = -1;

    public TextMenu(List<IStmt> programs) {
        this.programs = programs;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== TOY INTERPRETER ===");
            System.out.println("1. Select a program");
            System.out.println("2. Run selected program");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> selectProgram(scanner);
                case "2" -> runProgram();
                case "0" -> {
                    System.out.println("Exited");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void selectProgram(Scanner scanner) {
        System.out.println("\nAvailable programs:");

        for (int i = 0; i < programs.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + programs.get(i)
            );
        }

        System.out.print("Select program: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice < 1 || choice > programs.size()) {
                System.out.println("Invalid program number.");
                return;
            }

            selectedProgram = choice - 1;
            System.out.println("Program selected: " + choice);

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void runProgram() {
        if (selectedProgram == -1) {
            System.out.println("Please select a program first.");
            return;
        }

        try {
            IStmt program = programs.get(selectedProgram);

            PrgState state = new PrgState(
                    new MyStack<>(),
                    new MyDictionary<>(),
                    new MyList<>(),
                    program
            );

            Repository repository = new Repository(state);
            Controller controller = new Controller(repository);

            controller.setDisplayFlag(true);
            controller.allStep();

            System.out.println("\nFinal output: "
                    + state.getOut());

        } catch (MyException e) {
            System.out.println("Execution error: "
                    + e.getMessage());
        }
    }
}
