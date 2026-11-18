// Naama Deshet
package NewProject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Committee implements Comparable<Committee>, Cloneable, Serializable {
    private String name;
    private Set<Lecturer> lecturers = new HashSet<>();
    private Lecturer chair;
    private Degree degreeMembers;

    public Committee(String name, Lecturer committeeChair, Degree degreeMembers) throws nullException {
        setName(name);
        setChair(committeeChair);
        setDegreeMembers(degreeMembers);
    }

    public Committee(String name, Set<Lecturer> lecturers, Lecturer chair) {
        this.name = name;
        this.lecturers = new HashSet<>(lecturers);
        this.chair = chair;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws nullException {
        if (name == null) {
            throw new nullException("Didn't set name of committee");
        }
        this.name = name;
    }

    public Set<Lecturer> getLecturerNames() {
        return lecturers;
    }

    public void setLecturerNames(Set<Lecturer> lecturerNames) {
        this.lecturers = new HashSet<>(lecturerNames);
    }

    public Lecturer getChair() {
        return chair;
    }

    public void setChair(Lecturer chair) throws nullException {
        if (chair == null) {
            throw new nullException("Didn't set chair");
        }
        this.chair = chair;
    }

    public Degree getDegreeMembers() {
        return degreeMembers;
    }

    public void setDegreeMembers(Degree degreeMembers) throws nullException {
        this.degreeMembers = degreeMembers;
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

        return "{Committee: Name: " + name + ", Committee chair: "
                + chair.getName() + ", Committee's members degree: " + degreeMembers + ", Lecturers in committee: " +
                namesBuilder.toString() + "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Committee committee = (Committee) o;
        return Objects.equals(name, committee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void addLecturer(Lecturer lecturer) throws noSuccessAddException {
        if (lecturers.contains(lecturer) || lecturer.equals(chair) || lecturer.getDegree() != degreeMembers) {
            throw new noSuccessAddException("Lecturer already exists or is the chair or degree mismatch.");
        }
        lecturers.add(lecturer);
    }

    public void removeLecturer(Lecturer lecturer) throws noSuccessRemoveException {
        if (!lecturers.contains(lecturer)) {
            throw new noSuccessRemoveException("Lecturer doesn't exist in committee");
        }
        if (lecturer.equals(chair)) {
            throw new noSuccessRemoveException("Can't remove committee chair");
        }
        lecturers.remove(lecturer);
        lecturer.removeCommittee(this);
    }


    public boolean ableToBeChair(Lecturer chair) {
        if (!chair.canBeCommitteeChair()) {
            return false;
        }
        for (Lecturer l : lecturers) {
            if (l != null && l.getName().equals(chair.getName())) {
                return false;
            }
        }
        return true;
    }

    public boolean updateCommitteeChair(Lecturer chair) {
        if (ableToBeChair(chair)) {
            this.chair = chair;
            return true;
        }
        return false;
    }

    public int getSumOfArticles() {
        int sum = 0;
        for (Lecturer lecturer : lecturers) {
            if (lecturer != null) {
                sum += lecturer.getNumberOfArticles();
            }
        }
        return sum;
    }

    @Override
    public int compareTo(Committee o) {
        return this.lecturers.size() - o.lecturers.size();
    }

    @Override
    protected Committee clone() throws CloneNotSupportedException {
        Committee clone = (Committee) super.clone();
        clone.name = "new-" + this.name;
        clone.lecturers = new HashSet<>(this.lecturers);
        return clone;
    }
}