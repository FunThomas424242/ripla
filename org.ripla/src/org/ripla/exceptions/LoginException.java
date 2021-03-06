/*******************************************************************************
 * Copyright (c) 2013 RelationWare, Benno Luthiger
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * RelationWare, Benno Luthiger
 ******************************************************************************/
package org.ripla.exceptions;

/**
 * Exception to signal login failure.
 * 
 * @author Luthiger
 */
@SuppressWarnings("serial")
public class LoginException extends RiplaException {

	/**
	 * @param inSimpleMessage
	 */
	public LoginException(final String inSimpleMessage) {
		super(inSimpleMessage);
	}

}