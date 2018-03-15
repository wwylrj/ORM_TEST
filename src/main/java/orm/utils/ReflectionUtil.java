package orm.utils;


import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.asList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Title: 反射公用类</p>
 * <p>Description: </p>
 * @Author qizhao.pan
 * @Date 2017年1月16日 上午10:39:31
 * @Version V2.0 
 * 
 * 取值调用：
先获取字段类型
Field bizTypeField = ReflectionUtils.getFieldWithName(fromObject.getClass(), "biztype", false);
获取字段值
String bizType = ReflectionUtils.getFieldValue(fromObject, bizTypeField);

 */
public class ReflectionUtil {
	
	/**
	 * Creates an instance of the class with the given name. The class's no
	 * argument constructor is used to create an instance.
	 * 
	 * @param className
	 *            The name of the class, not null
	 * @param bypassAccessibility
	 *            If true, no exception is thrown if the parameterless
	 *            constructor is not public
	 * @return An instance of this class
	 * @throws RuntimeException
	 *             if the class could not be found or no instance could be
	 *             created
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T createInstanceOfType(String className, boolean bypassAccessibility) {
		try {
			Class<?> type = Class.forName(className);
			return (T) createInstanceOfType(type, bypassAccessibility);

		} catch (ClassCastException e) {
			throw new RuntimeException("Class " + className + " is not of expected type.", e);

		} catch (NoClassDefFoundError e) {
			throw new RuntimeException("Unable to load class " + className, e);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class " + className + " not found", e);

		} catch (RuntimeException e) {
			throw e;

		} catch (Exception e) {
			throw new RuntimeException("Error while instantiating class " + className, e);
		}
	}

	/**
	 * Creates an instance of the given type
	 * 
	 * @param <T>
	 *            The type of the instance
	 * @param type
	 *            The type of the instance
	 * @param bypassAccessibility
	 *            If true, no exception is thrown if the parameterless
	 *            constructor is not public
	 * @return An instance of this type
	 * @throws RuntimeException
	 *             If an instance could not be created
	 */
	public static <T> T createInstanceOfType(Class<T> type, boolean bypassAccessibility) {
		return createInstanceOfType(type, bypassAccessibility, new Class[0], new Object[0]);
	}

	/**
	 * Creates an instance of the given type
	 * 
	 * @param <T>
	 *            The type of the instance
	 * @param type
	 *            The type of the instance
	 * @param bypassAccessibility
	 *            If true, no exception is thrown if the parameterless
	 *            constructor is not public
	 * @param argumentTypes
	 *            The constructor arg types, not null
	 * @param arguments
	 *            The constructor args, not null
	 * @return An instance of this type
	 * @throws RuntimeException
	 *             If an instance could not be created
	 */
	public static <T> T createInstanceOfType(Class<T> type, boolean bypassAccessibility, Class[] argumentTypes,
			Object[] arguments) {

		if (type.isMemberClass() && !isStatic(type.getModifiers())) {
			throw new RuntimeException(
					"Creation of an instance of a non-static innerclass is not possible using reflection. The type "
							+ type.getSimpleName()
							+ " is only known in the context of an instance of the enclosing class "
							+ type.getEnclosingClass().getSimpleName()
							+ ". Declare the innerclass as static to make construction possible.");
		}
		try {
			Constructor<T> constructor = type.getDeclaredConstructor(argumentTypes);
			if (bypassAccessibility) {
				constructor.setAccessible(true);
			}
			return constructor.newInstance(arguments);

		} catch (InvocationTargetException e) {
			throw new RuntimeException("Error while trying to create object of class " + type.getName(), e.getCause());

		} catch (Exception e) {
			throw new RuntimeException("Error while trying to create object of class " + type.getName(), e);
		}
	}

	/**
	 * Gets the class for the given name. An RuntimeException is thrown when the
	 * class could not be loaded.
	 * 
	 * @param className
	 *            The name of the class, not null
	 * @return The class, not null
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassWithName(String className) {
		try {
			return (Class<T>) Class.forName(className);

		} catch (Throwable t) {
			throw new RuntimeException("Could not load class with name " + className, t);
		}
	}
	
	public static Field getFieldWithName(Class<?> clazz, String fieldName) {
		return getFieldWithName(clazz, fieldName, false);
	}
	
	/**
	 * 从给定的class里获取指定的字段属性
	 * @param clazz 类名，非空
	 * @param fieldName  字段属性名，非空
	 * @param isStatic  是否为静态类
	 * @return
	 */
	public static Field getFieldWithName(Class<?> clazz, String fieldName, boolean isStatic) {
		if (clazz == null || clazz.equals(Object.class)) {
			return null;
		}

		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			field = null;
		}

		if (field != null && isStatic(field.getModifiers()) == isStatic) {
			return field;
		}
		return getFieldWithName(clazz.getSuperclass(), fieldName, isStatic);
	}
	
	/**
	 * 获取类中字段值
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static <T> T getFieldValue(Object object, String fieldName) {
		Field field = getFieldWithName(object.getClass(), fieldName);
		if(field != null)
			return getFieldValue(object, field);
		else
			throw new RuntimeException("未找到该字段！");
	}
	
	/**
	 * Returns the value of the given field (may be private) in the given object
	 * 
	 * @param object
	 *            The object containing the field, null for static fields
	 * @param field
	 *            The field, not null
	 * @return The value of the given field in the given object
	 * @throws RuntimeException
	 *             if the field could not be accessed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object object, Field field) {
		try {
			field.setAccessible(true);
			return (T) field.get(object);

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error while trying to access field " + field, e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error while trying to access field " + field, e);
		}
	}
	
	public static Object getFieldValue(String field, Object obj) {
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getClass();
		Field idField = null;
		Object id = null;
		try {
			idField = clazz.getDeclaredField(field);
			idField.setAccessible(true);
			id = idField.get(obj);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Sets the given value to the given field on the given object
	 * 
	 * @param object
	 *            The object containing the field, not null
	 * @param field
	 *            The field, not null
	 * @param value
	 *            The value for the given field in the given object
	 * @throws RuntimeException
	 *             if the field could not be accessed
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Unable to assign the value to field: " + field.getName()
					+ ". Ensure that this field is of the correct type. Value: " + value, e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error while trying to access field " + field, e);
		}
	}
	
	/**
	 * Sets the given value to the given field and setters on the given object.
	 * 
	 * @param object
	 *            The object containing the field and setters, not null
	 * @param fields
	 *            The fields, not null
	 * @param setterMethods
	 *            The setter methods, not null
	 * @param value
	 *            The value for the given field and setters in the given object
	 */
	public static void setFieldAndSetterValue(Object object, Set<Field> fields, Set<Method> setterMethods, Object value) {
		for (Field field : fields) {
			try {
				setFieldValue(object, field, value);

			} catch (RuntimeException e) {
				throw new RuntimeException("Unable to assign the value to field: " + field.getName()
						+ ". Ensure that this field is of the correct type.", e);
			}
		}
		for (Method method : setterMethods) {
			if (!isSetter(method)) {
				throw new RuntimeException("Method " + method.getName()
						+ " is expected to be a setter method, but is not.");
			}
			try {
				invokeMethod(object, method, value);

			} catch (RuntimeException e) {
				throw new RuntimeException("Unable to invoke method: " + object.getClass().getSimpleName() + "."
						+ method.getName()
						+ ". Ensure that this method has following signature: void myMethod(ValueType value).", e);

			} catch (InvocationTargetException e) {
				throw new RuntimeException("Unable to invoke method: " + object.getClass().getSimpleName() + "."
						+ method.getName() + ". Method has thrown an exception.", e.getCause());
			}
		}
	}
	
	/**
	 * Invokes the given method with the given parameters on the given target
	 * object
	 * 
	 * @param target
	 *            The object containing the method, not null
	 * @param method
	 *            The method, not null
	 * @param arguments
	 *            The method arguments
	 * @return The result of the invocation, null if void
	 * @throws RuntimeException
	 *             if the method could not be invoked
	 * @throws InvocationTargetException
	 *             If the called method throwed an exception
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T invokeMethod(Object target, Method method, Object... arguments)
			throws InvocationTargetException {
		try {
			method.setAccessible(true);
			return (T) method.invoke(target, arguments);

		} catch (ClassCastException e) {
			throw new RuntimeException("Unable to invoke method. Unexpected return type " + method, e);

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error while invoking method " + method, e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error while invoking method " + method, e);
		}
	}
	
	/**
	 * For each method, check if it can be a setter for an object of the given
	 * type. A setter is a method with the following properties:
	 * <ul>
	 * <li>Method name is > 3 characters long and starts with set</li>
	 * <li>The fourth character is in uppercase</li>
	 * <li>The method has one parameter, with the type of the property to set</li>
	 * </ul>
	 * 
	 * @param method
	 *            The method to check, not null
	 * @return True if the given method is a setter, false otherwise
	 */
	public static boolean isSetter(Method method) {
		String methodName = method.getName();
		if (methodName.length() > 3 && methodName.startsWith("set") && method.getParameterTypes().length == 1) {
			String fourthLetter = methodName.substring(3, 4);
			if (fourthLetter.toUpperCase().equals(fourthLetter)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the enum value that has the given name.
	 * 
	 * @param enumClass
	 *            The enum class, not null
	 * @param enumValueName
	 *            The name of the enum value, not null
	 * @return The actual enum value, not null
	 * @throws RuntimeException
	 *             if no value could be found with the given name
	 */
	public static <T extends Enum<?>> T getEnumValue(Class<T> enumClass, String enumValueName) {
		T[] enumValues = enumClass.getEnumConstants();
		for (T enumValue : enumValues) {
			if (enumValueName.equalsIgnoreCase(enumValue.name())) {

				return enumValue;
			}
		}
		throw new RuntimeException("Unable to find a enum value in enum: " + enumClass + ", with value name: "
				+ enumValueName);
	}
	
	/**
	 * Gets the method with the given name from the given class or one of its
	 * super-classes.
	 * 
	 * @param clazz
	 *            The class containing the method
	 * @param methodName
	 *            The name of the method, not null
	 * @param isStatic
	 *            True for a static method, false for non-static
	 * @param parameterTypes
	 *            The parameter types
	 * @return The method, null if no matching method was found
	 */
	public static Method getMethod(Class<?> clazz, String methodName, boolean isStatic, Class<?>... parameterTypes) {
		if (clazz == null || clazz.equals(Object.class)) {
			return null;
		}

		Method result;
		try {
			result = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			result = null;
		}
		if (result != null && isStatic(result.getModifiers()) == isStatic) {
			return result;
		}
		return getMethod(clazz.getSuperclass(), methodName, isStatic, parameterTypes);
	}
	
	/**
	 * Gets all methods of the given class and all its super-classes.
	 * 
	 * @param clazz
	 *            The class
	 * @return The methods, not null
	 */
	public static Set<Method> getAllMethods(Class<?> clazz) {
		Set<Method> result = new HashSet<Method>();
		if (clazz == null || clazz.equals(Object.class)) {
			return result;
		}

		// add all methods of this class
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isSynthetic() || declaredMethod.isBridge()) {
				// skip methods that were added by the compiler
				continue;
			}
			result.add(declaredMethod);
		}
		// add all methods of the super-classes
		result.addAll(getAllMethods(clazz.getSuperclass()));
		return result;
	}
	
	/**
	 * Gets all fields of the given class and all its super-classes.
	 * 
	 * @param clazz
	 *            The class
	 * @return The fields, not null
	 */
	public static Set<Field> getAllFields(Class<?> clazz) {
		Set<Field> result = new HashSet<Field>();
		if (clazz == null || clazz.equals(Object.class)) {
			return result;
		}

		// add all fields of this class
		Field[] declaredFields = clazz.getDeclaredFields();
		result.addAll(asList(declaredFields));
		// add all fields of the super-classes
		result.addAll(getAllFields(clazz.getSuperclass()));
		return result;
	}
	
	public static void copyFields(Object fromObject, Object toObject) {
		try {
			copyFields(fromObject.getClass(), fromObject, toObject);
		} catch (Exception e) {
			throw new RuntimeException("Unable to copy fields.", e);
		}
	}

	private static void copyFields(Class<?> clazz, Object fromObject, Object toObject) throws IllegalAccessException {
		if (clazz == null) {
			return;
		}
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			Object fromValue = field.get(fromObject);
			field.set(toObject, fromValue);
		}
		copyFields(clazz.getSuperclass(), fromObject, toObject);
	}
	
	public static void invokeSet(String field,Object value,Object obj){
        Class clazz=obj.getClass();
        try {
            Method method=clazz.getDeclaredMethod("set"+StringUtil.underlineToBigCamel(field),value.getClass());
            method.invoke(obj,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}



