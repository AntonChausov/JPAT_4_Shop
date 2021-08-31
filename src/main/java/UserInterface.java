import java.util.List;

public interface UserInterface {
    String getString(String outputLine);
    void printString(String outputLine);
    int showMenu(List<String> actions);
}
