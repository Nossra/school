package se.consys.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import se.consys.Entities.Subject;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.SetHelper;
import se.consys.services.GenericService;
import se.consys.viewmodels.SubjectViewModel;
import se.consys.viewmodels.TeacherViewModel;

@Path("subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectController {
	
	private GenericService subjectService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Subject.class));
	private GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
	
	@GET
	public Response getAll() {
		List<Subject> subjects = subjectService.findAll();
		List<SubjectViewModel> svmList = new ArrayList<SubjectViewModel>();
		Set<String> teacherNames = new HashSet<String>();
		for (int i = 0; i < subjects.size(); i++) {
			for (Teacher t : subjects.get(i).getQualifiedTeachers()) {
				String teacherName = t.getFirstName() + " " + t.getLastName();
				teacherNames.add(teacherName);
			}
			SubjectViewModel svm = new SubjectViewModel(subjects.get(i).getId(), subjects.get(i).getSubjectName(), teacherNames);
			svmList.add(svm);
		}		
		return Response.status(200).entity(svmList).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Subject subject = (Subject) subjectService.findById(id);
			Set<String> teachers = new HashSet<String>();
			for (Teacher t : subject.getQualifiedTeachers()) {
				String teacher = t.getFirstName() + " " + t.getLastName();
				teachers.add(teacher);
			}
			SubjectViewModel svm = new SubjectViewModel(subject.getId(), subject.getSubjectName(), teachers);
			return Response.status(200).entity(svm).build();
		} catch (NoResultException e) {
			System.out.println("No subject found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
	@POST
	public Response post(Subject entity) {
		subjectService.create(entity);
		return Response.status(200).entity(entity).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Subject subject = (Subject) subjectService.findById(id);
			subjectService.delete(subject);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println("No subject found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
	@PATCH
	@Path("/{id}")
	public Response update(
			@DefaultValue("0") @PathParam("id") int id,
			@DefaultValue("") @QueryParam("subjectName") String subjectName,
			@DefaultValue("") @QueryParam("teachers") String teacherString) {
		try {
			Subject subject = (Subject) subjectService.findById(id);
			if (!subjectName.equals("")) subject.setSubjectName(subjectName);
			try {
				if (!teacherString.equals("")) {
					List<Integer> ids = SetHelper.separateIds(teacherString);
					Set<Teacher> teachers = new HashSet<Teacher>();
					for (int i = 0; i < ids.size(); i++) {
						Teacher t = (Teacher) teacherService.findById(ids.get(i));
						teachers.add(t);
					}
					subject.setQualifiedTeachers(teachers);
				} 
			} catch (NoResultException e) {
				System.out.println("No teachers found with those ids");
				return Response.status(204).build();
			}
			
			subjectService.update(subject);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println("No subject found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
}
