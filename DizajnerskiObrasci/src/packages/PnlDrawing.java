package packages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import geometry.Line;
import geometry.Point;
import geometry.Shape;

public class PnlDrawing extends JPanel {
	private FrmDrawing frame;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Point startPoint;
	private Shape selected;
	
	public PnlDrawing() {

	}
	
	public PnlDrawing(FrmDrawing frame) {
		this.frame = frame;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				thisMouseClicked(e);
			}
		});
	}
	protected void thisMouseClicked(MouseEvent e){
		Shape newShape = null;
		if(frame.getTglbtnSelection().isSelected()){
			selected = null;
			Iterator<Shape> it = shapes.iterator();
			while(it.hasNext()){
				Shape shape = it.next();
				shape.setSelected(false);
				if(shape.contains(e.getX(), e.getY())){
					selected = shape;
				}
			}
			if(selected != null){
				selected.setSelected(true);
			}
		}
		else if(frame.getTglbtnPoint().isSelected()){
			newShape = new Point(e.getX(), e.getY(),false, Color.BLACK);
		}
		else if(frame.getTglbtnLine().isSelected()){
			if(startPoint == null){
				startPoint = new Point(e.getX(), e.getY());
			}
			else{
				newShape = new Line(startPoint, new Point(e.getX(), e.getY()), false, Color.BLACK);
				startPoint = null;
			}
		}
		else if(frame.getTglbtnRectangle().isSelected()){
			DlgRectangle dlg = new DlgRectangle();
			dlg.setModal(true);
			dlg.getTxtUpperLeftPointX().setText(Integer.toString(e.getX()));
			dlg.getTxtUpperLeftPointY().setText(Integer.toString(e.getY()));
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getRect();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(frame.getTglbtnCircle().isSelected()){
			DlgCircle dlg = new DlgCircle();
			dlg.setModal(true);
			dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
			dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getCircle();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(frame.getTglbtnDonut().isSelected()){
			DlgDonut dlg = new DlgDonut();
			dlg.setModal(true);
			dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
			dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getDonut();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(newShape != null){
			shapes.add(newShape);
		}
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Iterator<Shape> it = shapes.iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
	}
	
	public Shape getSelected(){
		return selected;
	}
	
	public ArrayList<Shape> getShape(){
		return shapes;
	}
}
