package mvc;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DrawingFrame extends JFrame{
	
	private JPanel contentPane;
	private final ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnSelection;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnModify;
	private JToggleButton tglbtnDelete;
	private JToggleButton tglbtnUndo;
	private JToggleButton tglbtnRedo;
	private JToggleButton tglbtnToBack;
	private JToggleButton tglbtnToFront;
	private JToggleButton tglbtnBringToBack;
	private JToggleButton tglbtnBringToFront;
	private JLabel lblInnerColor;
	private JButton btnInnerColor;
	private JLabel lblOuterColor;
	private JButton btnOuterColor;
	private JPanel logPanel;
	private JScrollPane scrollPane;
	private JList<String> logList;
	private DefaultListModel<String> modelLogList = new DefaultListModel<String>();
	private JPanel filePanel;
	private JButton btnSaveLog;
	private JButton btnSaveDrawing;
	private JButton btnOpenLog;
	private JButton btnOpenDrawing;
	private JButton btnNextLogLine;
 
	/**
	 * Launch the application.
	 */
	
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	public DrawingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1200, 600);
			setTitle("Sacic Jelena IT5/2018");
			setResizable(false);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			filePanel = new JPanel();
			filePanel.setBackground(Color.LIGHT_GRAY);
			contentPane.add(filePanel, BorderLayout.WEST);
			
			btnSaveLog = new JButton("Save log");
			btnSaveLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.saveLog();
				}
			});
			
			btnSaveDrawing = new JButton("Save drawing");
			btnSaveDrawing.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.saveDrawing();
				}
			});
			
			btnOpenLog = new JButton("Open log");
			btnOpenLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.openLog();
				}
			});
			
			btnOpenDrawing = new JButton("Open drawing");
			btnOpenDrawing.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.openDrawing();
				}
			});
			
			btnNextLogLine = new JButton("Next log line");
			btnNextLogLine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.nextLogLine();
				}
			});
			btnNextLogLine.setEnabled(false);//da na pocetku ne bi bilo dostupno dugme nego tek kad se otvori log fajl
			
			GroupLayout gl_filePanel = new GroupLayout(filePanel);
			gl_filePanel.setHorizontalGroup(
				gl_filePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_filePanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_filePanel.createParallelGroup(Alignment.LEADING)
							.addComponent(btnNextLogLine)
							.addComponent(btnOpenDrawing)
							.addComponent(btnOpenLog)
							.addComponent(btnSaveDrawing)
							.addComponent(btnSaveLog))
						.addGap(10))
			);
			gl_filePanel.setVerticalGroup(
				gl_filePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_filePanel.createSequentialGroup()
						.addGap(5)
						.addComponent(btnSaveLog)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSaveDrawing)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnOpenLog)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnOpenDrawing)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNextLogLine)
						.addContainerGap(242, Short.MAX_VALUE))
			);
			filePanel.setLayout(gl_filePanel);
			
			contentPane.add(view, BorderLayout.CENTER);
			
			JToolBar toolBar = new JToolBar();
			contentPane.add(toolBar, BorderLayout.NORTH);
			
			tglbtnSelection = new JToggleButton("Selection");
			toolBar.add(tglbtnSelection);
			
			tglbtnPoint = new JToggleButton("Point");
			toolBar.add(tglbtnPoint);
			
			tglbtnLine = new JToggleButton("Line");
			toolBar.add(tglbtnLine);
			
			tglbtnRectangle = new JToggleButton("Rectangle");
			toolBar.add(tglbtnRectangle);
			
			tglbtnCircle = new JToggleButton("Circle");
			toolBar.add(tglbtnCircle);
			
			tglbtnDonut = new JToggleButton("Donut");
			toolBar.add(tglbtnDonut);
			
			tglbtnHexagon = new JToggleButton("Hexagon");
			toolBar.add(tglbtnHexagon);
			
			tglbtnModify = new JToggleButton("Modify");
			tglbtnModify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.modify();
				}
			});
			toolBar.add(tglbtnModify);
			
			tglbtnDelete = new JToggleButton("Delete");
			tglbtnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.delete();
				}
			});
			toolBar.add(tglbtnDelete);
			
			tglbtnUndo = new JToggleButton("Undo");
			tglbtnUndo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.undo();
				}
			});
			toolBar.add(tglbtnUndo);
			
			tglbtnRedo = new JToggleButton("Redo");
			tglbtnRedo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.redo();
				}
			});
			toolBar.add(tglbtnRedo);
			
			tglbtnToBack = new JToggleButton("To Back");
			tglbtnToBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.toBack();
				}
			});
			toolBar.add(tglbtnToBack);
			
			tglbtnToFront = new JToggleButton("To Front");
			tglbtnToFront.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.toFront();
				}
			});
			toolBar.add(tglbtnToFront);
			
			tglbtnBringToBack = new JToggleButton("Bring To Back");
			tglbtnBringToBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.bringToBack();
				}
			});
			toolBar.add(tglbtnBringToBack);
			
			tglbtnBringToFront = new JToggleButton("Bring To Front");
			tglbtnBringToFront.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controller.bringToFront();
				}
			});
			toolBar.add(tglbtnBringToFront);
			
			lblInnerColor = new JLabel("   Active inner color: ");
			toolBar.add(lblInnerColor);
			
			btnInnerColor = new JButton("   ");
			btnInnerColor.setBackground(Color.WHITE);
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color color = JColorChooser.showDialog(null, "Select color", btnInnerColor.getBackground());
					if(color != null){//da se spreci da se ponisti trenutno aktivna boja ako se klikne na cancel u dijalogu za odabir boje 
						btnInnerColor.setBackground(color);
					}
				}
			});
			toolBar.add(btnInnerColor);
			
			lblOuterColor = new JLabel("   Active outer color: ");
			toolBar.add(lblOuterColor);
			
			btnOuterColor = new JButton("   ");
			btnOuterColor.setBackground(Color.BLACK);
			btnOuterColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color color = JColorChooser.showDialog(null, "Select color", btnOuterColor.getBackground());
					if(color != null){//da se spreci da se ponisti trenutno aktivna boja ako se klikne na cancel u dijalogu za odabir boje 
						btnOuterColor.setBackground(color);
					}
				}
			});
			toolBar.add(btnOuterColor);
			
			btnGroup.add(tglbtnSelection);
			btnGroup.add(tglbtnPoint);
			btnGroup.add(tglbtnLine);
			btnGroup.add(tglbtnRectangle);
			btnGroup.add(tglbtnCircle);
			btnGroup.add(tglbtnDonut);
			btnGroup.add(tglbtnHexagon);
			btnGroup.add(tglbtnModify);
			btnGroup.add(tglbtnDelete);
			btnGroup.add(tglbtnUndo);
			btnGroup.add(tglbtnRedo);
			btnGroup.add(tglbtnToBack);
			btnGroup.add(tglbtnToFront);
			btnGroup.add(tglbtnBringToBack);
			btnGroup.add(tglbtnBringToFront);
			btnGroup.add(btnInnerColor);
			btnGroup.add(btnOuterColor);
			
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClick(e);//kaze obrati se kontroleru
			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		
		logPanel = new JPanel();
		contentPane.add(logPanel, BorderLayout.SOUTH);
		logPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		logPanel.add(scrollPane);
		
		logList = new JList<String>(modelLogList);
		scrollPane.setViewportView(logList);
	}
	
	public JToggleButton getTglbtnSelection(){
		return tglbtnSelection;
	}
	public JToggleButton getTglbtnPoint(){
		return tglbtnPoint;
	}
	public JToggleButton getTglbtnLine(){
		return tglbtnLine;
	}
	public JToggleButton getTglbtnRectangle(){
		return tglbtnRectangle;
	}
	public JToggleButton getTglbtnCircle(){
		return tglbtnCircle;
	}
	public JToggleButton getTglbtnDonut(){
		return tglbtnDonut;
	}
	
	public JToggleButton getTglbtnHexagon(){
		return tglbtnHexagon;
	}
	
	public JToggleButton getTglbtnDelete(){
		return tglbtnDelete;
	}
	
	public JToggleButton getTglbtnModify(){
		return tglbtnModify;
	}
	
	public JToggleButton getTglbtnUndo(){
		return tglbtnUndo;
	}
	
	public JToggleButton getTglbtnRedo(){
		return tglbtnRedo;
	}
	
	public JToggleButton getTglbtnToBack(){
		return tglbtnToBack;
	}
	
	public JToggleButton getTglbtnToFront(){
		return tglbtnToFront;
	}
	
	public JToggleButton getTglbtnBringToBack(){
		return tglbtnBringToBack;
	}
	
	public JToggleButton getTglbtnBringToFront(){
		return tglbtnBringToFront;
	}
	
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}
	
	public DefaultListModel<String> getModelLogList() {
		return modelLogList;
	}
	
	public void setModelLogList(DefaultListModel<String> modelLogList) {
		this.modelLogList = modelLogList;
	}
	
	public JButton getBtnNextLogLine() {
		return btnNextLogLine;
	}

	public DrawingView getView() {
		return view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}
