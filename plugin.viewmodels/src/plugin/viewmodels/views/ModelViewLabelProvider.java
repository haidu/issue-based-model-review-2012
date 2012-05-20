package plugin.viewmodels.views;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;


/**
 * Label Provider for the estimate column. If the item is a workitem, it shows the estimate of the work item. If the
 * item is another modelelement, it aggregates the estimate of annotated workitems.
 * 
 * @author helming
 */
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
            return adapterFactoryLabelProvider.getText(element);
        }
        
}
