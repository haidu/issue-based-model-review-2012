package plugin.viewmodels.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.unicase.model.UnicaseModelElement;

public class ViewModelsEditorHandler extends AbstractHandler{

	UnicaseModelElement eModelElement;
	Object node;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		final IStructuredSelection structSelection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		node = structSelection.getFirstElement();
		
		if (node instanceof UnicaseModelElement)
		{
			eModelElement = (UnicaseModelElement) node;
			System.out.println("Name: " + eModelElement.getName() + " Description: " + eModelElement.getDescription());
		}
		
		return null;
	}
}
