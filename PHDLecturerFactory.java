// Naama Deshet
package NewProject;

import java.util.Set;

public class PHDLecturerFactory extends LecturerFactory {
    @Override
    public Lecturer createLecturer(String name, String id, String nameOfDegree, double salary,
                                   Department department, Set<String> articles, String grantingBody) {
        return new Doctor(name, id, nameOfDegree, salary, department, articles);
    }
}
