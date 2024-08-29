import java.io.Serializable;

/**
 * Represents a project with various attributes such as project number, name, building design, and more.
 * Implements Serializable for object serialization.
 */
public class Project implements Serializable {
	private int projectNumber;
	private String projectName;
	private String buildingDesign;
	private String projectAddress;
	private int erfNumber;
	private double totalProjectFee;
	private double feePaidToDate;
	private String projectDeadline;
	private String projectStatus;

	/**
	 * Constructs a new Project with the specified details.
	 *
	 * @param projectNumber   the project number
	 * @param projectName     the project name
	 * @param buildingDesign  the type of building design
	 * @param projectAddress  the project address
	 * @param erfNumber       the ERF number
	 * @param totalProjectFee the total cost of the project
	 * @param feePaidToDate   the amount paid to date
	 * @param projectDeadline the project deadline
	 * @param projectStatus   the current status of the project
	 */
	public Project(int projectNumber, String projectName, String buildingDesign, String projectAddress, int erfNumber, double totalProjectFee, double feePaidToDate, String projectDeadline, String projectStatus) {
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.buildingDesign = buildingDesign;
		this.projectAddress = projectAddress;
		this.erfNumber = erfNumber;
		this.totalProjectFee = totalProjectFee;
		this.feePaidToDate = feePaidToDate;
		this.projectDeadline = projectDeadline;
		this.projectStatus = projectStatus;
	}

	// Getter methods
	public int getProjectNum() {
		return projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getBuildingDesign() {
		return buildingDesign;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public int getERFNum() {
		return erfNumber;
	}

	public double getTotalProjectFee() {
		return totalProjectFee;
	}

	public double getFeePaidToDate() {
		return feePaidToDate;
	}

	public String getProjectDeadline() {
		return projectDeadline;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	/**
	 * Returns a string representation of the project details.
	 *
	 * @return a string containing the formatted details of the project
	 */
	@Override
	public String toString() {
		return String.format(
				"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25d |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25s |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25s |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25s |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25d |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25.2f |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25.2f |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25s |\n" +
						"+--------------------------+---------------------------+\n" +
						"| %-24s | %-25s |\n" +
						"+--------------------------+---------------------------+\n",
				"Project number", projectNumber,
				"Project name", projectName,
				"Type of Building Design", buildingDesign,
				"Project Address", projectAddress,
				"ERF Number", erfNumber,
				"Total Cost of Project", totalProjectFee,
				"Total Cost Paid to date", feePaidToDate,
				"Project Deadline", projectDeadline,
				"Project Status", projectStatus
		);
	}
}
