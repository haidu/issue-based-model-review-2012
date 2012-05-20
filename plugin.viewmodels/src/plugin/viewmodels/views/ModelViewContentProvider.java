package plugin.viewmodels.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.unicase.metamodel.util.ModelUtil;
import org.unicase.model.Annotation;
import org.unicase.model.UnicaseModelElement;
import org.unicase.model.task.WorkItem;
import org.unicase.model.task.WorkPackage;
import org.unicase.model.task.util.TaxonomyAccess;


public class ModelViewContentProvider extends AdapterFactoryContentProvider{
	
	private UnicaseModelElement root;
	
    /**
     * . Constructor
     */
	public ModelViewContentProvider(){
		super (new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}
	
    /**
     * . {@inheritDoc}
     */
    @Override
    /*public Object[] getElements(Object object) {
            if (object instanceof UnicaseModelElement) {
                    UnicaseModelElement me = (UnicaseModelElement) object;
                    Set<UnicaseModelElement> result = TaxonomyAccess.getInstance().getOpeningLinkTaxonomy().getLeafOpeners(me);
                    return result.toArray(new Object[result.size()]);

            } else {
                    return new Object[0];
            }

    }*/
	
    public Object[] getElements(Object object) {
    	
    	System.out.println("OBJ CLASS " + object.getClass().toString());
    	
            if (object instanceof WorkPackage) {
            	System.out.println("WORKPACKAGE yass!!!!");
                    Set<UnicaseModelElement> elementsForWorkPackage = getElementsForWorkPackage(object);
                    return elementsForWorkPackage.toArray(new Object[elementsForWorkPackage.size()]);

            } else if (object instanceof UnicaseModelElement) {
            	System.out.println("UNICASE MODEL ELEMENT yass");
                    return getElementsForModelElement(object);

            } else {
            	System.out.println("ELSEE yass");
                    return super.getElements(object);
            }

    }
    /**
     * . Returns all model elements being annotated by WorkItems contained in this WorkPackage.
     * 
     * @param object WorkPackage
     * @return
     */
    private Set<UnicaseModelElement> getElementsForWorkPackage(Object object) {

            Set<UnicaseModelElement> ret = new HashSet<UnicaseModelElement>();
            WorkPackage workPackage = (WorkPackage) object;
            List<WorkItem> containedWorkItems = workPackage.getAllContainedWorkItems();
            for (WorkItem workItem : containedWorkItems) {
                    ret.addAll(workItem.getAnnotatedModelElements());
                    if (workItem instanceof WorkPackage) {
                            ret.addAll(getElementsForWorkPackage(workItem));
                    }
            }
            return ret;
    }

    /**
     * . Returns all openers of input element. I believe that for a model element as input there should also be an
     * implementation like that of a WorkPackage, i.e. for every opener that is an Annotation, instead of this opener
     * its annotated model elements should be shown. This is implemented but currently commented out.
     * 
     * @param object model element
     * @return
     */
    private Object[] getElementsForModelElement(Object object) {
            UnicaseModelElement me = (UnicaseModelElement) object;

            Set<UnicaseModelElement> openers = new HashSet<UnicaseModelElement>();
            openers.addAll(TaxonomyAccess.getInstance().getOpeningLinkTaxonomy().getOpeners(me));

            Set<UnicaseModelElement> ret = new HashSet<UnicaseModelElement>();
            ret.addAll(openers);

            ret.addAll(me.getAnnotations());
            return ret.toArray(new Object[ret.size()]);
    }
    
    /**
     * Returns the model element currently open in status view.
     * 
     * @return the model element currently open in status view.
     */
    public UnicaseModelElement getRoot() {
            return root;
    }

}
