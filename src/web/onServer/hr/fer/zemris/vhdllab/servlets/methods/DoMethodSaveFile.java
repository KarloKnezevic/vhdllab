package hr.fer.zemris.vhdllab.servlets.methods;

import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.servlets.ManagerProvider;
import hr.fer.zemris.vhdllab.servlets.RegisteredMethod;

import java.util.Properties;

/**
 * This class represents a registered method for "save file" request.
 * 
 * @author Miro Bezjak
 * @see MethodConstants#MTD_SAVE_FILE
 */
public class DoMethodSaveFile implements RegisteredMethod {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.servlets.RegisteredMethod#run(java.util.Properties, hr.fer.zemris.vhdllab.servlets.ManagerProvider)
	 */
	public Properties run(Properties p, ManagerProvider mprov) {
		VHDLLabManager labman = (VHDLLabManager)mprov.get("vhdlLabManager");
		String method = p.getProperty(MethodConstants.PROP_METHOD);
		String fileID = p.getProperty(MethodConstants.PROP_FILE_ID,null);
		String content = p.getProperty(MethodConstants.PROP_FILE_CONTENT,null);
		if(fileID == null) return errorProperties(method,MethodConstants.SE_METHOD_ARGUMENT_ERROR,"No file ID specified!");
		if(content == null) return errorProperties(method,MethodConstants.SE_METHOD_ARGUMENT_ERROR,"No file content specified!");
		
		// Save file
		try {
			Long id = Long.parseLong(fileID);
			labman.saveFile(id, content);
		} catch (NumberFormatException e) {
			return errorProperties(method,MethodConstants.SE_PARSE_ERROR,"Unable to parse file ID = '"+fileID+"'!");
		} catch (Exception e) {
			return errorProperties(method,MethodConstants.SE_CAN_NOT_SAVE_FILE,"File ("+fileID+") could not be saved!");
		}
		
		// Prepare response
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_METHOD,method);
		resProp.setProperty(MethodConstants.PROP_STATUS,MethodConstants.STATUS_OK);
		return resProp;
	}
	
	/**
	 * This method is called if error occurs.
	 * @param method a method that caused this error
	 * @param errNo error message number
	 * @param errorMessage error message to pass back to caller
	 * @return a response Properties
	 */
	private Properties errorProperties(String method, String errNo, String errorMessage) {
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_METHOD,method);
		resProp.setProperty(MethodConstants.PROP_STATUS,errNo);
		resProp.setProperty(MethodConstants.PROP_STATUS_CONTENT,errorMessage);
		return resProp;
	}
}