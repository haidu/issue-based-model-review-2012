package plugin.viewmodels;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ModelViewEditorInput implements IEditorInput {

	private EObject modelElement;
	
	public ModelViewEditorInput(EObject me){
		this.modelElement = me;
	}
	
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {

		if (adapter.equals(EObject.class)) {
			return getModelElement();
		}
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
			return "Issue based model review editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {		
		return getName();
	}
	
	public EObject getModelElement() {
		return modelElement;
	}

}
