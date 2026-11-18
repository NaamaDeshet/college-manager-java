// Naama Deshet
package NewProject;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Professor extends Doctor {
    private String grantingBody;

    public Professor(String name, String id, String nameOfDegree, double salary, Department department, Set<String> articles, String grantingBody) {
        super(name, id, nameOfDegree, salary, department, articles);
        this.grantingBody = grantingBody;
        this.setDegree(Degree.PROF);
    }

    public String getGrantingBody() {
        return grantingBody;
    }

    public void setGrantingBody(String grantingBody) {
        this.grantingBody = grantingBody;
    }

    @Override
    public String toString() {
        String string = super.toString().replace("}", "");
        return string + ", Granting body: " + grantingBody + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Professor professor = (Professor) o;
        return Objects.equals(grantingBody, professor.grantingBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), grantingBody);
    }
}