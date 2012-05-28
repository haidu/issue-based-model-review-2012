package plugin.viewmodels.editors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.EditorPart;
import org.unicase.model.UnicaseModelElement;
import plugin.viewmodels.ModelViewContentProvider;
import plugin.viewmodels.ModelViewEditorInput;
import plugin.viewmodels.ModelViewLabelProvider;

public class ModelViewEditor extends EditorPart {
	
	public static final String ID = "plugin.viewmodels.modelvieweditor";

	private TableViewer tableViewer;
	private UnicaseModelElement eModelElement;
	private ScrolledForm sForm;
	private FormToolkit toolkit;
	private ModelViewEditorInput mveInput;
	private EObject me;	
	private ToolItem issueBtn;
	private Text textName;
	
	public ModelViewEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof ModelViewEditorInput)) {
			throw new RuntimeException("Wrong input");
		}
			mveInput = (ModelViewEditorInput) input;
			me = mveInput.getModelElement();
			setSite(site);
			setInput(input);		 
	}

    @Override
    public void createPartControl(Composite parent) {  
    	/*Creating the form and toolkit*/
    	ManagedForm mForm = new ManagedForm(parent);
    	sForm = mForm.getForm();
    	toolkit = mForm.getToolkit();
    	sForm.setText("Issue Based Model Review:");  
    	GridLayout formLayout = new GridLayout();
    	formLayout.numColumns = 2;
        sForm.getBody().setLayout(formLayout);  
        
        /*Section with the model tables, and toolbar with a 'Add Issue' button*/
    	Section section = toolkit.createSection(sForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);		
        section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
	    section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));	    
		section.setText("Unicase Models:");
		section.setDescription("Section Descrition");	
		
		ToolBar tbar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
		issueBtn = new ToolItem(tbar, SWT.PUSH);
        issueBtn.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
        issueBtn.setToolTipText("Add Issue");
		section.setTextClient(tbar);
		section.setExpanded(true);	
		
		Composite client = toolkit.createComposite(section, SWT.FLAT);
		GridLayout sectionLayout = new GridLayout();
		sectionLayout.numColumns = 1;
		client.setLayout(sectionLayout);
		
		Table sectionTable = toolkit.createTable(client, SWT.NONE);
		GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableGridData.heightHint = 300;
		tableGridData.widthHint = 180;
		sectionTable.setLayoutData(tableGridData);
		toolkit.paintBordersFor(client);
		section.setClient(client);		
		
		tableViewer = new TableViewer(sectionTable);
		tableViewer.setContentProvider(new ModelViewContentProvider());
		tableViewer.setLabelProvider(new ModelViewLabelProvider());
		tableViewer.setInput(me);
		
		/*Model name and description in a text field*/
		textName = toolkit.createText(sForm.getBody(), "No model selected..");
		textName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		sForm.reflow(true);		
    	getSite().setSelectionProvider(tableViewer);		
    	addSelectionListener();
    	addIssueListener();
    }

	private void addSelectionListener() {
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection structSelection = (IStructuredSelection) event.getSelection();
					eModelElement = (UnicaseModelElement) structSelection.getFirstElement();
					textName.setText("Name: " + eModelElement.getName() + ",\n Description: " + eModelElement.getDescription());

				}
			}

		});

	}
	
	private void addIssueListener(){
		issueBtn.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {				
				IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class); 
				try {
					handlerService.executeCommand("org.unicase.ui.common.commands.annotateIssue", null);
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotDefinedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotEnabledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotHandledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFocus() {
		//form.setFocus();
		sForm.setFocus();
	}
	
	/**
	 * Disposes the toolkit
	 */
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}

}
