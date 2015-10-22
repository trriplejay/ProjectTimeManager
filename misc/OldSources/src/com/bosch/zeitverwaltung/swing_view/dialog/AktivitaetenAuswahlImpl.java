package com.bosch.zeitverwaltung.swing_view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetenAuswahl;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEventFactory;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;

/**
 * <p>
 * Implementierung des AktivitaetenAuswahlDialogs
 * </p>
 * 
 * @author Lars Geyer
 */
public class AktivitaetenAuswahlImpl extends JDialog implements
		AktivitaetenAuswahl {
	private static final long serialVersionUID = 1L;

	private EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener> listenerMgmt = new EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener>();

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JList auswahlListe = null;

	/**
	 * <p>
	 * Konstruktor, er initialisiert das Fenster. Aufruf in der Factory
	 * </p>
	 */
	public AktivitaetenAuswahlImpl(Frame meinFrame) throws HeadlessException {
		super(meinFrame);
		initialize();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSelected(Aktivitaet selektiert) {
		getAuswahlListe().setSelectedValue(selektiert, true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void showDialog() {
		setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDialogSchliessenListener(DialogSchliessenListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeDialogSchliessenListener(DialogSchliessenListener listener) {
		listenerMgmt.delEventListener(listener);
	}

	/**
	 * <p>
	 * Initialisierung des Dialogs
	 * </p>
	 */
	private void initialize() {
		this.setUndecorated(true);
		this.setContentPane(getJContentPane());
		this.pack();
		this.setResizable(false);
		this.setModal(true);
		Point position = getParent().getLocation();
		position.setLocation(position.x + 80, position.y + 40);
		this.setLocation(position);

		getAuswahlListe().addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					auswahlConfirmed();
				} else {
					auswahlAbbruch();
				}
			}
		});

		getAuswahlListe().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() > 1) {
						auswahlConfirmed();
					}
				}
			}
		});

		DefaultListModel listenModell = new DefaultListModel();

		Iterator<?> iter = ModellFactory.getFactory()
				.getAktivitaetenVerwaltung().elemente().iterator();
		while (iter.hasNext()) {
			listenModell.addElement(iter.next());
		}
		getAuswahlListe().setModel(listenModell);
		getAuswahlListe().setSelectedIndex(0);
	}

	/**
	 * <p>
	 * Eine Aktivität wurde ausgewählt und ein Confirm-Event wird erzeugt
	 * </p>
	 */
	private void auswahlConfirmed() {
		setVisible(false);
		ListModel modell = getAuswahlListe().getModel();
		int index = getAuswahlListe().getMinSelectionIndex();
		if ((index >= 0) && (index < modell.getSize())) {
			Aktivitaet auswahl = (Aktivitaet) modell.getElementAt(index);
			listenerMgmt.event(new DialogSchliessenEventFactory().<Aktivitaet>commitEvent(auswahl));
		}
		else {
			listenerMgmt.event(new DialogSchliessenEventFactory().abbruchEvent());			
		}
	}

	/**
	 * <p>
	 * Der Dialog wurde ohne Auswahl abgebrochen.
	 * </p>
	 */
	private void auswahlAbbruch() {
		setVisible(false);
		listenerMgmt.event(new DialogSchliessenEventFactory().abbruchEvent());			
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Fenster-Inhalt
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return ScrollPane, in dem Aktivitäten dargestellt werden.
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getAuswahlListe());
			jScrollPane.setPreferredSize(new Dimension(100, 150));
		}
		return jScrollPane;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Liste in der Aktivitäten dargestellt werden.
	 */
	private JList getAuswahlListe() {
		if (auswahlListe == null) {
			auswahlListe = new JList();
			auswahlListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return auswahlListe;
	}
}
