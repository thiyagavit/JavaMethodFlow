/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;

import com.sample.base.MethodCallStruct2;
import com.sample.base.MethodStruct2;
import com.sample.containers.ClassStructContainer2;
import com.sample.graphviz.GraphViz;
import com.sample.tree.GenericTree;
import com.sample.tree.GenericTreeNode;
import com.sample.utils.LangUtils;
import com.sample.visitors.CustomVisitor2;
import com.sample.visitors.MethodSigVisitor;

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
public class SourceParser2 {

	/**
	 * The dir. where temporary files will be created.
	 */
	private static final String TEMP_DIR = "temp";	

	/**
	 * Where is your dot program located? It will be called externally.
	 */
	private static final String DOT = "c:/Program Files/Graphviz2.38/bin/dot.exe";	
	
	private static final String OUTPUT_DIR = "callgraphs\\";
	
	
	private static final String[] SRC_DIRS = new String[] { 
			"testinputdir"
		}; 

	public static void main(String[] args) throws Exception {

		
		LangUtils.getLangPkgClasses();
		LangUtils.getlibJarClasses();
		LangUtils.getClassesFromProjectSrcDirs(SRC_DIRS);
		
		SourceParser2 parser = new SourceParser2();
		File[] files = new File[SRC_DIRS.length];
		int i = 0;

		for(String srcDir : SRC_DIRS) {
			files[i++] = new File(srcDir);

		}
		
		parser.processSrcDir(files, true);
		parser.processSrcDir(files, false);
		//System.out.println("************************************");
		//System.out.println(ClassStructContainer2.getInstance().toString());
		//System.out.println("************************************");
		String methodName = "com.sample.Test.printHello";
		parser.printMethodCallTreeForMethod(methodName);
	}

	public void processSrcDir(File[] files, boolean buildModelAlone) throws ParseException, IOException {
		for (File file : files) {

			if (file.isDirectory()) {
				processSrcDir(file.listFiles(), buildModelAlone);
			} else if(file.getName().endsWith(".java")) {        	
				processMethodCallTree(file, buildModelAlone);	
			}
		}
	}
	
	public void processMethodCallTree(File file, boolean buildModelAlone) throws ParseException, IOException {
		System.out.println("Processing java File " + file.getName() + " buildModelAlone mode : " + buildModelAlone);
		VoidVisitorAdapter visitor = buildModelAlone ? new MethodSigVisitor() : new CustomVisitor2();
		CompilationUnit cu = JavaParser.parse(file);

		// visit and print the methods names
		visitor.visit(cu, null);

	}		
	
	private void printMethodCallTreeForMethod(String methodQName) {
		GenericTree<String> tree = new GenericTree<String>();
		GenericTreeNode<String> dummyRoot = new GenericTreeNode<String>("start");
		constructTree(dummyRoot, methodQName);
		tree.setRoot(dummyRoot);		
		System.out.println(tree.toStringWithDepth());
		writeAsGraphVizFile(tree, methodQName);
	}
	
	//NOTE: GrpahViz must be installed in the system to generate call graph.
	public void writeAsGraphVizFile(GenericTree<String> tree, String methodQName) {
		String type = "gif";// can be dot, pdf, svg, png.
		GraphViz gv = new GraphViz(DOT, TEMP_DIR);
		
		//Create missing dirs.
		File tempDir = new File(TEMP_DIR);		
		if(!tempDir.exists()) {
			tempDir.mkdirs();
		}
		
		File outDir = new File(OUTPUT_DIR);		
		if(!outDir.exists()) {
			outDir.mkdirs();
		}
		
		
		gv.addln(gv.start_graph());

		buildWithDepth(gv, tree.getRoot());
		gv.addln(gv.end_graph());
		System.out.println(gv.getDotSource());
		String fileName = methodQName + Long.toString(System.currentTimeMillis()) + "." + type; 
		File out = new File(OUTPUT_DIR + fileName);
		gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}

	public void buildWithDepth(GraphViz gv, GenericTreeNode<String> node) {

		if(node.getChildren() != null && !node.getChildren().isEmpty()) {

			for(GenericTreeNode<String> childNode: node.getChildren()) {
				gv.addln("\"" + node.getData() + "\" -> \"" + childNode.getData() +"\";" );
				buildWithDepth(gv, childNode);
			}
		}

	}
	

	public void constructTree(GenericTreeNode<String> parentTreeNode, String methodName) {
		GenericTreeNode<String> node = new GenericTreeNode<String>(methodName);
		System.out.println("Adding child node [" + methodName + "]  to parent " + parentTreeNode.getData());
		parentTreeNode.addChild(node);
		
		MethodStruct2 m_struct = ClassStructContainer2.getInstance().getMatchingMethod(methodName, null, node, null);

		if(m_struct != null) {
			
			if(m_struct.getCalledMethods() != null && !m_struct.getCalledMethods().isEmpty()) {
				System.out.println("Method "+ methodName + " Called methods : " + m_struct.getCalledMethods().toString());
				
				for(MethodCallStruct2 called : m_struct.getCalledMethods()) {
					
					//Ignore cyclic method calls.
					if(!methodName.equals(called.getCalledMethod())) {
						constructTree(node, called.getCalledMethod());
					}
				}
			}
		}
	}
	

}
