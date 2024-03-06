package command;

import geometry.Circle;
import geometry.Shape;

public class UpdateCircleCmd implements Command {
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();
	
	public UpdateCircleCmd(Circle oldState, Circle newState){
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		/*original.getCenter().setX(oldState.getCenter().getX());
		original.getCenter().setY(oldState.getCenter().getY());
		try {
			original.setRadius(oldState.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		original.setColor(oldState.getColor());
		original.setInnerColor(oldState.getInnerColor());*/
		original = oldState.clone();
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		try {
			oldState.setRadius(newState.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		try {
			oldState.setRadius(original.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}

	@Override
	public Shape getShape(){
		return (Shape)oldState;
	}
	
	public String toString() {
		return "MODIFY " + original + " into " + newState;
	}
	
}
