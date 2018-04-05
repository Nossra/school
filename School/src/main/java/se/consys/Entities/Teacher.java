package se.consys.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Teacher implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	@ManyToMany(mappedBy = "qualifiedTeachers")
	private Set<Subject> qualifications;
	@OneToMany
	private Set<Course> supervisedCourses;
	@ManyToMany(mappedBy = "teachers")
	private Set<Lecture> lectures;
	
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
}
