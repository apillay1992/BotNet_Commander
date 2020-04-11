import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Poised {
	
    // Created a method() to display the menu.
	public static String displayMenu() {
		
		String menu = "Please select one of the options below, e.g. a,b or c: "
				+ "\na) Add project : "
				+ "\nb) Edit project details :"
				+ "\nc) View existing project: "
				+ "\nd) Add new person: "
				+ "\ne) Edit person details: "
				+ "\nf) View existing person details: "
				+ "\ng) View final project with person details: "
				+ "\nh) Finalise project: ";
		
		return menu;
	}	
		
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("****************Welcome to Poised******************");
		System.out.println("***********Project Management Software*************\n");
		
		// Called the displayMenu() method to display the menu.
		// Requested user input, user will choose one the options from the displayMenu method().
		System.out.println(displayMenu());
		Scanner user1 = new Scanner(System.in);
		String Input = user1.nextLine();
	
		// Used conditional statements for each function of the menu.
		// Requested user input for details about the project being captured.
		// If user chooses "a" the user will be able to add a new project.
		if(Input.equals("a")) {
			
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the project number: ");
			int project_num = in.nextInt();
					
			Scanner in_1 = new Scanner(System.in);
			System.out.println("Enter the project name: ");
			String project_name = in_1.nextLine();
					
			Scanner in_2 = new Scanner(System.in);
			System.out.println("Enter the building design: ");
		    String building_type = in_2.nextLine();
				    
		    Scanner in_3 = new Scanner(System.in);
			System.out.println("Enter the project address: ");
			String project_address = in_3.nextLine();
				
			Scanner in_4 = new Scanner(System.in);
			System.out.println("Enter the ERF number: ");
			int ERF_number = in_4.nextInt();
			
			Scanner in_5 = new Scanner(System.in);
			System.out.println("Enter the total cost of project: ");
			double total_cost = in_5.nextDouble();
				
			Scanner in_6 = new Scanner(System.in);
			System.out.println("Enter the amount paid to date: ");
			double paid_toDate = in_6.nextDouble();
				
			Scanner in_7 = new Scanner(System.in);
			System.out.println("Enter the deadline date, e.g. 25 December 2020: ");
			String deadline_date = in_7.nextLine();
			
			String status = "Not Complete";
			
			// We initiate a new object called project1.
			// We call the Project class created earlier.
			// Passed the input variables through as values for each object created.
			Project project1 = new Project(project_num, project_name, building_type, project_address, ERF_number, total_cost, paid_toDate, deadline_date, status);
			System.out.println("\n");
			System.out.println("Project added successfully!");
			
			// Displayed the project details by calling the toString method from the Project class.
			System.out.println(project1.toString());
			
			// Used the objectOutStream method to write the object project1 to a text file called Project.txt.
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Project.txt"));
			out.writeObject(project1);
			

		// if the user the user chooses "b".
		// Used the ObjectInputStream method to read the Object project1 from text file Project.txt. 
		}else if(Input.equals("b")) {
			ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("Project.txt"));
			Project p2 = (Project) in1.readObject();
			
			// Assigned new variables for each method Project class.
			// Added a new variable to store the status, which is now set to "Not Complete".
		    int project_num = p2.getProjectNum();	
		    String project_name = p2.getProjectName();
		    String building_type = p2.getBuildingDesign();
		    String project_address = p2.getProjectAddress();
		    int ERF_number = p2.getERFNum();
		    double total_cost = p2.getTotalProjectFee();
		    String status = "Not Complete";
		    
		    // Requested input from user for new deadline date.
			Scanner new_dueDate = new Scanner(System.in);
			System.out.println("Enter new deadline: ");
			String DueDate = new_dueDate.nextLine();
			
			// Requested input from user for new amount paid to date.
			Scanner new_paidToDate = new Scanner(System.in);
			System.out.println("Enter new total amount paid to date: R");
			double paidToDate = new_paidToDate.nextDouble();
			
			// Passed new variables through to Object project1 with new due date, new amount paid to date and the status.
			Project project1 = new Project(project_num, project_name, building_type, project_address, ERF_number, total_cost, paidToDate, DueDate, status);
			System.out.println();
			System.out.println("Project amended successfully!\n");
			System.out.println("New project details below: \n");
			
			// Printed out new details.
			System.out.println(project1.toString());
			
			// Wrote the new updated object to Project.txt.
			ObjectOutputStream new_out = new ObjectOutputStream(new FileOutputStream("Project.txt"));
			new_out.writeObject(project1);
				
		// If user chooses the "c".	
		// Reads project1 object from project.txt and displays the details.
		}else if(Input.equals("c")) {
			ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("Project.txt"));
			Project p2 = (Project) in1.readObject();
		    System.out.println(p2);
		    
		    
		// if user chooses "d".
		// User will be able to add a new customer, architect or contractor.
		}else if(Input.equals("d")) {
			
			//Requested input from user for all details about the expert/customer.
			Scanner input1 = new Scanner(System.in);
			System.out.println("Choose architect, contractor or customer :  ");
			String expert = input1.nextLine();
				
		    Scanner input2 = new Scanner(System.in);
		    System.out.println("Enter the name: ");
		    String name = input2.nextLine();
		   
			Scanner input3 = new Scanner(System.in);
			System.out.println("Enter the cell number:  ");
			String cell = input3.nextLine();
			
			Scanner input4 = new Scanner(System.in);
			System.out.println("Enter the email address:  ");
			String email = input4.nextLine();
		
			Scanner input5 = new Scanner(System.in);
			System.out.println("Enter physical address:  ");
			String address = input5.nextLine();
			
			// We initiate a new object called person1.
			// We call the Person class created earlier.
			// Passed the input variables through as values for each object created.
			Person person1 = new Person(expert, name, cell, email, address);
			System.out.println("\n");
			System.out.println("Person added successfully!");
			
			// Displayed the person1 object by calling the toString() method.
			System.out.println(person1.toString());
			
			// Wrote the new Object to text file called Person.txt.
			ObjectOutputStream new_person_out = new ObjectOutputStream(new FileOutputStream("Person.txt"));
			new_person_out.writeObject(person1);
			
		// If the user chooses "e".
		// The user will be able to edit the person details.
	    }else if(Input.equals("e")) {
	    	// Used the ObjectInputStream to read the object form the Person.txt.
	    	ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("Person.txt"));
			Person new_p2 = (Person) in2.readObject();
			
			// Assign new variables for each method from the Person class.
			String expert = new_p2.getExpertType();
			String name = new_p2.getName();
			String address = new_p2.getAddress();
			
			// Requested User input for the new cell number.
			Scanner new_cellNo = new Scanner(System.in);
			System.out.println("Enter new cell number: ");
			String new_cell = new_cellNo.nextLine();
			
			// Requested User input for the new Email address.
			Scanner new_emailAdd = new Scanner(System.in);
			System.out.println("Enter new email Address: ");
			String new_email = new_emailAdd.nextLine();
			
			// Passed new variable through to Object person1 including new_cell and new_email.
			Person person1 = new Person(expert, name, new_cell, new_email, address);
			System.out.println("\n");
			System.out.println("Details updated successfully!");
			
			// Printed out the new details by calling the toString() method.
			System.out.println(person1.toString());
			
			// Wrote the updated object to Person.txt
			ObjectOutputStream new_person_out1 = new ObjectOutputStream(new FileOutputStream("Person.txt"));
			new_person_out1.writeObject(person1);
			
		// if the user chooses the "f" the updated person details will be displayed from the Person.txt text file.
	    }else if(Input.equals("f")) {
	    	ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("Person.txt"));
			Person new_p2 = (Person) in2.readObject();
			System.out.println(new_p2);
			
		// if user chooses "g" then both the project and person details will be displayed() 
	    }else if(Input.equals("g")) {
	    	ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("Project.txt"));
			Project p2 = (Project) in1.readObject();
		    System.out.println(p2+"\n");
		    
		    ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("Person.txt"));
			Person new_p2 = (Person) in2.readObject();
			System.out.println(new_p2);
			
		// If the user chooses "h"
		// and if the total amount paid is greater than the amount paid to date then a invoice will be generated.
	    }else if(Input.equals("h")) {
	    	ObjectInputStream in1 = new ObjectInputStream(new FileInputStream("Project.txt"));
			Project p2 = (Project) in1.readObject();
			if(p2.getTotalProjectFee() > p2.getFeePaidToDate()) {
			
			    ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("Person.txt"));
				Person new_p2 = (Person) in2.readObject();
				
				double new_outstanding_balance = p2.getTotalProjectFee() - p2.getFeePaidToDate();
				
			    // Used the FileWriter method to write and format the tax invoice generated.
			    FileWriter myWriter = new FileWriter("tax_invoice.txt");
			    myWriter.write("\t\t\tTAX INVOICE");
			    myWriter.write("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		    	myWriter.write("\nINVOICED TO:\n\n"+new_p2.toString());
		    	myWriter.write("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		    	myWriter.write("\n\nTotal Cost of Project:\t\t\t\t R " + p2.getTotalProjectFee());
		    	myWriter.write("\nTotal amount paid to date:\t\t\t R "+ p2.getFeePaidToDate());
		    	myWriter.write("\n\n\n");
		    	myWriter.write("\n____________________________________________________________");
		    	myWriter.write("\nOutstanding Balance:\t\t\t\t R " + new_outstanding_balance);
		    	myWriter.close();
		    	System.out.println("Outstanding balance!\n" +"\nTax Invoice generated!");
				
		    // if the total project amount is equal to the amount paid to date then project will be marked as finalised!.
		    // all information about the finalised project will be written to the Completed_Project.txt text file.
			}else if(p2.getTotalProjectFee() == p2.getFeePaidToDate()) {
				
				// assigned new variables and add the status to the project1 Object.
			    int project_num = p2.getProjectNum();	
		        String project_name = p2.getProjectName();
		        String building_type = p2.getBuildingDesign();
		        String project_address = p2.getProjectAddress();
		        int ERF_number = p2.getERFNum();
		        double total_cost = p2.getTotalProjectFee();
		        double paidToDate = p2.getFeePaidToDate();
		        String DueDate = p2.getProjectDeadline();
		        String status = "Finalised";
		    
		    
		        Project project1 = new Project(project_num, project_name, building_type, project_address, ERF_number, total_cost, paidToDate, DueDate, status);
			    System.out.println();
			    System.out.println("Project finalised!\n");
			    System.out.println("Please see 'Completed_Project.txt' for full details!\nThank you!" );
			    
			    // Used the SimpleDateFormat method to get the current date which will added to the finalised project details.
			    Date date = new Date();
		    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		    	String strDate= formatter.format(date);
		    	
		    	// Wrote the new updated Object to Project.txt.
			    ObjectOutputStream new_out = new ObjectOutputStream(new FileOutputStream("Project.txt"));
			    new_out.writeObject(project1);
			    
			    
			    // Read the Object from person.text 
			    
			    ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("Person.txt"));
				Person new_p2 = (Person) in2.readObject();
			    
			    String expert = new_p2.getExpertType();
				String name = new_p2.getName();
				String cell = new_p2.getCellNumber();
				String email = new_p2.getEmail();
				String address = new_p2.getAddress();
				
			    
				Person person1 = new Person(expert, name, cell, email, address);
			    
				// Write the details of the object project1 and person1 to a text file called Completed_Project.txt.
			    FileWriter myWriter = new FileWriter("Completed_Project.txt");
			    myWriter.write("Finalised Project Details:\n\n");
			    myWriter.write(project1.toString());
			    ObjectOutputStream new_person_out = new ObjectOutputStream(new FileOutputStream("Person.txt"));
				new_person_out.writeObject(person1);
			    myWriter.write("\nCompletion Date: \t\t\t " + strDate);
			    myWriter.write("\n\nFinalised Person Details:\n\n");
			    myWriter.write(new_p2.toString());
		        myWriter.close();
	         } 
			
			
		
	    }
	}
}   
