package observer;

import mvc.DrawingModel;

public interface Observer {
	//void update(double bitcoinPrice, double etherPrice);
	void update(DrawingModel model);
}
