package hr.fer.zemris.vhdllab.applets.schema2.temporary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.PredefinedComponentsParser;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaInfo;
import hr.fer.zemris.vhdllab.applets.schema2.model.SchemaDeserializer;






public class Tester {

	/**
	 * Privremeno radi testiranja.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		testSD();
	}
	
	private static void testPredef() {
		PredefinedComponentsParser pcp = new PredefinedComponentsParser("configuration.xml");
		
		pcp.getConfiguration();
	}
	
	private static void testSD() {
		SchemaDeserializer sd = new SchemaDeserializer();
		FileInputStream fs = null;
		try {
			fs = new FileInputStream("d:/sample.xml");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(1);
		}
		
		ISchemaInfo info = sd.deserializeSchema(fs);
	}

}














