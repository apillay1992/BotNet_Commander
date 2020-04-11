import java.io.Serializable;

// Created a class called Person
public class Person implements Serializable {
	
	// Created 5 attributes
	private String expert_type;
	private String name;
	private String cell_num;
	private String email;
	private String address;
	
	// Constructor to create objects.
	public Person(String expert_type, String name, String cell_num, String email, String address) {
		this.expert_type = expert_type;
		this.name = name;
		this.cell_num = cell_num;
		this.email = email;
		this.address = address;
	}
	
	// Created method to get the type of person being added.
	public String getExpertType() {
		return expert_type;
	}
	// Created a method to get the name of the person.
	public String getName() {
		return name;
	}
	// Created a method to get the cell number.
	public String getCellNumber() {
		return cell_num;
	}
	// Created a method to get the email address.
	public String getEmail() {
		return email;
	}
	// Created a method to ge the physicall address.
	public String getAddress() {
		return address;
	}
	
	// Created a toString method to display all the person details.
	public String toString() {
		String output = "Expert type:\t\t\t\t " + expert_type;
		output += "\nName:\t\t\t\t\t " + name;
		output += "\nCell number:\t\t\t\t " + cell_num;
		output += "\nEmail address:\t\t\t\t " + email;
		output += "\nPhysical address:\t\t\t " + address;
		
		return output;
	}
	
	
	
}



