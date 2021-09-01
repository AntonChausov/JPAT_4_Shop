import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFiles {
    public static List<User> getUsers(String destinationUserData) {
        File file = new File(destinationUserData);
        List<User> users = new ArrayList<>();
        if (file.length() != 0) {
            try (FileInputStream fis = new FileInputStream(destinationUserData);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                users = (List<User>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return users;
    }

    public static boolean createFiles(String destinationUserData, String destinationItemsData) {
        boolean fileExist = createFile(destinationUserData);
        fileExist = createFile(destinationItemsData);
        return fileExist;
    }

    private static boolean createFile(String destination) {
        File file = new File(destination);
        boolean fileExist = file.exists();
        if (!fileExist){
            try {
                fileExist = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileExist;
    }

    public static boolean saveUserData(String destination, List<User> users){
        try (FileOutputStream fos = new FileOutputStream(destination);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static List<Items> getItems(String destination) {
        File file = new File(destination);
        List<Items> items = new ArrayList<>();
        if (file.length() != 0) {
            try (FileInputStream fis = new FileInputStream(destination);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                items = (List<Items>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return items;
    }

    public static <T> List<T> getListFromFile(String destination, T type) {
        File file = new File(destination);
        List<T> list = new ArrayList<>();
        if (file.length() != 0) {
            try (FileInputStream fis = new FileInputStream(destination);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                list = (List<T>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }
}
