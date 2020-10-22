package ro.dani.aplicatie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ro.dani.angajati.Angajat;
import ro.dani.exceptions.FutureDateException;
import ro.dani.exceptions.Not18YearsOldWhenHiredException;
import ro.dani.exceptions.NotFoundIDException;
import ro.dani.exceptions.Under18Exception;
import ro.dani.manager.Manager;
import ro.dani.manager.MasinaBuilder;
import ro.dani.masini.Masina;
import ro.dani.masini.Standard;
import ro.dani.masini.Transmisie;

public class AplicatieMain {

	private static Scanner scanner = new Scanner(System.in);
	private static Manager manager;
	private static MasinaBuilder masinaBuilder;
	public static List<Masina> listaAsteptare = new ArrayList<>();

	public static void main(String[] args) {
		manager = new Manager();
		masinaBuilder = new MasinaBuilder();
		while (true) {
			System.out.print("Alege optiune:");
			System.out.println("              - Actiuni agnajati (press 1)");
			System.out.println("              - Actiuni atelier (press 2)");
			System.out.println("              - Iesire (press 3)");
			int alegere = scanner.nextInt();
			if (alegere == 1) {
				actiuniAngajati();
			} else if (alegere == 2) {
				actiuniAtelier();
			}

			else if (alegere == 3)
				break;

		}
	}

	public static int getRandomNumber() {
		int min = 1;
		int max = 5;
		return (int) ((Math.random() * (max - min)) + min);
	}

	private static void actiuniAtelier() {
		while (true) {
			if (manager.getAngajati().size() == 0) {
				System.out.println(
						"Atelierul nu poate fi deschis daca nu exista cel putin un angajat. Adaugati cel putin un angajat.");
				return;
			}
			System.out.println();
			System.out.println("                   - Oricare angajat (press 1)");
			System.out.println("                   - Anumit angajat  (press 2)");
			System.out.println("                   - Afisare timp ocupat per angajat  (press 3)");
			System.out.println("                   - trecere timp  (press 4)");
			System.out.println("            	   - Inapoi (press 5)");

			int alegere = scanner.nextInt();
			if (alegere == 5)
				return;

			else if (alegere == 3) {
				List<Angajat> angajati = manager.getAngajati();
				for (Angajat a : angajati) {
					System.out.println("masini standard in lucru: " + a.nrMasStandInLucru
							+ " Are masina mare in lucru: " + a.masinaMareInLucru);
					System.out.println();
					System.out.print("Timipi de asteptare:   ");
					int total = 0;
					for (Masina entry : a.masiniInLucru) {
						System.out.print(entry.unitati + "   ");
						total += entry.unitati;
					}
					System.out.println("Total timp angajat cu id " + a.getId() + " este " + total);
				}
			}

			else if (alegere == 4) {
				System.out.println("            - introduceti unitate de timp de scazut");
				int unit = scanner.nextInt();
				trecereTimp(Integer.valueOf(unit));
			} else {

				System.out.println("                   - Creeaza masina (1 - Standard, 2 - Autobuz, 3 - Camion)");
				int alegereTipMasina = scanner.nextInt();
				Masina masina = null;
				if (alegereTipMasina == 1) {
					masina = masinaBuilder.adaugaStandard(Transmisie.Automat, 200000, 2000, true);
					adaugaMasinaStandardCuAlegere(masina, alegere);
				} else if (alegereTipMasina == 2) {
					masina = masinaBuilder.adaugaAutobuz(30, 500000, 2005, false);
					adaugaMasinaMareCuAlegere(masina, alegere);
				} else if (alegereTipMasina == 3) {
					masina = masinaBuilder.adaugaCamion(10, 400000, 2010, true);
					adaugaMasinaMareCuAlegere(masina, alegere);
				}
			}
		}
	}

	private static void adaugaMasinaStandardCuAlegere(Masina masina, int alegere) {
		if (alegere == 1)
			adaugaStandard(masina, Integer.valueOf(getRandomNumber()), null);
		else if (alegere == 2) {
			System.out.println("                   - alege id angajat dorit");
			int idAngajat = scanner.nextInt();
			adaugaStandard(masina, Integer.valueOf(getRandomNumber()), idAngajat);
		}
	}

	private static void adaugaMasinaMareCuAlegere(Masina masina, int alegere) {
		if (alegere == 1)
			adaugaMsinaMare(masina, Integer.valueOf(getRandomNumber()), null);
		else if (alegere == 2) {
			System.out.println("                   - alege id angajat dorit");
			int idAngajat = scanner.nextInt();
			adaugaMsinaMare(masina, Integer.valueOf(getRandomNumber()), idAngajat);
		}
	}

	private static void adaugaStandard(Masina mas, Integer timpDeLucru, Integer id) {
		if (id == null) {
			List<Angajat> angajati = manager.getAngajati();
			for (Angajat a : angajati) {
				if (a.nrMasStandInLucru < 3) {
					a.masiniInLucru.add(mas);
					mas.unitati = timpDeLucru;
					a.nrMasStandInLucru += 1;
					return;
				}
			}
			System.out.println("               Toti sunt ocupati!");
			System.out.println("                   - renunta (press 1)");
			System.out.println("                   - alege lista de asteptare  (press 2)");

			int alegere = scanner.nextInt();
			if (alegere == 1)
				return;
			else if (alegere == 2) {
				listaAsteptare.add(mas);
				mas.unitati = timpDeLucru;
			}
		} else {
			Angajat a = manager.getAngajat(id);
			if (a.nrMasStandInLucru < 3) {
				a.masiniInLucru.add(mas);
				mas.unitati = timpDeLucru;
				a.nrMasStandInLucru += 1;
			} else {
				System.out.println("               Este ocupat!");
				System.out.println("                   - renunta (press 1)");
				System.out.println("                   - alege unul liber  (press 2)");

				int alegere = scanner.nextInt();
				if (alegere == 1)
					return;
				else if (alegere == 2)
					adaugaStandard(mas, timpDeLucru, null);
			}
		}
	}

	private static void adaugaMsinaMare(Masina mas, Integer timpDeLucru, Integer id) {
		if (id == null) {
			List<Angajat> angajati = manager.getAngajati();
			for (Angajat a : angajati) {
				if (!a.masinaMareInLucru) {
					a.masiniInLucru.add(mas);
					mas.unitati = timpDeLucru;
					a.masinaMareInLucru = true;
					return;
				}
			}
			System.out.println("               Toti sunt ocupati!");
			System.out.println("                   - renunta (press 1)");
			System.out.println("                   - alege lista de asteptare  (press 2)");

			int alegere = scanner.nextInt();
			if (alegere == 1)
				return;
			else if (alegere == 2) {
				listaAsteptare.add(mas);
				mas.unitati = timpDeLucru;
			}
		} else {
			Angajat a = manager.getAngajat(id);
			if (!a.masinaMareInLucru) {
				a.masiniInLucru.add(mas);
				mas.unitati = timpDeLucru;
				a.masinaMareInLucru = true;
			} else {
				System.out.println("               Este ocupat!");
				System.out.println("                   - renunta (press 1)");
				System.out.println("                   - alege unul liber  (press 2)");

				int alegere = scanner.nextInt();
				if (alegere == 1)
					return;
				else if (alegere == 2)
					adaugaMsinaMare(mas, timpDeLucru, null);
			}
		}
	}

	private static void trecereTimp(Integer unitatiTimp) {
		List<Angajat> angajati = manager.getAngajati();
		for (Angajat a : angajati) {
			int unitatiRamase = a.trecereTimp(unitatiTimp);
			while (unitatiRamase > 0 && listaAsteptare.size() > 0) {
				boolean one = false;
				boolean two = false;
				for (Masina entry : listaAsteptare) {
					if (one && two)
						break;
					if (entry instanceof Standard && a.nrMasStandInLucru < 3) {
						a.masiniInLucru.add(entry);
						a.nrMasStandInLucru += 1;
						listaAsteptare = listaAsteptare.stream().filter(m -> m.id != entry.id)
								.collect(Collectors.toList());
						one = true;
					} else if ((!(entry instanceof Standard)) && !a.masinaMareInLucru) {
						a.masiniInLucru.add(entry);
						a.masinaMareInLucru = true;
						listaAsteptare = listaAsteptare.stream().filter(m -> m.id != entry.id)
								.collect(Collectors.toList());
						two = true;
					}
				}
				unitatiRamase = a.trecereTimp(unitatiRamase);
			}

		}

	}

	private static void actiuniAngajati() {
		while (true) {
			System.out.println("Alege optiune:");
			System.out.println("              - Sterge angajat (press 1)");
			System.out.println("              - Adauga Angajat (press 2)");
			System.out.println("              - Afiasare angajati (press 3)");
			System.out.println("              - editeaza nume (press 4)");
			System.out.println("              - editeaza prenume (press 5)");
			System.out.println("              - editeaza data nasterii (press 6)");
			System.out.println("              - editeaza data angajare (press 7)");
			System.out.println("              - Salariu (press 8)");
			System.out.println("              - Inapoi (press 9)");
			int alegere = scanner.nextInt();
			if (alegere == 1) {
				System.out.println("              - introduceti ID ul angajatului pe care doriti sa il stergeti");
				int id = scanner.nextInt();
				manager.stergeAngajat(id);
			} else if (alegere == 2) {
				int tipAngajat = alegeTipAngajat();
				scanner.nextLine();

				System.out.println("              - introduceti datele noului angajat:");
				String nume = introducetiNume("Nume");
				String prenume = introducetiNume("Prenume");
				Date dataNastere = introducetiDataNastere();
				Date dataAngajare = introducetiDataAngajare(dataNastere);
				if (tipAngajat == 1) {
					manager.adaugaDirector(nume, prenume, dataNastere, dataAngajare);
				} else if (tipAngajat == 2) {
					manager.adaugaMecanic(nume, prenume, dataNastere, dataAngajare);
				} else if (tipAngajat == 3) {
					manager.adaugaAsistent(nume, prenume, dataNastere, dataAngajare);
				}
			} else if (alegere == 3) {
				for (Angajat angajat : manager.getAngajati()) {
					afisazaAngajat(angajat);
				}
			} else if (alegere == 4) {
				scanner.nextLine();
				String nume = introducetiNume("Nume");
				int id = 0;
				while (true) {
					System.out.println("              - id angajat pt a fi editat:");
					id = scanner.nextInt();
					try {
						manager.changeNume(id, nume);
					} catch (NotFoundIDException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					break;
				}
				afisazaAngajat(manager.getAngajat(id));
			} else if (alegere == 5) {
				scanner.nextLine();
				String prenume = introducetiNume("prenume");
				int id = 0;
				while (true) {
					System.out.println("              - id angajat pt a fi editat:");
					id = scanner.nextInt();
					try {
						manager.changePrenume(id, prenume);
					} catch (NotFoundIDException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					break;
				}
				afisazaAngajat(manager.getAngajat(id));
			} else if (alegere == 6) {
				scanner.nextLine();
				Date dataNastere = introducetiDataNastere();
				int id = 0;
				while (true) {
					System.out.println("              - id angajat pt a fi editat:");
					id = scanner.nextInt();
					try {
						manager.changeDataNastere(id, dataNastere);
					} catch (NotFoundIDException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					break;
				}
				afisazaAngajat(manager.getAngajat(id));
			} else if (alegere == 7) {
				int id = 0;
				while (true) {
					System.out.println("              - id angajat pt a fi editat:");
					id = scanner.nextInt();
					scanner.nextLine();
					Date dataAngajare = introducetiDataAngajare(manager.getAngajat(id).getDataNastere());
					try {
						manager.changeDataAngajare(id, dataAngajare);
					} catch (NotFoundIDException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					break;
				}
				afisazaAngajat(manager.getAngajat(id));
			} else if (alegere == 8) {
				double salariu = 0;
				int id = 0;
				while (true) {
					System.out.println("              - id angajat");
					id = scanner.nextInt();
					try {
						salariu = manager.salariu(id);
					} catch (NotFoundIDException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					break;
				}
				afisazaAngajat(manager.getAngajat(id));
				System.out.println(" salariu: " + salariu);
			}

			else if (alegere == 9)
				return;
		}

	}

	private static void afisazaAngajat(Angajat angajat) {
		System.out.println("id: " + angajat.getId() + ", " + "nume: " + angajat.getNume() + ", " + "prenume: "
				+ angajat.getPrenume() + ", " + "dataNastere: " + angajat.getDataNastere() + ", " + "dataAngajarii: "
				+ angajat.getDataAngajarii() + ", ");
	}

	private static int alegeTipAngajat() {
		System.out.println("              - Alegeti tip angajat ( 1 - Director, 2 - Mecanic, 3 - Asistent)");
		while (true) {
			int tipAngajat = scanner.nextInt();
			if (tipAngajat == 1 || tipAngajat == 2 || tipAngajat == 3)
				return tipAngajat;
			System.out.println("              - Alegeti tip angajat ( 1 - Director, 2 - Mecanic, 3 - Asistent)");
		}
	}

	private static String introducetiNume(String tipNume) {
		System.out.println("                                                  - " + tipNume);
		String nume = null;
		while (true) {
			nume = scanner.nextLine();
			if (manager.esteNumeValabil(nume))
				return nume;
			else
				System.out.println("Nume gresit (Maxim 30 de caractere si minim 1), va rugam incercati inca odata!");
		}
	}

	private static Date introducetiDataNastere() {
		System.out.println("                                                  - Data nastere");
		Date dataNastere = null;
		while (true) {
			String dataNastereInput = scanner.nextLine();
			try {
				dataNastere = new Date(dataNastereInput);

				if (!manager.esteDataNastereValabila(dataNastere))
					throw new Under18Exception(
							"Nu se accepta o varsta mai mica de 18 ani, va rugam incercati inca odata!");
			} catch (IllegalArgumentException ex) {
				System.out.println("Format data incorect, reintroduceti (luna/ziua/an)");
				continue;
			} catch (Under18Exception ex) {
				System.out.println(ex.getMessage());
				continue;
			}
			return dataNastere;
		}
	}

	private static Date introducetiDataAngajare(Date dataNastere) {
		System.out.println("                                                  - Data angajarii");
		Date dataAngajare = null;
		while (true) {
			String dataAngajareInput = scanner.nextLine();
			try {
				dataAngajare = new Date(dataAngajareInput);

				if (!manager.esteDataAngajareValabila(dataAngajare))
					throw new FutureDateException(
							"Nu se accepta o data de angajare in viitor, va rugam incercati inca odata!");
				if (!manager.esteDataAngajareValabilaCuDataNasterii(dataAngajare, dataNastere))
					throw new Not18YearsOldWhenHiredException(
							"Nu se accepta o data de angajare la care angajatul nu are 18 ani, va rugam incercati inca odata!");
			} catch (IllegalArgumentException ex) {
				System.out.println("Format data incorect, reintroduceti (luna/ziua/an)");
				continue;
			} catch (FutureDateException ex) {
				System.out.println(ex.getMessage());
				continue;
			} catch (Not18YearsOldWhenHiredException ex) {
				System.out.println(ex.getMessage());
				continue;
			}
			return dataAngajare;
		}
	}
}
