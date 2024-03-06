package observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mvc.DrawingModel;

public class SelectedShape implements Observable{
	private DrawingModel model;
	
	private List<Observer> observers = new ArrayList<Observer>();

	public void setModel(DrawingModel model) {
		this.model = model;
		notifyObservers();
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void remveObserver(Observer observer) {
		observers.remove(observer);
	}
	
	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext())
			it.next().update(model);
		
	}
}
