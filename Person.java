import java.io.Serializable;

/**
 * The Person class represents an individual with specific expertise,
 * including contact details.
 */
public class Person implements Serializable {

	private String expert_type;
	private String name;
	private String cell_num;
	private String email;
	private String address;

	/**
	 * Constructor to create a new Person object.
	 *
	 * @param expert_type the type of expertise the person has
	 * @param name the name of the person
	 * @param cell_num the person's cell phone number
	 * @param email the person's email address
	 * @param address the person's physical address
	 */
	public Person(String expert_type, String name, String cell_num, String email, String address) {
		this.expert_type = expert_type;
		this.name = name;
		this.cell_num = cell_num;
		this.email = email;
		this.address = address;
	}

	public String getExpertType() {
		return expert_type;
	}

	public String getName() {
		return name;
	}

	public String getCellNumber() {
		return cell_num;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}
	
	// Created a toString method to display all the person details.
	@Override
	public String toString() {
		return String.format(
				"+-----------------------+-----------------------------+\n" +
						"| %-21s | %-27s |\n" +
						"+-----------------------+-----------------------------+\n" +
						"| %-21s | %-27s |\n" +
						"+-----------------------+-----------------------------+\n" +
						"| %-21s | %-27s |\n" +
						"+-----------------------+-----------------------------+\n" +
						"| %-21s | %-27s |\n" +
						"+-----------------------+-----------------------------+\n" +
						"| %-21s | %-27s |\n" +
						"+-----------------------+-----------------------------+\n",
				"Expert type", expert_type,
				"Name", name,
				"Cell number", cell_num,
				"Email address", email,
				"Physical address", address
		);
	}
}



