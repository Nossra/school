package se.consys.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
//		LocalDateTime ldt = LocalDateTime.MAX;
//		String a = "2018-12-01T12:42:01";
//		
//		ldt = LocalDateTime.parse(a);
//		System.out.println(ldt);
//		
//		
//		String studentString = "1-2-12-5-3-71";
//		String[] seperatedIds = studentString.split("-");
//		List<Integer> students = new ArrayList<Integer>();
//		for (int i = 0; i < seperatedIds.length; i++) {
//			students.add((int) Integer.parseInt(seperatedIds[i]));
//		}
//		
//		for (int s : students) {
//			System.out.println(s);
//		}
//		
//		
//		Map<LocalDateTime, Lecture> scheduledLectures = new HashMap<LocalDateTime, Lecture>();
//		scheduledLectures.put(LocalDateTime.MAX, new Lecture());
//		scheduledLectures.get(LocalDateTime.MAX).setId(5);
//		System.out.println(scheduledLectures.get(LocalDateTime.MAX).getId());
		List<Integer> lectures = getLectureIds(App.getScheduleStrings("2018-12-01T12:42:01,1/2018-12-02T15:42:01,4/2018-12-03T17:30:00,3"));
		System.out.println("LECTURE IDS");
		for (Integer i : lectures) {
			System.out.println(i);
		}
		System.out.println();
		List<LocalDateTime> timestamps = getTimestamps(App.getScheduleStrings("2018-12-01T12:42:01,1/2018-12-02T15:42:01,4/2018-12-03T17:30:00,3"));
		System.out.println("TIMESTAMPS");
		for (LocalDateTime ldt : timestamps) {
			System.out.println(ldt);
		}
				
	} 
	
	public static String[][] getScheduleStrings(String string) {
		String[] separatedScheduleStrings = string.split("/");
		Map<LocalDateTime, Lecture> lectures = new HashMap<LocalDateTime, Lecture>();

		String[][] separatedTimeAndCourseIds = new String[separatedScheduleStrings.length][];
		for (int i = 0; i < separatedScheduleStrings.length; i++) {
			separatedTimeAndCourseIds[i] = separatedScheduleStrings[i].split(",");
		}
		
		return separatedTimeAndCourseIds;
	}
	
	public static List<Integer> getLectureIds(String[][] array) {
		List<String> lectureStrings = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j % 2 != 0) {
					lectureStrings.add(array[i][j]);
				}
			}			
		}
		List<Integer> lectureIds = new ArrayList<Integer>();
		for (int i = 0; i < lectureStrings.size(); i++) {
			lectureIds.add(Integer.parseInt(lectureStrings.get(i)));
		}
		return lectureIds;
	}
	
	public static List<LocalDateTime> getTimestamps(String[][] array) {
		List<String> times = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j % 2 == 0) {
					times.add(array[i][j]);
				}
			}			
		}
		List<LocalDateTime> timestamps = new ArrayList<LocalDateTime>();
		for (int i = 0; i < times.size(); i++) {
			timestamps.add(LocalDateTime.parse(times.get(i)));
		}
		return timestamps;
	}
	
	
}
