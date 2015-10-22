package com.bosch.zeitverwaltung.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <p>
 * Die Klasse kapselt die komplexe DOM-Schnittstelle zum Einlesen einer
 * XML-Datei und bietet eine intuitive Schnittstelle zum hierarchischen
 * Durchlaufen des XML-Dokumentes.
 * </p>
 * 
 * @author Lars Geyer
 * @see DOMWrapper
 * @see DOMWriter
 */
public final class DOMReader extends DOMWrapper {
	/**
	 * <p>
	 * Konstruktor, um eine XML-Struktur aus einer Datei zu lesen.
	 * </p>
	 * 
	 * @param input
	 *            <em>File</em>-Handle zur XML-Datei
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	public DOMReader(File input) throws IOException {
		super();
		leseDatei(new FileInputStream(input));
	}

	/**
	 * <p>
	 * Konstruktor, um eine XML-Struktur aus einem Jar-File zu lesen. Der
	 * Zugriff darauf erfolgt über <em>ClassLoader.getResource</em>, der
	 * allerdings einen <em>InputStream</em> und kein <em>File</em>-Handle
	 * zurückgibt.
	 * </p>
	 * 
	 * @param input
	 *            <em>InputStream</em> der XML-Datei
	 * @throws IOException
	 *             Bei Dateizugriffproblemen
	 */
	public DOMReader(InputStream input) throws IOException {
		super();
		leseDatei(input);
	}

	/**
	 * <p>
	 * Gibt ein DOM-Element zurück. Es wird erwartet, dass unterhalb des Vaters
	 * genau ein Element mit dem übergebenen Tag-Namen existiert. Dieses wird
	 * zurückgegeben. Wird als Vater <em>null</em> übergeben, so wird das
	 * Wurzelelemente des Dokumentes durchsucht.
	 * </p>
	 * 
	 * @param vater
	 *            Vater-Element, unter dem Element gesucht wird
	 * @param tagname
	 *            Name des gesuchten Tags
	 * @return Element-Objekt des gesuchten Tags
	 */
	public Element getElement(Element vater, String tagname) {
		Element back = null;

		NodeList elementliste = extractNodeList(vater, tagname);
		if (elementliste.getLength() > 0) {
			back = (Element) elementliste.item(0);
		}

		return back;
	}

	/**
	 * <p>
	 * Gibt eine Liste mit DOM-Elementes zurück. Wenn mehrere Elemente gleichen
	 * Tags existieren, kann diese Methode angewendet werden, um alle Elemente
	 * zu erhalten. Wird als Vater <em>null</em> übergeben, so wird das
	 * Wurzelelemente des Dokumentes durchsucht.
	 * </p>
	 * 
	 * @param vater
	 *            Vater-Element, unter dem Element gesucht wird
	 * @param tagname
	 *            Name des gesuchten Tags
	 * @return Liste mit Element-Objekten des gesuchten Tags
	 */
	public Collection<Element> getElementListe(Element vater, String tagname) {
		Collection<Element> back = new LinkedList<Element>();

		NodeList elementliste = extractNodeList(vater, tagname);
		for (int i = 0; i < elementliste.getLength(); i++) {
			back.add((Element) elementliste.item(i));
		}

		return back;
	}

	/**
	 * <p>
	 * Extrahiert eine DOM-Nodeliste, die in den aufrufenden Methoden zur
	 * Extraktion der gesuchten Elemente benutzt wird.
	 * </p>
	 * 
	 * @param vater
	 *            Vater-Element, unter dem Element gesucht wird
	 * @param tagname
	 *            Name des gesuchten Tags
	 * @return Nodeliste mit Element-Objekten des gesuchten Tags
	 */
	private NodeList extractNodeList(Element vater, String tagname) {
		if (vater == null) {
			vater = doc.getDocumentElement();
		}

		return vater.getElementsByTagName(tagname);
	}

	/**
	 * <p>
	 * Methode zum Parsen einer XML-Datei und Aufbauen der DOM-Struktur. Sie
	 * wird im Konstruktor aufgerufen.
	 * </p>
	 * 
	 * @param inputStream
	 *            Input-Stream, über den die XML-Daten eingelesen werden.
	 * @throws IOException
	 *             Bei Zugriffsproblemen
	 */
	private void leseDatei(InputStream inputStream) throws IOException {
		try {
			doc = xmlBuilder.parse(inputStream);
			inputStream.close();
		} catch (SAXException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		}
	}
}
