package ro.dani.masini;

import java.util.Date;

public class Standard extends Masina {

	private Transmisie transmisie;

	public Standard(Transmisie transmisie, int id, int nrKm, int anFabric, boolean isDiesel) {
		super(id, nrKm, anFabric, isDiesel);
		this.transmisie = transmisie;
	}

	public double calcPolita() {
		double val = new Date().getYear() - anFabric;
		if (val <= 0)
			val = 1;
		val *= 100;
		if (isDiesel)
			val += 500;
		if (nrKm > 200000)
			val += 500;
		return val;
	}

	public double discout() {
		return calcPolita() * (5 / 100);
	}
}
