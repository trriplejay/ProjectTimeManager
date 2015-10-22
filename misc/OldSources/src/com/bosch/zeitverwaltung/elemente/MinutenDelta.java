package com.bosch.zeitverwaltung.elemente;

/**
 * <p>
 * Abstrakte Repräsentation eines Abrechnungseinheit
 * </p>
 * 
 * <p>
 * Die gültigen Abrechnungseinheiten werden in Unterklassen definiert.
 * </p>
 * 
 * @author Lars Geyer
 */
public final class MinutenDelta {
	public static final int MinutenDeltaEineMinuteID = 1;
	public static final int MinutenDeltaDreiMinutenID = 3;
	public static final int MinutenDeltaFuenfMinutenID = 5;
	public static final int MinutenDeltaFuenfzehnMinutenID = 15;

	public static final MinutenDelta MinutenDeltaEineMinute = new MinutenDelta(
			MinutenDeltaEineMinuteID);
	public static final MinutenDelta MinutenDeltaDreiMinuten = new MinutenDelta(
			MinutenDeltaDreiMinutenID);
	public static final MinutenDelta MinutenDeltaFuenfMinuten = new MinutenDelta(
			MinutenDeltaFuenfMinutenID);
	public static final MinutenDelta MinutenDeltaFuenfzehnMinuten = new MinutenDelta(
			MinutenDeltaFuenfzehnMinutenID);

	/**
	 * <p>
	 * Gibt das Minutendelta-Objekt zur ID zurück.
	 * </p>
	 * 
	 * @param Gesuchte id
	 * 
	 * @return Minutendelta-Objekt 
	 */
	public static MinutenDelta get(int id) {
		MinutenDelta back = null;
		
		switch(id) {
		case MinutenDeltaEineMinuteID:
			back = MinutenDeltaEineMinute;
			break;
		case MinutenDeltaDreiMinutenID:
			back = MinutenDeltaDreiMinuten;
			break;
		case MinutenDeltaFuenfMinutenID:
			back = MinutenDeltaFuenfMinuten;
			break;
		case MinutenDeltaFuenfzehnMinutenID:
			back = MinutenDeltaFuenfzehnMinuten;
			break;
		}
		return back;
	}
	
	private int id;

	/**
	 * <p>
	 * Konsturktor der Enumeration
	 * </p>
	 * 
	 * @param id
	 *            ID und gleichzeitg das Delta
	 */
	private MinutenDelta(int id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Gibt ID des Minutendeltas zurück.
	 * </p>
	 * 
	 * @retuzrn ID des Minutendeltas
	 */
	public int getID() {
		return id;
	}

	/**
	 * <p>
	 * Ausgabe der Abrechunungseinheit in Minuten.
	 * </p>
	 * 
	 * @return Abrechnungseinheit
	 */
	public int getMinutenDelta() {
		return id;
	}
}
