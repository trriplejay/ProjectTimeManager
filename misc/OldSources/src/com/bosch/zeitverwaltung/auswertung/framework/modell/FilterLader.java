package com.bosch.zeitverwaltung.auswertung.framework.modell;

/**
 * <p>
 * Erzeugt aus einem Klassennamen ein BuchungsFilter-Objekt.
 * </p>
 * 
 * @author Lars Geyer
 */
public class FilterLader {
	public BuchungsFilter erzeugeFilter(String filterKlasse)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> klasse = getClass().getClassLoader().loadClass(filterKlasse);
		return (BuchungsFilter)klasse.newInstance();
	}
}
