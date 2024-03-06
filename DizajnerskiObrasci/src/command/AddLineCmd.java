package command;

import mvc.DrawingModel;
import geometry.Line;
import geometry.Shape;

public class AddLineCmd implements Command {
	private Line line;
	private DrawingModel model;
	
	public AddLineCmd(Line line,DrawingModel model){
		this.line = line;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(line);
		/*if(line.isSelected()){
			model.addSelectedShape(line);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(line);
		/*if(line.isSelected()){
			model.removeSelectedShape(line);
		}*/
	}
	
	@Override
	public Shape getShape(){
		return (Shape)line;
	}
	
	public String toString() {
		return "ADD " + line;
	}
	
}
