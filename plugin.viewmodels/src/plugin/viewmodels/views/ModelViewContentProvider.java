package plugin.viewmodels.views;



import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;



public class ModelViewContentProvider extends AdapterFactoryContentProvider{
	
    /**
     * . Constructor
     */
	public ModelViewContentProvider(){
		super (new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}
	
    public Object[] getElements(Object object) {
    	
    	System.out.println("***OBJ CLASS is " + object.getClass().toString());   
        return super.getElements(object);     

    }
}
