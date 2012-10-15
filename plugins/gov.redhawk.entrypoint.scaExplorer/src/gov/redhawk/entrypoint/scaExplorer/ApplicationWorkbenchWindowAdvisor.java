/**
 * REDHAWK HEADER
 *
 * Identification: $Revision: 2647 $
 */
package gov.redhawk.entrypoint.scaExplorer;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WindowTrimProxy;
import org.eclipse.ui.internal.layout.IWindowTrim;
import org.eclipse.ui.internal.layout.TrimLayout;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Shell shell;

	public ApplicationWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(final IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1024, 768)); // SUPPRESS CHECKSTYLE MagicNumber
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(true);
		configurer.setShowFastViewBars(false);
		configurer.setShowProgressIndicator(true);
		configurer.setShellStyle(SWT.NO_TRIM);
		configurer.setTitle("SCA Explorer");
	}

	@Override
	public void postWindowOpen() {
		super.postWindowOpen();
		final IMenuManager menuManager = getWindowConfigurer().getActionBarConfigurer().getMenuManager();
		final IContributionItem[] menuItems = menuManager.getItems();

		for (final IContributionItem item : menuItems) {
			if ("org.eclipse.ui.run".equals(item.getId())) {
				menuManager.remove(item);
			}
		}
		menuManager.update(true);

	}

	@Override
	public void postWindowCreate() {
		super.postWindowCreate();
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		Shell shell = configurer.getWindow().getShell();
		shell.setMaximized(true);
	}
}
