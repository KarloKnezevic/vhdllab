package hr.fer.zemris.vhdllab.applets.schema2.model;

import hr.fer.zemris.vhdllab.applets.schema2.enums.EErrorTypes;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ICommandResponse;
import hr.fer.zemris.vhdllab.applets.schema2.misc.InfoMap;
import hr.fer.zemris.vhdllab.applets.schema2.misc.SchemaError;



public class CommandResponse implements ICommandResponse {
	private SchemaError error;
	private boolean success;
	private InfoMap info;
	
	/**
	 * Defaultni konstruktor
	 * pretpostavlja da nije doslo do greske
	 * i da je komanda uspjela.
	 */
	public CommandResponse() {
		error = null;
		success = true;
		info = new InfoMap();
	}
	
	/**
	 * Postavlja se uspjesnost zahtjeva.
	 * Za neuspjesan zahtjev error dobiva
	 * UNKNOWN_TYPE tip.
	 * 
	 * @param isSuccessful
	 */
	public CommandResponse(boolean isSuccessful) {
		success = isSuccessful;
		error = (success) ? (null) : (new SchemaError(EErrorTypes.UNKNOWN_TYPE));
		info = new InfoMap();
	}
	
	/**
	 * Postavlja se odredeni error.
	 * 
	 * @param error
	 * Ako se za error specificira null,
	 * isCommandSuccessful() ce biti true.
	 * Ako se specificira neki error,
	 * onda ce isCommandSuccessful() biti false;
	 */
	public CommandResponse(SchemaError responseError) {
		success = (responseError == null) ? (true) : (false);
		error = responseError;
		info = new InfoMap();
	}

	public SchemaError getError() {
		return error;
	}

	public InfoMap getInfoMap() {
		return info;
	}

	public boolean isCommandSuccessful() {
		return success;
	}
	

}




