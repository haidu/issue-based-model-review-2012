package plugin.viewmodels.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import plugin.viewmodels.views.ModelView;

public class ViewModelsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

			try {
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView("plugin.viewmodels.modelview");
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//modelview.createPartControl(parent);
		
		/*IWorkbenchPage activePage = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		ISelection selection = activePage.getSelection();
		
		System.out.println(selection.toString());
				
		if (selection !=null  & selection instanceof IStructuredSelection){
			
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.isEmpty()) {
				return null;
			}			
			System.out.println("Booyahh not null");
		}		*/
		
		return null;
	}
}

