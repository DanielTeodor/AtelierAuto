package ro.dani.angajati;

import java.util.Date;

public class Director extends Angajat {
	private static final double coeficientSalariat = 2;

	public Director(int id, String nume, String prenume, Date dataNastere, Date dataAngajarii) {
		super(id, nume, prenume, dataNastere, coeficientSalariat, dataAngajarii);
	}
}
