package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Sonderkativität, die eine Unterbrechung darstellt. Diese ist durch ihre
 * Eigenschaften festgelegt.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class UnterbrechungsAktivitaet extends Aktivitaet {
	/**
	 * <p>
	 * Konstruktor, er erzeugt die spezielle Unterbrechungs-Aktivität
	 * </p>
	 */
	public UnterbrechungsAktivitaet() {
		super("Unterbrechung", "Unterbrechung", "Unterbrechung", false);
	}

	/**
	 * <p>
	 * Methode wird leer überschrieben, dadurch kann dieses Property nicht mehr
	 * gesetzt werden.
	 * </p>
	 */
	@Override
	public void setEuro(double euro) {

	}

	/**
	 * <p>
	 * Methode wird leer überschrieben, dadurch kann dieses Property nicht mehr
	 * gesetzt werden.
	 * </p>
	 */
	@Override
	public void setKm(int km) {

	}

	/**
	 * <p>
	 * Methode wird leer überschrieben, dadurch kann dieses Property nicht mehr
	 * gesetzt werden.
	 * </p>
	 */
	@Override
	public void setReisezeit(boolean aktiv) {

	}
}
