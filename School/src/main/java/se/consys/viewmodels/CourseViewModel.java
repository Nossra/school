package se.consys.viewmodels;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.consys.Entities.Lecture;
import se.consys.Entities.Student;
public class CourseViewModel {
	private int id;
	private String courseName;
	private LocalDateTime timeStamp;
	private LocalDate startDate;
	private LocalDate endDate;
	private int durationInMonths;
	private Set<TeacherViewModel> supervisors;
	private Map<LocalDateTime, Lecture> scheduledLectures;
	private List<StudentViewModel> students;
	
	public CourseViewModel() {}
	
	public CourseViewModel(int id, String courseName) {
		this.setId(id);
		this.setCourseName(courseName);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getDurationInMonths() {
		return durationInMonths;
	}
	public void setDurationInMonths(int durationInMonths) {
		this.durationInMonths = durationInMonths;
	}
	public Map<LocalDateTime, Lecture> getScheduledLectures() {
		return scheduledLectures;
	}
	public void setScheduledLectures(Map<LocalDateTime, Lecture> scheduledLectures) {
		this.scheduledLectures = scheduledLectures;
	}
	public List<StudentViewModel> getStudents() {
		return students;
	}
	public void setStudents(List<StudentViewModel> students) {
		this.students = students;
	}
	public Set<TeacherViewModel> getSupervisors() {
		return supervisors;
	}
	public void setSupervisors(Set<TeacherViewModel> supervisors) {
		this.supervisors = supervisors;
	}
}
