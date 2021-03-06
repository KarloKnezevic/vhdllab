/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.service.workspace;

import hr.fer.zemris.vhdllab.entity.File;
import hr.fer.zemris.vhdllab.entity.PreferencesFile;
import hr.fer.zemris.vhdllab.entity.Project;
import hr.fer.zemris.vhdllab.service.hierarchy.Hierarchy;
import hr.fer.zemris.vhdllab.util.EntityUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;

public final class Workspace implements Serializable {

    private static final long serialVersionUID = -2455644044788201509L;

    private final Map<Project, ProjectMetadata> projectMetadata;
    private transient List<Project> projects;
    private final Set<File> predefinedFiles;
    private final List<PreferencesFile> preferencesFiles;

    public Workspace(Map<Project, ProjectMetadata> projectMetadata,
            Set<File> predefinedFiles, List<PreferencesFile> preferencesFiles) {
        Validate.notNull(projectMetadata, "Project metadata can't be null");
        Validate.notNull(predefinedFiles, "Predefined files can't be null");
        Validate.notNull(preferencesFiles, "Preferences files can't be null");
        this.projectMetadata = projectMetadata;
        EntityUtils.setNullFiles(this.projectMetadata.keySet());
        this.predefinedFiles = EntityUtils.cloneFiles(predefinedFiles);
        this.preferencesFiles = EntityUtils.cloneFiles(preferencesFiles);
    }

    public int getProjectCount() {
        return projectMetadata.size();
    }

    public List<Project> getProjects() {
        if (projects == null) {
            projects = new ArrayList<Project>(projectMetadata.keySet());
        }
        return projects;
    }

    public void addProject(Project project) {
        Validate.notNull(project, "Project can't be null");
        projectMetadata.put(project, new ProjectMetadata(project));
        if (projects != null) {
            int index = projects.indexOf(project);
            if(index == -1) {
                projects.add(project);
            } else {
                projects.set(index, project);
            }
        }
    }

    public void removeProject(Project project) {
        Validate.notNull(project, "Project can't be null");
        projectMetadata.remove(project);
        if (projects != null) {
            projects.remove(project);
        }
    }

    public void addFile(File file, Hierarchy hierarchy) {
        Validate.notNull(file, "File can't be null");
        ProjectMetadata metadata = getMetadata(file.getProject());
        Validate.notNull(metadata, "Project must be opened:"
                + file.getProject());
        metadata.addFile(file);
        metadata.setHierarchy(hierarchy);
    }

    public void removeFile(File file, Hierarchy hierarchy) {
        Validate.notNull(file, "File can't be null");
        ProjectMetadata metadata = getMetadata(file.getProject());
        Validate.notNull(metadata, "Project must be opened:"
                + file.getProject());
        metadata.removeFile(file);
        metadata.setHierarchy(hierarchy);
    }

    public Hierarchy getHierarchy(Project project) {
        ProjectMetadata metadata = getMetadata(project);
        if (metadata == null) {
            return null;
        }
        return metadata.getHierarchy();
    }

    public Set<File> getFiles(Project project) {
        ProjectMetadata metadata = getMetadata(project);
        if (metadata == null) {
            return null;
        }
        return metadata.getFiles();
    }

    private ProjectMetadata getMetadata(Project project) {
        Validate.notNull(project, "Project can't be null");
        return projectMetadata.get(project);
    }

    public Set<File> getPredefinedFiles() {
        return predefinedFiles;
    }

    public List<PreferencesFile> getPreferencesFiles() {
        return preferencesFiles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(2000);
        sb.append("Project metadata:{\n");
        for (int i = 0; i < getProjectCount(); i++) {
            Project project = getProjects().get(i);
            ProjectMetadata metadata = getMetadata(project);
            sb.append("*** ").append(i).append(" = ").append(project);
            sb.append(" {\n");
            if(metadata != null) {
                sb.append(metadata);
            } else {
                sb.append("metadata=null\n");
            }
            sb.append("}\n");
        }
        sb.append("}//end metadata\nPredefined files:{");
        for (File predefined : getPredefinedFiles()) {
            sb.append(predefined.getName()).append(",");
        }
        sb.append("}\nPreferences files:{");
        for (PreferencesFile preferences : getPreferencesFiles()) {
            sb.append(preferences.getName()).append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
