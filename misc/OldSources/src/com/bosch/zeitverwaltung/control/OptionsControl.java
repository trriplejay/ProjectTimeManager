package com.bosch.zeitverwaltung.control;

import com.bosch.zeitverwaltung.control.dialog.AktivitaetenEditorControl;
import com.bosch.zeitverwaltung.control.dialog.MinutenDeltaControl;
import com.bosch.zeitverwaltung.control.dialog.SortierungAendernControl;
import com.bosch.zeitverwaltung.modell.listener.AktivitaetenChangedListener;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.event.AktivitaetenEditorEvent;
import com.bosch.zeitverwaltung.view.event.AktivitaetenSortierungsEditorEvent;
import com.bosch.zeitverwaltung.view.event.MinutenDeltaEditorEvent;
import com.bosch.zeitverwaltung.view.event.OptionenEvent;
import com.bosch.zeitverwaltung.view.listener.OptionenListener;

/**
 * <p>
 * Dieses Control verwaltet alle Anforderungen an Options-Änderungen. Dazu
 * implementiert das Control die <em>OptionenListener</em>-Schnittstelle, mit
 * der die View-Komponente Options-Änderungen anfordert. Manche Änderungen
 * können direkt durchgeführt werden, bei anderen muss ein Editor-Dialog
 * aufgerufen werden, der die Optionsänderungen durchführt.
 * </p>
 * 
 * <p>
 * Die Schnittstelle <em>AktivitaetenChangedListener</em> implementiert die
 * Klasse, um Änderungen an den existierenden Aktivitäten mitgeteilt zu
 * bekommen. Das Control ist dann für die Information der View-Komponente bzgl.
 * dieser Änderung zuständig.
 * </p>
 * 
 * @author Lars Geyer
 * @see OptionenListener
 * @see AktivitaetenChangedListener
 * @see InitSchliessenControl
 */
public class OptionsControl implements OptionenListener {
	/**
	 * <p>
	 * Konstruktor, er teilt dem Hauptfenster die aktuellen Aktivitäten als Teil
	 * der Initialisierung mit.
	 * </p>
	 */
	public OptionsControl() {
		UIFactory.getFactory().erzeugeMainWindow().addOptionenListener(this);
	}

	/**
	 * <p>
	 * Implementiert <em>OptionenListener</em>-Interface. Der Benutzer hat
	 * den Wunsch nach einer Optionsänderung.
	 * </p>
	 * 
	 * @param evt
	 *            <em>OptionenEvent</em>, der gewünschte Optionenänderung
	 *            näher beschreibt
	 */
	public void event(OptionenEvent evt) {
		if (evt instanceof AktivitaetenEditorEvent) {
			AktivitaetenEditorControl dialog = new AktivitaetenEditorControl();
			dialog.showDialog();
		} else if (evt instanceof AktivitaetenSortierungsEditorEvent) {
			SortierungAendernControl dialog = new SortierungAendernControl();
			dialog.showDialog();
		} else if (evt instanceof MinutenDeltaEditorEvent) {
			MinutenDeltaControl dialog = new MinutenDeltaControl();
			dialog.showDialog();
		}
	}
}
