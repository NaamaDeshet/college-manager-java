# College Manager (Java)

A console application for managing the academic staff of a college: lecturers, study departments and academic committees. Built as a project in an Object-Oriented Design course, with an emphasis on clean design, design patterns and SOLID principles.

## Features

- **Lecturers:** add lecturers by degree (BA, MA, PHD, PROF), track published articles, update salaries.
- **Committees:** create committees, add or remove members, appoint a chair (only PHD or PROF lecturers are eligible).
- **Departments:** open study departments and assign lecturers to them.
- **Reports:** average salary (overall and per department), and lecturer / committee / department listings sorted by a criterion chosen at runtime (name, salary, number of members, and more).
- **Notification log:** every lecturer hiring and salary change is recorded and can be displayed.
- **Persistence:** the system state is saved to a file on exit and loaded on start.

## Design highlights

- **Design patterns:** Factory Method (lecturer creation), Observer (hiring and salary-change notifications), Singleton (notification center).
- **SOLID:**
  - SRP: UI logic lives in `ConsoleUI`; `Main` only starts the application.
  - OCP: committee-chair eligibility is a polymorphic method, so new degree types need no changes to existing logic.
  - ISP / DIP: storage is split into `ILoader` and `ISaver` interfaces, and the code depends on interfaces (`Set`, `IDataStorage`) rather than concrete classes.
- **Java Collections:** `HashSet` for data, `TreeSet` with `Comparator` for dynamic sorting, `Iterator` for traversal.
- **Documentation:** UML class, sequence, state and use-case diagrams are available as PDF files in the repository root (`Class Diagram.pdf`, `Sequence Diagram.pdf`, `state diagram.pdf`, `use case diagram.pdf`).

## Run

Requires Java 8 or later. From the repository root:

javac -d out *.java
java -cp out NewProject.Main

Choose actions from the text menu. To save your data, exit with option `0`.
