package se.consys.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Teacher implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	@ManyToMany(mappedBy = "qualifiedTeachers", fetch = FetchType.LAZY)
	private Set<Subject> qualifications;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "Course_Teacher",
			joinColumns = @JoinColumn(name="Course_Id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="Teacher_Id", referencedColumnName="id"))
	private Set<Course> supervisedCourses;
	@ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
	private Set<Lecture> lectures;
	
	public Teacher() { 
		
	}
	
	public Teacher(
			String firstName,
			String lastName,
			String email, 
			String phoneNumber,
			String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setPassword(password);
	}
	
	public Set<Lecture> getLectures() {
		return lectures;
	}
	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}
	public Set<Course> getSupervisedCourses() {
		return supervisedCourses;
	}
	public void setSupervisedCourses(Set<Course> supervisedCourses) {
		this.supervisedCourses = supervisedCourses;
	}
	public Set<Subject> getQualifications() {
		return qualifications;
	}
	public void setQualifications(Set<Subject> qualifications) {
		this.qualifications = qualifications;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
