//package simulator.view;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Frame;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JSpinner;
//import javax.swing.JTextArea;
//
//import simulator.model.RoadMap;
//import simulator.model.SimulatedObject;
//
//abstract public class AddEventDialog<T extends SimulatedObject, U> extends JDialog {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	
//	private String _title;
//	private int _status;
//	
//	private JComboBox<T> _object; //objeto sobre el que se produce el cambio
//	private JLabel _objL;
//	private JComboBox<U> _toChange; //el cambio
//	private JLabel _valueL;
//	private JSpinner _ticksSpinner;
//
//	public AddEventDialog(Frame frame, String title) {
//		super(frame, true);
//		_title = title;
//		initGUI();
//	}
//
//	private void initGUI() {
//		_status = 0;
//		
//		this.setTitle(_title);
//		
//		JPanel mainPanel = new JPanel();
//		mainPanel.setLayout(new BorderLayout());
//		setContentPane(mainPanel);
//		
//	    JTextArea help = new JTextArea();
//		help.setEditable(false);
//		_title.toLowerCase();
//		help.setText("Schedule an event to " + _title + " of a vehicle after a given"
//				+ " number o simulation ticks from now.");
//		help.setBackground(mainPanel.getBackground());
//		help.setWrapStyleWord(true);
//		help.setLineWrap(true);
//		help.setFont(new Font("TextArea.font", Font.BOLD, 14));
//		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
//		
//		mainPanel.add(help, BorderLayout.PAGE_START);
//		
//		JPanel settingsPanel = new JPanel();
//		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));
//		  
//		 _objL = new JLabel();
//		  settingsPanel.add(_objL);
//		  
//		  _object = new JComboBox<>();
//		  _object.setBackground(Color.WHITE);
//		  settingsPanel.add(_object);
//		  
//		  settingsPanel.add(Box.createHorizontalGlue());
//		  
//		  _valueL = new JLabel();
//		  settingsPanel.add(_valueL);
//		  
//		  _toChange = new JComboBox<>();
//		  _toChange.setBackground(Color.WHITE);
//		  
//		  settingsPanel.add(_toChange);
//		  
//		  settingsPanel.add(Box.createHorizontalGlue());
//		  
//		  JLabel ticks = new JLabel("Ticks: ");
//		  settingsPanel.add(ticks);
//
//		  _ticksSpinner = new JSpinner();
//		  settingsPanel.add(_ticksSpinner);
//		
//		  settingsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
//		   
//		mainPanel.add(settingsPanel, BorderLayout.CENTER);
//		
//		
//		JPanel buttonsPanel = new JPanel();
//		
//		JButton cancelButton = new JButton("Cancel");
//		cancelButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				_status = 0;
//				AddEventDialog.this.setVisible(false);
//			}
//		});
//		buttonsPanel.add(cancelButton);
//		
//		JButton okButton = new JButton("OK");
//		okButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (_object.getSelectedItem() != null && Integer.parseInt(_ticksSpinner.getValue().toString()) > 0) {
//					_status = 1;
//					AddEventDialog.this.setVisible(false);
//				}
//				else if(Integer.parseInt(_ticksSpinner.getValue().toString()) < 1) {
//					JOptionPane.showMessageDialog(null, "El valor de ticks tiene que ser mayor a 0", "Error", JOptionPane.WARNING_MESSAGE);;
//				}
//			}
//		});
//		buttonsPanel.add(okButton);
//
//		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 5));
//		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
//		
//		setPreferredSize(new Dimension(600, 200));
//		pack();
//		setResizable(false);
//		setVisible(false);
//	}
//	
//	protected JComboBox<T> get_object(){
//		return _object;
//	}
//	protected JComboBox<U> get_toChange(){
//		return _toChange;
//	}
//	protected int get_ticks() {
//		return Integer.parseInt(_ticksSpinner.getValue().toString());
//	}
//	protected void setLabels(String toChange, String value) {
//		this._objL.setText(toChange);
//		this._valueL.setText(value);
//	}
//	protected int getStatus() {
//		return _status;
//	}
//	
//	protected abstract int open(RoadMap map);
//	
//}
