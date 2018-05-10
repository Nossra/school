package se.consys.params;

import java.util.ArrayList;
import java.util.List;

public class SetHelper {
	public static List<Integer> separateIds(String idString) {
		String[] separatedIds = idString.split("-");
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < separatedIds.length; i++) {
			ids.add(Integer.parseInt(separatedIds[i]));
		}
		return ids;		
	}
}
