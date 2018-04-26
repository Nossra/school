package se.consys.params;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.consys.Entities.Lecture;

public class MapHelper {
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
