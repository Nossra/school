package se.consys.controllers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.omg.PortableServer.RequestProcessingPolicyOperations;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import se.consys.Entities.Course;
import se.consys.Entities.Lecture;
import se.consys.Entities.Student;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.LocalDateParam;
import se.consys.params.LocalDateTimeParam;
import se.consys.params.MapHelper;
import se.consys.params.SetHelper;
import se.consys.services.GenericService;
import se.consys.viewmodels.CourseViewModel;
import se.consys.viewmodels.StudentViewModel;
import se.consys.viewmodels.TeacherViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("rawtypes, unchecked")
@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseController {
	
	private GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
	private GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	private GenericService studentService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Student.class));
	private GenericService lectureService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Lecture.class));

	private String noCourseFoundMsg = "No course found with the specified id: ";
	
	
	@GET
	public Response getAll() {
		List<Course> courses = courseService.findAll();
		List<CourseViewModel> coursesToReturn = new ArrayList<CourseViewModel>();
		for (int i = 0; i < courses.size(); i++) {
			CourseViewModel cvm = new CourseViewModel();
			cvm.setCourseName(courses.get(i).getCourseName());
			cvm.setDurationInMonths(courses.get(i).getDurationInMonths());
			cvm.setEndDate(courses.get(i).getEndDate());
			cvm.setId(courses.get(i).getId());
			cvm.setScheduledLectures(courses.get(i).getScheduledLectures());
			cvm.setStartDate(courses.get(i).getStartDate());
			cvm.setTimeStamp(courses.get(i).getTimeStamp());
			
			if (courses.get(i).getSupervisors() != null) {
				Set<TeacherViewModel> teachers = new HashSet<TeacherViewModel>();
				for (Teacher t : courses.get(i).getSupervisors()) {
					TeacherViewModel tvm = new TeacherViewModel(t.getId(), t.getFirstName(), t.getLastName(), t.getEmail(), t.getPhoneNumber(), t.getPassword());
					teachers.add(tvm);
				}
				cvm.setSupervisors(teachers);
			}
			
			if (courses.get(i).getStudents() != null) {
				List<StudentViewModel> students = new ArrayList<StudentViewModel>();
				for (int j = 0; j < courses.get(i).getStudents().size(); j++) {
					StudentViewModel svm = new StudentViewModel(
							courses.get(i).getStudents().get(j).getId(), 
							courses.get(i).getStudents().get(j).getFirstName(),
							courses.get(i).getStudents().get(j).getLastName(), 
							courses.get(i).getStudents().get(j).getEmail(),
							courses.get(i).getStudents().get(j).getPassword());
					students.add(svm);
				}
				cvm.setStudents(students);
			}
			coursesToReturn.add(cvm);
		}
		
		return Response.ok(coursesToReturn).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Course course = (Course) courseService.findById(id);

			CourseViewModel cvm = new CourseViewModel();
			cvm.setCourseName(course.getCourseName());
			cvm.setDurationInMonths(course.getDurationInMonths());
			cvm.setEndDate(course.getEndDate());
			cvm.setId(course.getId());
			cvm.setScheduledLectures(course.getScheduledLectures());
			cvm.setStartDate(course.getStartDate());
			cvm.setTimeStamp(course.getTimeStamp());
			
			Set<TeacherViewModel> teachers = new HashSet<TeacherViewModel>();
			if (course.getSupervisors() != null) {
				for (Teacher t : course.getSupervisors()) {
					TeacherViewModel tvm = new TeacherViewModel(t.getId(), t.getFirstName(), t.getLastName(), t.getEmail(), t.getPhoneNumber(), t.getPassword());
					teachers.add(tvm);
				}
			}
			cvm.setSupervisors(teachers);
			
			List<StudentViewModel> students = new ArrayList<StudentViewModel>();
			if (course.getStudents() != null) {
				for (int i = 0; i < course.getStudents().size(); i++) {
					StudentViewModel svm = new StudentViewModel(
							course.getStudents().get(i).getId(), 
							course.getStudents().get(i).getFirstName(),
							course.getStudents().get(i).getLastName(), 
							course.getStudents().get(i).getEmail(),
							course.getStudents().get(i).getPassword());
					students.add(svm);
				}
			}
			cvm.setStudents(students);
			
			return Response.ok().entity(cvm).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg + id);
			return Response.status(204).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	public Response create(Course entity) {
		courseService.create(entity);
		return Response.status(201).entity(entity).build();
	}
	
//	PATCH into course/id and add a string of student, lectures or teacher id's separated with "-"
//	An example string entered could be "1-12-15-6-7" etc. These are split up and put into an array
//	that get parsed into a list of integers. Using the sethelper class. These are compared to all
//	the students in the database and put into a new list that gets added into the course object.  
	@PATCH
	@Path("/{id}")
	public Response partialUpdate(@DefaultValue("0") @PathParam("id") int id, 

			@DefaultValue("") @QueryParam("name") String courseName,
			@DefaultValue("-1") @QueryParam("duration") int durationInMonths,
			@DefaultValue("") @QueryParam("end") LocalDateParam endDate,
			@DefaultValue("") @QueryParam("start") LocalDateParam startDate,
			@DefaultValue("") @QueryParam("timestamp") LocalDateTimeParam timeStamp,
			@DefaultValue("") @QueryParam("teachers") String teacherString,
			@DefaultValue("") @QueryParam("students") String studentString,
			@DefaultValue("") @QueryParam("lectures") String lectureString)
			{
		try {
			Course courseToBeUpdated = (Course) courseService.findById(id);	
			System.out.println(courseName);
			if (!courseName.equals("")) courseToBeUpdated.setCourseName(courseName);
			if (durationInMonths != -1) courseToBeUpdated.setDurationInMonths(durationInMonths);
			if (endDate != null && !endDate.getLocalDate().equals(LocalDate.MIN)) courseToBeUpdated.setEndDate(endDate.getLocalDate());
			if (startDate != null && !startDate.getLocalDate().equals(LocalDate.MIN)) courseToBeUpdated.setStartDate(startDate.getLocalDate());
			if (timeStamp!= null && !timeStamp.getLocalDateTime().equals(LocalDateTime.MIN)) courseToBeUpdated.setTimeStamp(timeStamp.getLocalDateTime());
			
			// Teachers
			if (!teacherString.equals("")) {
				List<Integer> teacherIds = SetHelper.separateIds(teacherString);
				Set<Teacher> teachers = new HashSet<Teacher>();
				for (int i = 0; i < teacherIds.size(); i++) {
					Teacher teacher = (Teacher) teacherService.findById(teacherIds.get(i));
					teachers.add(teacher);
				}
				courseToBeUpdated.setSupervisors(teachers);
			}
			
			// Students
			if (!studentString.equals("")) {
				List<Integer> studentIds = SetHelper.separateIds(studentString);
				List<Student> students = new ArrayList<Student>();
				for (int i = 0; i < studentIds.size(); i++) {
					Student student = (Student) studentService.findById(studentIds.get(i));
					students.add(student);
				}
				courseToBeUpdated.setStudents(students);
			}
			
			courseService.update(courseToBeUpdated);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg + id);
			return Response.status(204).build();
		}
	 }	
	
//	EXAMPLE mapString: "2018-12-01T12:42:01,1/2018-12-02T15:42:01,4/2018-12-03T17:30:00,3"
	@PATCH
	@Path("/{id}/lectures")
	public Response partialUpdateOnLectures(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("null") @QueryParam("update") String mapString) {
			
		String[][] separatedString = MapHelper.getScheduleStrings(mapString);
		List<LocalDateTime> timestamps = MapHelper.getTimestamps(separatedString);
		List<Integer> lectureIds = MapHelper.getLectureIds(separatedString);
		if (timestamps.size() != lectureIds.size()) {
			System.out.println("The mapString was incorrect as there weren't the same amount of ids as there were timestamps.");
			return Response.status(400).build();
		} else {
			Map<LocalDateTime, Lecture> scheduledLectures = new HashMap<LocalDateTime, Lecture>();
			for (int i = 0; i < timestamps.size(); i++) {
				//GET LECTURE FROM LECTURE ID AND USE THEM TOGETHER WITH THE TIMESTAMPS TO CREATE
				//SEPARATE HASHMAPS  TO PUT INTO THE DATABASE
				try {
					Lecture lecture = (Lecture) lectureService.findById(lectureIds.get(i));
					scheduledLectures.put(timestamps.get(i), lecture);
				} catch(NoResultException e) {
					System.out.println("No result found for lecture id: " + lectureIds.get(i));
					return Response.status(204).build();
				}				
			}
			
			try {
				Course courseToBeUpdated = (Course) courseService.findById(id);
				courseToBeUpdated.setScheduledLectures(scheduledLectures);
				courseService.update(courseToBeUpdated);
				return Response.status(200).build();
			} catch (NoResultException e) {
				System.out.println(noCourseFoundMsg + id);
				return Response.status(204).build();
			}
		}	
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@DefaultValue("0") @PathParam("id") int id, Course entity) {
		try {
			Course courseToBeUpdated = (Course) courseService.findById(id);
			courseToBeUpdated.setCourseName(entity.getCourseName());
			courseToBeUpdated.setDurationInMonths(entity.getDurationInMonths());
			courseToBeUpdated.setEndDate(entity.getEndDate());
			courseToBeUpdated.setScheduledLectures(entity.getScheduledLectures());
			courseToBeUpdated.setStartDate(entity.getStartDate());
			courseToBeUpdated.setStudents(entity.getStudents());
			courseToBeUpdated.setTimeStamp(entity.getTimeStamp());
			courseService.update(courseToBeUpdated);
			return Response.status(200).entity(entity).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg + id);
			return Response.ok().status(204).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Course courseToBeDeleted = (Course) courseService.findById(id);
			courseService.delete(courseToBeDeleted);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg + id);
			return Response.status(204).build();
		} 
	}
}
