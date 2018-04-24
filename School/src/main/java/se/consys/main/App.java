package se.consys.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import se.consys.Entities.Course;
import se.consys.Entities.Lecture;
import se.consys.Entities.Student;
import se.consys.Entities.Subject;
import se.consys.Entities.Teacher;
import se.consys.Utilities.HibernateUtility;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.services.GenericService;

public class App {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		Session session = HibernateUtility.getSessionFactory().openSession();
		
//		GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Teacher.class));
//		GenericService studentService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Student.class));
//		GenericService lectureService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Lecture.class));
//		GenericService subjectService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Subject.class));
//		GenericService courseService = GenericService.getGenericService(new DaoGenericHibernateImpl<>(Course.class));
		LocalDateTime ldt = LocalDateTime.MAX;
//		String a = "2018-12-01T12:42:01";
//		
//		ldt = LocalDateTime.parse(a);
//		System.out.println(ldt);
//		
//		
		String studentString = "1-2-12-5-3-71";
		String[] seperatedIds = studentString.split("-");
		List<Integer> students = new ArrayList<Integer>();
		for (int i = 0; i < seperatedIds.length; i++) {
			students.add((int) Integer.parseInt(seperatedIds[i]));
		}
		
		for (int s : students) {
			System.out.println(s);
		}
				
	} 

}
