/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.utils;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

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

	private static Set<String> prjSrcClasses = new HashSet<String>();
	
	private static Set<String> libJarClasses = new HashSet<String>();

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
	public static void main(String[] args) throws IOException 
	{
		
		// TODO Auto-generated method stub
		//System.out.println(getLangPkgClasses());
		//System.out.println(getClassesOfOptPkgFromJarFile("file:/C:/Program%20Files/Java/jdk1.7.0_72/jre/lib/rt.jar", "java.lang"));
		//System.out.println(getlibJarClasses());
		
		/*
		File baseFile = new File("testinputdir");
		File childFile = new File("testinputdir/com/sample/Rest.java");
		System.out.println(baseFile.toURI().relativize(childFile.toURI()).getPath());
		*/
		Set<String> srcsPath = new HashSet<String>();
		srcsPath.add("testinputDir");
		String[] SRC_DIRS = new String[] { 
			"testinputdir"
		}; 
		System.out.println(getClassesFromProjectSrcDirs(SRC_DIRS));
		
	}

	public static Set<String> getlibJarClasses() {
		
		if(libJarClasses.isEmpty()) {
			try {
				List<String> libJars = FileUtils.readLines(new File("libjars.txt"));

				if(libJars != null && !libJars.isEmpty()) {
					for(String jarFile : libJars) {
						libJarClasses.addAll(getClassesOfOptPkgFromJarFile(jarFile, null));
					}
				}
			}
			catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		return libJarClasses;
	}

	
	public static Set<String> getLangPkgClasses() {
		
		if(LANG_CLASSES == null || LANG_CLASSES.isEmpty()) {
			String path = Object.class.getResource("Object.class").getPath().replace("!/java/lang/Object.class", "");
			System.out.println(path);
			//ZipFile z = new ZipFile(new File(new URI(path)));
			LANG_CLASSES = new HashSet<String>();
			LANG_CLASSES.addAll(getClassesOfOptPkgFromJarFile(path, "java.lang"));
		}
		return LANG_CLASSES;
	}
	
	public static Set<String> getClassesOfOptPkgFromJarFile(String zipPath, String pkg) {
		Set<String> classes = new HashSet<String>();

		ZipFile z = null;
		try {
			z = new ZipFile(new File(new URI(zipPath)));
			Enumeration<? extends ZipEntry> e =z.entries();
			String pkgFilterregEx = null;
			
			if(pkg != null) {
				pkgFilterregEx = pkg +"/\\w+\\.class";
			}
			while(e.hasMoreElements()) {
				String n = e.nextElement().getName();
				
				if(pkgFilterregEx != null) {
					if(n.matches(pkgFilterregEx)) {
						classes.add(n);	
					}				
				} else {
					classes.add(n);	
				}
			}
			z.close();
		}
		catch ( Exception e1 ) {
			e1.printStackTrace();
		}
		return classes;
	}
	
	public static boolean isClassFromLangPackage(String classSimpleName) {
		String classQName = "java/lang/" + classSimpleName + ".class";
		return getLangPkgClasses().contains(classQName);
	}
	
	public static Set<String> getClassesFromProjectSrcDirs(String[] srcsPath) {
		if(prjSrcClasses == null || prjSrcClasses.isEmpty()) {
			
			File[] files = new File[srcsPath.length];
			int i = 0;
			for(String srcDir : srcsPath) {
				files[i++] = new File(srcDir);
			}
			processSrcDir(files, null);
		}
		return prjSrcClasses;
	}
	
	public static void processSrcDir(File[] files, File rootSrcDir) {
		for (File file : files) {

			if(rootSrcDir == null) {
				rootSrcDir = file;
			}
			if (file.isDirectory()) {
				processSrcDir(file.listFiles(), rootSrcDir);
			} else if(file.getName().endsWith(".java")) {
				String projSrcRelFileName = rootSrcDir.toURI().relativize(file.toURI()).getPath();
				prjSrcClasses.add(projSrcRelFileName.replaceAll(".java", ".class"));
			}
		}
	}
	
	public static Set<String> resolveAsterickImport(String asterickImportLine) {
		Set<String> classes = new HashSet<String>();
		
		String pkgFromImportLine = asterickImportLine.substring(0, asterickImportLine.indexOf(".*"));
		
		//Search in project dir.
		
		
		
		return classes;
	}
	
	public static boolean isPrimitiveType(String type) {
		return PRIMITIVE_TYPES.contains(type);
	}
	
}
