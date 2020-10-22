package ro.dani.masini;

import java.util.Date;

public class Autobuz extends Masina {

	private int nrLoc;

	public Autobuz(int nrLoc, int id, int nrKm, int anFabric, boolean isDiesel) {
		super(id, nrKm, anFabric, isDiesel);
		this.nrLoc = nrLoc;
	}

	public double calcPolita() {
		double val = new Date().getYear() - anFabric;
		if (val <= 0)
			val = 1;
		val *= 200;
		if (isDiesel)
			val += 1000;
		if (nrKm > 200000)
			val += 1000;
		else if (nrKm > 100000)
			val += 500;
		return val;
	}

	public double discout() {
		return calcPolita() * (10 / 100);
	}
}
