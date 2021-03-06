package se.consys.viewmodels;

import java.time.LocalDateTime;
import java.util.Set;

public class LectureViewModel {
	private int id;
	private String courseName;
	private LocalDateTime timeStamp;
	private String lectureRoom;
	private Set<String> teacherNames;
	
	public LectureViewModel(int id, String courseName, LocalDateTime timeStamp, String lectureRoom) {
		this.setId(id);
		this.setCourseName(courseName);
		this.setTimeStamp(timeStamp);
		this.setLectureRoom(lectureRoom);
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
	public String getLectureRoom() {
		return lectureRoom;
	}
	public void setLectureRoom(String lectureRoom) {
		this.lectureRoom = lectureRoom;
	}

	public Set<String> getTeacherNames() {
		return teacherNames;
	}

	public void setTeacherNames(Set<String> teacherNames) {
		this.teacherNames = teacherNames;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
