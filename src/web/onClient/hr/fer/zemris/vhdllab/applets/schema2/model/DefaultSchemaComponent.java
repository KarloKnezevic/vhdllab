package hr.fer.zemris.vhdllab.applets.schema2.model;

import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.Parameter;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PortWrapper;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PredefinedComponent;
import hr.fer.zemris.vhdllab.applets.schema2.enums.EOrientation;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.InvalidParameterValueException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.NotImplementedException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.ParameterNotFoundException;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IComponentDrawer;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameter;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameterCollection;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaComponent;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISerializable;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IVHDLSegmentProvider;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;
import hr.fer.zemris.vhdllab.applets.schema2.misc.IntList;
import hr.fer.zemris.vhdllab.applets.schema2.misc.SMath;
import hr.fer.zemris.vhdllab.applets.schema2.misc.SchemaPort;
import hr.fer.zemris.vhdllab.applets.schema2.model.drawers.DefaultComponentDrawer;
import hr.fer.zemris.vhdllab.applets.schema2.model.parameters.CaselessParameter;
import hr.fer.zemris.vhdllab.applets.schema2.model.parameters.GenericParameter;
import hr.fer.zemris.vhdllab.applets.schema2.model.parameters.ParameterFactory;
import hr.fer.zemris.vhdllab.vhdl.model.CircuitInterface;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultCircuitInterface;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultPort;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultType;
import hr.fer.zemris.vhdllab.vhdl.model.Direction;
import hr.fer.zemris.vhdllab.vhdl.model.Port;
import hr.fer.zemris.vhdllab.vhdl.model.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultSchemaComponent implements ISchemaComponent {

	private static class PortRelation {
		public Port port;
		public List<SchemaPort> relatedTo;

		public PortRelation(Port p) {
			port = p;
			relatedTo = new ArrayList<SchemaPort>();
		}
	}
	
	private static class Orientation implements ISerializable {
		public EOrientation orientation = EOrientation.NORTH;
		
		public Orientation() { }
		public Orientation(EOrientation ori) { orientation = ori; }
		
		public void deserialize(String code) {
			orientation = EOrientation.parse(code);
		}
		public String serialize() {
			return orientation.serialize();
		}
	}
	
	private class DefaultSchemaComponentVHDLProvider implements IVHDLSegmentProvider {

		public String getInstantiation() {
			throw new NotImplementedException();
		}

		public String getSignalDefinitions() {
			throw new NotImplementedException();
		}
		
	}
	

	private static final int WIDTH_PER_PORT = 20;
	private static final int HEIGHT_PER_PORT = 20;
	private static final int EDGE_OFFSET = 30;
	
	

	/* private fields */
	private Caseless componentName;
	private String codeFileName;
	private String categoryName;
	private boolean generic;
	private IComponentDrawer drawer;
	private IParameterCollection parameters;
	private List<SchemaPort> schemaports;
	private List<PortRelation> portrelations;
	private int width, height;
	
	

	/* ctors */

	/**
	 * @param name
	 *            Jedinstveno ime ove instance komponente.
	 * @param predefComp
	 *            Wrapper za predefinirane komponente.
	 */
	public DefaultSchemaComponent(String name, PredefinedComponent predefComp) {
		// basic properties
		componentName = new Caseless(predefComp.getComponentName());
		codeFileName = predefComp.getCodeFileName();
		categoryName = predefComp.getCategoryName();
		generic = predefComp.isGenericComponent();

		// drawer
		initDrawer(predefComp);

		// parameters
		initParameters(predefComp);

		// ports
		initPorts(predefComp);
		
		// add default parameters
		initDefaultParameters(name);
	}

	private void initDefaultParameters(String name) {
		// default parameter - name
		CaselessParameter textpar =
			new CaselessParameter(ISchemaComponent.KEY_NAME, false, new Caseless(name));
		parameters.addParameter(textpar.getName(), textpar);
		
		// default parameter - component orientation
		GenericParameter<Orientation> orientpar =
			new GenericParameter<Orientation>(ISchemaComponent.KEY_ORIENTATION, false,
					new Orientation());
		parameters.addParameter(orientpar.getName(), orientpar);
	}

	private void initPorts(PredefinedComponent predefComp) {
		schemaports = new ArrayList<SchemaPort>();
		portrelations = new ArrayList<PortRelation>();
		Set<PortWrapper> portwrappers = predefComp.getPorts();
		if (portwrappers == null) return;
		
		SchemaPort schport;
		Type tp;
		Direction dir;
		Port port;
		PortRelation pr;
		int nc = 0, sc = 0, wc = 0, ec = 0, pos = 0;
		IntList toBeMoved = new IntList();
		for (PortWrapper pw : portwrappers) {
			if (pw.getType().equals(PortWrapper.STD_LOGIC_VECTOR)) {
				int[] range = new int[2];
				range[0] = Integer.parseInt(pw.getLowerBound());
				range[1] = Integer.parseInt(pw.getUpperBound());
				tp = new DefaultType(PortWrapper.STD_LOGIC_VECTOR, range, toVecDir(pw.getVectorAscension()));
				if (pw.getDirection().equals(PortWrapper.DIRECTION_IN)) dir = Direction.IN;
				else if (pw.getDirection().equals(PortWrapper.DIRECTION_OUT)) dir = Direction.OUT;
				else throw new NotImplementedException("Direction '" + pw.getDirection() + "' unknown.");
				
				port = new DefaultPort(pw.getName(), dir, tp);
				pr = new PortRelation(port);
				portrelations.add(pr);
				
				int increment;
				if (pw.getOrientation().equals(PortWrapper.ORIENTATION_NORTH)) {
					increment = createSchPortsFor(tp, pr, EOrientation.NORTH, toBeMoved, nc, pos);
					nc += increment;
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_SOUTH)) {
					increment = createSchPortsFor(tp, pr, EOrientation.SOUTH, toBeMoved, sc, pos);
					sc += increment;
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_WEST)) {
					increment = createSchPortsFor(tp, pr, EOrientation.WEST, toBeMoved, wc, pos);
					wc += increment;
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_EAST)) {
					increment = createSchPortsFor(tp, pr, EOrientation.EAST, toBeMoved, ec, pos);
					ec += increment;
				} else throw new NotImplementedException("Orientation '" + pw.getOrientation() + "' unknown.");
				
				pos += increment;
				
			} else if (pw.getType().equals(PortWrapper.STD_LOGIC)) {
				tp = new DefaultType(PortWrapper.STD_LOGIC, DefaultType.SCALAR_RANGE,
						DefaultType.SCALAR_VECTOR_DIRECTION);
				if (pw.getDirection().equals(PortWrapper.DIRECTION_IN)) dir = Direction.IN;
				else if (pw.getDirection().equals(PortWrapper.DIRECTION_OUT)) dir = Direction.OUT;
				else throw new NotImplementedException("Direction '" + pw.getDirection() + "' unknown.");
				
				port = new DefaultPort(pw.getName(), dir, tp);
				pr = new PortRelation(port);
				portrelations.add(pr);
				
				if (pw.getOrientation().equals(PortWrapper.ORIENTATION_NORTH)) {
					schport = new SchemaPort(EDGE_OFFSET + nc * WIDTH_PER_PORT, 0,
							new Caseless(port.getName()));
					nc++;
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_SOUTH)) {
					schport = new SchemaPort(EDGE_OFFSET + sc * WIDTH_PER_PORT, 0,
							new Caseless(port.getName()));
					sc++;
					toBeMoved.add(pos);
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_WEST)) {
					schport = new SchemaPort(0, EDGE_OFFSET + wc * HEIGHT_PER_PORT,
							new Caseless(port.getName()));
					wc++;
				} else if (pw.getOrientation().equals(PortWrapper.ORIENTATION_EAST)) {
					schport = new SchemaPort(0, EDGE_OFFSET + ec * HEIGHT_PER_PORT,
							new Caseless(port.getName()));
					ec++;
					toBeMoved.add(pos);
				} else throw new NotImplementedException("Orientation '" + pw.getOrientation() + "' unknown.");
				
				pr.relatedTo.add(schport);
				schemaports.add(schport);
				pos++;
			} else {
				throw new NotImplementedException("Port type '" + pw.getType() + "' is unknown.");
			}
		}
		
		// calculate width and height and set ports appropriately
		width = (((nc > sc) ? (nc) : (sc)) - 1) * WIDTH_PER_PORT;
		height = (((wc > ec) ? (wc) : (ec)) - 1) * HEIGHT_PER_PORT;
		
		pos = toBeMoved.size();
		for (int i = 0; i < pos; i++) {
			schport = schemaports.get(toBeMoved.get(i));
			if (schport.getOffset().x == 0) schport.setXOffset(width);
			else schport.setYOffset(height);
		}
	}
	
	private final int createSchPortsFor(Type tp, PortRelation pr, EOrientation ori, IntList toBeMoved,
			int stor, int stpos)
	{
		int from = tp.getRangeFrom(), to = tp.getRangeTo();
		
		SchemaPort schport = null;
		if (tp.hasVectorDirectionTO()) {
			for (int i = from; i <= to; i++) {
				if (ori == EOrientation.NORTH || ori == EOrientation.SOUTH) {
					schport = new SchemaPort(EDGE_OFFSET + stor * WIDTH_PER_PORT, 0,
							new Caseless(pr.port.getName() + "_" + i));
				}
				if (ori == EOrientation.EAST || ori == EOrientation.WEST) {
					schport = new SchemaPort(0, EDGE_OFFSET + stor * HEIGHT_PER_PORT,
							new Caseless(pr.port.getName() + "_" + i));
				}
				if (ori == EOrientation.EAST || ori == EOrientation.SOUTH) toBeMoved.add(stpos);
				stpos++;
				stor++;
				schemaports.add(schport);
				pr.relatedTo.add(schport);
			}
		} else {
			for (int i = to; i >= from; i--) {
				if (ori == EOrientation.NORTH || ori == EOrientation.SOUTH) {
					schport = new SchemaPort(EDGE_OFFSET + stor * WIDTH_PER_PORT, 0,
							new Caseless(pr.port.getName() + "_" + i));
				}
				if (ori == EOrientation.EAST || ori == EOrientation.WEST) {
					schport = new SchemaPort(0, EDGE_OFFSET + stor * HEIGHT_PER_PORT,
							new Caseless(pr.port.getName() + "_" + i));
				}
				if (ori == EOrientation.EAST || ori == EOrientation.SOUTH) toBeMoved.add(stpos);
				stpos++;
				stor++;
				schemaports.add(schport);
				pr.relatedTo.add(schport);
			}
		}
		
		return to - from + 1;
	}

	private final String toVecDir(String ascend) {
		if (ascend.equals(PortWrapper.ASCEND)) return DefaultType.VECTOR_DIRECTION_TO;
		else if (ascend.equals(PortWrapper.DESCEND)) return DefaultType.VECTOR_DIRECTION_DOWNTO;
		else throw new NotImplementedException("Ascension '" + ascend + "' unknown.");
	}

	private void initDrawer(PredefinedComponent predefComp) {
		try {
			Class cls = Class.forName(predefComp.getDrawerName());
			Class[] partypes = new Class[1];
			partypes[0] = ISchemaComponent.class;
			Constructor<IComponentDrawer> ct = cls.getConstructor(partypes);
			drawer = ct.newInstance();
		} catch (ClassNotFoundException cnfe) {
			drawer = new DefaultComponentDrawer(this);
		} catch (NoSuchMethodException nsme) {
			drawer = new DefaultComponentDrawer(this);
		} catch (IllegalArgumentException e) {
			drawer = new DefaultComponentDrawer(this);
		} catch (InstantiationException e) {
			drawer = new DefaultComponentDrawer(this);
		} catch (IllegalAccessException e) {
			drawer = new DefaultComponentDrawer(this);
		} catch (InvocationTargetException e) {
			drawer = new DefaultComponentDrawer(this);
		}
	}

	private void initParameters(PredefinedComponent predefComp) {
		parameters = new SchemaParameterCollection();
		Set<Parameter> params = predefComp.getParameters();
		if (params != null) {
			ParameterFactory parfactory = new ParameterFactory();
			IParameter par;
			for (Parameter parwrap : params) {
				par = parfactory.createParameter(parwrap);
				parameters.addParameter(par.getName(), par);
			}
		}
	}
	
	
	
	/* methods */

	public ISchemaComponent copyCtor() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	public String getCategoryName() {
		return categoryName;
	}

	public CircuitInterface getCircuitInterface() {
		List<Port> ports = new ArrayList<Port>();
		for (PortRelation pr : portrelations) {
			ports.add(new DefaultPort(pr.port.getName(), pr.port.getDirection(), pr.port.getType()));
		}
		return new DefaultCircuitInterface(componentName.toString(), ports);
	}

	public EOrientation getComponentOrientation() {
		try {
			return ((Orientation)(parameters.getValue(ISchemaComponent.KEY_ORIENTATION))).orientation;
		} catch (ParameterNotFoundException e) {
			throw new RuntimeException("Orientation parameter not found.");
		}
	}

	public IComponentDrawer getDrawer() {
		return drawer;
	}

	public int getHeight() {
		return height;
	}

	public Caseless getName() {
		try {
			return (Caseless)(parameters.getValue(ISchemaComponent.KEY_NAME));
		} catch (ParameterNotFoundException e) {
			throw new RuntimeException("Name parameter not found.");
		}
	}

	public IParameterCollection getParameters() {
		return parameters;
	}

	public List<SchemaPort> getPorts() {
		return schemaports;
	}

	public SchemaPort getSchemaPort(int xoffset, int yoffset, int dist) {
		int ind = SMath.calcClosestPort(xoffset, yoffset, dist, schemaports);
		if (ind == -1) return null;
		return schemaports.get(ind);
	}

	public SchemaPort getSchemaPort(int index) {
		if (index < 0 || index >= schemaports.size()) return null;
		return schemaports.get(index);
	}

	public Caseless getTypeName() {
		return componentName;
	}

	public IVHDLSegmentProvider getVHDLSegmentProvider() {
		return new DefaultSchemaComponentVHDLProvider();
	}

	public int getWidth() {
		return width;
	}

	public int portCount() {
		return schemaports.size();
	}

	public void setComponentOrientation(EOrientation orient) {
		try {
			parameters.setValue(ISchemaComponent.KEY_ORIENTATION, new Orientation(orient));
		} catch (InvalidParameterValueException e) {
			throw new RuntimeException("Orientation could not be set - invalid value.", e);
		} catch (ParameterNotFoundException e) {
			throw new RuntimeException("Orientation could not be set.", e);
		}
	}

	public void setName(Caseless name) {
		try {
			parameters.setValue(ISchemaComponent.KEY_NAME, name);
		} catch (InvalidParameterValueException e) {
			throw new RuntimeException("Name could not be set - invalid value.", e);
		} catch (ParameterNotFoundException e) {
			throw new RuntimeException("Name could not be set.", e);
		}
	}
	

	

}












