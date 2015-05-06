/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;

import com.sample.graphviz.GraphViz;
import com.sample.method.Method;
import com.sample.method.MethodCall;
import com.sample.method.MethodCallContainer;
import com.sample.tree.GenericTree;
import com.sample.tree.GenericTreeNode;
import com.sample.visitors.MethodVisitor;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    Apr 30, 2015: Created
 *
 */
public class MethodPrinter {

	/**
	 * The dir. where temporary files will be created.
	 */
	private static final String TEMP_DIR = "temp";	

	/**
	 * Where is your dot program located? It will be called externally.
	 */
	private static final String DOT = "c:/Program Files (x86)/Graphviz2.38/bin/dot.exe";	
	
	private static final String OUTPUT_DIR = "callgraphs\\";
	
	
	private static final String[] SRC_DIRS = new String[] { 
			"testinputdir"
		};  

	public static void main(String[] args) throws Exception {
		String methodToPrintTreeFor = "com.sample.Test.printHello";
		
		if(args != null && args.length == 1 && args[0] != null) {
			methodToPrintTreeFor = args[0];
		} 
		
		MethodPrinter printer = new MethodPrinter();
		File[] files = new File[SRC_DIRS.length];
		int i = 0;
		
		for(String srcDir : SRC_DIRS) {
			files[i++] = new File(srcDir);

		}		
		printer.processSrcDir(files);
		
		printer.printMethodCallTreeForMethod(methodToPrintTreeFor);
	}

	public void processMethodCallTree(File file) throws ParseException, IOException {
		System.out.println("Processing java File " + file.getName());
		MethodVisitor visitor = new MethodVisitor();
		CompilationUnit cu = JavaParser.parse(file);

		// visit and print the methods names
		visitor.visit(cu, null);

	}	

	public void processSrcDir(File[] files) throws ParseException, IOException {
		for (File file : files) {

			if (file.isDirectory()) {
				processSrcDir(file.listFiles());
			} else if(file.getName().endsWith(".java")) {        	
				processMethodCallTree(file);	
			}
		}
	}

	private void printMethodCallTreeForMethod(String method) {
		GenericTree<String> tree = new GenericTree<String>();
		GenericTreeNode<String> dummyRoot = new GenericTreeNode<String>("start");
		constructTree(dummyRoot, method);
		tree.setRoot(dummyRoot);
		writeAsGraphVizFile(tree, method);
		//System.out.println(tree.toStringWithDepth());
	}


	//NOTE: GrpahViz must be installed in the system to generate call graph.
	public void writeAsGraphVizFile(GenericTree<String> tree, String method) {
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
		String fileName = method + Long.toString(System.currentTimeMillis()) + "." + type; 
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
		parentTreeNode.addChild(node);
		MethodCall call = MethodCallContainer.getContainer().getMethodCalls().get(methodName);

		if(call != null) {
			if(call.getCalled() != null && !call.getCalled().isEmpty()) {

				for(Method called : call.getCalled()) {

					//Ignore cyclic method calls.
					if(!methodName.equals(called.getQualifiedName())) {
						constructTree(node, called.getQualifiedName());	
					}					
				}
			}			
		}
	}



}
