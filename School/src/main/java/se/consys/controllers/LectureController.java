package se.consys.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import se.consys.Entities.Course;
import se.consys.Entities.Lecture;
import se.consys.Entities.Teacher;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.params.LocalDateTimeParam;
import se.consys.params.SetHelper;
import se.consys.viewmodels.LectureViewModel;

@Path("lectures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LectureController {
	
	DaoGenericHibernateImpl lectureService = new DaoGenericHibernateImpl<>(Lecture.class);
	DaoGenericHibernateImpl courseService = new DaoGenericHibernateImpl<>(Course.class);
	DaoGenericHibernateImpl teacherService = new DaoGenericHibernateImpl<>(Teacher.class);
	
	@GET
	public Response get() {
		@SuppressWarnings("unchecked")
		List<Lecture> allLectures = lectureService.findAll();
		List<LectureViewModel> lvmList = new ArrayList<LectureViewModel>();
		for (Lecture l : allLectures) {
			LectureViewModel lvm = new LectureViewModel(l.getId(), l.getCourse().getCourseName(), l.getTimeOfLecture(), l.getLectureRoom());
			Set<String> teacherNames = new HashSet<String>();
			for (Teacher t : l.getTeachers()) {
				String teacherName = t.getFirstName() + " " + t.getLastName();
				teacherNames.add(teacherName);
			}
			lvm.setTeacherNames(teacherNames);
			lvmList.add(lvm);
		}
		return Response.ok(lvmList).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		try {
			Lecture l = (Lecture) lectureService.findById(id);
			LectureViewModel lvm = new LectureViewModel(l.getId(), l.getCourse().getCourseName(), l.getTimeOfLecture(), l.getLectureRoom());
			Set<String> teacherNames = new HashSet<String>();
			for (Teacher t : l.getTeachers()) {
				String teacherName = t.getFirstName() + " " + t.getLastName();
				teacherNames.add(teacherName);
			}
			lvm.setTeacherNames(teacherNames);
			return Response.ok().entity(lvm).build();
		} catch (NoResultException e) {
			System.out.println("No result found when calling lecture with the specified ID.");
			return Response.status(204).build();
		}
	}
	
	@POST
	public Response create(Lecture entity) {
		lectureService.create(entity);
		return Response.status(200).entity(entity).build();
	}
	
	@PATCH
	@Path("/{id}")
	public Response update(
			@DefaultValue("-1") @PathParam("id") int id,
			@DefaultValue("") @QueryParam("timestamp") LocalDateTimeParam timeStamp,
			@DefaultValue("") @QueryParam("room") String room,
			@DefaultValue("") @QueryParam("teachers") String teacherString,
			@DefaultValue("-1") @	QueryParam("course") int courseId) {
		try {
			Lecture lectureToBeUpdated = (Lecture) lectureService.findById(id);
			if (timeStamp != null && !timeStamp.getLocalDateTime().equals(LocalDateTime.MIN)) lectureToBeUpdated.setTimeOfLecture(timeStamp.getLocalDateTime());
			if (!room.equals("")) lectureToBeUpdated.setLectureRoom(room);
			if (courseId != -1) {
				try {
					Course course = (Course) courseService.findById(courseId);
					lectureToBeUpdated.setCourse(course);
				} catch(NoResultException e) {
					System.out.println("No course found with the specified id: " + courseId);
					return Response.status(204).build();
				}
			}
			if (!teacherString.equals("")) {
				try {
					List<Integer> separatedIds = SetHelper.separateIds(teacherString);
					Set<Teacher> teachers = new HashSet<Teacher>();
					for (int i = 0; i < separatedIds.size(); i++) {
						Teacher teacher = (Teacher) teacherService.findById(separatedIds.get(i));
						teachers.add(teacher);
					}
					lectureToBeUpdated.setTeachers(teachers);
				} catch (NoResultException e) {
					System.out.println("One or more lectures were not found with the sent ids.");
					return Response.status(204).build();
				}
			}
			lectureService.update(lectureToBeUpdated);
			return Response.status(200).build();
		} catch(NoResultException e) {
			System.out.println("No lecture found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@DefaultValue("0") @PathParam("id") int id) {
		try {
			Lecture lectureToDelete = (Lecture) lectureService.findById(id);
			lectureService.delete(lectureToDelete);
			return Response.status(200).build();
		} catch (NoResultException e) {
			System.out.println("No lecture was found with the specified id: " + id);
			return Response.status(204).build();
		}
	}
	
}
