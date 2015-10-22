package com.bosch.zeitverwaltung.view.dialog;

public abstract class UIDialogFactory {
	/**
	 * <p>
	 * Neuer Dialog zur Auswahl eines Tages.
	 * </p>
	 * 
	 * @return Tages-Auswahl-Dialog
	 */
	public abstract TagOeffnenAuswahl erzeugeTagOeffnenAuswahlDialog();

	/**
	 * <p>
	 * Neuer Dialog zur Auswahl einer Aktivität.
	 * </p>
	 * 
	 * @return Aktivitäten-Auswahl-Dialog
	 */
	public abstract AktivitaetenAuswahl erzeugeAktivitaetenAuswahlDialog();

	/**
	 * <p>
	 * Neuer Editor für die Eingabe/Veränderung der Aktivitäten-Liste.
	 * </p>
	 * 
	 * @return Aktivitäten-Editor
	 */
	public abstract AktivitaetenEditor erzeugeAktivitaetenEditor();

	/**
	 * <p>
	 * Neuer Minuten-Delta-Auswahldialog
	 * </p>
	 * 
	 * @return Minuten-Delta-Auswahldialog
	 */
	public abstract MinutenDeltaAuswahl erzeugeMinutenDeltaAuswahl();

	/**
	 * <p>
	 * Neuer Aktivitätssortierung-Auswahldialog
	 * </p>
	 * 
	 * @return Aktivitätssortierung-Auswahldialog
	 */
	public abstract AktivitaetsSortierungsAuswahl erzeugeAktivitaetsSortierungsAuswahl();

	/**
	 * <p>
	 * Neuer About Anwendungs-Dialog.
	 * </p>
	 * 
	 * @return About-Dialog
	 */
	public abstract AboutInformation erzeugeAboutInformationDialog();

	/**
	 * <p>
	 * Neuer Nachfragen-Dialog, um dem Benutzer direkte Fragen
	 * (Ja/Nein[/Abbruch])zu stellen
	 * </p>
	 * 
	 * @return Nachfragen-Dialog
	 */
	public abstract NachfragenBox erzeugeNachfragenDialog();
}
