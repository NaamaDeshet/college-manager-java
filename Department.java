// Naama Deshet
package NewProject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Department implements Serializable {
    private String name;
    private int numOfStudents;
    private Set<Lecturer> lecturers = new HashSet<>();

    public Department(String name, int numOfStudents) throws nullException {
        setName(name);
        setNumOfStudents(numOfStudents);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws nullException {
        if (name == null) {
            throw new nullException("Didn't set name of department");
        }
        this.name = name;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(int numOfStudents) throws nullException {
        if (numOfStudents < 0) {
            throw new nullException("didn't set number of students");
        }
        this.numOfStudents = numOfStudents;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = new HashSet<>(lecturers);
    }

    @Override
    public String toString() {
        StringBuilder namesBuilder = new StringBuilder("[");
        Iterator<Lecturer> iterator = lecturers.iterator();
        while (iterator.hasNext()) {
            namesBuilder.append(iterator.next().getName());
            if (iterator.hasNext()) {
                namesBuilder.append(", ");
            }
        }
        namesBuilder.append("]");

        return "Department {name: " + name +
                ", number of students: " + numOfStudents
                + ", lecturers: " + namesBuilder.toString() + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return numOfStudents == that.numOfStudents &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numOfStudents);
    }

    public void addLecturer(Lecturer lecturer) throws noSuccessAddException {
        if (lecturers.contains(lecturer)) {
            throw new noSuccessAddException("Didn't add lecturer.");
        }
        lecturers.add(lecturer);
    }

    public void removeLecturer(Lecturer lecturer) throws noSuccessRemoveException {
        boolean removed = lecturers.remove(lecturer);
        if (!removed) {
            throw new noSuccessRemoveException("Lecturer doesn't exist");
        }
    }

    public double getAvgSalary(Department name) {
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
}