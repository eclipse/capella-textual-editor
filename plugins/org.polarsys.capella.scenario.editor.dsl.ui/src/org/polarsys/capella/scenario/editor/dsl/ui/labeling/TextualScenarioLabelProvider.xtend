/*******************************************************************************
* Copyright (c) 2020 THALES GLOBAL SERVICES.
*  
*  This program and the accompanying materials are made available under the
*  terms of the Eclipse Public License 2.0 which is available at
*  http://www.eclipse.org/legal/epl-2.0
*  
*  SPDX-License-Identifier: EPL-2.0
*  
*  Contributors:
*     Thales - initial API and implementation
*******************************************************************************/
/*
 * generated by Xtext 2.18.0.M3
 */
package org.polarsys.capella.scenario.editor.dsl.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class TextualScenarioLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:
	
//	def text(SequenceMessage ele) {
//		'A greeting to ' + ele.name
//	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}
}
