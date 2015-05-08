/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 7, 2015: Created
 *
 */
public class LangUtils {

	
	private static Set<String> LANG_CLASSES = null;
	
	private static Set<String> PRIMITIVE_TYPES = new HashSet<String>();
	
	static {
		PRIMITIVE_TYPES.add("boolean");
		PRIMITIVE_TYPES.add("char");
		PRIMITIVE_TYPES.add("byte");
		PRIMITIVE_TYPES.add("short");
		PRIMITIVE_TYPES.add("int");
		PRIMITIVE_TYPES.add("long");
		PRIMITIVE_TYPES.add("float");
		PRIMITIVE_TYPES.add("double");
	}
	
	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ZipException 
	 */
	public static void main(String[] args) throws ZipException, IOException, URISyntaxException
	{
		// TODO Auto-generated method stub
		getLangPkgClasses();
	}


	public synchronized static Set<String> getLangPkgClasses() throws ZipException, IOException, URISyntaxException {
		
		if(LANG_CLASSES == null) {
			String path = Object.class.getResource("Object.class").getPath().replace("!/java/lang/Object.class", "");
			ZipFile z = new ZipFile(new File(new URI(path)));
			Enumeration<? extends ZipEntry> e =z.entries();
			LANG_CLASSES = new HashSet<String>();
			while(e.hasMoreElements()) {
				String n = e.nextElement().getName();
				if (n.matches("java/lang/\\w+\\.class")) {
					LANG_CLASSES.add(n);
				}
			}
			
		}
		return LANG_CLASSES;
	}
	
	public static boolean isClassFromLangPackage(String classSimpleName) throws Exception {
		String classQName = "java/lang/" + classSimpleName + ".class";
		return getLangPkgClasses().contains(classQName);
	}
	
	public static boolean isPrimitiveType(String type) {
		return PRIMITIVE_TYPES.contains(type);
	}
	
}
