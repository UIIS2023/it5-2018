package command;

import geometry.Donut;
import geometry.Shape;
import mvc.DrawingModel;

public class AddDonutCmd implements Command {
	private Donut donut;
	private DrawingModel model;
	
	public AddDonutCmd(Donut donut,DrawingModel model){
		this.donut = donut;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(donut);
		/*if(donut.isSelected()){
			model.addSelectedShape(donut);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(donut);
		/*if(donut.isSelected()){
			model.removeSelectedShape(donut);
		}*/
	}

	@Override
	public Shape getShape(){
		return (Shape)donut;
	}

	public String toString() {
		return "ADD " + donut;
	}
	
}
