/**
 * REDHAWK HEADER
 *
 * Identification: $Revision: 2231 $
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

/**
 * This class controls all aspects of the application's execution
 */
public class ScaWorkbench implements IEntryPoint {

	private static final String ATT_DISPLAY = "display";

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
		final int result = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
		display.dispose();
		return result;
	}
}
