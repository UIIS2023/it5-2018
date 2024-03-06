package mvc;

import java.util.ArrayList;
import geometry.Shape;
import geometry.Point;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	private Point startPoint;
	private Shape selected;
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public ArrayList<Shape> getSelectedShapes() {
		return selectedShapes;
	}
	 
	public Shape getSelected(){
		return selected;
	}
	
	public void setSelected(Shape shape){
		this.selected = shape;
	}
	
	public Point getStartPoint(){
		return startPoint;
	}
	
	public void setStartPoint(Point startPoint){
		this.startPoint = startPoint;
	}

	public void add(Shape p){
		shapes.add(p);
	}
	
	public void remove(Shape p){
		shapes.remove(p);
	}
	
	public void addSelectedShape(Shape p){
		selectedShapes.add(p);
	}
	
	public void removeSelectedShape(Shape p){
		selectedShapes.remove(p);
	}
	
	public Shape get(int index){
		return shapes.get(index);
	}
}
