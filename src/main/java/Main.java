import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static User currentUser;
    private static List<User> users = new ArrayList<>(); //наверно, не круто хранить список пользователей, но мы будем.
    private static List<Items> items = new ArrayList<>();
    private static String userLogin;
    // answers - Magics. Можно вычислять по длине массива, но так читать проще, как-будто
    private static int answer0 = 0,
            answer1 = 1,
            answer2 = 2,
            answer3 = 3,
            answer4 = 4,
            answer5 = 5,
            answer6 = 6;
    //

    public static void main(String[] args) {

        String pathToUserData = "UserData.ini";
        String pathToItemsData = "ItemsData.ini";
        UserInterface usedInterface = new ConsoleInterface();

        if (!WorkWithFiles.createFiles(pathToUserData, pathToItemsData)) {
            return;
        }

        usedInterface.printString("Hello!");
        //Вход
        if (firstPage(pathToUserData, usedInterface)) return; // так подменила IDEA

        //основное меню
        mainMenu(pathToItemsData, usedInterface);

        //завершение
        saveAllData(pathToUserData, pathToItemsData, usedInterface);
    }

    private static void saveAllData(String pathToUserData, String pathToItemsData, UserInterface usedInterface) {
        //DRY
        if (!WorkWithFiles.saveData(pathToUserData, users)){
            usedInterface.printString("Something wrong with saving users!");
        }
        //DRY
        if (!WorkWithFiles.saveData(pathToItemsData, items)){
            usedInterface.printString("Something wrong with saving items!");
        }
    }

    private static void mainMenu(String pathToItemsData, UserInterface usedInterface) {
        items = WorkWithFiles.getListFromFile(pathToItemsData);
        while (true) {
            String[] actions = {"1. Add money",
                    "2. Buy something",
                    "3. Check balance",
                    "4. My items",
                    "5. Add item",
                    "6. End"};
            int action = usedInterface.showMenu("You can: ", actions);
            if (action == answer1) {
                double addedMoney = Double.parseDouble(usedInterface.getString("How much: "));
                currentUser.addMoney(addedMoney);
            } else if (action == answer2) {
                buy(usedInterface);
            } else if (action == answer3) {
                usedInterface.printString(currentUser.getMoneyString());
            } else if (action == answer4) {
                myItems(usedInterface);
            } else if (action == answer5) {
                addItem(usedInterface);
            } else if (action == answer6) {
                break;
            }
        }
    }

    private static void addItem(UserInterface usedInterface) {
        String newItemName = usedInterface.getString("Input name: ");
        double newItemPrice = Double.parseDouble(usedInterface.getString("Input price: "));
        Items addedItem = new Items(newItemName, newItemPrice, currentUser);
        items.add(addedItem);
    }

    private static void myItems(UserInterface usedInterface) {
        List<Items> currentUserItems = itemFilter(ItemsOwner.CURRENT_USER);
        int action = showItemList(usedInterface, "Want change price? Choose item:", currentUserItems);
        if (action == answer0){
            return;
        } else {
            Items changingItem = currentUserItems.get(action - 1);
            double newPrice = Double.parseDouble(usedInterface.getString("Input new price: "));
            changingItem.setPrice(newPrice);
        }
    }

    private static void buy(UserInterface usedInterface) {
        List<Items> offeredItems = itemFilter(ItemsOwner.OTHER);
        int action = showItemList(usedInterface, "What you want buy:", offeredItems);
        if (action == answer0){
            return;
        } else {
            Items buyingItem = offeredItems.get(action - 1);
            if (currentUser.spendMoney(buyingItem.getPrice())) {
                User owner = buyingItem.getOwner();
                users.remove(owner);
                owner.addMoney(buyingItem.getPrice());
                users.add(owner); // не придумал, как сделать красивее.
                buyingItem.setOwner(currentUser);
            } else {
                usedInterface.printString("Not enough money");
            }
        }
    }

    private static List<Items> itemFilter(ItemsOwner io) {
        Predicate<Items> lambda;
        if (io.equals(ItemsOwner.OTHER)){
            lambda = (i) -> !i.getOwner().equals(currentUser);
        } else {
            lambda = (i) -> i.getOwner().equals(currentUser);
        }
        List <Items> filteredItems = items.stream()
                .filter(lambda)
                .collect(Collectors.toList());
        return filteredItems;
    }

    private static int showItemList(UserInterface usedInterface, String title, List<Items> offeredItems) {
        int counter = 1;
        String[] actions = new String[offeredItems.size() + 1];
        actions[0] = "0. Back";
        for (Items item : offeredItems) {
            actions[counter] = counter++ + ". " + item;
        }
        int action = usedInterface.showMenu(title, actions);
        return action;
    }

    private static boolean firstPage(String pathToUserData, UserInterface usedInterface) {
        String[] actions = {"1. LogIn",
                "2. Registration"};
        int action = usedInterface.showMenu("Choose action: ", actions);
        if (action == answer1) {
            if (!LogIn(pathToUserData, usedInterface)) {
                return true;
                //TODO: Добавить попытки можно, но пока не усложняем
            } else {
                usedInterface.printString("Hi " + currentUser.getName() + "! " + currentUser.getMoneyString());
            }
        } else {
            if (!Registration(pathToUserData, usedInterface)) {
                return true;
            }
        }
        return false;
    }

    private static boolean Registration(String pathToUserData, UserInterface usedInterface) {
        if (findUserByLogin(pathToUserData, usedInterface)) {
            usedInterface.printString("This login already taken.");
            return false;
            //TODO: Добавить попытки можно, но пока не усложняем
        }
        String userPassword = usedInterface.getString("Insert your password: ");
        String userName = usedInterface.getString("What is your name: ");
        currentUser = new User(userLogin, userPassword, userName);
        users.add(currentUser);
        return true;
    }

    private static boolean findUserByLogin(String pathToUserData, UserInterface usedInterface) {
        userLogin = usedInterface.getString("Insert your login: ");
        users = WorkWithFiles.getListFromFile(pathToUserData);
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

    private static boolean LogIn(String pathToUserData, UserInterface usedInterface) {
        boolean find = findUserByLogin(pathToUserData, usedInterface);
        if (find){
            String userPassword = usedInterface.getString("Insert your password: ");
            if (!currentUser.getPassword().equals(userPassword)) {
                usedInterface.printString("Incorrect password");
                find = false;
            }
        } else {
            usedInterface.printString("No user with this login");
        }
        return find;
    }

}
