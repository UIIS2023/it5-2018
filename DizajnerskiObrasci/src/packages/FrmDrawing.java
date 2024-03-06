package packages;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmDrawing extends JFrame {

	private JPanel contentPane;
	private PnlDrawing panel= new PnlDrawing(this);
	private final ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnSelection;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnModify;
	private JToggleButton tglbtnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Sacic Jelena IT5/2018");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(panel, BorderLayout.CENTER);
		
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
		
		tglbtnModify = new JToggleButton("Modify");
		tglbtnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modify();
			}
		});
		toolBar.add(tglbtnModify);
		
		tglbtnDelete = new JToggleButton("Delete");
		tglbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		toolBar.add(tglbtnDelete);
		
		btnGroup.add(tglbtnSelection);
		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		btnGroup.add(tglbtnRectangle);
		btnGroup.add(tglbtnCircle);
		btnGroup.add(tglbtnDonut);
		btnGroup.add(tglbtnModify);
		btnGroup.add(tglbtnDelete);
	}
	
	protected void delete(){
		int option = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
		Shape selected = panel.getSelected();
		if(selected != null){
			if(option == 0){
			panel.getShape().remove(selected);
			}
		}
		panel.repaint();
	}
	
	protected void modify(){
		Shape selected = panel.getSelected();
		if(selected != null){
			if(selected instanceof Point){
				Point point = (Point) selected;
				DlgPoint dlg = new DlgPoint();
				dlg.getTxtX().setText(Integer.toString(point.getX()));
				dlg.getTxtY().setText(Integer.toString(point.getY()));
				dlg.getBtnColor().setBackground(point.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					point.setX(dlg.getP().getX());
					point.setY(dlg.getP().getY());
					point.setColor(dlg.getP().getColor());
				}
			}
			else if(selected instanceof Line){
				Line line = (Line) selected;
				DlgLine dlg = new DlgLine();
				//dlg.setLine(line);
				dlg.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
				dlg.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
				dlg.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
				dlg.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
				dlg.getBtnColor().setBackground(line.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					Point p1 = new Point(dlg.getLine().getStartPoint().getX(), dlg.getLine().getStartPoint().getY());
					Point p2 = new Point(dlg.getLine().getEndPoint().getX(), dlg.getLine().getEndPoint().getY());
					line.setStartPoint(p1);
					line.setEndPoint(p2);
					line.setColor(dlg.getLine().getColor());
				}
			}
			else if(selected instanceof Rectangle){
				Rectangle rect = (Rectangle) selected;
				DlgRectangle dlg = new DlgRectangle();
				//dlg.setRectangle(rect);
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(rect.getUpperLeftPoint().getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(rect.getUpperLeftPoint().getY()));
				dlg.getTxtWidth().setText(Integer.toString(rect.getWidth()));
				dlg.getTxtHeight().setText(Integer.toString(rect.getHeight()));
				dlg.getBtnInnerColor().setBackground(rect.getInnerColor());
				dlg.getBtnOuterColor().setBackground(rect.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					rect.getUpperLeftPoint().setX(dlg.getRect().getUpperLeftPoint().getX());
					rect.getUpperLeftPoint().setY(dlg.getRect().getUpperLeftPoint().getY());
					rect.setWidth(dlg.getRect().getWidth());
					rect.setHeight(dlg.getRect().getHeight());
					rect.setColor(dlg.getRect().getColor());
					rect.setInnerColor(dlg.getRect().getInnerColor());
				}
			}
			else if(selected instanceof Donut){
				Donut donut = (Donut) selected;
				DlgDonut dlg = new DlgDonut();
				//dlg.setDonut(donut);
				dlg.getTxtCenterX().setText(Integer.toString(donut.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(donut.getCenter().getY()));
				dlg.getTxtRadius().setText(Integer.toString(donut.getRadius()));
				dlg.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
				dlg.getBtnInnerColor().setBackground(donut.getInnerColor());
				dlg.getBtnOuterColor().setBackground(donut.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					donut.getCenter().setX(dlg.getDonut().getCenter().getX());
					donut.getCenter().setY(dlg.getDonut().getCenter().getY());
					try {
						donut.setRadius(dlg.getDonut().getRadius());
					} catch (Exception e) {
						e.printStackTrace();
					}
					donut.setInnerRadius(dlg.getDonut().getInnerRadius());
					donut.setColor(dlg.getDonut().getColor());
					donut.setInnerColor(dlg.getDonut().getInnerColor());
				}
			}
			else if(selected instanceof Circle){
				Circle circle = (Circle) selected;
				DlgCircle dlg = new DlgCircle();
				//dlg.setCircle(circle);
				dlg.getTxtCenterX().setText(Integer.toString(circle.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(circle.getCenter().getY()));
				dlg.getTxtRadius().setText(Integer.toString(circle.getRadius()));
				dlg.getBtnInnerColor().setBackground(circle.getInnerColor());
				dlg.getBtnOuterColor().setBackground(circle.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					circle.getCenter().setX(dlg.getCircle().getCenter().getX());
					circle.getCenter().setY(dlg.getCircle().getCenter().getY());
					try {
						circle.setRadius(dlg.getCircle().getRadius());
					} catch (Exception e) {
						e.printStackTrace();
					}
					circle.setColor(dlg.getCircle().getColor());
					circle.setInnerColor(dlg.getCircle().getInnerColor());
				}
			}
		}
		panel.repaint();
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

}
