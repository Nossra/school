package se.consys.Entities;

import java.io.Serializable;
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

import org.hibernate.annotations.Cascade;

@Entity
public class Subject implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String subjectName;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "Subject_Teacher",
		joinColumns = @JoinColumn(name="Subject_Id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="Teacher_Id", referencedColumnName="id"))
	private Set<Teacher> qualifiedTeachers;
	
	public Subject() {}
	
	public Subject(String subjectName) {
		this.setSubjectName(subjectName);
	}
	
	public Set<Teacher> getQualifiedTeachers() {
		return qualifiedTeachers;
	}
	public void setQualifiedTeachers(Set<Teacher> qualifiedTeachers) {
		this.qualifiedTeachers = qualifiedTeachers;
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
}
