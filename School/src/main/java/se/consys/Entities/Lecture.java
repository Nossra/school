package se.consys.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

@Entity
public class Lecture implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDateTime timeOfLecture;
	private String lectureRoom;
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Course course;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "Lecture_Teacher",
		joinColumns = @JoinColumn(name="Lecture_Id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="Teacher_Id", referencedColumnName="id"))
	private Set<Teacher> teachers;
	
	public Lecture() {}
	
	public Lecture(LocalDateTime timeOfLecture, String lectureRoom) {
		this.setTimeOfLecture(timeOfLecture);
		this.setLectureRoom(lectureRoom);
	}
	
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
