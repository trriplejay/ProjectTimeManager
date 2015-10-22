package com.bosch.zeitverwaltung.funktion;

/**
 * <p>
 * Verwaltet eine Auswertung, die in Form von Resourcen-Strings übergeben
 * werden. Eine Auswertung besteht aus einem Auswertungs-Modell und einem
 * dazugehörigen View. Beide Klassen werden mittels XML-Dateien der Anwendung
 * bekannt gemacht, dafür ist das <em>FunktionsControl</em> zuständig. Es
 * erzeugt dann für jede gefundene Auswertung ein Objekt diesen Typs und meldet
 * die Auswertung in der Anwendung an.
 * 
 * @author Lars Geyer
 * @see FunktionsControl
 * @see AuswertungsModell
 * @see AuswertungsView
 */
public class Auswertung {
	private AuswertungsView<AuswertungsModell> view;

	/**
	 * <p>
	 * Erzeugt eine Auswertung aus dem zur Auswertung gehörenden Modell- und dem
	 * View-Objekt. Dazu werden die als String übergebenen Klassennamen
	 * dynamisch erzeugt. Danach initialisiert der Konstruktor die Auswertung.
	 * Die Exceptions werden dann geworfen, wenn aus den Strings die passenden
	 * Klassen nicht gefunden werden konnten.
	 * </p>
	 * 
	 * @param modell
	 *            Name der Modellklasse
	 * @param view
	 *            Name der Viewklasse
	 * @throws ClassNotFoundException
	 *             Wenn die Klassen nicht geladen werden können
	 * @throws IllegalAccessException
	 *             Wenn bei der Instanzierung ein Problem auftritt
	 * @throws InstantiationException
	 *             Wenn bei der Instanzierung ein Problem auftritt
	 */
	@SuppressWarnings("unchecked")
	public Auswertung(String modell, String view)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> modellKlasse = getClass().getClassLoader().loadClass(modell);
		Class<?> viewKlasse = getClass().getClassLoader().loadClass(view);
		AuswertungsModell modellInstanz = (AuswertungsModell) modellKlasse
				.newInstance();
		this.view = (AuswertungsView) viewKlasse.newInstance();
		this.view.setModell(modellInstanz);
	}

	/**
	 * <p>
	 * Auswertungs-Funktion: Methode wird aufgerufen, wenn die Auswertung
	 * angefordert wird.
	 * </p>
	 */
	public void auswertung() {
		view.showAuswertung();
	}
}
