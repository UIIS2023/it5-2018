package command;

import adapter.HexagonAdapter;
import geometry.Shape;
import mvc.DrawingModel;

public class AddHexagonCmd implements Command {
	private HexagonAdapter hexagonAdapt;
	private DrawingModel model;
	
	public AddHexagonCmd(HexagonAdapter hexagonAdapt,DrawingModel model){
		this.hexagonAdapt = hexagonAdapt;
		this.model = model;
	}
	@Override
	public void execute() {
		model.add(hexagonAdapt);
		/*if(hexagonAdapt.isSelected()){
			model.addSelectedShape(hexagonAdapt);
		}*/
	}

	@Override
	public void unexecute() {
		model.remove(hexagonAdapt);
		/*if(hexagonAdapt.isSelected()){
			model.removeSelectedShape(hexagonAdapt);
		}*/
	}

	@Override
	public Shape getShape(){
		return (Shape)hexagonAdapt;
	}
	
	public String toString() {
		return "ADD " + hexagonAdapt;
	}
	
}
