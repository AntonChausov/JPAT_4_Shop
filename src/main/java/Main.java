import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static User currentUser;
    private static List<User> users = new ArrayList<>(); //наверно, не круто хранить список пользователей, но мы будем.
    private static String userLogin;
    // answers - Magics. Можно вычислять по длине массива, но так читать проще, как-будто
    private static int answer1 = 1,
            answer2 = 2,
            answer3 = 3,
            answer4 = 4,
            answer5 = 5;
    //

    public static void main(String[] args) {

        String pathToUserData = "UserData.ini";
        String pathToItemsData = "ItemsData.ini";
        UserInterface interfase = new ConsoleInterface();

        if (!WorkWithFiles.createFiles(pathToUserData, pathToItemsData)) {
            return;
        }

        interfase.printString("Hello!");
        //Вход
        if (firstPage(pathToUserData, interfase)) return; // так подменила IDEA

        //основное меню
        //TODO: Прочитать товары в лист
        while (true) {
            String[] actions = {"1. Add money",
                    "2. Buy something",
                    "3. Check balance",
                    "4. My items",
                    "5. End"};
            int action = interfase.showMenu("You can: ", actions);
            if (action == answer1) {
                double addedMoney = Double.parseDouble(interfase.getString("How much?"));
                currentUser.addMoney(addedMoney);
            } else if (action == answer2) {
                //TODO: Показать товары не этого пользователя
                //TODO: Предложить выбор
                //TODO: если денег хватает - меняем владельца
                //TODO: команда назад
            } else if (action == answer3) {
                interfase.printString(currentUser.getMoneyString());
            } else if (action == answer4) {
                //TODO: Показать товары этого пользователя
            } else if (action == answer5) {
                break;
            }
        }
        //завершение
        boolean saved = WorkWithFiles.saveUserData(pathToUserData, users);
        if (!saved){
            interfase.printString("Something wrong!");
        }
    }

    private static boolean firstPage(String pathToUserData, UserInterface interfase) {
        String[] actions = {"1. LogIn",
                "2. Registration"};
        int action = interfase.showMenu("Choose action: ", actions);
        if (action == answer1) {
            if (!LogIn(pathToUserData, interfase)) {
                return true;
                //TODO: Добавить попытки можно, но пока не усложняем
            } else {
                interfase.printString("Hi " + currentUser.getName() + "! " + currentUser.getMoneyString());
            }
        } else {
            if (!Registration(pathToUserData, interfase)) {
                return true;
            }
        }
        return false;
    }

    private static boolean Registration(String pathToUserData, UserInterface interfase) {
        if (findUserByLogin(pathToUserData, interfase)) {
            interfase.printString("This login already taken.");
            return false;
            //TODO: Добавить попытки можно, но пока не усложняем
        }
        String userPassword = interfase.getString("Insert your password: ");
        String userName = interfase.getString("What is your name: ");
        currentUser = new User(userLogin, userPassword, userName);
        users.add(currentUser);
        return false;
    }

    private static boolean findUserByLogin(String pathToUserData, UserInterface interfase) {
        userLogin = interfase.getString("Insert your login: ");
        users = WorkWithFiles.getUsers(pathToUserData);
        boolean find = false;
        for (User user : users) {
            if (user.getLogin().equals(userLogin)) {
                currentUser = user;
                find = true;
                break;
            }
        }
        return find;
    }

    private static boolean LogIn(String pathToUserData, UserInterface interfase) {
        boolean find = findUserByLogin(pathToUserData, interfase);
        if (find){
            String userPassword = interfase.getString("Insert your password: ");
            if (!currentUser.getPassword().equals(userPassword)) {
                interfase.printString("Incorrect password");
                find = false;
            }
        } else {
            interfase.printString("No user with this login");
        }
        return find;
    }

}
