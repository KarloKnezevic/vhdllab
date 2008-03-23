package hr.fer.zemris.vhdllab.server.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import hr.fer.zemris.vhdllab.api.FileTypes;

import org.junit.Ignore;
import org.junit.Test;

/**
 * A test case for {@link ServerConfParser}.
 * 
 * @author Miro Bezjak
 */
public class LibrariesConfParserTest {

	/**
	 * Test getConfiguration.
	 */
	@Test
	public void getConfiguration() throws Exception {
		FileTypeMapping m;
		ServerConf expectedConf = new ServerConf();
		m = new FileTypeMapping(FileTypes.VHDL_SOURCE);
		m.setGenerator("hr.fer.zemris.vhdllab.service.generators.SourceGenerator");
		expectedConf.addFileTypeMapping(m);
		m = new FileTypeMapping(FileTypes.VHDL_TESTBENCH);
		m.setGenerator("hr.fer.zemris.vhdllab.service.generators.TestbenchGenerator");
		expectedConf.addFileTypeMapping(m);
		m = new FileTypeMapping(FileTypes.VHDL_SCHEMA);
		m.setGenerator("hr.fer.zemris.vhdllab.service.generators.SchemaGenerator");
		expectedConf.addFileTypeMapping(m);
		m = new FileTypeMapping(FileTypes.VHDL_AUTOMATON);
		m.setGenerator("hr.fer.zemris.vhdllab.service.generators.AutomatonGenerator");
		expectedConf.addFileTypeMapping(m);
		m = new FileTypeMapping(FileTypes.PREFERENCES_USER);
		expectedConf.addFileTypeMapping(m);

		ServerConf conf = ServerConfParser.getConfiguration();
		System.out.println(expectedConf);
		System.out.println(conf);
		assertEquals("configurations not equal.", expectedConf, conf);
	}

	/**
	 * File type is case insensitive
	 */
	@Test
	public void getFileTypeMapping() throws Exception {
		ServerConf conf = ServerConfParser.getConfiguration();
		FileTypeMapping mapping = conf.getFileTypeMapping(FileTypes.VHDL_SOURCE
				.toUpperCase());
		assertNotNull("file type is case sensitive.", mapping);
	}

	@Ignore("must be tested by a user and this has already been tested")
	@Test
	public void asString() throws Exception {
		ServerConf conf = ServerConfParser.getConfiguration();
		System.out.println(conf);
	}

}