package hr.fer.zemris.vhdllab.applets.schema2.temporary;

import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.PredefinedComponentsParser;
import hr.fer.zemris.vhdllab.applets.main.interfaces.ProjectContainer;
import hr.fer.zemris.vhdllab.applets.main.model.FileContent;
import hr.fer.zemris.vhdllab.applets.schema2.dummies.DummyProjectContainer;
import hr.fer.zemris.vhdllab.applets.schema2.dummies.DummyWizard;
import hr.fer.zemris.vhdllab.applets.schema2.gui.SchemaMainPanel;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ICommand;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaInfo;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;
import hr.fer.zemris.vhdllab.applets.schema2.model.commands.AddWireCommand;
import hr.fer.zemris.vhdllab.applets.schema2.model.commands.ConnectWireCommand;
import hr.fer.zemris.vhdllab.applets.schema2.model.commands.ExpandWireCommand;
import hr.fer.zemris.vhdllab.applets.schema2.model.serialization.SchemaDeserializer;
import hr.fer.zemris.vhdllab.applets.schema2.model.serialization.SchemaSerializer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.JFrame;






public class Tester {

	/**
	 * Privremeno radi testiranja.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		testSchema();
	}
	
	private static void testSchema() {
		// test init
		SchemaMainPanel mpanel = new SchemaMainPanel();
		ProjectContainer dummypc = new DummyProjectContainer();
		mpanel.setProjectContainer(dummypc);
		mpanel.init();
		
		// test setFileContent
		DummyWizard wiz = new DummyWizard();
		FileContent fc = wiz.getInitialFileContent(null, "dummyProject");
		mpanel.setFileContent(fc);
		
		// create frame
		JFrame frame = new JFrame();
		
		frame.setLayout(new BorderLayout());
		frame.add(mpanel, BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(550, 400));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		
		
		ICommand addwire = new AddWireCommand(new Caseless("MyWire"), 50, 350, 450, 350);
		mpanel.getController().send(addwire);
		ICommand expandwire = new ExpandWireCommand(new Caseless("MyWire"), 50, 350, 50, 450);
		mpanel.getController().send(expandwire);
		expandwire = new ExpandWireCommand(new Caseless("MyWire"), 50, 350, 50, 250);
		mpanel.getController().send(expandwire);
		expandwire = new ExpandWireCommand(new Caseless("MyWire"), 150, 350, 150, 450);
		mpanel.getController().send(expandwire);
		ICommand connect = new ConnectWireCommand(new Caseless("SomeCompi"), new Caseless("MyWire"), new Caseless("some_port"));
		mpanel.getController().send(connect);
	}
	
	private static void testDummyWiz() {
		DummyWizard wiz = new DummyWizard();
		
		FileContent fc = wiz.getInitialFileContent(null, "dummyProject");
		
		System.out.println(fc.getContent());
	}
	
	private static void testPredef() {
		PredefinedComponentsParser pcp = new PredefinedComponentsParser("configuration.xml");
		
		pcp.getConfiguration();
	}
	
	private static void testSD() {
		SchemaDeserializer sd = new SchemaDeserializer();
		FileReader fr = null;
		ISchemaInfo info = null;
		
		try {
			fr = new FileReader("d:/sample.xml");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(1);
		}
		
		try {
			info = sd.deserializeSchema(fr);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Ok, parsed.");
		
		SchemaSerializer ss = new SchemaSerializer();
		PrintWriter pw = new PrintWriter(System.out);
		try {
			ss.serializeSchema(pw, info);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}














