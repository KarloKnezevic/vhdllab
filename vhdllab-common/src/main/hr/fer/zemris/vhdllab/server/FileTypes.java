package hr.fer.zemris.vhdllab.server;

/**
 * Defines all file types that can be used (are recognized by server).
 * 
 * @author Miro Bezjak
 * @since 6/2/2008
 * @version 0.2
 */
public class FileTypes {

	/**
	 * Don't let anyone instantiate this class.
	 */
	private FileTypes() {
	}

	/**
	 * A vhdl source code file type.
	 */
	public static final String VHDL_SOURCE = "vhdl.source";

	/**
	 * A vhdl testbench file type.
	 */
	public static final String VHDL_TESTBENCH = "vhdl.testbench";

	/**
	 * A vhdl schema file type.
	 */
	public static final String VHDL_SCHEMA = "vhdl.schema";

	/**
	 * A vhdl automaton file type.
	 */
	public static final String VHDL_AUTOMATON = "vhdl.automaton";

	/**
	 * A user preferences file type.
	 */
	public static final String PREFERENCES_USER = "preferences.user";
	
	/**
	 * A global preferences file type.
	 */
	public static final String PREFERENCES_GLOBAL = "preferences.global";
	
}