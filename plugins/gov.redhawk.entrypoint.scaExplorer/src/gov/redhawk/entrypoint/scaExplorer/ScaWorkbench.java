/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.entrypoint.scaExplorer;

import java.io.IOException;
import java.util.logging.LogManager;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

/**
 * This class controls all aspects of the application's execution
 */
public class ScaWorkbench implements IEntryPoint {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return;
		}
		final Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			display.syncExec(new Runnable() {
				public void run() {
					if (!display.isDisposed()) {
						workbench.close();
					}
				}
			});
		}
	}

	public int createUI() {
		try {
			LogManager.getLogManager().readConfiguration(FileLocator.openStream(Activator.getDefault().getBundle(), new Path("javalogger.properties"), false));
		} catch (final SecurityException e) {
			// PASS
		} catch (final IOException e) {
			// PASS
		}
		final Display display = PlatformUI.createDisplay();
		//Read properties from bundle context (set in launch.ini) and set corresponding system props
		BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		String orbClass = context.getProperty(Activator.PROP_JACORB_ORB_CLASS);
		if (orbClass != null) {
			System.setProperty(Activator.PROP_JACORB_ORB_CLASS, orbClass);
		}
		String orbSingletonClass = context.getProperty(Activator.PROP_JACORB_ORB_SINGLETON_CLASS);
		if (orbSingletonClass != null) {
			System.setProperty(Activator.PROP_JACORB_ORB_SINGLETON_CLASS, orbSingletonClass);
		}
		String singleDomain = context.getProperty(Activator.PROP_SINGLE_DOMAIN);
		if (singleDomain != null) {
			System.setProperty(Activator.PROP_SINGLE_DOMAIN, singleDomain);
		}
		String sharedDomains = context.getProperty(Activator.PROP_SHARED_DOMAINS);
		if (sharedDomains != null) {
			System.setProperty(Activator.PROP_SHARED_DOMAINS, sharedDomains);
		}
		final int result = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
		display.dispose();
		return result;
	}
}
