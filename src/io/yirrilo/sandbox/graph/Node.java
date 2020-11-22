package io.yirrilo.sandbox.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

	private Integer name;
	private List<Integer> next = new ArrayList<>();
	private Integer branch;

	public Node(String nodeString) {
		
		String[] infos = nodeString.split(" ", 2);
		this.name = Integer.valueOf(infos[0]);
		if(infos.length == 2 ) {
			this.next.addAll(Arrays.stream(infos[1].split(" ")).map(Integer::valueOf).collect(Collectors.toList()));
		}
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public List<Integer> getNext() {
		return next;
	}

	public void setNext(List<Integer> next) {
		this.next = next;
	}

	public Integer getBranch() {
		return branch;
	}

	public void setBranch(Integer branch) {
		this.branch = branch;
	}

}
