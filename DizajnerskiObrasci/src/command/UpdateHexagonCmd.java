package command;

import adapter.HexagonAdapter;
import geometry.Shape;

public class UpdateHexagonCmd implements Command {
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter(-1,-1,-1);
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState){
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		/*original.getHexagon().setX(oldState.getHexagon().getX());
		original.getHexagon().setY(oldState.getHexagon().getY());
		original.getHexagon().setR(oldState.getHexagon().getR());
		original.getHexagon().setBorderColor(oldState.getHexagon().getBorderColor());
		original.getHexagon().setAreaColor(oldState.getHexagon().getAreaColor());*/
		original = oldState.clone();
		
		oldState.getHexagon().setX(newState.getHexagon().getX());
		oldState.getHexagon().setY(newState.getHexagon().getY());
		oldState.getHexagon().setR(newState.getHexagon().getR());
		oldState.getHexagon().setBorderColor(newState.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(newState.getHexagon().getAreaColor());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		oldState.getHexagon().setX(original.getHexagon().getX());
		oldState.getHexagon().setY(original.getHexagon().getY());
		oldState.getHexagon().setR(original.getHexagon().getR());
		oldState.getHexagon().setBorderColor(original.getHexagon().getBorderColor());
		oldState.getHexagon().setAreaColor(original.getHexagon().getAreaColor());
		oldState.setColor(original.getColor());
	}

	@Override
	public Shape getShape(){
		return (Shape)oldState;
	}
	
	public String toString() {
		return "MODIFY " + original + " into " + newState;
	}
	
}
