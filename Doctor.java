// Naama Deshet
package NewProject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Doctor extends Lecturer {
    protected Set<String> articles;

    public Doctor(String name, String id, String nameOfDegree, double salary, Department department, Set<String> articles) {
        super(name, id, Degree.PHD, nameOfDegree, salary, department);
        setArticles(articles);
    }

    public Set<String> getArticles() {
        return articles;
    }

    public void setArticles(Set<String> articles) {
        this.articles = articles;
    }

    @Override
    public boolean canBeCommitteeChair() {
        return true;
    }

    @Override
    public int getNumberOfArticles() {
        if (articles == null) return 0;
        return articles.size();
    }

    @Override
    public String toString() {
        String string = super.toString().replace("}", "");

        StringBuilder articlesBuilder = new StringBuilder("[");
        if (articles != null) {
            Iterator<String> iterator = articles.iterator();
            while (iterator.hasNext()) {
                articlesBuilder.append(iterator.next());
                if (iterator.hasNext()) {
                    articlesBuilder.append(", ");
                }
            }
        }
        articlesBuilder.append("]");

        return string + ", list of articles: " + articlesBuilder.toString() + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(articles, doctor.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), articles);
    }
}