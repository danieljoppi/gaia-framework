package net.sf.gaia.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe utilitária para validação de atribuições a objetos ou a tipos
 * primitivos e converte-los de string para um tipo definido.
 * 
 * @author daniel.joppi
 * 
 */
public abstract class SafeUtils {

	public static Long safeLong(String value) {
		if (value == null)
			return null;
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer safeInteger(String value) {
		if (value == null)
			return null;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Double safeDouble(String value) {
		if (value == null)
			return null;
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static String safeDateFormat(GregorianCalendar date) {
		return safeDateFormat(date, "dd/MM/yyyy HH:mm");
	}

	public static String safeDateFormat(GregorianCalendar date, String format) {
		if (safeIsNotNull(date, format)) {
			return safeDateFormat(date.getTime(), format);
		}
		return null;
	}

	public static String safeDateFormat(Date date, String format) {
		if (safeIsNotNull(date, format)) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		}
		return null;
	}

	public static boolean safeNotEquals(Object o1, Object o2) {
		return !safeEquals(o1, o2);
	}

	public static boolean safeEquals(Object o1, Object o2) {
		if (o1 == null)
			return o2 == null;
		else
			return o1.equals(o2);
	}

	public static boolean safeBoolean(Object obj) {
		if (obj == null || !(obj instanceof Boolean))
			return false;
		return ((Boolean) obj).booleanValue();
	}

	public static boolean safeBoolean(String obj) {
		if (obj == null)
			return false;
		String l = obj.toLowerCase();
		return l.startsWith("t") || l.startsWith("y") || l.startsWith("s");
	}

	public static String safeString(String value) {
		if (value == null || value.length() == 0 || "null".equals(value))
			return null;
		else
			return value;
	}

	public static String safeTrim(String value) {
		if (value == null || value.length() == 0 || "null".equals(value))
			return null;
		else
			return value.trim();
	}

	public static boolean safeIsNotNull(Object... objects) {
		for (int i = 0; i < objects.length; i++) {
			if (safeIsNull(objects[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método utilitário que verifica se a String passada é nulo e não vazia.
	 * 
	 * @param value instância de uma String.
	 * @return true se value for igual a null ou for vazia. E false se não for.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public static boolean safeIsNull(String value) {
		if (value == null || (value != null && value.trim().length() == 0))
			return true;
		else
			return false;
	}

	/**
	 * Método utilitário que verifica se o array de objetos passado é nulo.
	 * 
	 * @param objects array de instâncias de objetos Java.
	 * @return true se algum objeto do array for igual a null e false se não for.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public static boolean safeIsNull(Object... objects) {
		for (int i = 0; i < objects.length; i++) {
			if (!safeIsNull(objects[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método utilitário que verifica se o objeto passado é nulo.
	 * 
	 * @param object instância de um objeto Java.
	 * @return true se object for igual a null e false se não for.
	 * 
	 * @author daniel.joppi
	 * @since 11/01/2011
	 */
	public static boolean safeIsNull(Object object) {
		return object instanceof String ? safeIsNull((String) object) : object == null;
	}
	
	public static String safeGetterName(String name, Class<?> clazz) {
		String prefix = (Boolean.class.isAssignableFrom(clazz)) ? "is" : "get";
		if (name.startsWith(prefix)) {
			return name;
		} else {
			String methodName = prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
			return methodName;
		}
	}
	
	public static String safeSetterName(String name, Class<?> clazz) {
		String prefix = "set";
		if (name.startsWith(prefix)) {
			return name;
		} else {
			String methodName = prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
			return methodName;
		}
	}
}
