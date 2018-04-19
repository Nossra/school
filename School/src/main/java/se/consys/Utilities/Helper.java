package se.consys.Utilities;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

public class Helper {
	
	public static <T> Method findSetter(T entity, Class<?> referenceType) {
		
		try {
			BeanInfo info = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			Method setter = null;
			for(PropertyDescriptor prop : properties) {
				if(prop.getPropertyType().equals(referenceType)) {
					setter = prop.getWriteMethod();
					break;
				}
			}
			if(setter == null)
				throw new NoSuchElementException(referenceType.getName() + " has not been found in " + entity.getClass().getName());
			return setter;
		} catch(Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
	}
	
}
