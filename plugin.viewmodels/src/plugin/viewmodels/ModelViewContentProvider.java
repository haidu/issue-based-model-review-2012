package plugin.viewmodels;



import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.unicase.model.UnicaseModelElement;
import org.unicase.model.task.util.TaxonomyAccess;



public class ModelViewContentProvider extends AdapterFactoryContentProvider{
	
    /**
     * . Constructor
     */
	public ModelViewContentProvider(){
		super (new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}
	
    public Object[] getElements(Object object) {
    	if (object instanceof UnicaseModelElement)
    	{
    		UnicaseModelElement me = (UnicaseModelElement) object;
    		
    		Set<UnicaseModelElement> openers = TaxonomyAccess.getInstance().getOpeningLinkTaxonomy().getOpeners(me);
    		Set<UnicaseModelElement> leafOpeners = TaxonomyAccess.getInstance().getOpeningLinkTaxonomy().getLeafOpeners(me);
    		
    		Set<UnicaseModelElement> ret = new HashSet<UnicaseModelElement>();
    		ret.addAll(openers);
       		ret.addAll(leafOpeners);   	    		
    		
    		return ret.toArray(new Object[ret.size()]);

    	}
    	else{
    		return super.getElements(object);     
    	}

    }  
}
