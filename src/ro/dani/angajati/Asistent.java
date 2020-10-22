package ro.dani.angajati;

import java.util.Date;

public class Asistent extends Angajat {
	private static final double coeficientSalariat = 1;

	public Asistent(int id, String nume, String prenume, Date dataNastere, Date dataAngajarii) {
		super(id, nume, prenume, dataNastere, coeficientSalariat, dataAngajarii);
	}
}
