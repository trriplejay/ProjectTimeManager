package com.bosch.zeitverwaltung.swing_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.bosch.zeitverwaltung.elemente.AktivitaetsSortierung;
import com.bosch.zeitverwaltung.elemente.MinutenDelta;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.event.DeltaChangedEvent;
import com.bosch.zeitverwaltung.modell.event.SortierungChangedEvent;
import com.bosch.zeitverwaltung.modell.listener.DeltaChangedListener;
import com.bosch.zeitverwaltung.modell.listener.SortierungChangedListener;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetsSortierungsAuswahl;
import com.bosch.zeitverwaltung.view.dialog.MinutenDeltaAuswahl;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEvent;
import com.bosch.zeitverwaltung.view.event.DialogSchliessenEventFactory;
import com.bosch.zeitverwaltung.view.event.OptionenEvent;
import com.bosch.zeitverwaltung.view.event.OptionenEventFactory;
import com.bosch.zeitverwaltung.view.listener.DialogSchliessenListener;
import com.bosch.zeitverwaltung.view.listener.OptionenListener;

/**
 * <p>
 * Klasse definiert das Optionsmenü der Zeitverwaltung
 * </p>
 * 
 * @author Lars Geyer
 */
class OptionenMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private EventVerteiler<OptionenEvent, OptionenListener> listenerMgmt = new EventVerteiler<OptionenEvent, OptionenListener>();

	private MinutenDelta minutenDeltaAuswahl;
	private AktivitaetsSortierung aktivitaetsSortierung;

	private JMenu aktivitaetenMenu = null;
	private JMenuItem aktivitaetenEditieren = null;
	private JRadioButtonMenuItem sortierungKategorie = null;
	private JRadioButtonMenuItem sortierungAktivitaet = null;
	private JRadioButtonMenuItem sortierungNummer = null;
	private JMenu rundungsMenu = null;
	private JRadioButtonMenuItem eineMinute = null;
	private JRadioButtonMenuItem dreiMinuten = null;
	private JRadioButtonMenuItem fuenfMinuten = null;
	private JRadioButtonMenuItem fuenfzehnMinuten = null;

	/**
	 * <p>
	 * Der Konstruktor erzeugt die Swing-Objektstruktur des Menüs.
	 * </p>
	 */
	OptionenMenu() {
		initialisiere();
	}

	/**
	 * <p>
	 * Fügt Listener für Menüevents hinzu
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	void addOptionenListener(OptionenListener listener) {
		listenerMgmt.addEventListener(listener);
	}

	/**
	 * <p>
	 * Entfernt Listener für Menüevents
	 * </p>
	 * 
	 * @param listener
	 *            Listener
	 */
	void removeOptionenListener(OptionenListener listener) {
		listenerMgmt.delEventListener(listener);
	}

	/**
	 * <p>
	 * Minuten-Delta-Änderungen werden direkt im Menü gemacht. Diese Methode
	 * implementiert einen Dummy-Dialog, der die Änderung kommuniziert. Der
	 * Benutzer wählt einen Menüpunkt, dieser zeigt die Auswahl dem Control an,
	 * dieses schickt die Aufforderung zum Öffnen eines Dialogs an den View, der
	 * daraufhin mit diesem Dummy reagiert, um die bereits durchggeführte
	 * Auswahl zu kommunizieren.
	 * </p>
	 * 
	 * @return Minuten-Delta-Auswahl-Dialog
	 */
	MinutenDeltaAuswahl getMinutenDeltaAuswahl() {
		return new MinutenDeltaAuswahl() {
			private EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener> listenerMgmt = new EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener>();

			/**
			 * {@inheritDoc}
			 */
			public void addDialogSchliessenListener(
					DialogSchliessenListener listener) {
				listenerMgmt.addEventListener(listener);
			}

			/**
			 * {@inheritDoc}
			 */
			public void removeDialogSchliessenListener(
					DialogSchliessenListener listener) {
				listenerMgmt.delEventListener(listener);
			};

			/**
			 * {@inheritDoc}
			 */
			public void showDialog() {
				listenerMgmt.event(new DialogSchliessenEventFactory()
						.<MinutenDelta> commitEvent(minutenDeltaAuswahl));
			}
		};
	}

	/**
	 * <p>
	 * Aktivitäts-Sortierungs-Änderungen werden direkt im Menü gemacht. Diese
	 * Methode implementiert einen Dummy-Dialog, der die Änderung kommuniziert.
	 * Der Benutzer wählt einen Menüpunkt, dieser zeigt die Auswahl dem Control
	 * an, dieses schickt die Aufforderung zum Öffnen eines Dialogs an den View,
	 * der daraufhin mit diesem Dummy reagiert, um die bereits durchggeführte
	 * Auswahl zu kommunizieren.
	 * </p>
	 * 
	 * @return Aktivitätssortierungs-Auswahl-Dialog
	 */
	AktivitaetsSortierungsAuswahl getAktivitaetsSortierungsAuswahl() {
		return new AktivitaetsSortierungsAuswahl() {
			private EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener> listenerMgmt = new EventVerteiler<DialogSchliessenEvent, DialogSchliessenListener>();

			/**
			 * {@inheritDoc}
			 */
			public void addDialogSchliessenListener(
					DialogSchliessenListener listener) {
				listenerMgmt.addEventListener(listener);
			}

			/**
			 * {@inheritDoc}
			 */
			public void removeDialogSchliessenListener(
					DialogSchliessenListener listener) {
				listenerMgmt.delEventListener(listener);
			}

			/**
			 * {@inheritDoc}
			 */
			public void showDialog() {
				listenerMgmt
						.event(new DialogSchliessenEventFactory()
								.<AktivitaetsSortierung> commitEvent(aktivitaetsSortierung));
			}
		};
	}

	/**
	 * <p>
	 * Gibt einen <em>DeltaChangedListener</em> zurück, der Änderungen des
	 * Minuten-Deltas im Menü anzeigt.
	 * </p>
	 * 
	 * @return <em>DeltaChangedListener</em>
	 */
	public DeltaChangedListener getDeltaChangedListener() {
		return new DeltaChangedListener() {
			public void event(DeltaChangedEvent evt) {
				minutenDeltaAuswahl = evt.getMinutenDelta();
				if (evt.istMinutenDeltaEineMinute()) {
					getEineMinute().setSelected(true);
				} else if (evt.istMinutenDeltaDreiMinuten()) {
					getDreiMinuten().setSelected(true);
				} else if (evt.istMinutenDeltaFuenfMinuten()) {
					getFuenfMinuten().setSelected(true);
				} else if (evt.istMinutenDeltaFuenfzehnMinuten()) {
					getFuenfzehnMinuten().setSelected(true);
				}
			}
		};
	}

	/**
	 * <p>
	 * Gibt einen <em>SortierungChangedListener</em> zurück, der Änderungen
	 * des Minuten-Deltas im Menü anzeigt.
	 * </p>
	 * 
	 * @return <em>DSortierungChangedListener</em>
	 */
	public SortierungChangedListener getSortierungChangedListener() {
		return new SortierungChangedListener() {
			public void event(SortierungChangedEvent evt) {
				aktivitaetsSortierung = evt.sortierung();
				if (evt.sortierungNachAktivitätskategorie()) {
					getSortierungKategorie().setSelected(true);
				} else if (evt.sortierungNachAktivitätsname()) {
					getSortierungAktivitaet().setSelected(true);
				} else if (evt.sortierungNachBuchungsnummern()) {
					getSortierungNummer().setSelected(true);
				}
			}
		};
	}

	/**
	 * <p>
	 * Initialisierung des Menüs
	 * </p>
	 */
	private void initialisiere() {
		setText("Optionen");
		setMnemonic(KeyEvent.VK_O);
		add(getAktivitaetenMenu());
		add(getRundungsMenu());
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Aktivitäten-Submenü
	 */
	private JMenuItem getAktivitaetenMenu() {
		if (aktivitaetenMenu == null) {
			aktivitaetenMenu = new JMenu();
			aktivitaetenMenu.setText("Aktivitäten");
			aktivitaetenMenu.setMnemonic(KeyEvent.VK_A);
			aktivitaetenMenu.add(getAktivitaetenEditieren());
			aktivitaetenMenu.addSeparator();
			aktivitaetenMenu.add(getSortierungAktivitaet());
			aktivitaetenMenu.add(getSortierungNummer());
			aktivitaetenMenu.add(getSortierungKategorie());
			ButtonGroup group = new ButtonGroup();
			group.add(getSortierungAktivitaet());
			group.add(getSortierungNummer());
			group.add(getSortierungKategorie());
		}
		return aktivitaetenMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Aktivitäten-Editoren Menüpunkt
	 */
	private JMenuItem getAktivitaetenEditieren() {
		if (aktivitaetenEditieren == null) {
			aktivitaetenEditieren = new JMenuItem();
			aktivitaetenEditieren.setText("Editieren");
			aktivitaetenEditieren.setMnemonic(KeyEvent.VK_K);
			aktivitaetenEditieren.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_K, InputEvent.CTRL_MASK));
			aktivitaetenEditieren.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					listenerMgmt.event(new OptionenEventFactory()
							.aktivitaetenEditorEvent());
				}
			});
		}
		return aktivitaetenEditieren;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Sortierung nach Kategorie Menüpunkt
	 */
	private JRadioButtonMenuItem getSortierungKategorie() {
		if (sortierungKategorie == null) {
			sortierungKategorie = new JRadioButtonMenuItem();
			sortierungKategorie.setText("Sortierung nach Kategorie");
			sortierungKategorie.setMnemonic(KeyEvent.VK_Z);
			sortierungKategorie.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aktivitaetsSortierung = AktivitaetsSortierung.AktivitaetsSortierungNachKategorie;
					listenerMgmt.event(new OptionenEventFactory()
							.sortierungsEvent());
				}
			});
		}
		return sortierungKategorie;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Sortierung nach Aktivität Menüpunkt
	 */
	private JRadioButtonMenuItem getSortierungAktivitaet() {
		if (sortierungAktivitaet == null) {
			sortierungAktivitaet = new JRadioButtonMenuItem();
			sortierungAktivitaet.setText("Sortierung nach Aktivität");
			sortierungAktivitaet.setMnemonic(KeyEvent.VK_A);
			sortierungAktivitaet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aktivitaetsSortierung = AktivitaetsSortierung.AktivitaetsSortierungNachName;
					listenerMgmt.event(new OptionenEventFactory()
							.sortierungsEvent());
				}
			});
		}
		return sortierungAktivitaet;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Sortierung nach Buchungsnummer Menüpunkt
	 */
	private JRadioButtonMenuItem getSortierungNummer() {
		if (sortierungNummer == null) {
			sortierungNummer = new JRadioButtonMenuItem();
			sortierungNummer.setText("Sortierung nach Buchungsnummer");
			sortierungNummer.setMnemonic(KeyEvent.VK_N);
			sortierungNummer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					aktivitaetsSortierung = AktivitaetsSortierung.AktivitaetsSortierungNachBuchungsnummer;
					listenerMgmt.event(new OptionenEventFactory()
							.sortierungsEvent());
				}
			});
		}
		return sortierungNummer;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Minutendelta-Submenü
	 */
	private JMenu getRundungsMenu() {
		if (rundungsMenu == null) {
			rundungsMenu = new JMenu();
			rundungsMenu.setText("Buchungsraster");
			rundungsMenu.setMnemonic(KeyEvent.VK_R);
			rundungsMenu.add(getEineMinute());
			rundungsMenu.add(getDreiMinuten());
			rundungsMenu.add(getFuenfMinuten());
			rundungsMenu.add(getFuenfzehnMinuten());
			ButtonGroup group = new ButtonGroup();
			group.add(getEineMinute());
			group.add(getDreiMinuten());
			group.add(getFuenfMinuten());
			group.add(getFuenfzehnMinuten());
		}
		return rundungsMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Delta ist 1 Minute Menüpunkt
	 */
	private JRadioButtonMenuItem getEineMinute() {
		if (eineMinute == null) {
			eineMinute = new JRadioButtonMenuItem();
			eineMinute.setText("1 Minute");
			eineMinute.setMnemonic(KeyEvent.VK_1);
			eineMinute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minutenDeltaAuswahl = MinutenDelta.MinutenDeltaEineMinute;
					listenerMgmt.event(new OptionenEventFactory()
							.minutenDeltaEvent());
				}
			});
		}
		return eineMinute;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Delta ist 3 Minuten Menüpunkt
	 */
	private JRadioButtonMenuItem getDreiMinuten() {
		if (dreiMinuten == null) {
			dreiMinuten = new JRadioButtonMenuItem();
			dreiMinuten.setText("3 Minuten");
			dreiMinuten.setMnemonic(KeyEvent.VK_3);
			dreiMinuten.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minutenDeltaAuswahl = MinutenDelta.MinutenDeltaDreiMinuten;
					listenerMgmt.event(new OptionenEventFactory()
							.minutenDeltaEvent());
				}
			});
		}
		return dreiMinuten;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Delta ist 5 Minuten Menüpunkt
	 */
	private JRadioButtonMenuItem getFuenfMinuten() {
		if (fuenfMinuten == null) {
			fuenfMinuten = new JRadioButtonMenuItem();
			fuenfMinuten.setText("5 Minuten");
			fuenfMinuten.setMnemonic(KeyEvent.VK_5);
			fuenfMinuten.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minutenDeltaAuswahl = MinutenDelta.MinutenDeltaFuenfMinuten;
					listenerMgmt.event(new OptionenEventFactory()
							.minutenDeltaEvent());
				}
			});
		}
		return fuenfMinuten;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Delta ist 15 Minuten Menüpunkt
	 */
	private JRadioButtonMenuItem getFuenfzehnMinuten() {
		if (fuenfzehnMinuten == null) {
			fuenfzehnMinuten = new JRadioButtonMenuItem();
			fuenfzehnMinuten.setText("15 Minuten");
			fuenfzehnMinuten.setMnemonic(KeyEvent.VK_T);
			fuenfzehnMinuten.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					minutenDeltaAuswahl = MinutenDelta.MinutenDeltaFuenfzehnMinuten;
					listenerMgmt.event(new OptionenEventFactory()
							.minutenDeltaEvent());
				}
			});
		}
		return fuenfzehnMinuten;
	}
}
