package se.consys.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.consys.Entities.Course;
import se.consys.Entities.Lecture;
import se.consys.Entities.Subject;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.SetHelper;
import se.consys.services.GenericService;
import se.consys.viewmodels.LectureViewModel;
import se.consys.viewmodels.SubjectViewModel;
import se.consys.viewmodels.TeacherViewModel;

@Path("teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherController {
	
	private GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	private GenericService lectureService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Lecture.class));
	private GenericService subjectService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Subject.class));
	private GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
	String noTeacherFound = "No teacher was found with the specified id: ";
	
	@GET
	public Response get() {
		@SuppressWarnings("unchecked")
		List<Teacher> allTeachers = teacherService.findAll();
		List<TeacherViewModel> tvmListToReturn = new ArrayList<TeacherViewModel>();
		for (int i = 0; i < allTeachers.size(); i++) {
			TeacherViewModel tvm = new TeacherViewModel(
					allTeachers.get(i).getId(), 
					allTeachers.get(i).getFirstName(), 
					allTeachers.get(i).getLastName(), 
					allTeachers.get(i).getEmail(), 
					allTeachers.get(i).getPhoneNumber(), 
					allTeachers.get(i).getPassword());
			
			if (allTeachers.get(i).getQualifications() != null) {
				Set<String> subjectNames = new HashSet<String>();
				for (Subject subject : allTeachers.get(i).getQualifications()) {
					subjectNames.add(subject.getSubjectName());
				}
				tvm.setSubjectNames(subjectNames);
			}
			if (allTeachers.get(i).getSupervisedCourses() != null) {
				Set<String> courseNames = new HashSet<String>();
				for (Course courses : allTeachers.get(i).getSupervisedCourses()) {
					courseNames.add(courses.getCourseName());
				}
				tvm.setCourseNames(courseNames);
			}
			if (allTeachers.get(i).getLectures() != null) {
				Set<LectureViewModel> lecturesViewModelSet = new HashSet<LectureViewModel>();
				for (Lecture lectures : allTeachers.get(i).getLectures()) {
					LectureViewModel lvm = new LectureViewModel(
							lectures.getCourse().getCourseName(), 
							lectures.getTimeOfLecture(), 
							lectures.getLectureRoom());
					lecturesViewModelSet.add(lvm);
				}
				tvm.setLectures(lecturesViewModelSet);
			}
			tvmListToReturn.add(tvm);
		}
		return Response.ok(tvmListToReturn).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Teacher teacher = (Teacher) teacherService.findById(id);
			TeacherViewModel tvm = new TeacherViewModel(
					teacher.getId(), 
					teacher.getFirstName(),
					teacher.getLastName(),
					teacher.getEmail(),
					teacher.getPhoneNumber(), 
					teacher.getPassword());
			if (teacher.getLectures() != null) {
				Set<LectureViewModel> lvmSet = new HashSet<LectureViewModel>();
				for (Lecture lectures : teacher.getLectures()) {
					LectureViewModel lvm = new LectureViewModel(
							lectures.getCourse().getCourseName(),
							lectures.getTimeOfLecture(), 
							lectures.getLectureRoom());
					lvmSet.add(lvm);
				}
				tvm.setLectures(lvmSet);
			}
			if (teacher.getQualifications() != null) {
				Set<String> subjectNames = new HashSet<String>();
				for (Subject subjects : teacher.getQualifications()) {
					subjectNames.add(subjects.getSubjectName());
				}
				tvm.setSubjectNames(subjectNames);
			}
			if (teacher.getSupervisedCourses() != null) {
				Set<String> courseNames = new HashSet<String>();
				for (Course courses : teacher.getSupervisedCourses()) {
					courseNames.add(courses.getCourseName());
				}
				tvm.setCourseNames(courseNames);
			}
			
			return Response.ok().entity(tvm).build();
		} catch (NoResultException e) {
			System.out.println(noTeacherFound + id);
			return Response.status(204).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	public Response create(Teacher entity) {
		teacherService.create(entity);
		return Response.status(201).entity(entity).build();
	}
	
	@PATCH
	@Path("/{id}")
	public Response update(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("") @QueryParam("email") String email,
			@DefaultValue("") @QueryParam("firstname") String firstName,
			@DefaultValue("") @QueryParam("lastname") String lastName,
			@DefaultValue("") @QueryParam("pw") String passWord,
			@DefaultValue("") @QueryParam("phonenumber") String phoneNumber,
			@DefaultValue("") @QueryParam("lectures") String lectureString,
			@DefaultValue("") @QueryParam("qualifications") String qualificationsString,
			@DefaultValue("") @QueryParam("courses") String coursesString) {
		try {
			Teacher teacherToBeUpdated = (Teacher) teacherService.findById(id);
			TeacherViewModel tvm = new TeacherViewModel();
			if (!email.equals("")) teacherToBeUpdated.setEmail(email);
			if (!firstName.equals("")) teacherToBeUpdated.setFirstName(firstName);
			if (!lastName.equals("")) teacherToBeUpdated.setLastName(lastName);
			if (!passWord.equals("")) teacherToBeUpdated.setPassword(passWord);
			if (!phoneNumber.equals("")) teacherToBeUpdated.setPhoneNumber(phoneNumber);
			
			// Lectures
			if (!lectureString.equals("")) {
				List<Integer> lectureIds = SetHelper.separateIds(lectureString);
				Set<Lecture> lectures = new HashSet<Lecture>();
				for (int i = 0; i < lectureIds.size(); i++) {
					Lecture lecture = (Lecture) lectureService.findById(lectureIds.get(i));
					lectures.add(lecture);
				}
				teacherToBeUpdated.setLectures(lectures);
			}
			
			// Courses
			if (!coursesString.equals("")) {
				List<Integer> courseIds = SetHelper.separateIds(coursesString);
				Set<Course> courses = new HashSet<Course>();
				for (int i = 0; i < courseIds.size(); i++) {
					Course course = (Course) courseService.findById(courseIds.get(i));
					courses.add(course);
				}
				teacherToBeUpdated.setSupervisedCourses(courses);
			}
			
			// Qualifications
			if (!qualificationsString.equals("")) {
				List<Integer> qualificationIds = SetHelper.separateIds(qualificationsString);
				Set<Subject> subjects = new HashSet<Subject>();
				Set<String> subjectNames = new HashSet<String>();
				for (int i = 0; i < qualificationIds.size(); i++) {
					Subject subject = (Subject) subjectService.findById(qualificationIds.get(i));
					subjectNames.add(subject.getSubjectName());
					subjects.add(subject);
				}
				tvm.setSubjectNames(subjectNames);
				teacherToBeUpdated.setQualifications(subjects);
			}	
			
			tvm.setId(teacherToBeUpdated.getId());
			tvm.setFirstName(teacherToBeUpdated.getFirstName());
			tvm.setLastName(teacherToBeUpdated.getLastName());
			tvm.setEmail(teacherToBeUpdated.getEmail());
			tvm.setPhoneNumber(teacherToBeUpdated.getPhoneNumber());
			tvm.setPassword(teacherToBeUpdated.getPassword());
			
			for (Subject s : teacherToBeUpdated.getQualifications()) {
				System.out.println(s.getSubjectName());
			}
			
			teacherService.update(teacherToBeUpdated);
			return Response.status(200).entity(tvm).build();
		} catch (NoResultException e) {
			System.out.println("Something was not found with those ids.");
			return Response.status(204).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Teacher teacherToBeDeleted = (Teacher) teacherService.findById(id);
			teacherService.delete(teacherToBeDeleted);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println(noTeacherFound + id);
			return Response.status(204).build();
		} 
	}	
}
