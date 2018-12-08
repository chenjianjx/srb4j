package com.github.chenjianjx.srb4jfullsample.utils.lang;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * some methods that commons-lang doesn't have yet
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class MyLangUtils {

	public static Calendar newCalendar(long milis) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(milis);
		return c;
	}

	/**
	 * trim string fields to null. <br/>
	 * Note: only the root string fields are trimmed. string fields of the
	 * bean's composite fields are let alone. <br/>
	 * All reflection related security exceptions are ignored.
	 * 
	 * @param bean
	 */
	public static void trimRootLevelStringFields(Object bean) {
		if (bean == null) {
			return;
		}

		Field[] fields = bean.getClass().getDeclaredFields();
		if (fields == null) {
			return;
		}

		for (Field f : fields) {
			if (f.getType().isPrimitive()) {
				continue;
			}

			if (f.getType().equals(String.class)) {
				try {
					f.setAccessible(true);
					String value = (String) f.get(bean);
					f.set(bean, StringUtils.trimToNull(value));
				} catch (IllegalAccessException e) {
				}

			}
		}
	}

	/**
	 * it is null safe
	 * 
	 * @param dest
	 * @param src
	 */
	public static void copyProperties(Object dest, Object src) {
		if (dest == null || src == null) {
			return;
		}

		try {
			PropertyUtils.copyProperties(dest, src);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}

	}

	public static <DESTIN> DESTIN copyPropertiesToNewObject(
			Class<DESTIN> destClass, Object src) {
		if (src == null) {
			return null;
		}
		try {
			DESTIN destin = destClass.newInstance();
			copyProperties(destin, src);
			return destin;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * convert a collection of beans of type A, to a collection of beans of type
	 * B, by copying A's properties into B's
	 * 
	 * @param destClass
	 * @param srcCollection
	 * @return
	 */
	public static <DESTIN> List<DESTIN> copyPropertiesToNewCollections(
			Class<DESTIN> destClass, Collection<?> srcCollection) {
		List<DESTIN> destinList = new ArrayList<DESTIN>();
		if (srcCollection == null) {
			return destinList;
		}

		for (Object src : srcCollection) {
			destinList.add(copyPropertiesToNewObject(destClass, src));
		}

		return destinList;
	}

	public static byte[] toUtf8Bytes(String str) {
		try {
			return str.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

}
