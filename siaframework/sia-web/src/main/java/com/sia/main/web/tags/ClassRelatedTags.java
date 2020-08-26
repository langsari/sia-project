package com.sia.main.web.tags;

public class ClassRelatedTags {

	public static boolean instanceOf(Object object, String className) {
		boolean returnValue;

		try {
			returnValue = Class.forName(className).isInstance(object);
		} catch (ClassNotFoundException e) {
			returnValue = false;
		}

		return returnValue;
	}

}
