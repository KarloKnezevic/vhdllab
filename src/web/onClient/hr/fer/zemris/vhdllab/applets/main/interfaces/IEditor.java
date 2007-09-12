package hr.fer.zemris.vhdllab.applets.main.interfaces;

import hr.fer.zemris.vhdllab.applets.main.event.EditorListener;
import hr.fer.zemris.vhdllab.applets.main.model.FileContent;

/**
 * Interface that describes communication between MainApplet and instance that implements this interface.
 */
public interface IEditor {
	
	void addEditorListener(EditorListener l);
	void removeEditorListener(EditorListener l);
	void removeAllEditorListeners();
	EditorListener[] getEditorListeners();
	public boolean setModified(boolean flag);
	boolean isModified();
	
	/**
	 * Sets a FileContent that mostly represents internal format that is used in Editor.
	 * Note that {@linkplain hr.fer.zemris.vhdllab.constants.FileTypes#FT_VHDL_SOURCE} is also an
	 * "internal" format. This method is also used to initialize the component that uses internal
	 * format.
	 * @param content FileContent 
	 */
	void setFileContent(FileContent content);
	/**
	 * Returns an internal format.
	 * @return an internal format.
	 */
	String getData();
	String getProjectName();
	String getFileName();
	void setSavable(boolean flag);
	boolean isSavable();
	void setReadOnly(boolean flag);
	boolean isReadOnly();
	void highlightLine(int line);
	void setSystemContainer(ISystemContainer container);
	IWizard getWizard();
	void init();
	void dispose();
}
