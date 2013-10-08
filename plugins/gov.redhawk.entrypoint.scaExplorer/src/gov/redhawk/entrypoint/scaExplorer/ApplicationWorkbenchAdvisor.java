/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 */
package gov.redhawk.entrypoint.scaExplorer;

import gov.redhawk.sca.ui.ScaUiPlugin;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	/**
	 * We need to implement an appropriate adapter for RAP, for saving and restoring workbench resources.
	 * The code below is used for the RCP app, but the implementation of <code>gov.redhawk.entrypoint.scaExplorer.internal.ResourceFactory</code>
	 * is not valid for RAP
	 * */

	/* This class was essentially copied from org.eclipse.ide.internal */
//	class WorkbenchAdapterFactory implements IAdapterFactory {
//		private final Object resourceFactory = new ResourceFactory();
//
//		public Object getAdapter(final Object o, @SuppressWarnings("rawtypes") final Class adapterType) {
//			if (adapterType.isInstance(o)) {
//				return o;
//			}
//			if (adapterType == IPersistableElement.class) {
//				return getPersistableElement(o);
//			}
//			if (adapterType == IElementFactory.class) {
//				return getElementFactory(o);
//			}
//
//			return null;
//		}
//
//
//		public Class< ? >[] getAdapterList() {
//			return new Class[] { IElementFactory.class, IPersistableElement.class };
//		}
//
//		/**
//		 * Returns an object which is an instance of IPersistableElement
//		 * associated with the given object. Returns <code>null</code> if no
//		 * such object can be found.
//		 */
//		protected Object getPersistableElement(final Object o) {
//			if (o instanceof IResource) {
//				return new ResourceFactory((IResource) o);
//			}
//			return null;
//		}
//
//		/**
//		 * Returns an object which is an instance of IElementFactory associated
//		 * with the given object. Returns <code>null</code> if no such object
//		 * can be found.
//		 */
//		protected Object getElementFactory(final Object o) {
//			if (o instanceof IResource) {
//				return this.resourceFactory;
//			}
//			return null;
//		}
//
//	};

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		if (Boolean.valueOf(System.getProperty(ScaUiPlugin.PROP_SINGLE_DOMAIN))) {
			return ScaExplorerSingleDomainPerspective.PERSPECTIVE_ID;
		}
		return ScaExplorerPerspective.PERSPECTIVE_ID;
	}

	@Override
	public IAdaptable getDefaultPageInput() {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot();
	}

	@Override
	public void initialize(final IWorkbenchConfigurer configurer) {
		super.initialize(configurer);

		//Disable save and restore until we implement an appropriate adapter factory for RAP
		configurer.setSaveAndRestore(/*true*/ false);

		// This is important when setSaveAndRestore is true
		// without this, the getDefaultPageInput() class will not
		// be persistable and so the navigator content adapters will
		// not work after the first load to the workspace
		//final IAdapterManager manager = Platform.getAdapterManager();
		//manager.registerAdapters(new WorkbenchAdapterFactory(), IWorkspaceRoot.class);
	}

}
