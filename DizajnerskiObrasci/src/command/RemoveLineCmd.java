package command;

import mvc.DrawingModel;
import geometry.Line;
import geometry.Shape;

public class RemoveLineCmd implements Command {
	private Line line;
	private DrawingModel model;
	
	public RemoveLineCmd(Line line,DrawingModel model){
		this.line = line;
		this.model = model;
	}
	@Override
	public void execute() {
		model.remove(line);
	}

	@Override
	public void unexecute() {
		model.add(line);
	}

	@Override
	public Shape getShape(){
		return (Shape)line;
	}
	
}
