import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFiles {

    public static boolean createFiles(String destinationUserData, String destinationItemsData) {
        boolean fileExist = createFile(destinationUserData);
        fileExist = fileExist && createFile(destinationItemsData);
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

    public static <T> boolean saveData(String destination, List<T> list){
        try (FileOutputStream fos = new FileOutputStream(destination);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static <T> List<T> getListFromFile(String destination) {
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
