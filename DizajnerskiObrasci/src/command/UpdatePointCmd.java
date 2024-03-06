package command;

import geometry.Point;
import geometry.Shape;

public class UpdatePointCmd implements Command {
	private Point oldState;
	private Point newState;
	private Point original = new Point();
	
	public UpdatePointCmd(Point oldState, Point newState){
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		/*original.setX(oldState.getX());
		original.setY(oldState.getY());
		original.setColor(oldState.getColor());*/
		original = oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
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
