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
/*
 * Copyright (c) 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package hr.fer.zemris.vhdllab.platform.remoting;

import org.springframework.rules.PropertyConstraintProvider;
import org.springframework.rules.Rules;
import org.springframework.rules.constraint.Constraint;
import org.springframework.rules.constraint.property.PropertyConstraint;

/**
 * This class provides a bean suitable for use in a login form, providing
 * properties for storing the user name and password.
 * <p>
 * This bean provides basic constraints for the username and password
 * properties. Each is required to be at least 2 characters long. If you need
 * more specific constraints, then you should implement a subtype and override
 * the initRules method.
 * 
 * @author Larry Streepy
 * @author Ben Alex
 * 
 */
public class LoginDetails implements PropertyConstraintProvider {

    public static final String PROPERTY_USERNAME = "username";

    public static final String PROPERTY_PASSWORD = "password";

    private String username;

    private String password;

    private Rules validationRules;

    /**
     * Constructor. Pre-load our username field with the data currently stored
     * in the security context, if any.
     */
    public LoginDetails() {
        initRules();
    }

    /**
     * Initialize the field constraints for our properties. Minimal constraints
     * are enforced here. If you need more control, you should override this in
     * a subtype.
     */
    protected void initRules() {
        this.validationRules = new Rules(getClass()) {
            @Override
            protected void initRules() {
                add(PROPERTY_USERNAME, all(new Constraint[] { required(),
                        minLength(getUsernameMinLength()) }));
                add(PROPERTY_PASSWORD, all(new Constraint[] { required() }));
            }

            protected int getUsernameMinLength() {
                return 2;
            }

        };
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the property constraints.
     * 
     * @see org.springframework.rules.PropertyConstraintProvider#getPropertyConstraint(java.lang.String)
     */
    public PropertyConstraint getPropertyConstraint(String propertyName) {
        return validationRules.getPropertyConstraint(propertyName);
    }

}
