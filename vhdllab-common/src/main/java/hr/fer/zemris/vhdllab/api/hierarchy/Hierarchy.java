package hr.fer.zemris.vhdllab.api.hierarchy;

import hr.fer.zemris.vhdllab.entities.Caseless;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents dependencies that exist in a certain project.
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
public final class Hierarchy implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * A name of a project for whom a hierarchy is built.
	 */
	private final Caseless projectName;
	/**
	 * A set of nodes for each file in a project.
	 */
	private final Map<Caseless, HierarchyNode> nodes;
	/**
	 * A set of files that aren't used by any other file.
	 */
	private final transient Set<Caseless> topLevelFiles;

	/**
	 * Constructs a hierarchy for specified project. <code>nodes</code>
	 * parameter must contains each node for every file in a project.
	 * 
	 * @param projectName
	 *            a name of a project for whom a hierarchy is built
	 * @param nodes
	 *            a set of nodes for each file in a project
	 * @throws NullPointerException
	 *             if either parameter is <code>null</code>
	 */
	public Hierarchy(Caseless projectName, Set<HierarchyNode> nodes) {
		if (projectName == null) {
			throw new NullPointerException("Project name cant be null");
		}
		if (nodes == null) {
			throw new NullPointerException("Top level nodes cant be null");
		}
		this.projectName = projectName;
		this.nodes = new HashMap<Caseless, HierarchyNode>(nodes.size());
		this.topLevelFiles = new HashSet<Caseless>(nodes.size());
		for (HierarchyNode n : nodes) {
			this.topLevelFiles.add(n.getFileName());
		}
		for (HierarchyNode n : nodes) {
			HierarchyNode clone = new HierarchyNode(n);
			this.nodes.put(clone.getFileName(), clone);
			for (Caseless name : clone.getDependencies()) {
				this.topLevelFiles.remove(name);
			}
		}
	}

	/**
	 * Returns a name of a project for whom a hierarchy is built.
	 * 
	 * @return a name of a project for whom a hierarchy is built
	 */
	public Caseless getProjectName() {
		return projectName;
	}

	/**
	 * Returns an unmodifiable set of files (their names) that are not used in
	 * any other file. Return value can never be <code>null</code> although it
	 * can be empty set (if a project doesn't have any files).
	 * 
	 * @return a set of files (their names) that are not used in any other file
	 */
	public Set<Caseless> getTopLevelFiles() {
		return Collections.unmodifiableSet(topLevelFiles);
	}

	/**
	 * Returns a direct dependencies for a specified file. Transitive
	 * dependencies will not be returned. Return value can never be
	 * <code>null</code> although it can be empty set. Returned set is
	 * unmodifiable.
	 * 
	 * @param name
	 *            a name of a file for whom to get dependencies
	 * @return a dependencies for a specified file
	 * @throws NullPointerException
	 *             if <code>name</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if specified file doesn't exist in a hierarchy
	 */
	public Set<Caseless> getDependenciesForFile(Caseless name) {
		if (name == null) {
			throw new NullPointerException("File name cant be null");
		}
		HierarchyNode node = nodes.get(name);
		if (node == null) {
			throw new IllegalArgumentException("No such file: " + name);
		}
		return node.getDependencies();
	}

	/**
	 * Returns all (direct or transitive) dependencies for a specified file.
	 * Return value can never be <code>null</code> although it can be empty
	 * set.
	 * 
	 * @param name
	 *            a name of a file for whom to get dependencies
	 * @return all (direct and transitive) dependencies for a specified file
	 * @throws NullPointerException
	 *             if <code>name</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if specified file doesn't exist in a hierarchy
	 */
	public Set<Caseless> getAllDependenciesForFile(Caseless name) {
		Set<Caseless> dependencies = new HashSet<Caseless>();
		resolveDependencies(name, dependencies);
		return dependencies;
	}

	/**
	 * Resolves all (direct or transitive) dependencies.
	 * 
	 * @param name
	 *            a name of a file
	 * @param dependencies
	 *            a set where to store found dependencies
	 * @throws NullPointerException
	 *             if <code>name</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if specified file doesn't exist in a hierarchy
	 */
	private void resolveDependencies(Caseless name, Set<Caseless> dependencies) {
		Set<Caseless> names = getDependenciesForFile(name);
		dependencies.addAll(names);
		for (Caseless n : names) {
			resolveDependencies(n, dependencies);
		}
	}

	/**
	 * Returns a number of files in a hierarchy.
	 * 
	 * @return a number of files in a hierarchy
	 */
	public int fileCount() {
		return nodes.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + projectName.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hierarchy))
			return false;
		final Hierarchy other = (Hierarchy) obj;
		return projectName.equals(other.projectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(15 + nodes.size() * 20);
		sb.append("Hierarchy: ").append(projectName).append(" {\n");
		for (HierarchyNode node : nodes.values()) {
			sb.append(node).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Make a defensive copy.
	 */
	private Object readResolve() {
		return new Hierarchy(projectName, new HashSet<HierarchyNode>(nodes
				.values()));
	}

}