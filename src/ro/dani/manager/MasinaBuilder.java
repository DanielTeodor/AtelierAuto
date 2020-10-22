package ro.dani.manager;

import ro.dani.masini.Autobuz;
import ro.dani.masini.Camion;
import ro.dani.masini.Standard;
import ro.dani.masini.Transmisie;

public class MasinaBuilder {
	private int idCounter = 0;

	public Standard adaugaStandard(Transmisie transmisie, int nrKm, int anFabric, boolean isDiesel) {
		return new Standard(transmisie, incrementeazaId(), nrKm, anFabric, isDiesel);
	}

	public Camion adaugaCamion(int tonaj, int nrKm, int anFabric, boolean isDiesel) {
		return new Camion(tonaj, incrementeazaId(), nrKm, anFabric, isDiesel);
	}

	public Autobuz adaugaAutobuz(int nrLoc, int nrKm, int anFabric, boolean isDiesel) {
		return new Autobuz(nrLoc, incrementeazaId(), nrKm, anFabric, isDiesel);
	}

	private int incrementeazaId() {
		return idCounter++;
	}
}
