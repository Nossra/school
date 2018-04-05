package se.consys.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Lecture implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime timeOfLecture;
	private String lectureRoom;
	@ManyToOne
	private Course course;
	@ManyToMany
	@JoinTable(
		name = "Lecture_Teacher",
		joinColumns = @JoinColumn(name="Lecture_Id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="Teacher_Id", referencedColumnName="id"))
	private Set<Teacher> teachers;
	
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getLectureRoom() {
		return lectureRoom;
	}
	public void setLectureRoom(String lectureRoom) {
		this.lectureRoom = lectureRoom;
	}
	public LocalDateTime getTimeOfLecture() {
		return timeOfLecture;
	}
	public void setTimeOfLecture(LocalDateTime timeOfLecture) {
		this.timeOfLecture = timeOfLecture;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
