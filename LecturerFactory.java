// Naama Deshet
package NewProject;

import java.util.Set;


public abstract class LecturerFactory {
    public abstract Lecturer createLecturer(String name, String id, String nameOfDegree, double salary,
                                            Department department, Set<String> articles, String grantingBody);
}
