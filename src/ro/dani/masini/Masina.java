package ro.dani.masini;

public abstract class Masina {

	public int id;
	protected int nrKm;
	protected int anFabric;
	protected boolean isDiesel;
	public int unitati;

	public Masina(int id, int nrKm, int anFabric, boolean isDiesel) {
		this.id = id;
		this.nrKm = nrKm;
		this.anFabric = anFabric;
		this.isDiesel = isDiesel;
	}

	public abstract double calcPolita();

	public abstract double discout();

	public Integer getUnitati() {
		return unitati;
	}
}
