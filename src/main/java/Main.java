import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static User currentUser;

    public static void main(String[] args) {

        String pathToUserData = "UserData.ini";
        UserInterface interfase = new ConsoleInterface();

        if (!createFile(pathToUserData)) {
            return;
        }

        interfase.printString("Hello!");
        interfase.printString("Choose action: ");
        List<String> actions = new ArrayList<>();
        actions.add("1. LogIn");
        actions.add("2. Registration");
        int action = interfase.showMenu(actions);
        actions.clear();
        if (action == actions.size() - 1) {
            if (!LogIn(pathToUserData, interfase)) {
                interfase.printString("Incorrect login or password. Bye!");
                return;
            }
            else {

            }
        }
    }

    private static boolean LogIn(String pathToUserData, UserInterface interfase) {
        List<User> users = getUsers(pathToUserData);

        String userLogin = interfase.getString("Insert your login: ");
        String userPassword = interfase.getString("Insert your password: ");
        User LogInUser = new User(userLogin, userPassword);
        boolean find = false;
        for (User user : users) {
            if (user.equals(LogInUser)){
                find = true;
                currentUser = user;
                break;
            }
        }
        return find;
    }

    private static List<User> getUsers(String pathToUserData) {
        List<User> users = new ArrayList<>();
        User user = null;
        try (FileInputStream fis = new FileInputStream(pathToUserData);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            user = (User) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        users.add(user);
        return users;
    }

    private static boolean createFile(String destination) {
        //Файл в корне проекта
        File userData = new File(destination);
        boolean fileExist = userData.exists();
        if (!fileExist){
            try {
                fileExist = userData.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileExist;
    }
}
