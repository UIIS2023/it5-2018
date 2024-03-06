package command;

import mvc.DrawingModel;
import geometry.Point;
import geometry.Shape;

public class RemovePointCmd implements Command {
	private Point point;
	private DrawingModel model;
	
	public RemovePointCmd(Point point,DrawingModel model){
		this.point = point;
		this.model = model;
	}
	@Override
	public void execute() {
		model.remove(point);
	}

	@Override
	public void unexecute() {
		model.add(point);
	}

	@Override
	public Shape getShape(){
		return (Shape)point;
	}
	
}
