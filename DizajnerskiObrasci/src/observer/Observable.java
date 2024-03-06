package observer;

public interface Observable {
	void addObserver(Observer observer);//dodavanje observerra
	
	void remveObserver(Observer observer);//brisanje observera
	
	void notifyObservers();//obavestavanje
}
