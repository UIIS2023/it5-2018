package command;

import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command {
	private Shape shape;
	private DrawingModel model;
	
	public ToFrontCmd(Shape shape,DrawingModel model){
		this.shape = shape;
		this.model = model;
	}
	@Override
	public void execute() {
		int index = model.getShapes().indexOf(shape);
		Shape pomShape = model.get(index+1);
		model.getShapes().remove(index+1);
		model.getShapes().remove(index);
		model.getShapes().add(index, pomShape);
		model.getShapes().add(index+1, shape);
	}

	@Override
	public void unexecute() {
		int index = model.getShapes().indexOf(shape);
		Shape pomShape = model.get(index-1);
		model.getShapes().remove(index);
		model.getShapes().remove(index-1);
		model.getShapes().add(index-1, shape);
		model.getShapes().add(index, pomShape);
	}

	@Override
	public Shape getShape(){
		return shape;
	}
	
	public String toString() {
		return "TOFRONT " + shape;
	}
	
}
