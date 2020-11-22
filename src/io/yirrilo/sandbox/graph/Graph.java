package io.yirrilo.sandbox.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class Graph {

	private Map<Integer, Node> nodes = new HashMap<>();
	private Map<Integer, List<Integer>> targetToPrevious = new HashMap<>();
	private Map<Integer, List<Integer>> branches = new HashMap<>();

	public void addNode(Node node) {
		this.nodes.put(node.getName(), node);
		node.getNext().forEach(target -> addLink(target, node.getName()));
	}

	private void addLink(Integer target, Integer from) {
		List<Integer> froms = Optional.ofNullable(targetToPrevious.get(target)).orElse(new ArrayList<Integer>());
		froms.add(from);
		targetToPrevious.put(target, froms);
	}

	public void build() {
		Queue<Integer> branchStarters = new LinkedList<Integer>(findNodeWithoutNext());
		while (!branchStarters.isEmpty()) {
			Integer starter = branchStarters.poll();
			Node node = nodes.get(starter);
			if(node.getBranch()!=null) {
				
				continue;
			}
			Integer branchId = createBranch(starter);
			boolean continueBranch = true;
			while (continueBranch) {
				Node starterNode = nodes.get(starter);
				addNodeToBranch(starterNode, branchId);
				continueBranch = false;
				List<Integer> froms = targetToPrevious.get(starter);
				if(froms == null) {
					
				} else if (froms.size() > 1) {
					branchStarters.addAll(froms);
				} else if (froms.size() == 1) {
					Node previous = nodes.get(froms.get(0));
					if (previous.getNext().size() == 1) {
						continueBranch = true;
						starter = previous.getName();
					} else {
						branchStarters.add(froms.get(0));
					}
				}
			}
		}
	}

	private void addNodeToBranch(Node previous, Integer branchId) {
		previous.setBranch(branchId);
		branches.get(branchId).add(previous.getName());
	}

	private Integer createBranch(Integer starter) {
		Integer branchId = branches.keySet().size();
		nodes.get(starter).setBranch(branchId);
		branches.put(branchId, new ArrayList<>(List.of()));
		return branchId;
	}

	private List<Integer> findNodeWithoutNext() {
		return this.nodes.values().stream().filter(node -> node.getNext().isEmpty()).map(Node::getName)
				.collect(Collectors.toList());
	}

	public Collection<Integer> getLongestBranch() {
		List<Integer> retour = List.of();
		for (List<Integer> nodeFromBranch : this.branches.values()) {
			if(retour.size() < nodeFromBranch.size()) {
				retour = nodeFromBranch;
			}
		}
		return retour;
	}

	public Collection<List<Integer>> getBranches() {
		return branches.values();
	}

}
