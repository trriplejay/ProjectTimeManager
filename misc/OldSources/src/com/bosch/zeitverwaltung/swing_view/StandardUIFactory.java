package com.bosch.zeitverwaltung.swing_view;

import com.bosch.zeitverwaltung.swing_view.dialog.StandardDialogFactory;
import com.bosch.zeitverwaltung.view.MainWindow;
import com.bosch.zeitverwaltung.view.UIFactory;
import com.bosch.zeitverwaltung.view.dialog.UIDialogFactory;

/**
 * <p>
 * Standard-UI-Factory, gibt Referenz auf Hauptfenster und Dialog-Factory
 * zurück.
 * </p>
 * 
 * @author Lars Geyer
 * 
 */
public class StandardUIFactory extends UIFactory {
	private MainWindowImpl hauptFenster;
	private UIDialogFactory dialogFactory;
	
	/**
	 * <p>
	 * Konstruktor, er registriert sich als Standard-UI-Factory
	 * </p> 
	 */
	public StandardUIFactory() {
		setFactory(this);
		hauptFenster = new MainWindowImpl();
		dialogFactory = new StandardDialogFactory(hauptFenster);
	}

	/**
	 * {@inheritDoc}
	 */
	public MainWindow erzeugeMainWindow() {
		return hauptFenster;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UIDialogFactory getDialogFactory() {
		return dialogFactory;
	}
}
