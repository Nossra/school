package se.consys.controllers;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.consys.Entities.Lecture;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;

@Path("lectures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LectureController {
	DaoGenericHibernateImpl lectureService = new DaoGenericHibernateImpl<>(Lecture.class);
	@GET
	public Response get() {
		@SuppressWarnings("unchecked")
		List<Lecture> allLectures = lectureService.findAll();
		return Response.ok(allLectures).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Teacher lectureToBeReturned = (Teacher) lectureService.findById(id);
			return Response.ok().entity(lectureToBeReturned).build();
		} catch (NoResultException e) {
			System.out.println("No result found when calling teacher with the specified ID.");
			return Response.status(204).build();
		}
	}
}
