package com.github.lyokofirelyte.WC.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.github.lyokofirelyte.WCAPI.WCUtils;

public class Utils extends WCUtils {
	
	public static void changeFinalStatic(Field field, Object value) throws Exception {
		
		Field mod = Field.class.getDeclaredField("modifiers");
		
		field.setAccessible(true);
		mod.setAccessible(true);
		mod.setInt(field, field.getModifiers() & ~ Modifier.FINAL);
		
		field.set(null, value);
		
	}
}