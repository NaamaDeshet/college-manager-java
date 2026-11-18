
// Naama Deshet
package NewProject;

import java.util.Comparator;

public class LecturersByArticlesComparator implements Comparator<Lecturer> {

    @Override
    public int compare(Lecturer l1, Lecturer l2){
        int firstLecturer = l1.getNumberOfArticles();
        int secondLecturer = l2.getNumberOfArticles();

        if (firstLecturer > secondLecturer){
            return 1;
        } else if (firstLecturer < secondLecturer){
            return -1;
        } else {
            return 0;
        }
    }
}