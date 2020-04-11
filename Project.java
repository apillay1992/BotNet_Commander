import java.io.Serializable;

// Created 9 attributes
public class Project implements Serializable {
	private int project_number;
	private String project_name;
	private String building_design;
	private String project_address;
	private int ERF_number;
	private double total_project_fee;
	private double fee_paid_toDate;
	private String project_deadline;
	private String project_status;
	
	// Constructors to create objects.
	public Project(int project_number, String project_name, String building_design, String project_address, int ERF_number, double total_project_fee, double fee_paid_toDate, String project_deadline, String project_status) {
		this.project_number = project_number;
		this.project_name = project_name;
		this.building_design = building_design;
		this.project_address = project_address;
		this.ERF_number = ERF_number;
		this.total_project_fee = total_project_fee;
		this.fee_paid_toDate = fee_paid_toDate;
		this.project_deadline = project_deadline;
		this.project_status = project_status;
	}
	
	// Created a method to get the project number.
	public int getProjectNum() {
		return project_number;
	}
	// Created a method to get the Project name.
    public String getProjectName() {
    	return project_name;
    }
	// Created a method to get the building design.
    public String getBuildingDesign() {
    	return building_design;
    }
    // Created a method to get the address.
    public String getProjectAddress() {
    	return project_address;
    }
    // Created a method to get the ERF number.
    public int getERFNum() {
    	return ERF_number;
    }
    // Created a method to get the total project fee.
    public double getTotalProjectFee() {
    	return total_project_fee;
    }
    // Created a method to get the amount paid to date.
    public double getFeePaidToDate() {
    	return fee_paid_toDate;
    }
    // Created a method to get the project deadline date.
    public String getProjectDeadline() {
    	return project_deadline;
    }
    // Created a method to get the Project status.
    public String getProjectStatus() {
    	return project_status;
    }
    // Created a toString() method to display the details about the project.
    public String toString() {
    	String output = "Project number:\t\t\t\t " + project_number;
    	output  += "\nProject name:\t\t\t\t " + project_name;
    	output += "\nType of Building Design:\t\t " + building_design;
    	output += "\nProject Address:\t\t\t " + project_address;
    	output += "\nERF Number:\t\t\t\t " + ERF_number;
    	output += "\nTotal Cost of Project:\t\t\t R " + total_project_fee;
    	output += "\nTotal Cost Paid to date:\t\t R " + fee_paid_toDate;
    	output += "\nProject Deadline:\t\t\t " + project_deadline;
    	output += "\nProject Status:\t\t\t\t " + project_status;    	
    	return output;
       
    }
    
}
