package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class PersonEmploymentEx extends PersonEmployment implements Employment {

	private EntityState state;
	private Company company;
	private Person person;
	private Identifiable role;
	private Identifiable job;
	private Company destinationCompany;

	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Identifiable getRole() {
		return role;
	}

	public void setRole(Identifiable role) {
		this.role = role;
	}

	public Identifiable getJob() {
		return job;
	}

	public void setJob(Identifiable job) {
		this.job = job;
	}

	public Company getDestinationCompany() {
		return destinationCompany;
	}

	public void setDestinationCompany(Company destinationCompany) {
		this.destinationCompany = destinationCompany;
	}
}
