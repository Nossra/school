package se.consys.viewmodels;

import java.util.Set;

public class SubjectViewModel {
	private int id;
	private String subjectName;
	private Set<TeacherViewModel> qualifiedTeachers;
	
	public SubjectViewModel(int id, String subjectName, Set<TeacherViewModel> qualifiedTeachers) {
		this.setId(id);
		this.setSubjectName(subjectName);
		this.setQualifiedTeachers(qualifiedTeachers);
	}
	

	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public Set<TeacherViewModel> getQualifiedTeachers() {
		return qualifiedTeachers;
	}


	public void setQualifiedTeachers(Set<TeacherViewModel> qualifiedTeachers) {
		this.qualifiedTeachers = qualifiedTeachers;
	}
}	
