/*******************************************************************************
* Copyright (c) 2012 RelationWare, Benno Luthiger
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* RelationWare, Benno Luthiger
******************************************************************************/

package org.ripla.demo.widgets.controllers;

import org.ripla.demo.widgets.Constants;
import org.ripla.demo.widgets.views.InputWidgetsView;
import org.ripla.web.annotations.UseCaseController;
import org.ripla.web.controllers.AbstractController;
import org.ripla.web.exceptions.RiplaException;

import com.vaadin.ui.Component;

/**
 * Controller for the Vaadin input widgets.
 * 
 * @author Luthiger
 */
@UseCaseController
public class InputWidgetsController extends AbstractController {

	/* (non-Javadoc)
	 * @see org.ripla.web.controllers.AbstractController#needsPermission()
	 */
	@Override
	protected String needsPermission() {
		return Constants.PERMISSION_INPUT_WIDGETS;
	}

	/* (non-Javadoc)
	 * @see org.ripla.web.controllers.AbstractController#runChecked()
	 */
	@Override
	protected Component runChecked() throws RiplaException {
		loadContextMenu(Constants.CONTEXT_MENU_SET_WIDGETS);

		return new InputWidgetsView();
	}

}