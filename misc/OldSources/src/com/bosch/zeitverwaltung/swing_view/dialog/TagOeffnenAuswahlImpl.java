package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bosch.zeitverwaltung.elemente.Tag;
import com.bosch.zeitverwaltung.view.dialog.BasisDialog;
import com.bosch.zeitverwaltung.view.dialog.TagOeffnenAuswahl;

import net.sf.nachocalendar.components.DatePanel;

/**
 * <p>
 * Realisierung des Dialogs, der es erlaubt einen Tag auszuwählen, dessen
 * Buchungen dann im Hauptfenster dargestellt werden sollen. Auf den
 * <em>BasisDialog</em> wird zurückgegriffen, um die Dialog-Schließen
 * Funktionalität zu verwenden.
 * </p>
 * 
 * @author Lars Geyer
 * @see com.bosch.zeitverwaltung.control.dialog.TagOeffnenControl
 * @see BasisDialog
 */
public class TagOeffnenAuswahlImpl extends BasisDialogImpl implements
		TagOeffnenAuswahl {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private DatePanel tagAuswahl = null;

	/**
	 * <p>
	 * Konstruktor, er initialisiert den Dialog
	 * </p>
	 * 
	 * @param meinFrame
	 */
	public TagOeffnenAuswahlImpl(Frame meinFrame) {
		super(meinFrame);
		initialize();
	}

	/**
	 * <p>
	 * Diese Methode wird vor <em>showDialog</em> aufgerufen und weißt den
	 * Dialog an, den übergebenen Tag initial zu selektieren.
	 * </p>
	 * 
	 * @param startTag
	 *            Initial zu selektierender Tag
	 */
	public void setStartTag(Tag startTag) {
		getTagAuswahl().setDate(startTag.getDate());
	}

	/**
	 * <p>
	 * Ermittelt die Botschaft für den Dialog-Schließen-Event bei einem Commit.
	 * </p>
	 * 
	 * @return Botschaft für den Dialog-Schließen-Event bei einem Commit
	 */
	protected Object getBotschaft() {
		Tag back = null;

		Date auswahl = getTagAuswahl().getDate();
		if (auswahl != null) {
			back = new Tag(auswahl);
		}

		return back;
	}

	/**
	 * <p>
	 * Aufbau der Dialogbestandteile.
	 * </p>
	 * 
	 * @return Panel mit dem Inhalt des Dialogs
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(5);
			borderLayout.setVgap(10);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getTagAuswahl(), BorderLayout.CENTER);
			jContentPane.add(getSchließenPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * <p>
	 * Über diese Methode wird der Titel hier in der Initialisierung gesetzt.
	 * </p>
	 * 
	 * @return Titel des Dialogs
	 */
	protected String getTitel() {
		return "Buchungen öffnen";
	}

	/**
	 * <p>
	 * Initialisierung der Kalender-Darstellung
	 * </p>
	 * 
	 * @return DatePanel, dass die Auswahl eines Tages in einem Kalender erlaubt
	 */
	private DatePanel getTagAuswahl() {
		if (tagAuswahl == null) {
			tagAuswahl = new DatePanel();
			TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"Buchungstag");
			tagAuswahl.setBorder(umrandung);
		}
		return tagAuswahl;
	}
}
