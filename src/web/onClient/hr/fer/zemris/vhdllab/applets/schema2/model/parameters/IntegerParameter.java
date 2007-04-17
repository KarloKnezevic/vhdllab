package hr.fer.zemris.vhdllab.applets.schema2.model.parameters;

import hr.fer.zemris.vhdllab.applets.schema2.enums.EParamTypes;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.InvalidParameterValueException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.NotImplementedException;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameter;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameterConstraint;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Time;
import hr.fer.zemris.vhdllab.applets.schema2.model.parameters.constraints.IntegerConstraint;





public class IntegerParameter implements IParameter {
	
	
	
	public void deserialize(String code) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	public String serialize() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	private Integer val;
	private IntegerConstraint constraint;
	
	
	public IntegerParameter() {
		val = new Integer(0);
		constraint = new IntegerConstraint();
	}
	
	public IntegerParameter(int value) {
		val = new Integer(value);
		constraint = new IntegerConstraint();
	}
	
	
	public boolean checkStringValue(String stringValue) {
		if (stringValue == null) return false;
		
		try {
			Integer.parseInt(stringValue);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public IParameterConstraint getConstraint() {
		return constraint;
	}

	public EParamTypes getType() {
		return EParamTypes.INTEGER;
	}

	public Object getValue() {
		return val;
	}

	public Boolean getValueAsBoolean() throws ClassCastException {
		throw new ClassCastException();
	}

	public Double getValueAsDouble() throws ClassCastException {
		throw new ClassCastException();
	}

	public Integer getValueAsInteger() throws ClassCastException {
		return val;
	}

	public String getValueAsString() throws ClassCastException {
		throw new ClassCastException();
	}

	public Time getValueAsTime() throws ClassCastException {
		throw new ClassCastException();
	}

	public boolean isParsable() {
		return true;
	}

	public void setAsString(String stringValue) throws InvalidParameterValueException {
		if (stringValue == null) throw new InvalidParameterValueException("Null passed.");
		
		Integer newval = Integer.parseInt(stringValue);
		
		if (!constraint.checkValue(newval)) {
			throw new InvalidParameterValueException("Correctly parsed, but not allowed by constraint.");
		}
		
		val = newval;
	}

	public void setValue(Object value) throws InvalidParameterValueException {
		if (value == null || !(value instanceof Integer)) throw new InvalidParameterValueException("Non-integer passed.");
		
		if (!(constraint.checkValue(value))) throw new InvalidParameterValueException("Not allowed by constraint.");
		
		val = (Integer)value;
	}

	
}










