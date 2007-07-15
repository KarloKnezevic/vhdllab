package hr.fer.zemris.vhdllab.applets.schema2.model.parameters.events;

import hr.fer.zemris.vhdllab.applets.schema2.enums.EPropertyChange;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameter;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameterEvent;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaComponent;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaInfo;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaWire;
import hr.fer.zemris.vhdllab.applets.schema2.misc.ChangeTuple;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultPort;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultType;
import hr.fer.zemris.vhdllab.vhdl.model.Port;
import hr.fer.zemris.vhdllab.vhdl.model.Type;

import java.util.ArrayList;
import java.util.List;






/**
 * Uzima prvi port komponente kojoj pripada
 * i mijenja mu velicinu na zadanu.
 * Ovaj event nije undoable.
 * 
 * @author brijest
 *
 */
public class FirstPortResizer implements IParameterEvent {
	
	/* static fields */
	
	

	/* private fields */
	
	

	/* ctors */
	
	public FirstPortResizer() {
	}
	

	/* methods */

	public List<ChangeTuple> getChanges() {
		List<ChangeTuple> changes = new ArrayList<ChangeTuple>();
		
		changes.add(new ChangeTuple(EPropertyChange.CANVAS_CHANGE));
		changes.add(new ChangeTuple(EPropertyChange.COMPONENT_PROPERTY_CHANGE));
		
		return changes;
	}

	public boolean isUndoable() {
		return false;
	}

	public boolean performChange(Object oldvalue, IParameter parameter, ISchemaInfo info, ISchemaWire wire, ISchemaComponent component) {
		if (component == null) return false;
		
		int oldnum = (Integer)oldvalue, portnum;
		
		try {
			portnum = parameter.getValueAsInteger();
		} catch (ClassCastException e) {
			return false;
		}
		
		if (oldnum == portnum) return true;
		if (portnum <= 0) return false;
		
		Port port = null;
		try {
			port = component.getPort(0);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		
		
		// create new port of same type but new size
		Type tp = port.getType();
		if (tp.isScalar()) return false;
		int[] range = new int[2];
		range[0] = tp.getRangeFrom();
		range[1] = tp.getRangeTo() + (portnum - oldnum);
		port = new DefaultPort(port.getName(), port.getDirection(),
				new DefaultType("std_logic_vector", range, tp.getVectorDirection()));
		component.setPort(0, port);
		
		return true;
	}
}


























