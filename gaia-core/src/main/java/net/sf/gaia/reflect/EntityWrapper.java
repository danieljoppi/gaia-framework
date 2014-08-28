package net.sf.gaia.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.gaia.util.SafeUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe wrapper para atribuir e capturar os campos de uma classe.
 * 
 * @author daniel.joppi
 *
 * @param <E> tipo da classe.
 */
public class EntityWrapper<E> {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EntityWrapper.class);

	private E object;
	
	private Class<?> clazz;
	
	public EntityWrapper(E object) {
		this.object = object;
		this.clazz = object.getClass();
	}

	/**
	 * Método que captura o campo field da classe <E>. Tamém é permitido
	 * capturar o campo de uma outra classe que estaja ligada a <E>, exemplo: 
	 * Na classe GaiaUser tem o campo "group" que é do tipo GaiaGroup. Para
	 * capturar o nome do grupo basta passar no campo field "group.name" que a
	 * engine irá resolve-lo.
	 * 
	 * @param <T>
	 *            tipo da classe de retorno.
	 * @param field
	 *            string com o campo da classe que deseja buscar.
	 * @return valor do campo field. E null caso não achar o campo.
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String field) {
		if (SafeUtils.safeIsNull(field)) {
			throw new NullPointerException("Field is empty on class " + clazz.getName());
		}
		
		Class<?> clazz = this.clazz;
		String[] fields = field.split("\\.");
		try {
			Object obj = findObject(field);
			String filedObj = fields[fields.length-1];
			clazz = obj.getClass();

			String methodName = SafeUtils.safeGetterName(filedObj, clazz);
			Method m = createMethod(clazz, methodName);
			Object o = m.invoke(obj, new Object[] { });
			
			return (T) o;
		} catch (Exception e) {
			throw new IllegalArgumentException("Field " + field + " not found on class " + clazz.getName(), e);
		}
	}

	/**
	 * Método que atribui o campo field da classe <E>. Tamém é permitido
	 * atribuir o campo de uma outra classe que estaja ligada a <E>, exemplo: 
	 * Na classe GaiaUser tem o campo "group" que é do tipo GaiaGroup. Para
	 * atribuir o nome do grupo basta passar no campo field "group.name" que a
	 * engine irá resolve-lo.
	 * 
	 * @param field
	 *            string com o campo da classe que deseja buscar.
	 * @param o
	 *            valor do objeto a ser atribuido.
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	public void setValue(String field, Object o) {
		if (SafeUtils.safeIsNull(field)) {
			throw new NullPointerException("Field is empty on class " + clazz.getName());
		}
		
		Class<?> clazz = this.clazz;
		String[] fields = field.split("\\.");
		try {
			Object obj = findObject(field);
			String filedObj = fields[fields.length-1];
			clazz = obj.getClass();

			Class<?> clazzMethod = o.getClass();
			String methodName = SafeUtils.safeSetterName(filedObj, clazz);
			for(Method m : clazz.getMethods()) {
				// busca a classe do paramentro correto para atribuir o valor
				if(methodName.equals(m.getName())) {
					clazzMethod = m.getParameterTypes()[0];
					break;
				}
			}
			
			Method m = createMethod(clazz, methodName, clazzMethod);
			if (clazzMethod.isAssignableFrom(o.getClass())) {
				m.invoke(obj, new Object[] { o });
			} else {
				m.invoke(obj, new Object[] { this.convertClass(o, clazzMethod) });
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Field " + field + " not found on class " + clazz.getName(), e);
		}
	}
	
	/**
	 * Método que transfroma o objeto atual e numa nova instância da classe
	 * toClazz. Mas somente se nessa nova classe possuir um construtor com 
	 * o objeto atual como parametro.
	 * 
	 * @param atual instância do objeto a ser convertido.
	 * @param toClazz nova classe que o objeto atual será convertido.
	 * @return nova instância do objeto atual da classe toClazz.
	 * @throws Exception
	 * 
	 * @author daniel.joppi
	 * @since 06/02/2011
	 */
	public Object convertClass(Object atual, Class<?> toClazz) throws Exception {
		Constructor<?> cons = null;

		for(Constructor<?> ct : toClazz.getConstructors()) {
			Class<?>[] pt = ct.getParameterTypes();
			
			if (pt.length == 1) {
				Class<?> clazz = pt[0];
				if(int.class.equals(pt[0])) {
					clazz = Integer.class;
				} else if(long.class.equals(pt[0])) {
					clazz = Long.class;
				} else if(short.class.equals(pt[0])) {
					clazz = Short.class;
				} else if(float.class.equals(pt[0])) {
					clazz = Float.class;
				} else if(double.class.equals(pt[0])) {
					clazz = Double.class;
				} else if(boolean.class.equals(pt[0])) {
					clazz = Boolean.class;
				}
				
				if (clazz.equals(atual.getClass())) {
					cons = ct;
					break;
				}
			}
		}
		
		if (cons == null) {
			cons = toClazz.getConstructor(new Class<?>[] { String.class });
			return cons.newInstance(""+atual);
		} else {
			return cons.newInstance(atual);
		}
	}

	/**
	 * Método que atribui null ao campo field da classe <E>. Tamém é permitido
	 * atribuir o campo de uma outra classe que estaja ligada a <E>, exemplo: 
	 * Na classe GaiaUser tem o campo "group" que é do tipo GaiaGroup. Para
	 * atribuir o nome do grupo basta passar no campo field "group.name" que a
	 * engine irá resolve-lo.
	 * 
	 * @param field
	 *            string com o campo da classe que deseja buscar.
	 * @param c
	 *            classe que o método set do objeto recebe.
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	public void setNullValue(String field, Class<?> c) {
		if (SafeUtils.safeIsNull(field)) {
			throw new NullPointerException("Field is empty on class " + clazz.getName());
		}
		
		Class<?> clazz = this.clazz;
		String[] fields = field.split("\\.");
		try {
			Object obj = findObject(field);
			String filedObj = fields[fields.length-1];
			clazz = obj.getClass();

			String methodName = SafeUtils.safeSetterName(filedObj, clazz);
			Method m = createMethod(clazz, methodName, c);
			m.invoke(obj, new Object[] { null });
		} catch (Exception e) {
			throw new IllegalArgumentException("Field " + field + " not found on class " + clazz.getName(), e);
		}
	}

	/**
	 * Método que cria os método de get e set.
	 * 
	 * @param clazz
	 *            classe do objeto que contêm o método.
	 * @param methodName
	 *            nome do método.
	 * @return instância do método localizado na classe clazz.
	 * @throws Exception
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	private Method createMethod(Class<?> clazz, String methodName) throws Exception {
		return createMethod(clazz, methodName, null);
	}

	/**
	 * Método que cria os método de get e set.
	 * 
	 * @param clazz
	 *            classe do objeto que contêm o método.
	 * @param methodName
	 *            nome do método.
	 * @param param
	 *            classe de parametro para o método. Caso null não é passado
	 *            nenhum parametro para o método.
	 * @return instância do método localizado na classe clazz.
	 * @throws Exception
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	private Method createMethod(Class<?> clazz, String methodName, Class<?> param) throws Exception {
		try {
			Class<?>[] params = (param==null) ?  new Class<?>[] { } : new Class<?>[] { param };
			return clazz.getMethod(methodName, params);
		} catch (Exception e) {
			if (Object.class.equals(param)) {
				throw e;
			} else {
				try {
					return createMethod(clazz, methodName, param.getSuperclass());
				} catch (NoSuchMethodException nsme) {
					throw new IllegalArgumentException(e.getMessage(),nsme);
				}
			}
		}
	}

	/**
	 * Método que localiza o objeto que irá ser manipulado pe wrapper.
	 * 
	 * @param field
	 *            string com o campo da classe que deseja buscar.
	 * @return instância do objeto que contêm o campo field.
	 * @throws Exception
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	private Object findObject(String field) throws Exception {
		return findObject(field, this.object);
	}

	/**
	 * Método que localiza o objeto que irá ser manipulado pe wrapper.
	 * 
	 * @param field
	 *            string com o campo da classe que deseja buscar.
	 * @param obj
	 *            instância do objeto atual da busca. É o que será usado para
	 *            ver se contêm ou não o campo field.
	 * @return instância do objeto que contêm o campo field.
	 * @throws Exception
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Object findObject(String field, Object obj) throws Exception {
		String[] s = field.split("\\.");
		if (s.length == 1) {
			return obj;
		} else if (s.length > 1) {
			String filedObj = s[0];
			String newField = field.replace(filedObj+".", "");
			Class<?> clazz = obj.getClass();
			
			// se o campo for um array
			// converte para um tipo lista
			if(clazz.isArray()) {
				obj = Arrays.asList(obj);
				clazz = List.class;
			}
			
			// se campo é uma lista de objetos
			if(Collection.class.isAssignableFrom(clazz)) {
				Collection<Object> collection = (Collection<Object>) obj;
				List<Object> list = new ArrayList<Object>();
				
				for(Iterator<?> it = collection.iterator(); it.hasNext();) {
					Object aObj = it.next();
					Class<?> aClazz = obj.getClass();

					String methodName = SafeUtils.safeGetterName(filedObj, aClazz);
					Method m = createMethod(aClazz, methodName);
					Object newObj = m.invoke(aObj, new Object[] { });
					
					// se o retorno tamém for uma lista
					if(Collection.class.isAssignableFrom(newObj.getClass())) {
						list.addAll((Collection<?>)newObj);
					} else {
						list.add(newObj);
					}
				}
				
				return list;
			} else {
				String methodName = SafeUtils.safeGetterName(filedObj, clazz);
				Method m = createMethod(clazz, methodName);
				Object newObj = m.invoke(obj, new Object[] { });
				
				return findObject(newField, newObj);
			}
		}		
		return null;
	}

	/**
	 * Método que retorna a instâcnia do objeto manipulado pelo wrapper.
	 * 
	 * @return retorna a instâcnia do objeto manipulado pelo wrapper.
	 * 
	 * @author daniel.joppi
	 * @since 20/01/2011
	 */
	public E getObject() {
		return this.object;
	}
}
