
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.GroupLayout.SequentialGroup;

import bi.FilesManagement;

/**	This class represents the Graphic User Interface of the application.
 * @author Netanel
 * @version 1.1
 */
@SuppressWarnings("serial")
public class Gui extends JFrame {
	
	//	#region data members

	/**	This is an instance of the class.
	 * 
	 */
	private static Gui m_s_instance = null;
	
	/**	The height of the frame.
	 * 
	 */
	private final int m_frameHeight = 315;
	
	/**	The width of the frame.
	 * 
	 */
	private final int m_framewidth = 560;
		
	/**	An JPanel which contains 2 JPanels in the frame.
	 * 
	 */
	private JPanel m_container;
	
	/**	The section of the folder's browse and select.
	 * 
	 */
	private JPanel m_folderPanel;
	
	/**	The section of the copy process progress.
	 * 
	 */
	private JPanel m_actionAndProgressPanel;
	
	/**	Label that displays "Path:". 
	 * 
	 */
	private JLabel m_pathLabel;
	
	/**	Label that displays "Progress:". 
	 * 
	 */
	private JLabel m_progressLabel;

	/**	Text field that displays the source folder which chosen to distributed.
	 * 
	 */
	private JTextField m_pathTextField;
	
	/**	Text area which displays the progress of the distribution of the images.
	 * 
	 */
	private JTextArea m_progressTextArea;
	
	/**	The scroll bars of the prgress text area.
	 * 
	 */
	private JScrollPane m_scrollPane;
	
	/**	Button which allows the user to chose the images source folder which be distributed.
	 * 
	 */
	private JButton m_sourceFolderBrowseButton;

	/**	Button which starts the distribution process.
	 * 
	 */
	private JButton m_startButton;
	
	/**	Button which close the application.
	 * 
	 */
	private JButton m_closeButton;

	/**	The layout of the folder JPanel.
	 * 
	 */
	private GroupLayout m_folderGroupLayout;
	
	/** The layout of the actionAndProgress JPanel.
	 * 
	 */
	private GroupLayout m_actionAndProgressGroupLayout;
	
	/**	The object which take care to the images distribution.
	 * 
	 */
	private FilesManagement m_fileManagement;
	
	//	#endRegion data members

	//	#region properties
	
	/*	no properties.	*/
	
	//	#endRegion properties

	//	#region constructors

	/** Default constructor
	 * 
	 */
	public Gui() {
		/*	empty constructor, Singleton design pattern	*/
	}

	/**	Constructor that receives 1 parameter
	 * @param title The title of the application.
	 */
	private Gui(String title) {
		super(title);
		this.m_container = new JPanel();
		this.m_folderPanel = new JPanel();
		this.m_actionAndProgressPanel = new JPanel();
		this.m_pathLabel = new JLabel( "Path:" );
		this.m_progressLabel = new JLabel( "Progress:                                                        0%" );
		this.m_pathTextField = new JTextField( "Please choose the folder which contains the images you want to distribute..." );
		this.m_progressTextArea = new JTextArea();
		this.m_scrollPane = new JScrollPane( this.m_progressTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		this.m_sourceFolderBrowseButton = new JButton( "Browse" );
		this.m_startButton = new JButton( "Start" );
		this.m_closeButton = new JButton( "Close" );
		this.m_folderGroupLayout = new GroupLayout( this.m_folderPanel );
		this.m_actionAndProgressGroupLayout = new GroupLayout( this.m_actionAndProgressPanel );
		this.m_fileManagement = new FilesManagement().getInstance();

		//	initialize the text area.
		this.m_progressTextArea.setEditable(false);
		this.m_progressTextArea.setLineWrap(true);
		this.m_progressTextArea.setWrapStyleWord(true);
		
		//	defines the sizes of the panels.
		this.m_folderPanel.setPreferredSize( new Dimension( this.m_framewidth, ( this.m_frameHeight / 24 ) *7 ) );
		this.m_actionAndProgressPanel.setPreferredSize( new Dimension( this.m_framewidth, ( this.m_frameHeight / 24 ) * 17 ) );

		//	initialize & arrange the JFrame and JPanels layouts.
		frameInitialize( this.m_frameHeight, this.m_framewidth );
		this.m_container.setLayout( new BoxLayout( this.m_container, BoxLayout.Y_AXIS ) );
		arrangeFolderPanelGroupLayout( this.m_folderGroupLayout );
		this.m_folderPanel.setLayout( this.m_folderGroupLayout );
		arrangeActionAndProgressGroupLayout( this.m_actionAndProgressGroupLayout );
		this.m_actionAndProgressPanel.setLayout( this.m_actionAndProgressGroupLayout );

		//	adding action listeners to JComponents.
		ButtonActionListener buttonActionListener = new ButtonActionListener();
		this.m_closeButton.addActionListener( buttonActionListener );
		this.m_startButton.addActionListener( buttonActionListener );
		this.m_sourceFolderBrowseButton.addActionListener( buttonActionListener );
		TextFieldActionListener textFieldActionListener = new TextFieldActionListener();
		this.m_pathTextField.addFocusListener( textFieldActionListener );
		
		//	adding all the JComponents to JFrame.
		this.m_container.add( this.m_folderPanel );
		this.m_container.add( this.m_actionAndProgressPanel );
		add( this.m_container );
	}
	
	//	#endRegion constructors
	
	//	#region private methods

	/**	Method that initializes the frame.
	 * 
	 */
	private void frameInitialize(int height, int width) {
		setSize( width, height );
		setLocationRelativeTo( null );				//	center the frame.
		setVisible( true );
		setResizable( false );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	}

	/**	Arranges the objects in the folder JPanel.
	 * @param layout is the Folder JPanel layout.
	 */
	private void arrangeFolderPanelGroupLayout(GroupLayout layout) {
		GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
		horizontalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_pathLabel )
				.addComponent( this.m_pathTextField ) );
		horizontalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_sourceFolderBrowseButton ) );
		GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
		verticalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_pathLabel ) );
		verticalGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
				.addComponent( this.m_pathTextField )
				.addComponent( this.m_sourceFolderBrowseButton ) );
		updateGroupLayout( layout, horizontalGroup, verticalGroup );
	}

	/**	Arranges the objects in the actionAndProgress JPanel.
	 * @param layout is the Folder actionAndProgress layout.
	 */
	private void arrangeActionAndProgressGroupLayout(GroupLayout layout) {
		GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
		horizontalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_progressLabel )
				.addComponent( this.m_scrollPane ) );
		horizontalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_startButton )
				.addComponent( this.m_closeButton ) );
		GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
		verticalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_progressLabel )
				.addComponent( this.m_startButton ) );
		verticalGroup.addGroup( layout.createParallelGroup()
				.addComponent( this.m_scrollPane )
				.addComponent( this.m_closeButton ) );
		updateGroupLayout( layout, horizontalGroup, verticalGroup );
	}
	
	/**	Initialize group layout.
	 * @param tempLayout is the layout which be initialize.
	 * @param tempHorizontalGroup group of objects in the horizontal order.
	 * @param tempVerticalGroup group of objects in the vertical order
	 */
	private void updateGroupLayout(GroupLayout tempLayout, SequentialGroup tempHorizontalGroup,
			SequentialGroup tempVerticalGroup) {
		tempLayout.setAutoCreateGaps( true );
		tempLayout.setAutoCreateContainerGaps( true );
		tempLayout.setVerticalGroup( tempVerticalGroup );
		tempLayout.setHorizontalGroup( tempHorizontalGroup );
	}
	
	//	#endRegion private methods

	//	#region action listeners

	/**	Class that implements the action listener interface, for button actions.
	 * @author Netanel
	 *
	 */
	private class ButtonActionListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch( e.getActionCommand() ) {
				case "Browse":
					final JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
					fileChooser.showOpenDialog( fileChooser );
					if( fileChooser.getSelectedFile() != null ) {
						m_pathTextField.setText( fileChooser.getSelectedFile().toString() );
					}
					break;
				case "Start":
					m_fileManagement.setSourceFolder( m_pathTextField.getText() );
					new Thread( m_fileManagement ).start();
					break;
				case "Close":
					System.exit(0);
					break;
				}
			}
	}
	
	
	private class TextFieldActionListener implements FocusListener {

		/** Removes the text from path text field when get focused.
		 * 
		 */
		@Override
		public void focusGained(FocusEvent e) {
			m_pathTextField.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	
	//	#endRegion action listeners
	
	//	#region public methods

	/**	Method that returns an instance of this class
	 * @return the instance of the class.
	 */
	@SuppressWarnings("static-access")
	public Gui getInstance() {
		if( Gui.this.m_s_instance == null ) {
			Gui.this.m_s_instance = new Gui( "Images distribution by date" );
		}
		return( Gui.this.m_s_instance );
	}

	/**	Prints new sentence in the Progress Text Area.
	 * @param sentence is the new sentence.
	 */
	public void appendTextToProgressTextArea(String sentence) {
		this.m_progressTextArea.append( sentence + "\n" );
		this.m_progressTextArea.setCaretPosition(this.m_progressTextArea.getDocument().getLength());
	}

	/**	Updates the percentage of the progress.
	 * @param number the new number.
	 */
	public void updatePercentage(float number) {
		this.m_progressLabel.setText( "Progress:                                                        "
				+ number + "%" );
	}
	
	//	#endRegion public methods

	//	#region encapsulation methods
	
	/*	no encapsulation methods	*/
	
	//	#endRegion encapsulation methods

}

