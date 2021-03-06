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
package hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans;







public class ParameterWrapper {
	
	private String paramClassName;
	private Boolean generic;
	private String paramType;
	private String name;
	private String value;
	private String valueType;
	private String allowedValues;
	private String eventName;
	
	public void setParamClassName(String paramClassName) {
		this.paramClassName = paramClassName;
	}
	public String getParamClassName() {
		return paramClassName;
	}
	public void setGeneric(Boolean generic) {
		this.generic = generic;
	}
	public Boolean getGeneric() {
		return generic;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamType() {
		return paramType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setAllowedValues(String allowedValues) {
		if (allowedValues.equals("")) this.allowedValues = null;
		else this.allowedValues = allowedValues;
	}
	public String getAllowedValues() {
		return allowedValues;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getValueType() {
		return valueType;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventName() {
		return eventName;
	}

}





