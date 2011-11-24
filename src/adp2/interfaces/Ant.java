package adp2.interfaces;


public interface Ant {
	Path traveledPath();
	int position();
	void step();
	boolean hasFinished();
	int weglaenge();
	double alpha();
	Double getWaitingTime();
	int prePosition();
}


