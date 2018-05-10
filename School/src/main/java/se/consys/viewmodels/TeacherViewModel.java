package se.consys.viewmodels;

import java.util.Set;

public class TeacherViewModel {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private Set<String> subjectNames;
	private Set<String> courseNames;
	private Set<LectureViewModel> lectures;
	
	public TeacherViewModel() {}
	
	public TeacherViewModel(int id, String firstName, String lastName, String email, String phoneNumber, String password) {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setPassword(password);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(Set<String> subjectNames) {
		this.subjectNames = subjectNames;
	}

	public Set<String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(Set<String> courseNames) {
		this.courseNames = courseNames;
	}

	public Set<LectureViewModel> getLectures() {
		return lectures;
	}

	public void setLectures(Set<LectureViewModel> lectures) {
		this.lectures = lectures;
	}
}
