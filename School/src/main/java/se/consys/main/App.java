package se.consys.main;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import se.consys.Entities.Course;
import se.consys.Entities.Student;
import se.consys.Entities.Teacher;
import se.consys.Utilities.HibernateUtility;
import se.consys.dataaccess.DaoGenericHibernateImpl;
import se.consys.services.GenericService;

public class App {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		
		GenericService teacherService = GenericService.getGenericService(new DaoGenericHibernateImpl(Teacher.class));
		GenericService studentService = GenericService.getGenericService(new DaoGenericHibernateImpl(Student.class));
		Teacher t = new Teacher();
//		t.setEmail("mart.nilss@gmail.com");
//		t.setFirstName("Martin");
//		t.setLastName("Nilsson");
//		t.setPhoneNumber("6546546");
		Student s = new Student();
		s.setFirstName("studenttest2");
		t.setFirstName("teachertest2");
		
		teacherService.create(s);
		studentService.create(t);
	
		
  
	} 

}
