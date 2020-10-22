package ro.dani.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ro.dani.angajati.Angajat;
import ro.dani.angajati.Asistent;
import ro.dani.angajati.Director;
import ro.dani.angajati.Mecanic;
import ro.dani.exceptions.NotFoundIDException;

public class Manager {
	public List<Angajat> angajati = new ArrayList<>();
	private int idCounter = 0;

	public List<Angajat> getAngajati() {
		return angajati;
	}

	public void adaugaDirector(String nume, String prenume, Date dataNastere, Date dataAngajarii) {
		Angajat angajatNou = new Director(incrementeazaId(), nume, prenume, dataNastere, dataAngajarii);
		angajati.add(angajatNou);
	}

	public void adaugaMecanic(String nume, String prenume, Date dataNastere, Date dataAngajarii) {
		Angajat angajatNou = new Mecanic(incrementeazaId(), nume, prenume, dataNastere, dataAngajarii);
		angajati.add(angajatNou);
	}

	public void adaugaAsistent(String nume, String prenume, Date dataNastere, Date dataAngajarii) {
		Angajat angajatNou = new Asistent(incrementeazaId(), nume, prenume, dataNastere, dataAngajarii);
		angajati.add(angajatNou);
	}

	public void stergeAngajat(int id) {
		angajati = angajati.stream().filter(a -> a.getId() != id).collect(Collectors.toList());
	}

	public void changeNume(int id, String nume) throws NotFoundIDException {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		if (angajatOptional.isPresent()) {
			angajatOptional.get().setNume(nume);
		} else
			throw new NotFoundIDException("ID invalid, va rugam incercati inca odata!");
	}

	public void changePrenume(int id, String prenume) throws NotFoundIDException {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		if (angajatOptional.isPresent()) {
			angajatOptional.get().setPrenume(prenume);
		} else
			throw new NotFoundIDException("ID invalid, va rugam incercati inca odata!");
	}

	public void changeDataNastere(int id, Date dataNastere) throws NotFoundIDException {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		if (angajatOptional.isPresent()) {
			angajatOptional.get().setDataNastere(dataNastere);
		} else
			throw new NotFoundIDException("ID invalid, va rugam incercati inca odata!");

	}

	public void changeDataAngajare(int id, Date dataAngajare) throws NotFoundIDException {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		if (angajatOptional.isPresent()) {
			angajatOptional.get().setDataAngajarii(dataAngajare);
		} else
			throw new NotFoundIDException("ID invalid, va rugam incercati inca odata!");

	}

	public double salariu(int id) throws NotFoundIDException {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		if (angajatOptional.isPresent()) {
			return angajatOptional.get().calculeazaSalariu();
		} else
			throw new NotFoundIDException("ID invalid, va rugam incercati inca odata!");

	}

	public boolean esteDataNastereValabila(Date dataNastere) {
		return ((new Date()).getYear() - dataNastere.getYear()) >= 18;
	}

	public boolean esteDataAngajareValabila(Date dataAngajare) {
		return dataAngajare.getTime() <= (new Date()).getTime();
	}

	private int incrementeazaId() {
		return idCounter++;
	}

	public boolean esteNumeValabil(String nume) {
		return nume != null && nume.length() < 31;
	}

	public Angajat getAngajat(int id) {
		Optional<Angajat> angajatOptional = angajati.stream().filter(a -> a.getId() == id).findAny();
		return angajatOptional.get();
	}

	public boolean esteDataAngajareValabilaCuDataNasterii(Date dataAngajare, Date dataNastere) {

		return dataAngajare.getYear() - dataNastere.getYear() >= 18;
	}

}
