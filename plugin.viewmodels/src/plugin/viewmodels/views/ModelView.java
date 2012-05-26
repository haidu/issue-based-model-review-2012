package plugin.viewmodels.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.unicase.metamodel.ModelElement;
import org.unicase.metamodel.Project;
import org.unicase.metamodel.util.ProjectChangeObserver;
import org.unicase.model.UnicaseModelElement;
import org.unicase.ui.unicasecommon.common.util.UnicaseActionHelper;
import org.unicase.workspace.Workspace;
import org.unicase.workspace.WorkspaceManager;

public class ModelView extends ViewPart implements ProjectChangeObserver, ISelectionListener {

	public static final String ID = "plugin.modelview.views.ModelView";

    private Project activeProject;
	private TableViewer tableViewer;
	private Workspace workspace;
	private AdapterImpl adapterImpl;
	private UnicaseModelElement input;
	private UnicaseModelElement eModelElement;
    
	public ModelView() {		
		
		 workspace = WorkspaceManager.getInstance().getCurrentWorkspace(); 
		 activeProject = workspace.getActiveProjectSpace().getProject();
 		 adapterImpl = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				
					if (activeProject != null) {
						activeProject.removeProjectChangeObserver(ModelView.this);
					}
					// add listener to get notified when work items get deleted/added/changed
					if (workspace.getActiveProjectSpace() != null) {
						activeProject = workspace.getActiveProjectSpace().getProject();
						activeProject.addProjectChangeObserver(ModelView.this);
					} else {
						try {
							if (msg.getOldValue().equals(WorkspaceManager.getProjectSpace(activeProject))) {
								setInput(null);
							}
						} catch (IllegalStateException e) {
							setInput(null);
						}
					}								
			}
		};
		workspace.eAdapters().add(adapterImpl);
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(Composite parent) {
		
		tableViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.VIRTUAL);
		tableViewer.setContentProvider(new ModelViewContentProvider());
		//tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		tableViewer.setLabelProvider(new ModelViewLabelProvider());
		//tableViewer.setInput(activeProject.eAllContents());
		tableViewer.setInput(activeProject);
		
		getSite().setSelectionProvider(tableViewer);
		
		addSelectionListener();
		
		hookDoubleClick();
    }  
    
    
	private void addSelectionListener() {
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection structSelection = (IStructuredSelection) event.getSelection();
					eModelElement = (UnicaseModelElement) structSelection.getFirstElement();
					System.out.println("Name: " + eModelElement.getName() + " Description: " + eModelElement.getDescription());
				}
			}

		});

	}
	
    
	private void hookDoubleClick() {
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				UnicaseActionHelper.openModelElement((UnicaseModelElement) sel.getFirstElement(), tableViewer.getClass().getName());
			}

		});
	}
    
	@Override
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	@Override
	public void modelElementAdded(Project arg0, ModelElement arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelElementDeleteCompleted(Project arg0, ModelElement arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelElementDeleteStarted(Project arg0, ModelElement arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(Notification arg0, Project arg1, ModelElement arg2) {
		// TODO Auto-generated method stub
		tableViewer.refresh();
	}

	@Override
	public void projectDeleted(Project arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Set input to the view. currently input is set using drag and drop on top composite. Later we implement a context
	 * menu command for it too.
	 *
	 * @param newInput input model element
	 */
	public void setInput(UnicaseModelElement newInput) {
		input = newInput;
		refreshView();

	}
	
	public void refreshView() {
		if (input == null) {
			resetView();
			return;
		}
		tableViewer.setInput(input);
	}

	private void resetView() {
		
		tableViewer.setInput(null);
		
	}
	
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == this) {
			return;
		}
		EObject element = extractObjectFromSelection(selection);
		if (element == null) {
			return;
		}
		revealME(element);
		
	}
	
	private void revealME(EObject me) {

		if (me == null) {
			return;
		}

		else{
			System.out.println("reveal me");
			getTableViewer().setSelection(new StructuredSelection(me), true);
		}
	}
	
	private EObject extractObjectFromSelection(ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.size() == 1) {
			Object node = structuredSelection.getFirstElement();
			System.out.println("extract from selection");
			return (EObject) node;
		}

		return null;
	}	

}