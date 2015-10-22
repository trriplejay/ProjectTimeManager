package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.BasisModell;
import com.bosch.zeitverwaltung.view.dialog.ListEditor;
import com.bosch.zeitverwaltung.view.event.DialogAktionEvent;
import com.bosch.zeitverwaltung.view.event.DialogAktionEventFactory;
import com.bosch.zeitverwaltung.view.listener.DialogAktionListener;

/**
 * <p>
 * Abstrakter Listen-Editor. Ein Listeneditor ist ein Dialog, in dem Objekte
 * eines Typs in einer Liste verwaltet werden können. Der Dialog erlaubt das
 * Erzeugen, Verändern und Löschen der Objekte. Ein spezielles Eingabefeld
 * erlaubt die Definition und Betrachtung der Eigenschaften eines Objekts.
 * </p>
 * 
 * @author Lars Geyer
 * @see BasisDialogImpl
 * @see ListEditor
 * @see BasisModell
 * 
 * @param <Element>
 *            Der Objekttyp, der in der Liste dargestellt werden soll.
 */
public abstract class ListEditorImpl<Element> extends BasisDialogImpl implements
		ListEditor<Element>, ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JList elementListe = null;
	private JScrollPane jScrollPane = null;
	private JPanel listenPanel = null;
	private AddChangeDeletePanel aktionsPanel = null;

	private EventVerteiler<DialogAktionEvent, DialogAktionListener> listenerMgmt = new EventVerteiler<DialogAktionEvent, DialogAktionListener>();

	/**
	 * <p>
	 * Konstruktor, er initialisiert den Dialog
	 * </p>
	 * 
	 * @param meinFrame
	 *            Hauptfenster, in dessen Kontext Dialog dargestellt werden soll
	 */
	protected ListEditorImpl(Frame meinFrame) {
		super(meinFrame);
	}

	/**
	 * <p>
	 * Zugriff auf das Modell, in dem die Objekte, die der Listen-Editor
	 * manipulieren soll, verwaltet werden. Das Modell ist abhängig von den zu
	 * verwaltenden Daten und wird daher von einer abgeleiteten Klasse gesetzt.
	 * Diese weiß, um welche Objekte es sich handelt und wo diese herkommen.
	 * </p>
	 * 
	 * @return Referenz auf das die Objekte verwaltende Modell
	 */
	protected abstract BasisModell<Element> getModell();

	/**
	 * <p>
	 * Hinzufügen einer Event-Senke für Listen-Aktions-Events, d.h. wenn ein
	 * Objekt der Liste manipuliert werden soll, wird dies an die mittels dieser
	 * Methode übergebenen Senken als Event mitgeteilt.
	 * </p>
	 * 
	 * @param listener
	 *            Event-Senke für Listen-Aktions-Events
	 */
	public void addDialogAktionListener(DialogAktionListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeDialogAktionListener(DialogAktionListener listener) {
		listenerMgmt.delEventListener(listener);
	}
	
	/**
	 * <p>
	 * Methode gibt das aktuell in der Liste selektierte Element zurück. Die
	 * Liste erlaubt nur die Auswahl eines Objekts.
	 * </p>
	 */
	// Suppress Warnings ist notwendig, da der Cast auf Element vom Typsystem
	// nicht überprüft werden kann
	// Er passt aber in diesem Fall immer
	@SuppressWarnings("unchecked")
	private Element getAktuelleAuswahl() {
		Element aktElement = null;
		int auswahl = getElementListe().getMinSelectionIndex();

		if (auswahl >= 0) {
			aktElement = (Element) getElementListe().getModel().getElementAt(
					auswahl);
		}

		return aktElement;
	}

	/**
	 * <p>
	 * Temporäre Methode, um eine Änderung des Listen-Modells mitzuteilen. Diese
	 * Methode muss noch geändert werden, wenn das Modell verändert wird, sollte
	 * dies über Events direkt mitgeteilt werden.
	 * </p>
	 * 
	 * @param veraenderterIndex
	 *            Index des Elements das verändert wurde. Dieses soll nach dem
	 *            Wechsel selektiert sein. -1, falls keine Selektion erfolgen
	 *            soll
	 */
	public void listenModellVeraendert(int veraenderterIndex) {
		DefaultListModel listenModell = (DefaultListModel) getElementListe()
				.getModel();
		listenModell.clear();
		Iterator<?> iter = getModell().elemente().iterator();
		while (iter.hasNext()) {
			listenModell.addElement(iter.next());
		}

		if ((veraenderterIndex >= 0)
				&& (veraenderterIndex < listenModell.getSize())) {
			getElementListe().setSelectedIndex(veraenderterIndex);
		}
	}

	/**
	 * <p>
	 * Implementiert das <em>ListSelectionListener</em>-Interface. Bei
	 * Änderungen an der Selektion von Objekt in der Liste, wird dieser Event
	 * erzeugt. Der Dialog muss dann die Daten im Eingabefeld anpassen, was in
	 * der Behandlung dieses Events passiert.
	 * </p>
	 * 
	 * @param evt
	 *            Übergebener Event, er wird nicht verwendet, da die Daten darin
	 *            nicht zuverlässig sind. Stattdessen wird das selektierte
	 *            Objekt direkt bei der Liste erfragt.
	 */
	// Suppress Warnings ist notwendig, da der Cast auf Element vom Typsystem
	// nicht überprüft werden kann
	// Er passt aber in diesem Fall immer
	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent evt) {
		int auswahl = getElementListe().getMinSelectionIndex();

		if (auswahl >= 0) {
			Element aktElement = (Element) getElementListe().getModel()
					.getElementAt(auswahl);
			setEingabe(aktElement);
		} else {
			setEingabe(null);
		}
	}

	/**
	 * <p>
	 * Implementiert das <em>ActionListener</em>-Interface. Hat der Benutzer
	 * den Wunsch zum Verändern eines Objekts aus der Liste, betätigt er einen
	 * der Buttons im Button-Panel. Dieser Event wird mittels des ActionEvents
	 * mitgeteilt. Er wird hier abgefangen und dann entsprechend verteilt.
	 * </p>
	 * 
	 * @param event
	 *            Der <em>ActionEvent</em>. Er enthält das Action-Kommando,
	 *            mit dem der betätigte Button signalisiert wird.
	 */
	public void actionPerformed(ActionEvent event) {
		String kommando = event.getActionCommand();
		DialogAktionEventFactory eventFactory = new DialogAktionEventFactory();
		if (kommando.equals(AddChangeDeletePanel.hinzufuegenKommando)) {
			try {
				listenerMgmt.event(eventFactory
						.<Element>hinzufuegenEvent(getAktuelleEingabe()));
			} catch (Exception e) {
			}
		} else if (kommando.equals(AddChangeDeletePanel.veraendernKommando)) {
			try {
				listenerMgmt.event(eventFactory.veraendernEvent(
						getAktuelleAuswahl(), getAktuelleEingabe()));
			} catch (Exception e) {
			}
		} else if (kommando.equals(AddChangeDeletePanel.loeschenKommando)) {
			try {
				listenerMgmt.event(eventFactory
						.loeschenEvent(getAktuelleAuswahl()));
			} catch (Exception e) {
			}
		}
	}

	/**
	 * <p>
	 * Setzen der Daten des aktuell selektierten Objekts im Eingabefeld. Das
	 * Eingabefeld ist abhängig vom Objekttyp und wird daher in einer
	 * abgeleiteten Klasse definiert. Ist kein Objekt selektiert, so wird das
	 * Eingabefeld resetiert.
	 * </p>
	 * 
	 * @param eingabeBasis
	 *            Das selektierte Objekt, bzw. <em>null</em>, falls kein
	 *            Objekt selektiert
	 */
	protected abstract void setEingabe(Element eingabeBasis);

	/**
	 * <p>
	 * Gibt die aktuelle Eingabe als Element zurück.
	 * </p>
	 * 
	 * @return Aktuelle Eingabe
	 */
	protected abstract Element getAktuelleEingabe();
	
	/**
	 * <p>
	 * Initialisierung der Listen-Dialogs spezifischen Einstellungen
	 * </p>
	 */
	protected void initialize() {
		super.initialize();
		listenModellVeraendert(-1);
		getElementListe().addListSelectionListener(this);
	}

	/**
	 * <p>
	 * Initialisierung der Dialog-Komponenten
	 * </p>
	 * 
	 * @return Panel mit den Dialog-Komponenten
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(5);
			borderLayout.setVgap(10);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getListenPanel(), BorderLayout.NORTH);
			jContentPane.add(getEingabePanel(), BorderLayout.CENTER);
			jContentPane.add(getSchließenPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * <p>
	 * Initialisierung des Eingabepanels. Dieses wird anwendungsspezifisch in
	 * einer abgeleiteten Klasse definiert.
	 * </p>
	 * 
	 * @return Eingabepanel
	 */
	protected abstract JPanel getEingabePanel();

	/**
	 * <p>
	 * Initialisierung des Panels, in dem die Liste dargestellt ist.
	 * </p>
	 * 
	 * @return Listen-Panel
	 */
	protected JPanel getListenPanel() {
		if (listenPanel == null) {
			listenPanel = new JPanel();
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(5);
			borderLayout.setVgap(10);
			listenPanel.setLayout(borderLayout);
			TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					getListenTitel());
			listenPanel.setBorder(umrandung);
			listenPanel.add(getJScrollPane(), BorderLayout.CENTER);
			listenPanel.add(getAktionsPanel(), BorderLayout.EAST);
		}
		return listenPanel;
	}

	/**
	 * <p>
	 * Der Titel der Liste ist anwendungsabhängig und muss daher von einer
	 * abgeleiteten Klasse definiert werden. Diese Methode dient dazu, den Titel
	 * hier einsetzen zu können.
	 * </p>
	 * 
	 * @return Titel des Listenbereichs des Dialogs
	 */
	protected abstract String getListenTitel();

	/**
	 * <p>
	 * Initialisierung des Scoll-Panels, in dem die Liste dargestellt werden
	 * soll
	 * </p>
	 * 
	 * @return Scoll-Panel mit Liste
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getElementListe());
		}
		return jScrollPane;
	}

	/**
	 * <p>
	 * Initialisierung der Liste
	 * </p>
	 * 
	 * @return Liste
	 */
	private JList getElementListe() {
		if (elementListe == null) {
			elementListe = new JList();
			elementListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			elementListe.setModel(new DefaultListModel());
		}
		return elementListe;
	}

	/**
	 * <p>
	 * Initialisierung des Button-Panels, mit dem Elemente der Liste verändert
	 * werden können.
	 * </p>
	 * 
	 * @return Button-Panel
	 */
	protected AddChangeDeletePanel getAktionsPanel() {
		if (aktionsPanel == null) {
			aktionsPanel = new AddChangeDeletePanel(this, false);
		}
		return aktionsPanel;
	}
}