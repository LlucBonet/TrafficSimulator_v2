package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import simulator.control.Controller;

public class ChangeCO2ClassDialog extends JDialog {

	private Controller _ctrl;
	
	public ChangeCO2ClassDialog() {
		// TODO Auto-generated constructor stub
	}
	
	public ChangeCO2ClassDialog(Controller ctrl) {
		_ctrl = ctrl;
		initChangeCO2ClassDialog();
	}


	public ChangeCO2ClassDialog(Frame owner) {
		super(owner);
	}

	public ChangeCO2ClassDialog(Dialog owner) {
		super(owner);
	}

	public ChangeCO2ClassDialog(Window owner) {
		super(owner);
	}

	public ChangeCO2ClassDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}

	public ChangeCO2ClassDialog(Frame owner, String title) {
		super(owner, title);
	}

	public ChangeCO2ClassDialog(Dialog owner, boolean modal) {
		super(owner, modal);
	}

	public ChangeCO2ClassDialog(Dialog owner, String title) {
		super(owner, title);
	}

	public ChangeCO2ClassDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
	}

	public ChangeCO2ClassDialog(Window owner, String title) {
		super(owner, title);
	}

	public ChangeCO2ClassDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public ChangeCO2ClassDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public ChangeCO2ClassDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
	}

	public ChangeCO2ClassDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public ChangeCO2ClassDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public ChangeCO2ClassDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}
	
	public void initChangeCO2ClassDialog(){
		//tiene JComboBox(1er bloque transp.) para vehiculo, JLabel para el texto
		this.setTitle("Change CO2 Class");
		this.setLayout(new BorderLayout());
		
		JTextArea text = new JTextArea();
		text.setText("Schedule an event to change the CO2 class of a vehicle after a given"
				+ "number o simulation ticks from now.");
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		this.add(text, BorderLayout.PAGE_START);
		
		
		
		this.pack();
		this.setVisible(true);
	}
	

}
