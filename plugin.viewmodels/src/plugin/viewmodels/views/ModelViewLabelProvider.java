package plugin.viewmodels.views;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ModelViewLabelProvider extends ColumnLabelProvider{

    private AdapterFactoryLabelProvider adapterFactoryLabelProvider;

        /**
         * Constructor.
         * 
         * @param contentProvider Model element currently open in status view
         */
        public ModelViewLabelProvider() {
            super();
            this.adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
                    ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getText(Object element) {
            return adapterFactoryLabelProvider. getText(element);
        }
        
		@Override        
		public Image getImage(Object element) {
			return getAdapterFactoryLabelProvider().getImage(element);
		}
        
    	/**
    	 * . this returns the adapterFactoryLabelProvider used to retrieve text and images
    	 *
    	 * @return The adapterFactoryLabelProvider used to retrieve text and images
    	 */
    	public AdapterFactoryLabelProvider getAdapterFactoryLabelProvider() {
    		return adapterFactoryLabelProvider;
    	}        
        
}
