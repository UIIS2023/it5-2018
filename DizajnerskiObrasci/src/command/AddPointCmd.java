package command;

import mvc.DrawingModel;
import geometry.Point;
import geometry.Shape;

public class AddPointCmd implements Command {
	private Point point;
	private DrawingModel model;
	
	public AddPointCmd(Point point,DrawingModel model){
		this.point = point;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(point);
		/*if(point.isSelected()){
			model.addSelectedShape(point);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(point);
		/*if(point.isSelected()){
			model.removeSelectedShape(point);
		}*/
	}
	
	@Override
	public Shape getShape(){
		return (Shape)point;
	}
	
	public String toString() {
		return "ADD " + point;
	}
	
}
