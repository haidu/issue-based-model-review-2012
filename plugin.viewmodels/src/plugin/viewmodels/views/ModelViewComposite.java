package plugin.viewmodels.views;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.unicase.metamodel.ModelElement;
import org.unicase.metamodel.Project;
import org.unicase.metamodel.util.ProjectChangeObserver;
import org.unicase.model.UnicaseModelElement;
import org.unicase.ui.unicasecommon.common.TreeViewerColumnSorter;
import org.unicase.workspace.ProjectSpace;
import org.unicase.workspace.Workspace;
import org.unicase.workspace.WorkspaceManager;
import org.unicase.workspace.WorkspacePackage;

public class ModelViewComposite extends Composite implements ProjectChangeObserver{

	
    private TreeViewer treeViewer;
    private Workspace workspace;
    private AdapterImpl adapterImpl;
    private EList<ModelElement> modelElementList;
    private Project activeProject;
    
    /**
     * Constructor.
     * 
     * @param parent parent
     * @param style style
     */
    public ModelViewComposite(Composite parent, int style) {
            super(parent, style);
            this.setLayout(new GridLayout());
            createTree();

            workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
            activeProject = workspace.getActiveProjectSpace().getProject();
            if (workspace.getActiveProjectSpace() != null) {
                    workspace.getActiveProjectSpace().getProject().addProjectChangeObserver(ModelViewComposite.this);
            }
            adapterImpl = new AdapterImpl() {
                    @Override
                    public void notifyChanged(Notification msg) {
                            if ((msg.getFeatureID(Workspace.class)) == WorkspacePackage.WORKSPACE__ACTIVE_PROJECT_SPACE) {

                                    // remove old listeners
                                    Object oldValue = msg.getOldValue();
                                    if (oldValue instanceof ProjectSpace) {
                                            ((ProjectSpace) oldValue).getProject().removeProjectChangeObserver(ModelViewComposite.this);
                                    }
                                    // add listener to get notified when work items get deleted/added/changed
                                    if (workspace.getActiveProjectSpace() != null) {
                                            workspace.getActiveProjectSpace().getProject().addProjectChangeObserver(
                                                    ModelViewComposite.this);
                                    }
                            }
                    }
            };
            workspace.eAdapters().add(adapterImpl);

    }
    
    private void createTree() {
    	
    	modelElementList = activeProject.getAllModelElements();
    	
        treeViewer = new TreeViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
        treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        treeViewer.setContentProvider(new ModelViewContentProvider());
        // sort contents
        treeViewer.setComparator(new ViewerComparator());
        //treeViewer.setInput(modelElementList.get(1));
}
    
    private void addColumns(TreeViewer viewer) {
        Tree tree = viewer.getTree();
        tree.setHeaderVisible(true);
        
        // root nodes (WorkPackage) and their contained WorkItems
        TreeViewerColumn tclmWorkItem = new TreeViewerColumn(viewer, SWT.NONE);
        tclmWorkItem.getColumn().setText("WorkItem");
        tclmWorkItem.getColumn().setWidth(300);
    }

/**
 * . set input to TreeViewer
 * 
 * @param me input model element
 */
public void setInput(UnicaseModelElement me) {
        treeViewer.setInput(me);
}

/**
 * {@inheritDoc}
 * 
 * @see org.unicase.metamodel.util.ProjectChangeObserver#modelElementAdded(org.unicase.metamodel.Project,
 *      org.unicase.model.UnicaseModelElement)
 */
public void modelElementAdded(Project project, EObject modelElement) {
        treeViewer.refresh();
}

/**
 * {@inheritDoc}
 * 
 * @see org.unicase.metamodel.util.ProjectChangeObserver#modelElementDeleteCompleted(org.unicase.model.UnicaseModelElement)
 */
public void modelElementRemoved(Project project, EObject modelElement) {
        treeViewer.refresh();

}

/**
 * {@inheritDoc}
 * 
 * @see org.unicase.metamodel.util.ProjectChangeObserver#notify(org.eclipse.emf.common.notify.Notification,
 *      org.unicase.metamodel.Project, org.unicase.model.UnicaseModelElement)
 */
public void notify(Notification notification, Project project, EObject modelElement) {
        treeViewer.update(modelElement, null);
}

/**
 * @see org.eclipse.swt.widgets.Widget#dispose()
 */
@Override
public void dispose() {

        workspace.eAdapters().remove(adapterImpl);
        if (workspace.getActiveProjectSpace() != null && workspace.getActiveProjectSpace().getProject() != null) {
                workspace.getActiveProjectSpace().getProject().removeProjectChangeObserver(ModelViewComposite.this);

        }

        super.dispose();
}

/**
 * {@inheritDoc}
 * 
 * @see org.unicase.metamodel.util.ProjectChangeObserver#projectDeleted(org.unicase.metamodel.Project)
 */
public void projectDeleted(Project project) {
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
public void modelElementAdded(Project arg0, ModelElement arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void notify(Notification arg0, Project arg1, ModelElement arg2) {
	// TODO Auto-generated method stub
	
}

}
