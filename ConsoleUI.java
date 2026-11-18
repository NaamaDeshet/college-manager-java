// Naama Deshet
package NewProject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Comparator;

public class ConsoleUI {
    private Scanner scan;
    private CollageManager collageManager;
    private IDataStorage storage;

    public ConsoleUI(IDataStorage storage) {
        scan = new Scanner(System.in);
        this.storage = storage;
    }

    public void start() {
        System.out.println("Welcome :) ");
        collageManager = storage.load();
        if (collageManager == null) {
            System.out.println("No saved data found. Starting fresh.");
            System.out.println("Enter the name of collage: ");
            String collageName = scan.nextLine();
            collageManager = new CollageManager(collageName);
        } else {
            System.out.println("Data loaded successfully.");
            collageManager.reattachObservers();
        }

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.println("Choose a number: ");
            int menuNum = scan.nextInt();
            scan.nextLine();
            switch (menuNum) {
                case 0:
                    boolean saved = storage.save(collageManager);
                    if (saved) {
                        System.out.println("Data saved successfully.");
                    } else {
                        System.out.println("Error saving data.");
                    }
                    isRunning = false;
                    System.out.println("Good Bye");
                    break;

                case 1:
                    System.out.println("Enter lecturer name: ");
                    String name = scan.nextLine();

                    while (collageManager.isLecturerExist(name)) {
                        System.out.println("This name already exist, please enter a different name: ");
                        name = scan.nextLine();
                    }
                    System.out.println("Enter Id: ");
                    String id = scan.nextLine();
                    System.out.println("Enter degree (BA/MA/PHD/PROF): ");
                    String input = scan.nextLine().toUpperCase();
                    Degree degree = null;
                    try {
                        boolean validDegree = false;
                        for (Degree d : Degree.values()) {
                            if (d.name().equals(input)) {
                                degree = d;
                                validDegree = true;
                                break;
                            }
                        }
                        if (!validDegree) {
                            throw new InvalidDegreeException("wrong degreeMember");
                        }
                    } catch (InvalidDegreeException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Enter name of degree: ");
                    String nameOfDegree = scan.nextLine();
                    System.out.println("Enter salary: ");
                    double salary = scan.nextDouble();

                    try {
                        Lecturer.validateSalary(salary);
                    } catch (noSuccessAddException e) {
                        System.out.println("Salary can't be a number below 0, lecturer didn't add");
                        break;
                    }
                    scan.nextLine();
                    System.out.println("Enter department: ");
                    String department = scan.nextLine();

                    Set<String> articles = new HashSet<>();
                    String grantingBody = null;
                    if (degree == Degree.PHD || degree == Degree.PROF) {
                        System.out.println("Enter articles (separate by comma): ");
                        String articlesLine = scan.nextLine();
                        articles.addAll(Arrays.asList(articlesLine.split("\\s*,\\s*")));
                    }
                    if (degree == Degree.PROF) {
                        System.out.println("Enter institution who awarded the professorship: ");
                        grantingBody = scan.nextLine();
                    }
                    collageManager.createLecturer(name, id, degree, nameOfDegree, salary, department, articles, grantingBody);
                    collageManager.updateLecturerInDepartment(department, name);
                    System.out.println("Lecturer added successfully");
                    break;

                case 2:
                    System.out.println("Enter committee name: ");
                    String nameCommittee = scan.nextLine();
                    while (collageManager.isCommitteeExist(nameCommittee)) {
                        System.out.println("This name already exist, please enter a different name: ");
                        nameCommittee = scan.nextLine();
                    }
                    System.out.println("Enter committee chair name: ");
                    String chairName = scan.nextLine();
                    while (!collageManager.isNameInLecturers(chairName)) {
                        System.out.println("This lecturer doesn't exist, please try again: ");
                        chairName = scan.nextLine();
                    }
                    if (!collageManager.able2BChair(chairName)) {
                        System.out.println("This lecturer can't be a committee chair, committee wasn't added. ");
                        break;
                    }
                    System.out.println("Enter committee's members degree (BA/MA/PHD/PROF): ");
                    String member = scan.nextLine().toUpperCase();
                    Degree degreeMember = null;
                    try {
                        boolean validDegree = false;
                        for (Degree d : Degree.values()) {
                            if (d.name().equals(member)) {
                                degreeMember = d;
                                validDegree = true;
                                break;
                            }
                        }
                        if (!validDegree) {
                            throw new InvalidDegreeException("wrong degreeMember");
                        }
                    } catch (InvalidDegreeException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    collageManager.createCommittee(nameCommittee, chairName, degreeMember);
                    System.out.println("Committee added successfully");
                    break;

                case 3:
                    System.out.println("Please enter the name of committee: ");
                    String nameOfCommittee = scan.nextLine();
                    if (!collageManager.isCommitteeExist(nameOfCommittee)) {
                        System.out.println("Committee doesn't exist, please try again: ");
                    } else {
                        System.out.println("please enter the name of the member: ");
                        String nameOfMember = scan.nextLine();
                        if (!collageManager.isLecturerExist(nameOfMember)) {
                            System.out.println("Member is not a lecturer in collage. ");
                        } else {
                            try {
                                collageManager.addMemberToCommittee(nameOfCommittee, nameOfMember);
                                System.out.println("Member added to committee");
                            } catch (noSuccessAddException e) {
                                System.out.println("Member didn't add to committee. ");
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("Please enter the name of committee: ");
                    String committeeName = scan.nextLine();
                    if (!collageManager.isCommitteeExist(committeeName)) {
                        System.out.println("Committee doesn't exist, please try again: ");
                    } else {
                        System.out.println("Enter committee chair name: ");
                        String ChairName = scan.nextLine();
                        if (!collageManager.isNameInLecturers(ChairName)) {
                            System.out.println("This lecturer doesn't exist, please try again: ");
                        } else {
                            if (collageManager.updateChair(committeeName, ChairName)) {
                                System.out.println("Committee chair updated");
                            } else {
                                System.out.println("Committee chair didn't update.");
                            }
                        }
                    }
                    break;
                case 5:
                    System.out.println("Please enter the name of committee: ");
                    String nameCommittee1 = scan.nextLine();
                    if (!collageManager.isCommitteeExist(nameCommittee1)) {
                        System.out.println("Committee doesn't exist, please try again: ");
                    } else {
                        System.out.println("Enter committee Member: ");
                        String committeeMember = scan.nextLine();
                        if (!collageManager.isLecturerExist(committeeMember)) {
                            System.out.println("Member is not a lecturer in collage, please try again: ");
                        } else {
                            try {
                                collageManager.removeMemberFromCommittee(nameCommittee1, committeeMember);
                                System.out.println("Member removed");
                            } catch (noSuccessRemoveException e) {
                                System.out.println("Member can't be removed");
                            }
                        }
                    }
                    break;
                case 6:
                    System.out.println("Enter name of department: ");
                    String nameOfDepartment = scan.nextLine();
                    while (collageManager.isDepartmentExist(nameOfDepartment)) {
                        System.out.println("Department already exist, please try again: ");
                        nameOfDepartment = scan.nextLine();
                    }
                    System.out.println("Enter number of students in Department: ");
                    int numOfStudents = scan.nextInt();
                    scan.nextLine();
                    collageManager.createDepartment(nameOfDepartment, numOfStudents);
                    System.out.println("Department added successfully");
                    break;
                case 7:
                    System.out.println("The average salary is: " + collageManager.displayAvgSalary());
                    break;
                case 8:
                    System.out.println("Enter name of department: ");
                    String departmentName = scan.nextLine();
                    if (!collageManager.isDepartmentExist(departmentName)) {
                        System.out.println("Department doesn't exist, please try again: ");
                    } else {
                        System.out.println("The avg salary is: " + collageManager.displayAvgSalaryDepartment(departmentName));
                    }
                    break;
                case 9:
                    if (!collageManager.hasLecturers()) {
                        System.out.println("No lecturers have been added");
                    } else {
                        System.out.println("Do you want to sort the lecturers?");
                        System.out.println("1 - No sort");
                        System.out.println("2 - Sort by Name");
                        System.out.println("3 - Sort by Salary");
                        int sortOption = scan.nextInt();
                        scan.nextLine();

                        if (sortOption == 2) {
                            System.out.println(collageManager.displaySortedLecturers(new Comparator<Lecturer>() {
                                @Override
                                public int compare(Lecturer l1, Lecturer l2) {
                                    return l1.getName().compareTo(l2.getName());
                                }
                            }));
                        } else if (sortOption == 3) {
                            System.out.println(collageManager.displaySortedLecturers(new Comparator<Lecturer>() {
                                @Override
                                public int compare(Lecturer l1, Lecturer l2) {
                                    return Double.compare(l1.getSalary(), l2.getSalary());
                                }
                            }));
                        } else {
                            System.out.println(collageManager.displayLecturers());
                        }
                    }
                    break;
                case 10:
                    if (!collageManager.hasCommittees()) {
                        System.out.println("No committees have been added");
                    } else {
                        System.out.println("Do you want to sort the committees?");
                        System.out.println("1 - No sort");
                        System.out.println("2 - Sort by Name");
                        System.out.println("3 - Sort by Number of Members");
                        int sortOption = scan.nextInt();
                        scan.nextLine();

                        if (sortOption == 2) {
                            System.out.println(collageManager.displaySortedCommittees(new Comparator<Committee>() {
                                @Override
                                public int compare(Committee c1, Committee c2) {
                                    return c1.getName().compareTo(c2.getName());
                                }
                            }));
                        } else if (sortOption == 3) {
                            System.out.println(collageManager.displaySortedCommittees(new Comparator<Committee>() {
                                @Override
                                public int compare(Committee c1, Committee c2) {
                                    return Integer.compare(c1.getLecturerNames().size(), c2.getLecturerNames().size());
                                }
                            }));
                        } else {
                            System.out.println(collageManager.displayCommittees());
                        }
                    }
                    break;
                case 11:
                    if (!collageManager.hasDepartments()) {
                        System.out.println("No Departments have been added");
                    } else {
                        System.out.println("Do you want to sort the departments?");
                        System.out.println("1 - No sort");
                        System.out.println("2 - Sort by Name");
                        System.out.println("3 - Sort by Number of Students");
                        int sortOption = scan.nextInt();
                        scan.nextLine();

                        if (sortOption == 2) {
                            System.out.println(collageManager.displaySortedDepartments(new Comparator<Department>() {
                                @Override
                                public int compare(Department d1, Department d2) {
                                    return d1.getName().compareTo(d2.getName());
                                }
                            }));
                        } else if (sortOption == 3) {
                            System.out.println(collageManager.displaySortedDepartments(new Comparator<Department>() {
                                @Override
                                public int compare(Department d1, Department d2) {
                                    return Integer.compare(d1.getNumOfStudents(), d2.getNumOfStudents());
                                }
                            }));
                        } else {
                            System.out.println(collageManager.displayDepartments());
                        }
                    }
                    break;
                case 12:
                    System.out.println("Please enter the name of department: ");
                    String departmentsName = scan.nextLine();
                    if (!collageManager.isDepartmentExist(departmentsName)) {
                        System.out.println("Department doesn't exist, please try again: ");
                    } else {
                        System.out.println("Enter name of lecturer: ");
                        String lecturerName = scan.nextLine();
                        if (!collageManager.isLecturerExist(lecturerName)) {
                            System.out.println("This lecturer doesn't exist, please try again: ");
                        } else {
                            try {
                                collageManager.addMemberToDepartment(lecturerName, departmentsName);
                                System.out.println("Lecturer added successfully");
                            } catch (noSuccessAddException e) {
                                System.out.println("Lecturer can't be added: " + e.getMessage());
                            }
                        }
                    }
                    break;
                case 13:
                    System.out.println("Please enter the name of department: ");
                    String nameDepartment = scan.nextLine();
                    if (!collageManager.isDepartmentExist(nameDepartment)) {
                        System.out.println("Department doesn't exist, please try again: ");
                    } else {
                        System.out.println("Enter name of lecturer: ");
                        String departmentMember = scan.nextLine();
                        if (!collageManager.isLecturerExist(departmentMember)) {
                            System.out.println("This lecturer doesn't exist in collage, please try again: ");
                        } else {
                            try {
                                collageManager.removeMemberFromDepartment(nameDepartment, departmentMember);
                                System.out.println("Lecturer removed successfully");
                            } catch (noSuccessRemoveException e) {
                                System.out.println("Lecturer can't be removed");
                            }
                        }
                    }
                    break;

                case 14:
                    System.out.println("Enter first committee name: ");
                    String committeeA = scan.nextLine();
                    if (!collageManager.isCommitteeExist(committeeA)) {
                        System.out.println("Committee doesn't exist, please try again.");
                        break;
                    }
                    System.out.println("Enter second committee name: ");
                    String committeeB = scan.nextLine();
                    if (!collageManager.isCommitteeExist(committeeB)) {
                        System.out.println("Committee doesn't exist, please try again.");
                        break;
                    }
                    Committee c1 = collageManager.getCommittee(committeeA);
                    Committee c2 = collageManager.getCommittee(committeeB);
                    int result = collageManager.compareCommitteesByLecturerCount(c1, c2);
                    if (result > 0) {
                        System.out.println("The committee \"" + c1.getName() + "\" has more members.");
                    } else if (result < 0) {
                        System.out.println("The committee \"" + c2.getName() + "\" has more members.");
                    } else {
                        System.out.println("Both committees have the same number of members.");
                    }
                    break;
                case 15:
                    System.out.println("Enter first committee name: ");
                    String com1 = scan.nextLine();
                    if (!collageManager.isCommitteeExist(com1)) {
                        System.out.println("Committee doesn't exist, please try again.");
                        break;
                    }

                    System.out.println("Enter second committee name: ");
                    String com2 = scan.nextLine();
                    if (!collageManager.isCommitteeExist(com2)) {
                        System.out.println("Committee doesn't exist, please try again.");
                        break;
                    }

                    Committee committee1 = collageManager.getCommittee(com1);
                    Committee committee2 = collageManager.getCommittee(com2);

                    int resultByArticles = collageManager.CompareCommitteesByArticlesSum(committee1, committee2);

                    if (resultByArticles > 0) {
                        System.out.println("The committee \"" + committee1.getName() + "\" has more total articles.");
                    } else if (resultByArticles < 0) {
                        System.out.println("The committee \"" + committee2.getName() + "\" has more total articles.");
                    } else {
                        System.out.println("Both committees have the same number of total articles.");
                    }
                    break;
                case 16:
                    System.out.println("Enter name of the first lecturer: ");
                    String name1 = scan.nextLine();
                    Lecturer l1 = collageManager.getLecturer(name1);
                    if (l1 == null) {
                        System.out.println("Lecturer \"" + name1 + "\" not found.");
                        break;
                    }
                    System.out.println("Enter name of the second lecturer: ");
                    String name2 = scan.nextLine();
                    Lecturer l2 = collageManager.getLecturer(name2);
                    if (l2 == null) {
                        System.out.println("Lecturer \"" + name2 + "\" not found.");
                        break;
                    }
                    try {
                        int result1 = collageManager.compareDoctorAndProfByArticlesAmount(l1, l2);
                        if (result1 > 0) {
                            System.out.println(l1.getName() + " has more articles.");
                        } else if (result1 < 0) {
                            System.out.println(l2.getName() + " has more articles.");
                        } else {
                            System.out.println("Both have the same number of articles.");
                        }
                    } catch (InvalidDegreeException e) {
                        System.out.println("Comparison failed: " + e.getMessage());
                    }
                    break;

                case 17:
                    System.out.println("Enter committee name: ");
                    String comName = scan.nextLine();
                    Committee committee = collageManager.getCommittee(comName);
                    if (committee == null) {
                        System.out.println("Committee not found.");
                        break;
                    }
                    try {
                        Committee clonedCommittee = committee.clone();
                        collageManager.addCommittee(clonedCommittee);
                        System.out.println("Committee cloned successfully! New committee name: " + clonedCommittee.getName());
                    } catch (CloneNotSupportedException e) {
                        System.out.println("Cloning not supported: " + e.getMessage());
                    }
                    break;

                case 18:
                    System.out.println("Enter name of lecturer: ");
                    String salaryLecturerName = scan.nextLine();
                    if (!collageManager.isLecturerExist(salaryLecturerName)) {
                        System.out.println("This lecturer doesn't exist, please try again: ");
                    } else {
                        System.out.println("Enter new salary: ");
                        double newSalary = scan.nextDouble();
                        scan.nextLine();
                        try {
                            collageManager.updateLecturerSalary(salaryLecturerName, newSalary);
                            System.out.println("Salary updated successfully");
                        } catch (noSuccessAddException e) {
                            System.out.println("Salary can't be a number below 0, salary wasn't updated");
                        }
                    }
                    break;

                case 19:
                    if (!collageManager.hasNotifications()) {
                        System.out.println("No notifications yet");
                    } else {
                        System.out.println(collageManager.displayNotificationLog());
                    }
                    break;

                default:
                    System.out.println("Invalid number, try again.");
                    break;
            }
        }
        scan.close();
    }

    private void printMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add new lecturer");
        System.out.println("2 - Add new committee");
        System.out.println("3 - Add member to committee");
        System.out.println("4 - Update committee's chair");
        System.out.println("5 - Remove member from committee");
        System.out.println("6 - Add study department");
        System.out.println("7 - Lecturers salary avg");
        System.out.println("8 - Lecturers salary avg of study department");
        System.out.println("9 - Lecturers information");
        System.out.println("10 - Committee information");
        System.out.println("11 - Department information");
        System.out.println("12 - Add member to department");
        System.out.println("13 - Remove member from department");
        System.out.println("14 - Compare two committees by number of members");
        System.out.println("15 - Compare two committees by total articles written by members");
        System.out.println("16 - Compare number of articles between two Doctors/Professors");
        System.out.println("17 - Copy Committee");
        System.out.println("18 - Update lecturer's salary");
        System.out.println("19 - View notification log");
    }
}