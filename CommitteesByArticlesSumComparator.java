// Naama Deshet

package NewProject;


import java.util.Comparator;

public class CommitteesByArticlesSumComparator implements Comparator<Committee> {

    @Override
    public int compare(Committee o1, Committee o2) {
        int firstSize = o1.getSumOfArticles();
        int secondSize = o2.getSumOfArticles();

        if (firstSize > secondSize) {
            return 1;
        } else if (firstSize < secondSize) {
            return -1;
        } else {
            return 0;
        }
    }
}
