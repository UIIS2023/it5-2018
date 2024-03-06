package mvc;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import geometry.Shape;
import geometry.Point;


public class DrawingView  extends JPanel{
	
	private DrawingModel model = new DrawingModel();
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
	 
	public DrawingModel getModel() {
		return model;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while(it.hasNext()){
			it.next().draw(g);
		}
	}	
	
}
