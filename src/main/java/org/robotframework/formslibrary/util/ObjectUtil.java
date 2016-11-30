package org.robotframework.formslibrary.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.robotframework.formslibrary.FormsLibraryException;

/**
 * Adding frmall.jar (which contains all the Oracle Forms classes) to the java
 * agent path prevents the application from launching. As a workaround, we don't
 * include the jar, but use reflection to interact with the oracle forms
 * objects.
 */
public class ObjectUtil {

	/**
	 * Remove brackets () from method path.
	 */
	private static String cleanMethodPath(String methodPath) {

		if (methodPath == null || methodPath.trim().length() == 0) {
			return null;
		}
		return methodPath.replaceAll("\\(", "").replaceAll("\\)", "");
	}

	/**
	 * Invoke an object method that returns a boolean result.
	 */
	public static boolean getBoolean(Object object, String methodName) {

		try {
			Method m = object.getClass().getMethod(cleanMethodPath(methodName));
			return (Boolean) m.invoke(object);
		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}
	}

	/**
	 * Invoke an object method which should not return null.
	 */
	public static Object getNonNullResult(Object object, String methodName, String message) {

		Object result = invokeMethod(object, cleanMethodPath(methodName));
		if (result == null) {
			throw new FormsLibraryException(message + " - " + object.getClass().getName() + "." + methodName + " returned null. ");
		}
		return result;
	}

	/**
	 * Call an object method and return the result. The path can contain
	 * multiple levels, e.g. getContext.getFirstEntry.getText
	 * 
	 * @return string content or null if any object in the call graph is null.
	 */
	public static String getString(Object object, String methodPath) {

		String path = cleanMethodPath(methodPath);

		if (path == null) {
			return null;
		}

		String[] methodsToCall = new String[] { path };
		if (path.indexOf('.') != -1) {
			methodsToCall = path.split("\\.");
		}

		for (String methodName : methodsToCall) {

			try {
				Method m = object.getClass().getMethod(methodName);
				object = m.invoke(object);
			} catch (Exception e) {
				Logger.error(e);
				object = null;
				break;
			}
		}

		if (object != null) {
			return object.toString();
		}
		return null;
	}

	/**
	 * Invoke an object method.
	 */
	public static Object invokeMethod(Object object, String methodName) {

		try {
			Method m = object.getClass().getMethod(cleanMethodPath(methodName));
			return m.invoke(object);
		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}

	}

	/**
	 * Invoke an object method with a boolean arg.
	 */
	public static Object invokeMethodWithBoolArg(Object object, String methodName, boolean value) {

		try {

			Method m = object.getClass().getMethod(cleanMethodPath(methodName), boolean.class);
			return m.invoke(object, value);

		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}
	}

	/**
	 * Invoke an object method with a string arg.
	 */
	public static void invokeMethodWithStringArg(Object object, String methodName, String value) {

		try {

			Method m = object.getClass().getMethod(cleanMethodPath(methodName), String.class);
			m.invoke(object, value);

		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}

	}

	/**
	 * Invoke an object method with a specified argument type.
	 */
	public static Object invokeMethodWithTypedArg(Object object, String methodName, String argType, Object value) {

		try {
			Method[] methods = object.getClass().getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals(cleanMethodPath(methodName))) {
					Class<?>[] pTypes = m.getParameterTypes();
					if (pTypes != null && pTypes.length == 1 && (pTypes[0].toString().equals(argType) || pTypes[0].getName().equals(argType))) {
						return m.invoke(object, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}
		return null;
	}

	/**
	 * Invoke an object method with an int arg.
	 */
	public static Object invokeMethodWithIntArg(Object object, String methodName, int value) {

		try {

			Method m = object.getClass().getMethod(cleanMethodPath(methodName), int.class);
			return m.invoke(object, value);

		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}
	}

	/**
	 * Invoke an object method with a two int arguments
	 */
	public static Object invokeMethodWith2IntArgs(Object object, String methodName, int value1, int value2) {

		try {

			Method m = object.getClass().getMethod(cleanMethodPath(methodName), int.class, int.class);
			return m.invoke(object, value1, value2);

		} catch (Exception e) {
			throw new FormsLibraryException("Could not invoke method " + methodName, e);
		}
	}

	private static Field getField(Class<?> clazz, String fieldName) {
		Class<?> tmpClass = clazz;
		do {
			for (Field field : tmpClass.getDeclaredFields()) {
				String candidateName = field.getName();
				if (!candidateName.equals(fieldName)) {
					continue;
				}
				field.setAccessible(true);
				return field;
			}
			tmpClass = tmpClass.getSuperclass();
		} while (clazz != null);
		throw new FormsLibraryException("Unable to get field " + fieldName);
	}

	public static Object getField(Object object, String name) {
		if (name.indexOf(".") == -1) {
			Field field = getField(object.getClass(), name);
			field.setAccessible(true);
			try {
				return field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FormsLibraryException("Unable to access the field " + name + " from object " + object);
			}

		}
		String[] parts = name.split("\\.", 2);
		return getField(getField(object, parts[0]), parts[1]);

	}

	public static Object getFieldIfExists(Object object, String name) {
		if (name.indexOf(".") == -1) {
			Field field = getFieldIfExists(object.getClass(), name);
			if (field == null) {
				return null;
			}
			field.setAccessible(true);
			try {
				return field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FormsLibraryException("Unable to access the field " + name + " from object " + object);
			}

		}
		String[] parts = name.split("\\.", 2);
		return getFieldIfExists(getFieldIfExists(object, parts[0]), parts[1]);

	}

	private static Field getFieldIfExists(Class<?> clazz, String fieldName) {
		Class<?> tmpClass = clazz;
		do {
			for (Field field : tmpClass.getDeclaredFields()) {
				String candidateName = field.getName();
				if (!candidateName.equals(fieldName)) {
					continue;
				}
				field.setAccessible(true);
				return field;
			}
			tmpClass = tmpClass.getSuperclass();
		} while (tmpClass != null);
		return null;
	}

}
