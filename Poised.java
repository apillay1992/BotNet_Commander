import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Poised {

	// Display menu options for the project management software
	public static String displayMenu() {
		return "Please select one of the options below, e.g. a, b, or c: " +
				"\na) Add project" +
				"\nb) Edit project details" +
				"\nc) View existing project" +
				"\nd) Add new person" +
				"\ne) Edit person details" +
				"\nf) View existing person details" +
				"\ng) View final project with person details" +
				"\nh) Finalise project" +
				"\ni) Exit";
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			String input;
			boolean running = true;

			System.out.println("****************Welcome to Poised******************");
			System.out.println("***********Project Management Software*************\n");

			while (running) {
				System.out.println(displayMenu());
				System.out.print("Enter your choice: ");
				input = scanner.nextLine().toLowerCase(); // Using toLowerCase to handle uppercase input

				switch (input) {
					case "a":
						addProject(scanner);
						break;
					case "b":
						editProject();
						break;
					case "c":
						viewProject();
						break;
					case "d":
						addPerson(scanner);
						break;
					case "e":
						editPerson();
						break;
					case "f":
						viewPerson();
						break;
					case "g":
						viewFinalProject();
						break;
					case "h":
						finalizeProject();
						break;
					case "i":
						running = false;
						System.out.println("Exiting the program. Goodbye!");
						break;
					default:
						System.out.println("Invalid option selected. Please choose a valid option from the menu.");
						break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void addProject(Scanner scanner) throws IOException {
		try {
			System.out.print("Enter the project number: ");
			int projectNum = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			System.out.print("Enter the project name: ");
			String projectName = scanner.nextLine();

			System.out.print("Enter the building design: ");
			String buildingType = scanner.nextLine();

			System.out.print("Enter the project address: ");
			String projectAddress = scanner.nextLine();

			System.out.print("Enter the ERF number: ");
			int erfNumber = scanner.nextInt();

			System.out.print("Enter the total cost of the project: ");
			double totalCost = scanner.nextDouble();

			System.out.print("Enter the amount paid to date: ");
			double paidToDate = scanner.nextDouble();
			scanner.nextLine(); // Consume the newline character

			System.out.print("Enter the deadline date (e.g. 25 December 2020): ");
			String deadlineDate = scanner.nextLine();

			String status = "Not Complete";

			Project project1 = new Project(projectNum, projectName, buildingType, projectAddress, erfNumber, totalCost, paidToDate, deadlineDate, status);
			System.out.println("\nProject added successfully!");
			System.out.println(project1);

			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Project.txt"))) {
				out.writeObject(project1);
			}

		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please enter the correct data types.");
			scanner.nextLine(); // Clear the invalid input
		}
	}

	private static void editProject() throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Project.txt"))) {
			Project p2 = (Project) in.readObject();

			System.out.print("Enter new deadline: ");
			Scanner scanner = new Scanner(System.in);
			String dueDate = scanner.nextLine();

			System.out.print("Enter new total amount paid to date: R");
			double paidToDate = scanner.nextDouble();

			Project updatedProject = new Project(
					p2.getProjectNum(), p2.getProjectName(), p2.getBuildingDesign(), p2.getProjectAddress(),
					p2.getERFNum(), p2.getTotalProjectFee(), paidToDate, dueDate, "Not Complete"
			);

			System.out.println("\nProject amended successfully!");
			System.out.println("New project details below: \n" + updatedProject);

			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Project.txt"))) {
				out.writeObject(updatedProject);
			}
		}
	}

	private static void viewProject() throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Project.txt"))) {
			Project p2 = (Project) in.readObject();
			System.out.println(p2);
		}
	}

	private static void addPerson(Scanner scanner) throws IOException {
		System.out.print("Choose architect, contractor, or customer: ");
		String expertType = scanner.nextLine();

		System.out.print("Enter the name: ");
		String name = scanner.nextLine();

		System.out.print("Enter the cell number: ");
		String cellNum = scanner.nextLine();

		System.out.print("Enter the email address: ");
		String email = scanner.nextLine();

		System.out.print("Enter physical address: ");
		String address = scanner.nextLine();

		Person person1 = new Person(expertType, name, cellNum, email, address);
		System.out.println("\nPerson added successfully!");
		System.out.println(person1);

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Person.txt"))) {
			out.writeObject(person1);
		}
	}

	private static void editPerson() throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Person.txt"))) {
			Person p2 = (Person) in.readObject();

			Scanner scanner = new Scanner(System.in);

			System.out.print("Enter new cell number: ");
			String newCellNum = scanner.nextLine();

			System.out.print("Enter new email address: ");
			String newEmail = scanner.nextLine();

			Person updatedPerson = new Person(p2.getExpertType(), p2.getName(), newCellNum, newEmail, p2.getAddress());
			System.out.println("\nDetails updated successfully!");
			System.out.println(updatedPerson);

			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Person.txt"))) {
				out.writeObject(updatedPerson);
			}
		}
	}

	private static void viewPerson() throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Person.txt"))) {
			Person p2 = (Person) in.readObject();
			System.out.println(p2);
		}
	}

	private static void viewFinalProject() throws IOException, ClassNotFoundException {
		try (ObjectInputStream projectIn = new ObjectInputStream(new FileInputStream("Project.txt"));
			 ObjectInputStream personIn = new ObjectInputStream(new FileInputStream("Person.txt"))) {

			Project project = (Project) projectIn.readObject();
			Person person = (Person) personIn.readObject();
			System.out.println(project + "\n" + person);
		}
	}

	private static void finalizeProject() throws IOException, ClassNotFoundException {
		try (ObjectInputStream projectIn = new ObjectInputStream(new FileInputStream("Project.txt"))) {
			Project p2 = (Project) projectIn.readObject();

			if (p2.getTotalProjectFee() > p2.getFeePaidToDate()) {
				try (ObjectInputStream personIn = new ObjectInputStream(new FileInputStream("Person.txt"))) {
					Person person = (Person) personIn.readObject();

					double outstandingBalance = p2.getTotalProjectFee() - p2.getFeePaidToDate();

					try (FileWriter writer = new FileWriter("tax_invoice.txt")) {
						writer.write("\t\t\tTAX INVOICE");
						writer.write("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
						writer.write("\nINVOICED TO:\n\n" + person);
						writer.write("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
						writer.write("\n\nTotal Cost of Project:\t\t\t\t R " + p2.getTotalProjectFee());
						writer.write("\nTotal amount paid to date:\t\t\t R " + p2.getFeePaidToDate());
						writer.write("\n\n\n");
						writer.write("\n____________________________________________________________");
						writer.write("\nOutstanding Balance:\t\t\t\t R " + outstandingBalance);
					}
					System.out.println("An invoice for the outstanding balance has been created: tax_invoice.txt");
				}
			} else {
				System.out.println("The project has been paid in full.");
			}

			Project finalizedProject = new Project(
					p2.getProjectNum(), p2.getProjectName(), p2.getBuildingDesign(), p2.getProjectAddress(),
					p2.getERFNum(), p2.getTotalProjectFee(), p2.getFeePaidToDate(), p2.getProjectDeadline(), "Complete"
			);

			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Project.txt"))) {
				out.writeObject(finalizedProject);
			}
			System.out.println("The project has been finalized.");
		}
	}
}
