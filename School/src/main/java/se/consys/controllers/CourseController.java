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

import org.hibernate.Session;
import se.consys.Entities.Course;
import se.consys.Entities.Lecture;
import se.consys.Entities.Student;
import se.consys.Entities.Teacher;
import se.consys.Utilities.HibernateUtility;
import se.consys.Utilities.LocalDateParam;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.services.GenericService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseController {
	
	private Session session = HibernateUtility.getSessionFactory().openSession();
	private GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
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
	public Response update(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("null") @QueryParam("name") String courseName,
			@DefaultValue("-1") @QueryParam("duration") int durationInMonths,
			@DefaultValue("") @QueryParam("end") LocalDateParam endDate)
//			@DefaultValue("null") @QueryParam("start") LocalDate startDate,
//			@DefaultValue("null") @QueryParam("timestamp") LocalDateTime timeStamp,
//			@DefaultValue("null") @QueryParam("lectures") Map<LocalDateTime, Lecture> scheduledLectures,
//			@DefaultValue("null") @QueryParam("students") List<Student> students,
//			@DefaultValue("null") @QueryParam("supervisor") Teacher supervisor)
			{
		Course courseToBeUpdated = (Course) courseService.findById(id);	
		if (courseName != null) courseToBeUpdated.setCourseName(courseName);
		if (durationInMonths != -1) courseToBeUpdated.setDurationInMonths(durationInMonths);
		if (endDate != null && !endDate.getLocalDate().equals(LocalDate.MIN)) courseToBeUpdated.setEndDate(endDate.getLocalDate());
//		if (startDate != null) courseToBeUpdated.setStartDate(startDate);
//		if (timeStamp != null) courseToBeUpdated.setTimeStamp(timeStamp);
//		
//		//relational stuff
//		if (scheduledLectures != null) courseToBeUpdated.setScheduledLectures(scheduledLectures);
//		if (students != null) courseToBeUpdated.setStudents(students);
//		if (supervisor != null) courseToBeUpdated.setSupervisor(supervisor);
		
		courseService.update(courseToBeUpdated);
		return Response.status(200).entity(courseToBeUpdated).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@DefaultValue("0") @PathParam("id") int id, Course entity) {
		try {
			System.out.println("testing");
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
