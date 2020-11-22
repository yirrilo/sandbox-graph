package io.yirrilo.sandbox.graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FindBranch {
	public static void main(String[] args) {
		
		Graph graph = new Graph();
		try {
			List<String> lines = Files.readAllLines(Path.of("demo.graph"));
			for (String nodeString : lines) {
				graph.addNode(new Node(nodeString));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graph.build();
		System.out.println(graph.getBranches());
	}
}
