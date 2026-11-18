// Naama Deshet
package NewProject;

import java.util.Set;

public class ProfessorLecturerFactory extends LecturerFactory {
    @Override
    public Lecturer createLecturer(String name, String id, String nameOfDegree, double salary,
                                   Department department, Set<String> articles, String grantingBody) {
        return new Professor(name, id, nameOfDegree, salary, department, articles, grantingBody);
    }
}
