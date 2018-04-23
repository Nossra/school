package se.consys.controllers;

import java.util.List;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.services.GenericService;

@Path("teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherController {
	
	private GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	
	@GET
	public Response get() {
		@SuppressWarnings("unchecked")
		List<Teacher> allTeachers = teacherService.findAll();
		return Response.ok(allTeachers).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Teacher teacherToBeReturned = (Teacher) teacherService.findById(id);
			return Response.ok().entity(teacherToBeReturned).build();
		} catch (NoResultException e) {
			System.out.println("No result found when calling teacher with the specified ID.");
			return Response.status(204).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	public Response create(Teacher entity) {
		teacherService.create(entity);
		return Response.status(201).entity(entity).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@DefaultValue("0") @PathParam("id") int id, Teacher entity) {
		try {
			Teacher teacherToBeUpdated = (Teacher) teacherService.findById(id);
			teacherToBeUpdated.setEmail(entity.getEmail());
			teacherToBeUpdated.setFirstName(entity.getFirstName());
			teacherToBeUpdated.setLastName(entity.getLastName());
			teacherToBeUpdated.setPassword(entity.getPassword());
			teacherToBeUpdated.setPhoneNumber(entity.getPhoneNumber());
			
			//relational stuff
			teacherToBeUpdated.setLectures(entity.getLectures());
			teacherToBeUpdated.setQualifications(entity.getQualifications());
			teacherToBeUpdated.setSupervisedCourses(entity.getSupervisedCourses());
			
			teacherService.update(teacherToBeUpdated);
			return Response.status(200).entity(entity).build();
		} catch (NoResultException e) {
			System.out.println("No result found when calling teacher with the specified ID.");
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
			System.out.println("No result found when calling teacher with the specified ID.");
			return Response.status(204).build();
		} 
	}	
}
