package hr.fer.zemris.vhdllab.service.generator.automat;

import hr.fer.zemris.vhdllab.model.File;
import hr.fer.zemris.vhdllab.service.ServiceException;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.service.generator.IVHDLGenerator;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.xml.sax.SAXException;

public class VHDLGenerator implements IVHDLGenerator {
	protected String parsedVHDL;
	
	public VHDLGenerator(String data) {
		super();
		AUTParser aut=new AUTParser();
		try {
			aut.AUTParse(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		LinkedList<Stanje> stanja = aut.stanja;
		HashSet<Prijelaz> prijelazi = aut.prijelazi;
		AUTPodatci podatci = aut.podatci;
		
		IAutomatVHDLGenerator inter=null;
		if(podatci.tip.toUpperCase().equals("MOORE"))
			 inter=new MooreParser(stanja,podatci,prijelazi);
		else inter=new MealyParser(stanja,podatci,prijelazi);
		parsedVHDL=inter.getData();
	}
	
	public VHDLGenerator() {
		super();
	}

	public String getParsedVHDL() {
		return parsedVHDL;
	}

	public String generateVHDL(File f, VHDLLabManager labman) throws ServiceException {
		AUTParser aut=new AUTParser();
		
		try {
			aut.AUTParse(f.getContent());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		LinkedList<Stanje> stanja = aut.stanja;
		HashSet<Prijelaz> prijelazi = aut.prijelazi;
		AUTPodatci podatci = aut.podatci;
		
		IAutomatVHDLGenerator inter=null;
		if(podatci.tip.toUpperCase().equals("MOORE"))
			 inter=new MooreParser(stanja,podatci,prijelazi);
		else inter=new MealyParser(stanja,podatci,prijelazi);
		parsedVHDL=inter.getData();
		return parsedVHDL;
	}
	
}
