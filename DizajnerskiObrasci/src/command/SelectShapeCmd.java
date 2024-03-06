package command;

import java.util.ArrayList;
import java.util.Iterator;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {
	private ArrayList<Shape> selectedShapes;
	private ArrayList<Shape> pom = new ArrayList<Shape>();
	private Shape selectedShape;
	private int i=0;
	private StringBuilder sb;
	
	public SelectShapeCmd(ArrayList<Shape> selectedShapes){
		this.selectedShapes = selectedShapes;
		
		Iterator<Shape> itPom = selectedShapes.iterator();
		
		while(itPom.hasNext()){
			Shape shape = itPom.next();
			
			pom.add(shape);
		}
		
		Iterator<Shape> it = pom.iterator();
		while(it.hasNext()){
			Shape shape = it.next();
			if(!shape.isSelected()){
				i++;
			}
		}
	}
	@Override
	public void execute() {
		//shape.setSelected(true);
		sb = new StringBuilder();
		
		if(i == 1) {
			selectedShapes.clear();//jer se u unexecute brise iz liste selektovanih oblika pri deselekciji
			Iterator<Shape> itPom = pom.iterator();
			
			while(itPom.hasNext()){
				Shape shape = itPom.next();
				
				selectedShapes.add(shape);
			}
			
			sb.append("SELECT ");
			Iterator<Shape> itSelected = pom.iterator();
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				if(!shape.isSelected()){
					shape.setSelected(true);
					selectedShape = shape;
					sb.append(shape.toString());
				}
			}
		}
		else {
			sb.append("DESELECT ");
			Iterator<Shape> itSelected = pom.iterator();
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				if(shape.isSelected()){
					shape.setSelected(false);
					sb.append(shape.toString());
					sb.append(" ");
				}
			}
		}
	}

	@Override
	public void unexecute() {
		//shape.setSelected(false);
		
		if(i == 1) {
			Iterator<Shape> itSelected = pom.iterator();
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				if(shape == selectedShape){
					shape.setSelected(false);
				}
			}
			selectedShapes.remove(selectedShape);//da bi se kad se odselektuje oblik ako smo imali dva selektovana da moze modify da postane dostupno
		}
		else {
			Iterator<Shape> itSelected = pom.iterator();
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				if(!shape.isSelected()){
					selectedShapes.add(shape);
					shape.setSelected(true);
				}
			}
		}
	}

	@Override
	public Shape getShape(){
		return null;
	}
	
	public String toString() {
		return sb.toString();
	}
}
