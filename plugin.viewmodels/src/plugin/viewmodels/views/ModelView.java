package plugin.viewmodels.views;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.unicase.metamodel.ModelElement;
import org.unicase.metamodel.Project;
import org.unicase.metamodel.util.ProjectChangeObserver;
import org.unicase.model.UnicaseModelElement;
import org.unicase.model.task.WorkPackage;
import org.unicase.ui.common.util.EventUtil;
import org.unicase.workspace.Workspace;
import org.unicase.workspace.WorkspaceManager;
import org.unicase.workspace.WorkspacePackage;

public class ModelView extends ViewPart implements ProjectChangeObserver{

	public static final String ID = "plugin.modelview.views.ModelView";

    private Project activeProject;
    private UnicaseModelElement input;
	
    private AdapterImpl adapterImpl;
	private TreeViewer treeViewer;
	private Workspace workspace;
	private TabFolder tabFolder;

    
    private EList<ModelElement> modelElementList;
	
	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	
	public ModelView() {
		 this.input = null;
		 
		 workspace = WorkspaceManager.getInstance().getCurrentWorkspace();
         if (workspace.getActiveProjectSpace() != null) {
                 activeProject = workspace.getActiveProjectSpace().getProject();
                 activeProject.addProjectChangeObserver(ModelView.this);        
                 
                 /*modelElementList = activeProject.getAllModelElements();                 
                 
                 System.out.println("*************");
                 for (ModelElement tempElement : modelElementList)
                 {
                	 if (tempElement instanceof UnicaseModelElement){
                		 System.out.println("Model Element: " + tempElement.toString());	 
                	 }                	 
                	 else
                	 {
                		 System.out.println("**NOT** a Model Element: " + tempElement.toString());
                	 }
                 }
                 System.out.println("activePoject:  " + activeProject.toString());
                 System.out.println("*************");*/
                 
         }
         adapterImpl = new AdapterImpl() {
             @Override
             public void notifyChanged(Notification msg) {
                     if ((msg.getFeatureID(Workspace.class)) == WorkspacePackage.WORKSPACE__ACTIVE_PROJECT_SPACE) {
                             // remove old listeners
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
             }
     };
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(Composite parent) {
    	
    	treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new ModelViewContentProvider());
		treeViewer.setLabelProvider(new ModelViewLabelProvider());
		treeViewer.setInput(activeProject);
            
    }

    public void setInput(UnicaseModelElement newInput) {
        input = newInput;
        refreshView();
    }
    
    public void refreshView() {
        if (input == null) {
                return;
        }
        
        System.out.println("IN REFRESH: " + WorkspaceManager.getProjectSpace(input).getProjectName());
        
        if (input instanceof WorkPackage) {
            WorkPackage wp = (WorkPackage) input;
        }
    }

	public void setFocus() {
        EventUtil.logFocusEvent("plugin.modelview.views.ModelView");
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
		
	}

	@Override
	public void projectDeleted(Project arg0) {
		// TODO Auto-generated method stub
		
	}
}