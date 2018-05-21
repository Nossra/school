package se.consys.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.consys.Entities.Course;
import se.consys.Entities.Student;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.LocalDateParam;
import se.consys.params.LocalDateTimeParam;
import se.consys.params.SetHelper;
import se.consys.services.GenericService;
import se.consys.viewmodels.CourseViewModel;
import se.consys.viewmodels.StudentViewModel;

@Path("students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentController {
	
	private GenericService studentService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Student.class));
	private GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
	
	@GET
	public Response getAll() {
		List<Student> students = studentService.findAll();
		List<StudentViewModel> svmList = new ArrayList<StudentViewModel>();
		for (Student s : students) {
			StudentViewModel student = new StudentViewModel(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail(), s.getPassword());
			List<String> courses = new ArrayList<String>();
			for (int i = 0; i < s.getCourses().size(); i++) {
				String course = s.getCourses().get(i).getCourseName();
				courses.add(course);
			}
			student.setCourseNames(courses);
			svmList.add(student);
		}
		return Response.status(200).entity(svmList).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Student student = (Student) studentService.findById(id);
			StudentViewModel svm = new StudentViewModel(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());
			List<String> courseNames = new ArrayList<String>();
			for (Course c : student.getCourses()) {
				String courseName = c.getCourseName();
				courseNames.add(courseName);
			}
			svm.setCourseNames(courseNames);
			return Response.status(200).entity(svm).build();
		} catch (NoResultException e) {
			System.out.println("No student was found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
	@POST
	public Response create(Student entity) {
		studentService.create(entity);
		return Response.status(200).entity(entity).build();
	}
	
	@POST
	@Path("/{login}")
	public Response login(Student student) {
		boolean valid = studentService.Login(student.getEmail(), student.getPassword());
		if (valid) {
			return Response.status(200).build(); 
		} else {
			System.out.println("Credentials invalid, try again.");
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@DELETE
	public Response delete(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Student studentToDelete = (Student) studentService.findById(id);
			studentService.delete(studentToDelete);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println("No student was found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
	@PATCH
	@Path("/{id}")
	public Response patch(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("") @QueryParam("firstname") String firstName,
			@DefaultValue("") @QueryParam("lastname") String lastName,
			@DefaultValue("") @QueryParam("email") String email,
			@DefaultValue("") @QueryParam("pw") String password,
			@DefaultValue("") @QueryParam("courses") String coursesString) {
		try {
			Student student = (Student) studentService.findById(id);
			if (!firstName.equals("")) student.setFirstName(firstName);
			if (!lastName.equals("")) student.setLastName(lastName);
			if (!email.equals("")) student.setEmail(email);
			if (!password.equals("")) student.setPassword(password);
			StudentViewModel svm = new StudentViewModel(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());

//	THIS IS NOT AN OPTION AS LONG AS ITS NOT IN THE STUDENT ENTITY. STUDENTS SHOULD NOT
//	BE ABLE TO EDIT THEIR COURSES.
//			if (!coursesString.equals("")) {
//				try {
//					List<Integer> courseIds = SetHelper.separateIds(coursesString);
//					List<Course> courses = new ArrayList<Course>();
//					List<String> courseNames = new ArrayList<String>();
//					for (int i = 0; i < courseIds.size(); i++) {
//						Course course = (Course) courseService.findById(courseIds.get(i));
//						courseNames.add(course.getCourseName());
//						courses.add(course);
//					}
//					student.setCourses(courses);
//					svm.setCourseNames(courseNames);
//				} catch (NoResultException e) {
//					System.out.println("One or more courses were not found with those ids.");
//					return Response.status(204).build();
//				}
//			}
			studentService.update(student);
			return Response.status(200).entity(svm).build();
		} catch (NoResultException e) {
			System.out.println("No student was found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
}