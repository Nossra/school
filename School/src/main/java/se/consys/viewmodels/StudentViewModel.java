package se.consys.viewmodels;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import se.consys.Entities.Course;

public class StudentViewModel {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<String> courseNames;
	
	public StudentViewModel() {
		
	}
	
	public StudentViewModel(int id, String firstName, String lastName, String email, String password) {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public List<String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(List<String> courseNames) {
		this.courseNames = courseNames;
	}
}
