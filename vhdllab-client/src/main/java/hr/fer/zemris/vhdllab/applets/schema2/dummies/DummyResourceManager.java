package hr.fer.zemris.vhdllab.applets.schema2.dummies;

import hr.fer.zemris.vhdllab.api.hierarchy.Hierarchy;
import hr.fer.zemris.vhdllab.api.results.CompilationResult;
import hr.fer.zemris.vhdllab.api.results.SimulationResult;
import hr.fer.zemris.vhdllab.api.results.VHDLGenerationResult;
import hr.fer.zemris.vhdllab.api.vhdl.CircuitInterface;
import hr.fer.zemris.vhdllab.applets.main.UniformAppletException;
import hr.fer.zemris.vhdllab.applets.main.event.VetoableResourceListener;
import hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DummyResourceManager implements IResourceManager {

	@Override
	public void addVetoableResourceListener(VetoableResourceListener l) {
		
	}

	@Override
	public CompilationResult compile(String projectName, String fileName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public boolean createNewProject(String projectName)
			throws UniformAppletException {
		return false;
	}

	@Override
	public boolean createNewResource(String projectName, String fileName,
			String type, String data) throws UniformAppletException {
		return false;
	}

	@Override
	public void deleteFile(String projectName, String fileName)
			throws UniformAppletException {
		
	}

	@Override
	public void deleteProject(String projectName) throws UniformAppletException {
		
	}

	@Override
	public boolean existsFile(String projectName, String fileName)
			throws UniformAppletException {
		return false;
	}

	@Override
	public boolean existsProject(String projectName)
			throws UniformAppletException {
		return false;
	}

	@Override
	public Hierarchy extractHierarchy(String projectName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public List<String> getAllCircuits(String projectName)
			throws UniformAppletException {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAllProjects() throws UniformAppletException {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAllTestbenches(String projectName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public CircuitInterface getCircuitInterfaceFor(String projectName,
			String fileName) throws UniformAppletException {
		return null;
	}

	@Override
	public String getFileType(String projectName, String fileName) {
		return null;
	}

	@Override
	public List<String> getFilesFor(String projectName)
			throws UniformAppletException {
		return null;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManagement#getPredefinedFileContent(java.lang.String)
	 */
	@Override
	public String getPredefinedFileContent(String fileName)
			throws UniformAppletException {
		if (!fileName.equals("predefined.xml")) throw new RuntimeException("Dummy only supports 'predefined.xml'.");
		
		InputStream stream = this.getClass().getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder("");
		String s;
		
		try {
			while ((s = reader.readLine()) != null) {
				sb.append(s);
				sb.append('\n');
			}
		} catch (IOException e) {
			return "";
		}
		
		return sb.toString();
	}

	@Override
	public VetoableResourceListener[] getVetoableResourceListeners() {
		return null;
	}

	@Override
	public boolean isCircuit(String projectName, String fileName) {
		return false;
	}

	@Override
	public boolean isCompilable(String projectName, String fileName) {
		return false;
	}

	@Override
	public boolean isCorrectEntityName(String name) {
		return false;
	}

	@Override
	public boolean isCorrectProjectName(String name) {
		return false;
	}

	@Override
	public boolean isSimulatable(String projectName, String fileName) {
		return false;
	}

	@Override
	public boolean isSimulation(String projectName, String fileName) {
		return false;
	}

	@Override
	public boolean isTestbench(String projectName, String fileName) {
		return false;
	}

	@Override
	public void removeAllVetoableResourceListeners() {
		
	}

	@Override
	public void removeVetoableResourceListener(VetoableResourceListener l) {
		
	}

	@Override
	public SimulationResult simulate(String projectName, String fileName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public VHDLGenerationResult generateVHDL(String projectName, String fileName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public String getFileContent(String projectName, String fileName)
			throws UniformAppletException {
		return null;
	}

	@Override
	public void saveFile(String projectName, String fileName, String content)
			throws UniformAppletException {
		
	}

	@Override
	public boolean isCorrectFileName(String name) {
		return false;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#canGenerateVHDLCode(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean canGenerateVHDLCode(String projectName, String fileName) {
		return false;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#saveErrorMessage(java.lang.String)
	 */
	@Override
	public void saveErrorMessage(String content) throws UniformAppletException {
		
	}

    @Override
    public boolean isPredefined(String projectName, String fileName) {
        // TODO Auto-generated method stub
        return false;
    }

}
