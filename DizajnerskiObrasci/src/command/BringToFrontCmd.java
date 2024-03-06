package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {
	private Shape shape;
	private DrawingModel model;
	private int indexShape;
	
	public BringToFrontCmd(Shape shape,DrawingModel model){
		this.shape = shape;
		this.model = model;
		indexShape =  model.getShapes().indexOf(shape);
	}
	@Override
	public void execute() {
		int index = model.getShapes().indexOf(shape);
		while(index  < model.getShapes().size()-1){
			Shape pomShape = model.get(index+1);
			model.getShapes().remove(index+1);
			model.getShapes().remove(index);
			model.getShapes().add(index, pomShape);
			model.getShapes().add(index+1, shape);
			index = model.getShapes().indexOf(shape);
		}
	}

	@Override
	public void unexecute() {
		int index = model.getShapes().indexOf(shape);
		while(index > indexShape){
			Shape pomShape = model.get(index-1);
			model.getShapes().remove(index);
			model.getShapes().remove(index-1);
			model.getShapes().add(index-1, shape);
			model.getShapes().add(index, pomShape);
			index = model.getShapes().indexOf(shape);
		}
	}

	@Override
	public Shape getShape(){
		return shape;
	}
	
	public String toString() {
		return "BRINGTOFRONT " + shape;
	}
	
}
