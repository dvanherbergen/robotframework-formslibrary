package org.robotframework.formslibrary.util;

import java.lang.reflect.Method;

import junit.framework.Assert;

/**
 * Adding frmall.jar to the java agent path prevents the application from
 * launching. As a workaround, we don't include the jar, but use reflection to
 * interact with the oracle forms objects.
 */
public class ObjectUtil {

    private static String cleanPath(String path) {

        if (path == null || path.trim().length() == 0) {
            return null;
        }

        return path.replaceAll("\\(", "").replaceAll("\\)", "");
    }

    public static boolean getBoolean(Object object, String methodName) {

        try {
            Method m = object.getClass().getMethod(cleanPath(methodName));
            return (Boolean) m.invoke(object);
        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }
        return false;
    }

    public static Object getNonNullResult(Object object, String methodName, String message) {

        Object result = invoke(object, cleanPath(methodName));
        if (result == null) {
            throw new RuntimeException(message + " - " + object.getClass().getName() + "." + methodName + " returned null. ");
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

        String path = cleanPath(methodPath);

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

    public static Object invoke(Object object, String methodName) {

        try {
            Method m = object.getClass().getMethod(cleanPath(methodName));
            return m.invoke(object);
        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }
        return null;

    }

    public static Object invokeMethodWithBoolArg(Object object, String methodName, boolean value) {

        try {

            Method m = object.getClass().getMethod(cleanPath(methodName), boolean.class);
            return m.invoke(object, value);

        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }
        return null;
    }

    public static void invokeMethodWithStringArg(Object object, String methodName, String value) {

        try {

            Method m = object.getClass().getMethod(cleanPath(methodName), String.class);
            m.invoke(object, value);

        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }

    }

    public static Object invokeMethodWithTypedArg(Object object, String methodName, String argType, Object value) {

        try {
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(cleanPath(methodName))) {
                    Class<?>[] pTypes = m.getParameterTypes();
                    if (pTypes != null && pTypes.length == 1 && (pTypes[0].toString().equals(argType) || pTypes[0].getName().equals(argType))) {
                        return m.invoke(object, value);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }
        return null;
    }

    public static Object invokeWithIntArg(Object object, String methodName, int value) {

        try {

            Method m = object.getClass().getMethod(cleanPath(methodName), int.class);
            return m.invoke(object, value);

        } catch (Exception e) {
            Logger.error(e);
            Assert.fail("Could not invoke method " + methodName);
        }
        return null;
    }

}
