package hr.fer.zemris.vhdllab.applets.schema2.interfaces;

import hr.fer.zemris.vhdllab.applets.schema2.enums.ECanvasState;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;

import java.beans.PropertyChangeListener;

/**
 * Sučelje koje opisuje controller u lokalnom MVC-u za GUI schematica.
 * 
 * @author ddelac
 * 
 */
public interface ILocalGuiController {

	/**
	 * Ovom metodom dodaje se novi PropertyChangeListener na controller. Ovom se
	 * listeneru javljaju samo specifične promjene zadane properyName imenom.
	 * 
	 * @param propertyName
	 *            ime properya na kojeg se ovaj listener registrira
	 * @param listener
	 *            razred koji implementira {@link PropertyChangeListener}
	 */
	public void addListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Ovom metodom dodaje se novi PropertyChangeListener na controller. Ovom se
	 * listeneru javljaju sve promjene na bilokojem od polja controllera, tj
	 * obavještava se u trenutku poziva bilokojeg od settera nad ovim
	 * controllerom.
	 * 
	 * @param listener
	 *            razred koji implementira {@link PropertyChangeListener}
	 */
	public void addListener(PropertyChangeListener listener);

	/**
	 * Metoda vrača komponentu koja se treba dodati. U ovom polju mora se
	 * pohraniti identifikator za komponentu koju canvas u trenutku manipulacije
	 * gumba na mišu mora dodati u model instrukcijom: {@link ICommand}.
	 * 
	 * @return identifikator komponente za dodati
	 */
	public Caseless getComponentToAdd();

	/**
	 * Metoda postavlja polje componentToAdd u modelu i javlja listenerima da se
	 * neko od polja promjenilo.
	 * 
	 * @param componentToAdd
	 *            identifikator komponente u schematicu.
	 */
	public void setComponentToAdd(Caseless componentToAdd);

	/**
	 * U polju selectedComponent predviđeno je da canvas postavi ime komponente
	 * koja je trenutno selektirana te da toolbar pomoću tog imena može iz
	 * modela dohvatiti komponentu. (pošto je ime komponente unikatno za svaku
	 * komponentu).
	 * 
	 * @return naziv (identifikator instance) selektirane komponente.
	 */
	public Caseless getSelectedComponent();

	/**
	 * Metoda postavlja polje selectedComponent. Ovu metodu će vjerojatno
	 * pozivati Canvas, a obavjestit će sve listenere o promjeni.
	 * 
	 * @param selectedComponent
	 *            selektirana komponenta (identifikator selektirane komponente)
	 */
	public void setSelectedComponent(Caseless selectedComponent);

	/**
	 * Dohvaća stanje rada u kojem je trenutno Canvas. Stanje rada predstavlja
	 * trenutnu funkciju canvasa na manipulacije mišem.
	 * 
	 * @return Stanje rada u kojem se canvas trenutno nalazi
	 */
	public ECanvasState getState();

	/**
	 * Metoda postavlja novo stanje rada canvasu. Primjer: korisnik odabere da
	 * će dodati neku komponentu u model, toolbar poziva prvo
	 * setComponentToAdd(ime); pa potom
	 * setState(CanvasState.ADD_COMPONENT_STATE); U tom trenutku canvas će preči
	 * u novo stanje rada, pročitati polje componentToAdd i biti spreman za rad.
	 * 
	 * @param state
	 *            neki od CanvasState-ova
	 */
	public void setState(ECanvasState state);

}