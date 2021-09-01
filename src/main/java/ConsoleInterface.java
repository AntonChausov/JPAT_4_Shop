import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    Scanner scanner;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getString(String outputLine) {
        System.out.print(outputLine);
        return scanner.nextLine();
    }

    @Override
    public void printString(String outputLine) {
        System.out.println(outputLine);
    }

    @Override
    public int showMenu(String title, String[] actions){
        int action = 0;
        String choose;
        printString(title);
        while (true) {
            for (String point : actions) {
                printString(point);
            }
            choose = scanner.nextLine();
            try {
                action = Integer.parseInt(choose);
            } catch (NumberFormatException e) {
                printString("Incorrect, try again");
            }
            if (action <= actions.length){
                break;
            } else {
                printString("There is no such option");
            }
        }
        return action;
    }
}
