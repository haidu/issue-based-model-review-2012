package plugin.viewmodels.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.unicase.model.UnicaseModelElement;

import plugin.viewmodels.ModelViewEditorInput;
import plugin.viewmodels.editors.ModelViewEditor;

public class ViewModelsEditorHandler extends AbstractHandler{
	
	Object o;
	EObject me;
	UnicaseModelElement eModelElement;
	ModelViewEditorInput input;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {	
		final IStructuredSelection structSelection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		o = structSelection.getFirstElement();
		me = (EObject) o;
		input = new ModelViewEditorInput(me);

 		try {
			PlatformUI.getWorkbench ().getActiveWorkbenchWindow().getActivePage().openEditor(input, ModelViewEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}
}
