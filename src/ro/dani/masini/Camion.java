package ro.dani.masini;

import java.util.Date;

public class Camion extends Masina {

	private int tonaj;

	public Camion(int tonaj, int id, int nrKm, int anFabric, boolean isDiesel) {
		super(id, nrKm, anFabric, isDiesel);
		this.tonaj = tonaj;
	}

	public double calcPolita() {
		double val = new Date().getYear() - anFabric;
		if (val <= 0)
			val = 1;
		val *= 300;
		if (nrKm > 800000)
			val += 700;
		return val;
	}

	public double discout() {
		return calcPolita() * (15 / 100);
	}
}
