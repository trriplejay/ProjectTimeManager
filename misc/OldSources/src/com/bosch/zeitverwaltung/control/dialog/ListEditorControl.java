package com.bosch.zeitverwaltung.control.dialog;

import com.bosch.zeitverwaltung.modell.BasisModell;
import com.bosch.zeitverwaltung.view.dialog.ListEditor;
import com.bosch.zeitverwaltung.view.event.DialogAktionEvent;
import com.bosch.zeitverwaltung.view.event.DialogCommitEvent;
import com.bosch.zeitverwaltung.view.event.DialogHinzufuegenEvent;
import com.bosch.zeitverwaltung.view.event.DialogLoeschenEvent;
import com.bosch.zeitverwaltung.view.event.DialogVeraendernEvent;
import com.bosch.zeitverwaltung.view.listener.DialogAktionListener;

/**
 * <p>
 * Control für einen List-Editor
 * </p>
 * 
 * @author Lars Geyer
 * 
 * @param <Element>
 *            Die editierten Elemente
 */
public abstract class ListEditorControl<Element> extends
		BasisEditorControl<Element> {
	/**
	 * <p>
	 * Behandlung eines Element erzeugen Events
	 * </p>
	 * 
	 * @param eingabe
	 *            Neue Eingabe
	 */
	private void neuesObjekt(Element eingabe) {
		try {
			if (eingabe != null) {
				int index = getModell().elementHinzufuegen(eingabe);
				getEditor().listenModellVeraendert(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Behandlung eines Element löschen Events
	 * </p>
	 * 
	 * @param auswahl
	 *            Zu löschendes Element
	 */
	private void loescheObjekt(Element auswahl) {
		if (auswahl != null) {
			getModell().elementLoeschen(auswahl);
			getEditor().listenModellVeraendert(-1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected void commit(DialogCommitEvent<Element> evt) {
		getModell().commitAenderung();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void abort() {
		getModell().restoreAenderung();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void initialize() {
		super.initialize();
		getModell().starteAenderung();
		getEditor().addDialogAktionListener(new DialogAktionListener() {
			/**
			 * <p>
			 * Listener für Element Veränderungs Events
			 * </p>
			 * 
			 * @param evt
			 *            Eventobjekt
			 */
			@SuppressWarnings("unchecked")
			public void event(DialogAktionEvent evt) {
				if (evt instanceof DialogHinzufuegenEvent) {
					neuesObjekt(((DialogHinzufuegenEvent<Element>) evt)
							.getEingabe());
				} else if (evt instanceof DialogVeraendernEvent) {
					// Derzeit nicht unterstützt
				} else if (evt instanceof DialogLoeschenEvent) {
					loescheObjekt(((DialogLoeschenEvent<Element>) evt)
							.getSelektion());
				}
			}
		});
	}

	/**
	 * <p>
	 * Erlaubt den Zugriff auf das Datenmodell über die abgeleitete Klasse
	 * </p>
	 * 
	 * @return Datenmodell
	 */
	protected abstract BasisModell<Element> getModell();

	/**
	 * {@inheritDoc}
	 */
	protected abstract ListEditor<Element> getEditor();
}
