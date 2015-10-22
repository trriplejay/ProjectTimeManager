package com.bosch.zeitverwaltung.services;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

/**
 * <p>
 * Die Klasse kapselt die komplexe DOM-Schnittstelle zum Speichern einer
 * XML-Datei und bietet eine intuitive Schnittstelle zum hierarchischen Erzeugen
 * des XML-Dokumentes.
 * </p>
 * 
 * @author Lars Geyer
 * @see DOMWrapper
 * @see DOMReader
 */
public final class DOMWriter extends DOMWrapper {
	private File xmlfile = null;

	/**
	 * <p>
	 * Konstruktor, er erzeugt ein leeres XML-DOM-Dokument.
	 * </p>
	 * 
	 * @param output
	 *            Name der XML-Datei, die geschrieben werden soll
	 * @param tagname
	 *            Name des Wurzel-Tags
	 * @throws IOException
	 *             Bei Zugriffproblemen
	 */
	public DOMWriter(File output, String tagname) throws IOException {
		super();
		xmlfile = output;
		doc = xmlBuilder.newDocument();
		Element wurzel = doc.createElement(tagname);
		doc.appendChild(wurzel);
	}

	/**
	 * <p>
	 * Erzeuge ein neues Tag-Element unterhalb des Vater-Elements. Wird als für
	 * den Vater <em>null</em> übergeben, so hänge das Element an das
	 * Wurzel-Element. Die Methode gibt eine Referenz auf das erzeugte Element
	 * zurück, es ist aber bereits in die XML-Hierarchie eingehängt.
	 * </p>
	 * 
	 * @param vater
	 *            Vater-Element, an das neuer Tag gehängt werden soll
	 * @param tagname
	 *            Name des Tags, das erzeugt werden soll.
	 * @return Eine Referenz auf das erzeugte Element
	 */
	public Element neuesElement(Element vater, String tagname) {
		if (vater == null) {
			vater = doc.getDocumentElement();
		}

		Element back = doc.createElement(tagname);
		vater.appendChild(back);
		return back;
	}

	/**
	 * <p>
	 * Nach der Erzeugung der XML-Struktur wird diese Methode aufgerufen, um das
	 * XML-File zu schreiben.
	 * </p>
	 * 
	 * @throws IOException
	 *             Bei Zugriffsproblemen
	 */
	public void speichereDatei() throws IOException {
		try {
			Transformer xmlOutput = TransformerFactory.newInstance()
					.newTransformer();
			xmlOutput.setOutputProperty(OutputKeys.INDENT, "yes");
			xmlOutput.setOutputProperty(OutputKeys.STANDALONE, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlfile);
			xmlOutput.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		}
	}
}
