import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFiles {
    public static List<User> getUsers(String pathToUserData) {
        File file = new File(pathToUserData);
        List<User> users = new ArrayList<>();
        if (file.length() != 0) {
            User user = null;
            try (FileInputStream fis = new FileInputStream(pathToUserData);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                users = (List<User>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return users;
    }

    public static boolean createFiles(String destinationUserData, String destinationItemsData) {
        //Файл в корне проекта
        File userData = new File(destinationUserData);
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
}
