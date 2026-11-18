// Naama Deshet
package NewProject;

public class Main {
    public static void main(String[] args) {
        IDataStorage storage = new FileManager();
        ConsoleUI ui = new ConsoleUI(storage);
        ui.start();
    }
}