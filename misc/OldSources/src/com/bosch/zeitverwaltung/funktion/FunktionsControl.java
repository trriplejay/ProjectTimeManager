package com.bosch.zeitverwaltung.funktion;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.w3c.dom.Element;

import com.bosch.zeitverwaltung.services.ClasspathSuche;
import com.bosch.zeitverwaltung.services.DOMReader;
import com.bosch.zeitverwaltung.view.FunktionsTrigger;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.event.FunktionsEvent;
import com.bosch.zeitverwaltung.view.listener.FunktionsListener;

/**
 * <p>
 * Verwaltung der Auswertungen, die auf Buchungen angewendet werden können. Die
 * Klasse durchsucht den Klassenpfad nach XML-Dateien eines bestimmten Typs und
 * extrahiert die Informationen daraus. Jede diese Dateien definiert mindestens
 * ein Paar von Modell- und View-Klassen, die zusammen eine Auswertung ergeben.
 * Für jedes gefundene Paar wird ein <em>Auswertung</em>-Objekt angelegt und
 * die Auswertung wird über <em>FunktionsTrigger</em> der Anwendung bekannt
 * gemacht.
 * </p>
 * 
 * <p>
 * Auswertungs-Anforderungen werden mittels des <em>FunktionsListener</em>-Interfaces
 * dem Control übergeben. Es ermittelt dann die entsprechende Auswertung und
 * fordert diese zur Anzeige auf.
 * </p>
 * 
 * @author Lars Geyer
 * @see FunktionsTrigger
 * @see Auswertung
 * @see FunktionsListener
 */
public class FunktionsControl implements FunktionsListener {
	/**
	 * Name der XML-Datei, die im Klassenpfad gesucht wird.
	 */
	private static final String auswertungConfDatei = "zvw_auswertung.xml";

	private Map<String, Auswertung> auswertungen = new HashMap<String, Auswertung>();

	/**
	 * <p>
	 * Konstruktor durchsucht den Klassenpfad nach Auswertungen, erzeugt die
	 * entsprechenden Klassen und meldet die Auswertung bei der Anwendung an.
	 * </p>
	 */
	public FunktionsControl() {
		ClasspathSuche auswertungsDateien = new ClasspathSuche(
				auswertungConfDatei);
		SortedSet<AuswertungsDaten> auswertungsDaten = extrahiereModellViewPaare(auswertungsDateien);
		auswertungenErzeugenUndAnmelden(auswertungsDaten);
	}

	/**
	 * <p>
	 * Erzeugt Auswertungen und meldet diese beim System an.
	 * </p>
	 * 
	 * @param auswertungsDaten
	 *            Die gefundenen Auswertungen, bestehend aus einem Namen, einer
	 *            Kurzwahl im Menü, sowie den Klassennamen von View und Modell
	 */
	private void auswertungenErzeugenUndAnmelden(
			SortedSet<AuswertungsDaten> auswertungsDaten) {
		FunktionsTrigger menue = UIFactory.getFactory().erzeugeMainWindow()
				.getFunktionsTrigger();
		Iterator<AuswertungsDaten> iter = auswertungsDaten.iterator();
		while (iter.hasNext()) {
			AuswertungsDaten aktAuswertung = iter.next();
			try {
				Auswertung auswertungsInstanz = new Auswertung(
						aktAuswertung.modell, aktAuswertung.view);
				menue.addFunktion(aktAuswertung.name, aktAuswertung.kurzwahl);
				auswertungen.put(aktAuswertung.name, auswertungsInstanz);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		menue.addFunktionsListener(this);
	}

	/**
	 * <p>
	 * Einlesen der XML-Daten für jede Auswertung, die im Classpath gefunden wurde und
	 * Erzeugen der Auswertungsdaten.
	 * </p>
	 * 
	 * @param auswertungsDateien Resultat der Classpath-Suche
	 * @return Liste mit Auswertungen
	 */
	private SortedSet<AuswertungsDaten> extrahiereModellViewPaare(
			ClasspathSuche auswertungsDateien) {
		SortedSet<AuswertungsDaten> auswertungsDaten = new TreeSet<AuswertungsDaten>(
				new Comparator<AuswertungsDaten>() {
					public int compare(AuswertungsDaten elem1,
							AuswertungsDaten elem2) {
						return elem1.id - elem2.id;
					}
				});

		while (auswertungsDateien.hasFiles()) {
			try {
				DOMReader aktDatei = new DOMReader(auswertungsDateien
						.nextInputStream());
				Iterator<Element> iter = aktDatei.getElementListe(null,
						"auswertung").iterator();
				while (iter.hasNext()) {
					Element aktElement = iter.next();
					Element nameElement = aktDatei.getElement(aktElement,
							"name");
					Element kurzwahlElement = aktDatei.getElement(aktElement,
							"kurzwahl");
					Element modellElement = aktDatei.getElement(aktElement,
							"modell");
					Element viewElement = aktDatei.getElement(aktElement,
							"view");

					AuswertungsDaten aktAuswertung = new AuswertungsDaten();
					aktAuswertung.id = Integer.parseInt(aktElement
							.getAttribute("id"));
					aktAuswertung.name = nameElement.getTextContent();
					aktAuswertung.kurzwahl = Integer.parseInt(kurzwahlElement
							.getTextContent());
					aktAuswertung.modell = modellElement.getTextContent();
					aktAuswertung.view = viewElement.getTextContent();

					auswertungsDaten.add(aktAuswertung);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return auswertungsDaten;
	}

	/**
	 * <p>
	 * Diese Methode implementiert die <em>FunktionsListener</em>-Schnittstelle.
	 * Die Methode wird aufgerufen, sobald eine Auswertung aufgerufen wurde. Das
	 * Funktions-Control ist für die Darstellung der Auswertung verantwortlich.
	 * Es ruft dazu die <em>auswertung</em>-Methode des <em>Auswertungs</em>-Objekts
	 * auf.
	 * </p>
	 * 
	 * @param evt
	 *            Event-Objekt mit dem Namen der Auswertung
	 */
	public void event(FunktionsEvent evt) {
		Auswertung aktuelle = auswertungen.get(evt.getFunktionsName());
		aktuelle.auswertung();
	}
}

/**
 * <p>
 * Einfache Datenstruktur, die alle für eine Auswertung relevanten Daten hält.
 * </p>
 * 
 * @author Lars Geyer
 * @see FunktionsControl
 */
class AuswertungsDaten {
	int id;
	String name;
	int kurzwahl;
	String modell;
	String view;
}