package se.consys.controllers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
import se.consys.Utilities.HibernateUtility;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.LocalDateParam;
import se.consys.params.LocalDateTimeParam;
import se.consys.params.MapHelper;
import se.consys.services.GenericService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SuppressWarnings("rawtypes, unchecked")
@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseController {
	
	private GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
	private GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	private GenericService studentService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Student.class));
	private String noCourseFoundMsg = "No course found with the specified id.";
	
	
	@GET
	public Response getAll() {
		List<Course> courses = courseService.findAll();
		return Response.ok(courses).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Course course = (Course) courseService.findById(id);
			return Response.ok().entity(course).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg);
			return Response.status(204).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	public Response create(Course entity) {
		courseService.create(entity);
		return Response.status(201).entity(entity).build();
	}
	
	@PATCH
	@Path("/{id}")
	public Response partialUpdate(@DefaultValue("0") @PathParam("id") int id, 

			@DefaultValue("null") @QueryParam("name") String courseName,
			@DefaultValue("-1") @QueryParam("duration") int durationInMonths,
			@DefaultValue("") @QueryParam("end") LocalDateParam endDate,
			@DefaultValue("") @QueryParam("start") LocalDateParam startDate,
			@DefaultValue("") @QueryParam("timestamp") LocalDateTimeParam timeStamp,
//			@DefaultValue("null") @QueryParam("lectures") Map<LocalDateTimeParam, String> scheduledLectures,
			@DefaultValue("-1") @QueryParam("supervisor") int supervisor)
			{
		Course courseToBeUpdated = (Course) courseService.findById(id);	
		System.out.println(courseName);
		if (courseName != null) courseToBeUpdated.setCourseName(courseName);
		if (durationInMonths != -1) courseToBeUpdated.setDurationInMonths(durationInMonths);
		if (endDate != null && !endDate.getLocalDate().equals(LocalDate.MIN)) courseToBeUpdated.setEndDate(endDate.getLocalDate());
		if (startDate != null && !startDate.getLocalDate().equals(LocalDate.MIN)) courseToBeUpdated.setStartDate(startDate.getLocalDate());
		if (timeStamp != null && !timeStamp.getLocalDateTime().equals(LocalDateTime.MIN)) courseToBeUpdated.setTimeStamp(timeStamp.getLocalDateTime());
		
		//relational stuff
//		if (scheduledLectures != null) courseToBeUpdated.setScheduledLectures(scheduledLectures);
		if (supervisor != -1) courseToBeUpdated.setSupervisor((Teacher) teacherService.findById(supervisor));
		
		courseService.update(courseToBeUpdated);
		return Response.status(200).build();
	}

//	PATCH into course/id/students?update and add a string of student id's separated with "-"
//	An example string entered could be "1-12-15-6-7" etc. These are split up and put into an array
//	that get parsed into a list of integers. These are compared to all the students in the database
//	and put into a new list that gets added into the course object.  
	@PATCH
	@Path("/{id}/students")
	public Response partialUpdateOnStudents(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("null") @QueryParam("update") String studentString) {
		String[] seperatedIds = studentString.split("-");
		List<Integer> studentIds = new ArrayList<Integer>();
		for (int i = 0; i < seperatedIds.length; i++) {
			studentIds.add((int) Integer.parseInt(seperatedIds[i]));
		}
		
		List<Student> allStudents = studentService.findAll();
		List<Student> StudentsToAddIntoCourse = new ArrayList<Student>();
		for (int i = 0; i < allStudents.size(); i++) {
			for(int j = 0; j < studentIds.size(); j++) {
				if (allStudents.get(i).getId() == studentIds.get(j)) {
					StudentsToAddIntoCourse.add(allStudents.get(i));
				}
			}
		}
		
		Course courseToBeUpdated = (Course) courseService.findById(id);
		if (studentString != null) courseToBeUpdated.setStudents(StudentsToAddIntoCourse);
		courseService.update(courseToBeUpdated);
		
		return Response.status(200).build();
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
			
			for (int i = 0; i < timestamps.size(); i++) {
				for (int j = 0; j < lectureIds.size(); j++) {
					//GET LECTURE FROM LECTURE ID AND USE THEM TOGETHER WITH THE TIMESTAMPS TO CREATE
					//SEPARATE HASHMAPS  TO PUT INTO THE DATABASE
				}
			}
			
		return Response.status(200).build();
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
			courseToBeUpdated.setSupervisor(entity.getSupervisor());
			courseToBeUpdated.setTimeStamp(entity.getTimeStamp());
			courseService.update(courseToBeUpdated);
			return Response.status(200).entity(entity).build();
		} catch (NoResultException e) {
			System.out.println(noCourseFoundMsg);
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
			System.out.println(noCourseFoundMsg);
			return Response.status(204).build();
		} 
	}
}
