// Naama Deshet
package NewProject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Set;

public class CollageManager implements Serializable {
    private String collageName;

    private Set<Lecturer> lecturers = new HashSet<>();
    private Set<Committee> committees = new HashSet<>();
    private Set<Department> departments = new HashSet<>();

    public CollageManager(String collageName) {
        this.collageName = collageName;
    }


    public void createLecturer(String name, String id, Degree degree, String nameOfDegree, double salary, String departmentName, Set<String> articles, String grantingBody) {
        Department department = findDepartmentByName(departmentName);
        LecturerFactory factory = getFactory(degree);

        if (factory == null) {
            throw new InvalidDegreeException("wrong degreeMember");
        }

        Lecturer lecturerInfo = factory.createLecturer(name, id, nameOfDegree, salary, department, articles, grantingBody);
        addLecturer(lecturerInfo);
    }

    private LecturerFactory getFactory(Degree degree) {
        if (degree == Degree.BA) {
            return new BALecturerFactory();
        } else if (degree == Degree.MA) {
            return new MALecturerFactory();
        } else if (degree == Degree.PHD) {
            return new PHDLecturerFactory();
        } else if (degree == Degree.PROF) {
            return new ProfessorLecturerFactory();
        }
        return null;
    }

    public void addLecturer(Lecturer lecturerInfo) {
        if (isExist(lecturerInfo)) {
            throw new noSuccessAddException("Lecturer already exists.");
        } else {
            lecturers.add(lecturerInfo);

            lecturerInfo.addObserver(NotificationCenter.getInstance());
            lecturerInfo.notifyObservers("Lecturer " + lecturerInfo.getName() + " was hired to the collage.");
        }
    }


    public void reattachObservers() {
        for (Lecturer lecturer : lecturers) {
            if (lecturer != null) {
                lecturer.addObserver(NotificationCenter.getInstance());
            }
        }
    }

    public boolean updateLecturerSalary(String lecturerName, double newSalary) {
        Lecturer lecturer = getLecturer(lecturerName);
        if (lecturer == null) {
            return false;
        }
        Lecturer.validateSalary(newSalary);
        lecturer.setSalary(newSalary);
        return true;
    }

    public String displayNotificationLog() {
        return NotificationCenter.getInstance().displayLog();
    }

    public boolean hasNotifications() {
        return NotificationCenter.getInstance().hasNotifications();
    }

    public void createCommittee(String committee, String chairName, Degree degreeMember) {
        Lecturer chair = getLecturer(chairName);
        Committee committeeInfo = new Committee(committee, chair, degreeMember);
        addCommittee(committeeInfo);
    }

    public void addCommittee(Committee committeeInfo) {
        committees.add(committeeInfo);
    }

    public void createDepartment(String name, int numOfStudents) {
        Department department = new Department(name, numOfStudents);
        addDepartment(department);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public boolean isLecturerExist(String value) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer != null && lecturer.getName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCommitteeExist(String value) {
        for (Committee committee : committees) {
            if (committee != null && committee.getName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDepartmentExist(String nameOfDepartment) {
        for (Department department : departments) {
            if (department != null && department.getName().equals(nameOfDepartment)) {
                return true;
            }
        }
        return false;
    }

    public void addMemberToCommittee(String nameOfCommittee, String nameOfMember) {
        Lecturer lecturer = getLecturer(nameOfMember);
        Committee committee = getCommittee(nameOfCommittee);
        if (lecturer == null) {
            throw new nullException("Lecture doesn't exist.");
        }
        if (committee == null) {
            throw new nullException("Committee doesn't exist.");
        }
        lecturer.addCommittee(committee);
        committee.addLecturer(lecturer);
    }

    public void removeMemberFromCommittee(String nameOfCommittee, String nameOfMember) {
        Lecturer lecturer = getLecturer(nameOfMember);
        Committee committee = getCommittee(nameOfCommittee);
        if (lecturer == null) {
            throw new nullException("Lecture doesn't exist.");
        }
        if (committee == null) {
            throw new nullException("Committee doesn't exist.");
        }
        committee.removeLecturer(lecturer);
    }

    public void addMemberToDepartment(String lecturer, String department) {
        Lecturer l = getLecturer(lecturer);
        Department d = getDepartment(department);
        if (l == null) {
            throw new noSuccessAddException("Lecture doesn't exist.");
        }
        if (d == null) {
            throw new noSuccessAddException("Department doesn't exist.");
        }
        if (l.getDepartment() != null) {
            throw new noSuccessAddException("Lecturer is already exist");
        }
        d.addLecturer(l);
        l.setDepartment(d);
    }

    public void removeMemberFromDepartment(String nameOfDepartment, String nameOfMember) {
        Lecturer lecturer = getLecturer(nameOfMember);
        Department department = getDepartment(nameOfDepartment);
        if (lecturer == null) {
            throw new noSuccessRemoveException("Lecture doesn't exist.");
        }
        if (department == null) {
            throw new noSuccessRemoveException("Department doesn't exist.");
        }
        department.removeLecturer(lecturer);
        lecturer.setDepartment(null);
    }

    public double displayAvgSalary() {
        double sum = 0;
        int count = 0;

        for (Lecturer lecturer : lecturers) {
            if (lecturer != null) {
                sum += lecturer.getSalary();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        } else {
            return sum / count;
        }
    }

    public double displayAvgSalaryDepartment(String departmentName) {
        Department department = findDepartmentByName(departmentName);
        if ((department != null)) {
            return department.getAvgSalary(department);
        }
        return 0;
    }

    public String displayLecturers() {
        StringBuilder result = new StringBuilder();
        Iterator<Lecturer> iterator = lecturers.iterator();
        while (iterator.hasNext()) {
            Lecturer lecturer = iterator.next();
            if (lecturer != null) {
                result.append(lecturer.toString());
            }
        }
        return result.toString();
    }

    public String displayCommittees() {
        StringBuilder result = new StringBuilder();
        Iterator<Committee> iterator = committees.iterator();
        while (iterator.hasNext()) {
            Committee committee = iterator.next();
            if (committee != null) {
                result.append(committee.toString());
            }
        }
        return result.toString();
    }

    public String displayDepartments() {
        StringBuilder result = new StringBuilder();
        Iterator<Department> iterator = departments.iterator();
        while (iterator.hasNext()) {
            Department dept = iterator.next();
            if (dept != null) {
                result.append(dept.toString());
            }
        }
        return result.toString();
    }

    public String displaySortedLecturers(Comparator<Lecturer> comparator) {
        TreeSet<Lecturer> sortedSet = new TreeSet<>(comparator);
        sortedSet.addAll(lecturers);

        StringBuilder result = new StringBuilder();
        Iterator<Lecturer> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            Lecturer lecturer = iterator.next();
            if (lecturer != null) {
                result.append(lecturer.toString());
            }
        }
        return result.toString();
    }

    public String displaySortedCommittees(Comparator<Committee> comparator) {
        TreeSet<Committee> sortedSet = new TreeSet<>(comparator);
        sortedSet.addAll(committees);

        StringBuilder result = new StringBuilder();
        Iterator<Committee> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            Committee committee = iterator.next();
            if (committee != null) {
                result.append(committee.toString());
            }
        }
        return result.toString();
    }

    public String displaySortedDepartments(Comparator<Department> comparator) {
        TreeSet<Department> sortedSet = new TreeSet<>(comparator);
        sortedSet.addAll(departments);

        StringBuilder result = new StringBuilder();
        Iterator<Department> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            Department dept = iterator.next();
            if (dept != null) {
                result.append(dept.toString());
            }
        }
        return result.toString();
    }

    public boolean hasLecturers() {
        return !lecturers.isEmpty();
    }

    public boolean hasCommittees() {
        return !committees.isEmpty();
    }

    public boolean hasDepartments() {
        return !departments.isEmpty();
    }

    public Lecturer getLecturer(String nameOfMember) {
        for (Lecturer l : lecturers) {
            if (l != null && l.getName().equals(nameOfMember)) {
                return l;
            }
        }
        return null;
    }

    public Committee getCommittee(String committeeName) {
        for (Committee c : committees) {
            if (c != null && c.getName().equals(committeeName)) {
                return c;
            }
        }
        return null;
    }

    public Department getDepartment(String departmentName) {
        for (Department d : departments) {
            if (d != null && d.getName().equals(departmentName)) {
                return d;
            }
        }
        return null;
    }

    public boolean updateChair(String nameOfCommittee, String chair) {
        Lecturer lecturer = getLecturer(chair);
        Committee committee = getCommittee(nameOfCommittee);
        if (lecturer == null || committee == null) {
            return false;
        }
        return committee.updateCommitteeChair(lecturer);
    }

    public boolean able2BChair(String chairName) {
        Lecturer chair = getLecturer(chairName);
        if (chair == null) {
            return false;
        }
        return chair.canBeCommitteeChair();
    }

    public boolean updateLecturerInDepartment(String departmentName, String nameOfLecturer) {
        Department department = findDepartmentByName(departmentName);
        Lecturer lecturer = getLecturer(nameOfLecturer);
        if (department != null && lecturer != null) {
            department.addLecturer(lecturer);
            return true;
        }
        return false;
    }

    public Department findDepartmentByName(String nameOfDepartment) {
        for (Department d : departments) {
            if (d != null && d.getName().equals(nameOfDepartment)) {
                return d;
            }
        }
        return null;
    }

    public boolean isNameInLecturers(String value) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int CompareCommitteesByArticlesSum(Committee c1, Committee c2) {
        CommitteesByArticlesSumComparator comparator = new CommitteesByArticlesSumComparator();
        return comparator.compare(c1, c2);
    }

    public int compareCommitteesByLecturerCount(Committee c1, Committee c2) {
        return c1.compareTo(c2);
    }

    public int compareDoctorAndProfByArticlesAmount(Lecturer l1, Lecturer l2) throws InvalidDegreeException {
        if ((l1.getDegree() == Degree.PHD || l1.getDegree() == Degree.PROF) && (l2.getDegree() == Degree.PHD || l2.getDegree() == Degree.PROF)) {
            LecturersByArticlesComparator comparator = new LecturersByArticlesComparator();
            return comparator.compare(l1, l2);
        }
        throw new InvalidDegreeException("This lecturer is not PHD or PROF");
    }

    private boolean isExist(Lecturer lecturer) {
        for (Lecturer l : lecturers) {
            if (l != null && l.equals(lecturer)) {
                return true;
            }
        }
        return false;
    }
}