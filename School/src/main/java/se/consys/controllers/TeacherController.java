package se.consys.controllers;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.services.GenericService;

@Path("teachers")
@Produces("application/json")
@Consumes("application/json")
public class TeacherController {
	
	GenericService teacherService;

	public TeacherController()  {
		this.teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	}
	
	@GET
	public Response getAllTeachers() {
		@SuppressWarnings("unchecked")
		List<Teacher> allTeachers = teacherService.findAll();
		return Response.ok(allTeachers).build();
	}
	
}