package command;

import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingModel;

public class AddRectangleCmd implements Command {
	private Rectangle rectangle;
	private DrawingModel model;
	
	public AddRectangleCmd(Rectangle rectangle,DrawingModel model){
		this.rectangle = rectangle;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(rectangle);
		/*if(rectangle.isSelected()){
			model.addSelectedShape(rectangle);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(rectangle);
		/*if(rectangle.isSelected()){
			model.removeSelectedShape(rectangle);
		}*/
	}

	@Override
	public Shape getShape(){
		return (Shape)rectangle;
	}
	
	public String toString() {
		return "ADD " + rectangle;
	}
	
}
