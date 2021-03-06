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
package hr.fer.zemris.vhdllab.applets.editor.schema2.predefined;

import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.ParameterWrapper;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PortWrapper;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PredefinedComponent;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PredefinedConf;

import java.io.StringReader;

import org.apache.commons.digester.Digester;

public class PredefinedComponentsParser {
	
	private PredefinedConf conf = null;
	private StringReader reader = null;
	
	public PredefinedComponentsParser(String fileContent) {
		reader = new StringReader(fileContent);
	}
	
	public PredefinedConf getConfiguration() {
		if(conf == null) {
			conf = parseConfiguration();
		}
		return conf;
	}
	
	private PredefinedConf parseConfiguration() {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("componentList", PredefinedConf.class);
		digester.addObjectCreate("componentList/component", PredefinedComponent.class);
		digester.addBeanPropertySetter("componentList/component/componentName");
		digester.addBeanPropertySetter("componentList/component/codeFileName");
		digester.addBeanPropertySetter("componentList/component/drawerName");
		digester.addBeanPropertySetter("componentList/component/categoryName");
		digester.addBeanPropertySetter("componentList/component/preferredName");
		digester.addBeanPropertySetter("componentList/component/genericComponent");

		digester.addObjectCreate("componentList/component/parameterList/parameter", ParameterWrapper.class);
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/generic");
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/paramType");
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/name");
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/value");
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/allowedValues");
		digester.addBeanPropertySetter("componentList/component/parameterList/parameter/eventName");
		digester.addSetNext("componentList/component/parameterList/parameter", "addParameter");
		
		digester.addObjectCreate("componentList/component/portList/port", PortWrapper.class);
		digester.addBeanPropertySetter("componentList/component/portList/port/name");
		digester.addBeanPropertySetter("componentList/component/portList/port/orientation");
		digester.addBeanPropertySetter("componentList/component/portList/port/direction");
		digester.addBeanPropertySetter("componentList/component/portList/port/type");
		digester.addBeanPropertySetter("componentList/component/portList/port/vectorAscension");
		digester.addBeanPropertySetter("componentList/component/portList/port/lowerBound");
		digester.addBeanPropertySetter("componentList/component/portList/port/upperBound");
		digester.addSetNext("componentList/component/portList/port", "addPort");
		
		digester.addSetNext("componentList/component", "addPredefinedComponent");
		
		try {
			return (PredefinedConf) digester.parse(reader);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
