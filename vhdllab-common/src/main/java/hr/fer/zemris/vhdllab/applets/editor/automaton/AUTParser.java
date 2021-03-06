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
package hr.fer.zemris.vhdllab.applets.editor.automaton;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
public class AUTParser {
		
	public AUTPodatci podatci;
	public LinkedList<Stanje> stanja=new LinkedList<Stanje>();
	public HashSet<Prijelaz> prijelazi=new HashSet<Prijelaz>();
	
	public AUTParser(){
		super();
	}
	
	public void AUTParse(String podatci) throws IOException, SAXException{
		Digester digester=new Digester();
		
		digester.push(this);
		
		digester.addCallMethod("Automat/Podatci_Sklopa","dodajPodatke",8);
		digester.addCallParam("Automat/Podatci_Sklopa/Ime",0);
		digester.addCallParam("Automat/Podatci_Sklopa/Tip",1);
		digester.addCallParam("Automat/Podatci_Sklopa/Interfac",2);
		digester.addCallParam("Automat/Podatci_Sklopa/Pocetno_Stanje",3);
		digester.addCallParam("Automat/Podatci_Sklopa/Reset",4);
		digester.addCallParam("Automat/Podatci_Sklopa/Clock",5);
		digester.addCallParam("Automat/Podatci_Sklopa/Sirina",6);
		digester.addCallParam("Automat/Podatci_Sklopa/Visina",7);
		
		digester.addCallMethod("Automat/Stanje","dodajStanje",6);
		digester.addCallParam("Automat/Stanje/Ime",0);
		digester.addCallParam("Automat/Stanje/Izlaz",1);
		digester.addCallParam("Automat/Stanje/Ox",2);
		digester.addCallParam("Automat/Stanje/Oy",3);
		digester.addCallParam("Automat/Stanje/Else",4);
		digester.addCallParam("Automat/Stanje/ElseIzlaz",5);
		
		digester.addCallMethod("Automat/Prijelaz","dodajPrijelaz",4);
		digester.addCallParam("Automat/Prijelaz/Iz",0);
		digester.addCallParam("Automat/Prijelaz/U",1);
		digester.addCallParam("Automat/Prijelaz/Pobuda",2);
		digester.addCallParam("Automat/Prijelaz/Izlaz",3);
		
		digester.parse(new StringReader(podatci));
	}
	
	public void dodajPodatke(String ime,String tip,String interfac,String pocetnoStanje,String rs,String cl,String s, String v){
		podatci=new AUTPodatci(ime,tip,interfac,pocetnoStanje,rs,cl, s, v);
	}
	public void dodajStanje(String ime, String izlaz, String ox,String oy,String el,String eIz){
		Stanje st=new Stanje(ime,izlaz,ox,oy,el,eIz);
		stanja.add(st);
	}
	public void dodajPrijelaz(String iz,String u,String pobuda,String izlaz){
		Prijelaz pr=new Prijelaz(iz,u,pobuda,izlaz);
		boolean test=true;
		for(Prijelaz temp:prijelazi)
			if(temp.equals(pr)){
				temp.dodajPodatak(pobuda,izlaz);
				test=false;
			}
		if(test)prijelazi.add(pr);
	}
}
