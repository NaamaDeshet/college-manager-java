// Naama Deshet
package NewProject;

import java.util.Set;

public class BALecturerFactory extends LecturerFactory {
    @Override
    public Lecturer createLecturer(String name, String id, String nameOfDegree, double salary,
                                   Department department, Set<String> articles, String grantingBody) {
        return new Lecturer(name, id, Degree.BA, nameOfDegree, salary, department);
    }
}
