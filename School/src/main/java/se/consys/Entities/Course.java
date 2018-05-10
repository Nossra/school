package se.consys.Entities;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ElementCollection;

@Entity
public class Course implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String courseName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd@HH:mm:ss")
	private LocalDateTime timeStamp;
	private LocalDate startDate;
	private LocalDate endDate;
	private int durationInMonths;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "course_teacher",
			joinColumns = @JoinColumn(name="course_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="teacher_id", referencedColumnName="id"))
	private Set<Teacher> supervisors;
	@ElementCollection
	private Map<LocalDateTime, Lecture> scheduledLectures;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "Course_Student",
		joinColumns = @JoinColumn(name="Course_Id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="Student_Id", referencedColumnName="id"))
	private List<Student> students;
	
	public Course() {}
	
	public Course(
			String courseName,
			LocalDateTime timeStamp,
			LocalDate startDate,
			LocalDate endDate,
			int durationInMonths) {
		this.setCourseName(courseName);
		this.setTimeStamp(timeStamp);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setDurationInMonths(durationInMonths);;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public Map<LocalDateTime, Lecture> getScheduledLectures() {
		return scheduledLectures;
	}
	public void setScheduledLectures(Map<LocalDateTime, Lecture> scheduledLectures) {
		this.scheduledLectures = scheduledLectures;
	}
	public int getDurationInMonths() {
		return durationInMonths;
	}
	public void setDurationInMonths(int durationInMonths) {
		this.durationInMonths = durationInMonths;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Set<Teacher> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(Set<Teacher> supervisors) {
		this.supervisors = supervisors;
	}
}
