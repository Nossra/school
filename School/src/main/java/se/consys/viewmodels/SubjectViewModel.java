package se.consys.viewmodels;

import java.util.Set;

public class SubjectViewModel {
	private int id;
	private String subjectName;
	private Set<String> qualifiedTeachers;
	
	public SubjectViewModel(int id, String subjectName, Set<String> qualifiedTeachers) {
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


	public Set<String> getQualifiedTeachers() {
		return qualifiedTeachers;
	}


	public void setQualifiedTeachers(Set<String> qualifiedTeachers) {
		this.qualifiedTeachers = qualifiedTeachers;
	}
}	
