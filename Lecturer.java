// Naama Deshet
package NewProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Lecturer implements Serializable {
    private String name;
    private String id;
    private Degree degree;
    private String nameOfDegree;
    private double salary;
    private Department department;


    private Set<Committee> committees = new HashSet<>();


    private transient List<Observer> observers;

    public Lecturer(String name, String id, Degree degree, String nameOfDegree, double salary, Department department) throws nullException {
        setId(id);
        setName(name);
        setDegree(degree);
        setNameOfDegree(nameOfDegree);
        setSalary(salary);
        setDepartment(department);
    }

    public Set<Committee> getCommittees() {
        return committees;
    }

    public void setCommittees(Set<Committee> committees) throws nullException {
        if (committees == null) {
            throw new nullException("Didn't set committees");
        }
        this.committees = committees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws nullException {
        if (name == null) {
            throw new nullException("Didn't set name");
        }
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws nullException {
        if (id == null) {
            throw new nullException("Didn't set id");
        }
        this.id = id;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) throws nullException {
        if (degree == null) {
            throw new nullException("Didn't set degree");
        }
        this.degree = degree;
    }

    public String getNameOfDegree() {
        return nameOfDegree;
    }

    public void setNameOfDegree(String nameOfDegree) throws nullException {
        if (nameOfDegree == null) {
            throw new nullException("Didn't set name of degree");
        }
        this.nameOfDegree = nameOfDegree;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) throws nullException {
        if (salary < 0) {
            throw new nullException("Didn't set salary");
        }
        double oldSalary = this.salary;
        this.salary = salary;

        if (oldSalary != salary) {
            notifyObservers("Salary of lecturer " + name + " changed from " + oldSalary + " to " + salary);
        }
    }


    public static void validateSalary(double salary) {
        if (salary < 0) {
            throw new noSuccessAddException("Salary cannot be negative.");
        }
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean canBeCommitteeChair() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder namesBuilder = new StringBuilder("[");
        Iterator<Committee> iterator = committees.iterator();
        while (iterator.hasNext()) {
            namesBuilder.append(iterator.next().getName());
            if (iterator.hasNext()) {
                namesBuilder.append(", ");
            }
        }
        namesBuilder.append("]");

        return "Lecturer {Name: " + name + ", Id: " + id + ", Degree: " + degree + ", Name of degree: " + nameOfDegree +
                ", Salary: " + salary + ", Department: " + (department != null ? department.getName() : "null") + ", Committees: " + namesBuilder.toString() + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Double.compare(salary, lecturer.salary) == 0 &&
                Objects.equals(name, lecturer.name) &&
                Objects.equals(id, lecturer.id) &&
                degree == lecturer.degree &&
                Objects.equals(nameOfDegree, lecturer.nameOfDegree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, degree, nameOfDegree, salary);
    }

    public void addCommittee(Committee committee) throws noSuccessAddException {
        if (isExist(committee)) {
            throw new noSuccessAddException("Didn't add committee");
        }
        committees.add(committee);
    }

    public void removeCommittee(Committee committee) throws noSuccessRemoveException {
        boolean removed = false;
        Iterator<Committee> iterator = committees.iterator();
        while (iterator.hasNext()) {
            Committee currentCommittee = iterator.next();
            if (currentCommittee.getName().equals(committee.getName())) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new noSuccessRemoveException("Didn't remove committee");
        }
    }

    private boolean isExist(Committee committee) {
        Iterator<Committee> iterator = committees.iterator();
        while (iterator.hasNext()) {
            Committee c = iterator.next();
            if (c != null && c.equals(committee)) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfArticles() {
        return 0;
    }


    private List<Observer> getObservers() {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        return observers;
    }

    public void addObserver(Observer observer) {
        getObservers().add(observer);
    }

    public void removeObserver(Observer observer) {
        getObservers().remove(observer);
    }

    public void notifyObservers(String message) {
        Iterator<Observer> iterator = getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().update(message);
        }
    }
}