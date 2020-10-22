package ro.dani.angajati;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ro.dani.masini.Masina;
import ro.dani.masini.Standard;

public abstract class Angajat {
	private int id;
	private String nume;
	private String prenume;
	private Date dataNastere;
	private double coeficientSalariat;
	private Date dataAngajarii;
	public int nrMasStandInLucru = 0;
	public boolean masinaMareInLucru = false;
	public List<Masina> masiniInLucru = new ArrayList<>();

	protected Angajat(int id, String nume, String prenume, Date dataNastere, double coeficientSalariat,
			Date dataAngajarii) {
		this.id = id;
		this.nume = nume;
		this.prenume = prenume;
		this.dataNastere = dataNastere;
		this.coeficientSalariat = coeficientSalariat;
		this.dataAngajarii = dataAngajarii;
	}

	public int getId() {
		return id;
	}

	public String getNume() {
		return nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public Date getDataNastere() {
		return dataNastere;
	}

	public double getCoeficientSalariat() {
		return coeficientSalariat;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public void setDataNastere(Date dataNastere) {
		this.dataNastere = dataNastere;
	}

	public Date getDataAngajarii() {
		return dataAngajarii;
	}

	public void setDataAngajarii(Date dataAngajarii) {
		this.dataAngajarii = dataAngajarii;
	}

	public double calculeazaSalariu() {
		return vechime() * coeficientSalariat * 1000;
	}

	private double vechime() {
		int aniDif = new Date().getYear() - dataAngajarii.getYear();
		if (aniDif > 0) {
			return aniDif;
		}
		return 1;
	}

	public int trecereTimp(Integer nrUnitati) {

		for (Masina entry : masiniInLucru) {
			if (nrUnitati == 0)
				return nrUnitati;
			Integer val = entry.getUnitati() - nrUnitati;
			if (val > 0) {
				nrUnitati = 0;
				entry.unitati = val;
			} else if (val == 0) {
				nrUnitati = 0;
				removeMasina(entry);
			} else {
				nrUnitati = val * -1;
				removeMasina(entry);
			}
		}
		return nrUnitati;
	}

	private void removeMasina(Masina mas) {
		masiniInLucru = masiniInLucru.stream().filter(m -> m.id != mas.id).collect(Collectors.toList());
		if (mas instanceof Standard) {
			nrMasStandInLucru -= 1;
		} else {
			masinaMareInLucru = false;
		}
	}
}
