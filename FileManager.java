
// Naama Deshet
package NewProject;

import java.io.*;

public class FileManager implements IDataStorage {
    private static final String FILE_NAME = "data.dat";

    @Override
    public CollageManager load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (CollageManager) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean save(CollageManager collageManager) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(collageManager);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}