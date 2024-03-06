package command;

import geometry.Circle;
import geometry.Shape;
import mvc.DrawingModel;

public class AddCircleCmd implements Command {
	private Circle circle;
	private DrawingModel model;
	
	public AddCircleCmd(Circle circle,DrawingModel model){
		this.circle = circle;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(circle);
		/*if(circle.isSelected()){
			model.addSelectedShape(circle);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(circle);
		/*if(circle.isSelected()){
			model.removeSelectedShape(circle);
		}*/
	}

	@Override
	public Shape getShape(){
		return (Shape)circle;
	}
	
	public String toString() {
		return "ADD " + circle;
	}
	
}
