package com.bosch.zeitverwaltung.swing_view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import com.bosch.zeitverwaltung.elemente.Aktivitaet;
import com.bosch.zeitverwaltung.elemente.Buchung;
import com.bosch.zeitverwaltung.event.EventVerteiler;
import com.bosch.zeitverwaltung.modell.ModellFactory;
import com.bosch.zeitverwaltung.modell.TagesBuchungen;
import com.bosch.zeitverwaltung.modell.event.AktivitaetenChangedEvent;
import com.bosch.zeitverwaltung.modell.event.TagChangedEvent;
import com.bosch.zeitverwaltung.modell.listener.AktivitaetenChangedListener;
import com.bosch.zeitverwaltung.modell.listener.TagChangedListener;
import com.bosch.zeitverwaltung.swing_view.modell.LokalesTabellenModell;
import com.bosch.zeitverwaltung.view.FunktionsTrigger;
import com.bosch.zeitverwaltung.view.MainWindow;
import com.bosch.zeitverwaltung.view.dialog.AktivitaetsSortierungsAuswahl;
import com.bosch.zeitverwaltung.view.dialog.MinutenDeltaAuswahl;
import com.bosch.zeitverwaltung.view.event.BeendenEvent;
import com.bosch.zeitverwaltung.view.event.BeendenEventFactory;
import com.bosch.zeitverwaltung.view.listener.BeendenListener;
import com.bosch.zeitverwaltung.view.listener.BuchungsListener;
import com.bosch.zeitverwaltung.view.listener.DateiListener;
import com.bosch.zeitverwaltung.view.listener.HilfeListener;
import com.bosch.zeitverwaltung.view.listener.OptionenListener;

/**
 * {@inheritDoc}
 * 
 * @author Lars Geyer
 */
public class MainWindowImpl extends JFrame implements MainWindow {
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel1 = null; // @jve:decl-index=0:visual-constraint="655,323"

	private JComboBox aktivitaetenEditor = null;
	private JComboBox aktivitaetenAuswahl = null;
	private JButton jetztBuchen = null;

	private JTable zeitenTabelle = null;
	private LokalesTabellenModell zeitenModell = null;

	private JMenuBar zeitMenu = null;
	private DateiMenu dateiMenu = null;
	private BuchungsMenu buchungsMenu = null;
	private FunktionsMenu funktionsMenu = null;
	private OptionenMenu optionMenu = null;
	private HilfeMenu hilfeMenu = null;

	private JMenuItem beendeTool = null;

	private EventVerteiler<BeendenEvent, BeendenListener> beendenManager = new EventVerteiler<BeendenEvent, BeendenListener>();

	/**
	 * <p>
	 * Konstruktor, er initialisiert das Fenster. Aufruf in der Factory
	 * </p>
	 */
	MainWindowImpl() {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
		registriereListener();
	}

	/**
	 * {@inheritDoc}
	 */
	public void addOptionenListener(OptionenListener listener) {
		optionMenu.addOptionenListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeOptionenListener(OptionenListener listener) {
		optionMenu.removeOptionenListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addHilfeListener(HilfeListener listener) {
		hilfeMenu.addHilfeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeHilfeListener(HilfeListener listener) {
		hilfeMenu.removeHilfeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addBuchungsListener(BuchungsListener listener) {
		buchungsMenu.addBuchungsListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeBuchungsListener(BuchungsListener listener) {
		buchungsMenu.removeBuchungsListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDateiListener(DateiListener listener) {
		dateiMenu.addDateiListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeDateiListener(DateiListener listener) {
		dateiMenu.removeDateiListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addBeendenListener(BeendenListener listener) {
		beendenManager.addEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeBeendenListener(BeendenListener listener) {
		beendenManager.delEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public FunktionsTrigger getFunktionsTrigger() {
		return funktionsMenu;
	}

	/**
	 * {@inheritDoc}
	 */
	public void starteDarstellung() {
		setVisible(true);
	}

	/**
	 * <p>
	 * Gibt die aktuell selektierte Buchung zurück, wird in den Menüs verwendet,
	 * um Aktionen zu triggern
	 * </p>
	 * 
	 * @return Aktuelle Buchung
	 */
	Buchung getSelektion() {
		int selektiert = getZeitenTabelle().getSelectedRow();
		return getZeitenModell().getBuchung(selektiert);
	}

	/**
	 * <p>
	 * Implementierung des Auswahl-Dialogs für Minuten-Delta-Änderungen
	 * </p>
	 * 
	 * @return Minuten-Delta-Auswahldialog
	 */
	public MinutenDeltaAuswahl getMinutenDeltaAuswahl() {
		return getOptionMenu().getMinutenDeltaAuswahl();
	}

	/**
	 * <p>
	 * Implementierung des Auswahl-Dialogs für Aktivitätensortierungs-Änderungen
	 * </p>
	 * 
	 * @return Aktivitäten-Sortierungs-Auswahldialog
	 */
	public AktivitaetsSortierungsAuswahl getAktivitaetsSortierungsAuswahl() {
		return getOptionMenu().getAktivitaetsSortierungsAuswahl();
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Tabellen Modell für JTable
	 */
	private LokalesTabellenModell getZeitenModell() {
		if (zeitenModell == null) {
			zeitenModell = new LokalesTabellenModell();
			zeitenTabelle.setModel(zeitenModell);
		}
		return zeitenModell;
	}

	/**
	 * <p>
	 * Registriere alle Modell-Listener des Hauptfensters.
	 * </p>
	 */
	private void registriereListener() {
		ModellFactory factory = ModellFactory.getFactory();
		factory.getMinutenDelta().addDeltaChangedListener(
				getOptionMenu().getDeltaChangedListener());
		factory.getMinutenDelta().addDeltaChangedListener(
				getZeitenModell().getDeltaChangedListener());
		factory.getAktivitaetenVerwaltung().addSortierungChangedListener(
				getOptionMenu().getSortierungChangedListener());
		factory.getAktivitaetenVerwaltung().addAktivitaetenChangedListener(
				new AktivitaetenChangedListener() {
					public void event(AktivitaetenChangedEvent evt) {
						Iterator<Aktivitaet> iter = evt.getAktivitaetenListe()
								.iterator();
						DefaultComboBoxModel aktModell = new DefaultComboBoxModel();

						while (iter.hasNext()) {
							aktModell.addElement(iter.next());
						}
						Aktivitaet selektiert = (Aktivitaet) getAktivitaetenAuswahl()
								.getSelectedItem();
						getAktivitaetenAuswahl().setModel(aktModell);
						if (selektiert != null) {
							getAktivitaetenAuswahl()
									.setSelectedItem(selektiert);
						}
						aktivitaetenEditor = new JComboBox(aktModell);
						getZeitenTabelle().getColumnModel().getColumn(2)
								.setCellEditor(
										new DefaultCellEditor(
												aktivitaetenEditor));
					}
				});
		factory.getTagesVerwaltung().addTagChangedListener(
				getZeitenModell().getTagChangedListener());
		factory.getTagesVerwaltung().addTagChangedListener(
				new TagChangedListener() {
					public void event(TagChangedEvent evt) {
						TagesBuchungen neuerTag = evt.getBuchungsManager();
						TableColumnModel cm = getZeitenTabelle()
								.getColumnModel();
						cm.getColumn(0).setPreferredWidth(50);
						cm.getColumn(1).setPreferredWidth(50);
						cm.getColumn(2).setPreferredWidth(120);
						cm.getColumn(3).setPreferredWidth(120);
						cm.getColumn(4).setPreferredWidth(250);
						cm.getColumn(5).setPreferredWidth(70);
						cm.getColumn(0).setCellEditor(
								new NewDefaultCellEditor(new JTextField()));
						cm.getColumn(1).setCellEditor(
								new NewDefaultCellEditor(new JTextField()));
						cm.getColumn(2).setCellEditor(
								new DefaultCellEditor(aktivitaetenEditor));
						cm.getColumn(4).setCellEditor(
								new NewDefaultCellEditor(new JTextField()));
						setTitle("Zeitverwaltung - Buchungen für "
								+ neuerTag.getBuchungsTag().toString());
					}
				});
	}

	/**
	 * <p>
	 * Initialisierung des Fensters
	 * </p>
	 */
	private void initialize() {
		this.setJMenuBar(getZeitMenu());
		this.setContentPane(getJPanel());
		this.setTitle("Zeitverwaltung");
		this.setSize(700, 481);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				beendenManager.event(new BeendenEventFactory().beendenEvent());
			}
		});
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Fenster-Inhalt
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 0.0;
			gridBagConstraints1.gridx = 0;
			jPanel.add(getJScrollPane(), gridBagConstraints);
			jPanel.add(getJPanel1(), gridBagConstraints1);
		}
		return jPanel;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return ScrollPane für Tabelle
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getZeitenTabelle());
		}
		return jScrollPane;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Buchungstabelle
	 */
	private JTable getZeitenTabelle() {
		if (zeitenTabelle == null) {
			zeitenTabelle = new JTable() {
				private static final long serialVersionUID = 1L;

				public Component prepareEditor(TableCellEditor editor, int row,
						int column) {
					Component c = super.prepareEditor(editor, row, column);

					if (c instanceof JTextComponent) {
						((JTextField) c).selectAll();
					}

					return c;
				}
			};
			zeitenTabelle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			zeitenTabelle.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent evt) {
					if (evt.getModifiers() != 0) {
						zeitenModell.setEditierbar(false);
					}
				}

				public void keyReleased(KeyEvent evt) {
					if (evt.getModifiers() == 0) {
						zeitenModell.setEditierbar(true);
					}
				}

				public void keyTyped(KeyEvent evt) {
				}
			});
		}
		return zeitenTabelle;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Buchungs-Panel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 0.0;
			gridBagConstraints.weighty = 0.0;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints.gridx = 1;
			TitledBorder umrandung = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					"Buchung durchführen");
			jPanel1.setBorder(umrandung);
			jPanel1.add(getJetztBuchen(), gridBagConstraints);
			jPanel1.add(getAktivitaetenAuswahl(), gridBagConstraints1);
		}
		return jPanel1;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Buchungsbutton
	 */
	private JButton getJetztBuchen() {
		if (jetztBuchen == null) {
			jetztBuchen = new JButton();
			jetztBuchen.setText("Buchung");
			jetztBuchen.setMnemonic(KeyEvent.VK_U);
			jetztBuchen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Aktivitaet temp = (Aktivitaet) getAktivitaetenAuswahl()
							.getSelectedItem();
					buchungsMenu.neueBuchung(temp);
				}
			});
		}
		return jetztBuchen;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Aktivitätenauswahl für Buchung
	 */
	private JComboBox getAktivitaetenAuswahl() {
		if (aktivitaetenAuswahl == null) {
			aktivitaetenAuswahl = new JComboBox();
		}
		return aktivitaetenAuswahl;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Menüzeile
	 */
	private JMenuBar getZeitMenu() {
		if (zeitMenu == null) {
			zeitMenu = new JMenuBar();
			zeitMenu.add(getDateiMenu());
			zeitMenu.add(getBuchungsMenu());
			zeitMenu.add(getOptionMenu());
			zeitMenu.add(getFunktionsMenu());
			zeitMenu.add(getHilfeMenu());
		}
		return zeitMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Dateimenü
	 */
	private DateiMenu getDateiMenu() {
		if (dateiMenu == null) {
			dateiMenu = new DateiMenu();
			dateiMenu.addSeparator();
			dateiMenu.add(getBeendeTool());
		}
		return dateiMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Beenden-Menüpunkt im Dateimenü
	 */
	private JMenuItem getBeendeTool() {
		if (beendeTool == null) {
			beendeTool = new JMenuItem();
			beendeTool.setText("Beenden");
			beendeTool.setMnemonic(KeyEvent.VK_E);
			beendeTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					InputEvent.CTRL_MASK));
			beendeTool.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					beendenManager.event(new BeendenEventFactory()
							.beendenEvent());
				}
			});
		}
		return beendeTool;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Buchungsmenü
	 */
	private BuchungsMenu getBuchungsMenu() {
		if (buchungsMenu == null) {
			buchungsMenu = new BuchungsMenu(this);
		}
		return buchungsMenu;
	}

	/**
	 * <p>
	 * Diese Methode initialisiert das FunktionsMenu.
	 * 
	 * @return FunktionsMenu
	 */
	private FunktionsMenu getFunktionsMenu() {
		if (funktionsMenu == null) {
			funktionsMenu = new FunktionsMenu();
		}
		return funktionsMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Optionenmenü
	 */
	private OptionenMenu getOptionMenu() {
		if (optionMenu == null) {
			optionMenu = new OptionenMenu();
		}
		return optionMenu;
	}

	/**
	 * <p>
	 * Zugriff und Initialisierung des GUI-Objekts
	 * </p>
	 * 
	 * @return Hilfemenü
	 */
	private HilfeMenu getHilfeMenu() {
		if (hilfeMenu == null) {
			hilfeMenu = new HilfeMenu();
		}
		return hilfeMenu;
	}
}

/**
 * <p>
 * Zellen-Editor, das zu editierende Textfeld automatisch zu selektieren.
 * </p>
 * 
 * @author Lars Geyer
 */
class NewDefaultCellEditor extends DefaultCellEditor implements FocusListener {
	private static final long serialVersionUID = 1L;
	private JTextField saveTextField = null;

	/**
	 * <p>
	 * gesperrt
	 * </p>
	 */
	protected NewDefaultCellEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	/**
	 * <p>
	 * gesperrt
	 * </p>
	 */
	protected NewDefaultCellEditor(JComboBox comboBox) {
		super(comboBox);
	}

	/**
	 * <p>
	 * Merken des Textfeld-Elements und verbinden mit einem Focus-Listener
	 * <p>
	 * 
	 * @param textField
	 *            Textfeld der Tabelle
	 */
	NewDefaultCellEditor(JTextField textField) {
		super(textField);
		saveTextField = textField;
		textField.addFocusListener(this);
	}

	/**
	 * <p>
	 * Wenn die Zelle den Focus erhält, soll sie das Textfeld selektieren.
	 * </p>
	 * 
	 * @param evt
	 *            Event-Objekt
	 */
	public void focusGained(FocusEvent evt) {
		saveTextField.selectAll();
	}

	/**
	 * <p>
	 * unbenutzt
	 * </p>
	 */
	public void focusLost(FocusEvent evt) {
	}
}