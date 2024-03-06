package command;

import adapter.HexagonAdapter;
import geometry.Shape;
import mvc.DrawingModel;

public class RemoveHexagonCmd implements Command {
	private HexagonAdapter hexagonAdapt;
	private DrawingModel model;
	
	public RemoveHexagonCmd(HexagonAdapter hexagonAdapt,DrawingModel model){
		this.hexagonAdapt = hexagonAdapt;
		this.model = model;
	}
	@Override
	public void execute() {
		model.remove(hexagonAdapt);
	}

	@Override
	public void unexecute() {
		model.add(hexagonAdapt);
	}

	@Override
	public Shape getShape(){
		return (Shape)hexagonAdapt;
	}
	
}
