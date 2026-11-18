// Naama Deshet
package NewProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationCenter implements Observer {
    private static NotificationCenter instance;

    private List<String> log = new ArrayList<>();


    private NotificationCenter() {
    }

    public static NotificationCenter getInstance() {
        if (instance == null) {
            instance = new NotificationCenter();
        }
        return instance;
    }

    @Override
    public void update(String message) {
        log.add(message);
    }

    public boolean hasNotifications() {
        return !log.isEmpty();
    }

    public String displayLog() {
        StringBuilder result = new StringBuilder();
        Iterator<String> iterator = log.iterator();
        while (iterator.hasNext()) {
            result.append(iterator.next()).append("\n");
        }
        return result.toString();
    }
}
