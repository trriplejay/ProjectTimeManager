package com.bosch.zeitverwaltung.services;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

/**
 * <p>
 * Wrapper um das DOM-Paket, da dieses sehr umständlich zu nutzen ist. Die
 * Wrapper-Klassen erlauben einen intuitiven Zugriff auf die XML-Elemente,
 * setzen allerdings eine Struktur voraus, die von den benutzten XML-Formaten
 * eingehalten werden. Die wesentliche Einschränkung ist die strenge
 * Hierarchisierung von Tags, Text-Elemente sind nur als reiner Inhalt eines
 * Tags erlaubt und dürfen nicht mit weiteren Tags vermischt werden.
 * </p>
 * 
 * <p>
 * Der Wrapper kapselt die Erzeugung des Document-Builders, der dann zum
 * Erzeugen von XML-Dokumenten verwendet werden kann.
 * </p>
 * 
 * @author Lars Geyer
 * @see DOMReader
 * @see DOMWriter
 */
public abstract class DOMWrapper {
	protected DocumentBuilder xmlBuilder;

	protected Document doc = null;

	/**
	 * <p>
	 * Der Konstruktor erzeugt einen Dokument-Builder und gibt eine
	 * <em>IOException</em> zurück, falls dabei ein Problem entstanden ist.
	 * </p>
	 * 
	 * @throws IOException
	 *             Falls die Erzeugung eines Document-Builders nicht
	 *             funktioniert
	 */
	public DOMWrapper() throws IOException {
		try {
			xmlBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new IOException(e.getLocalizedMessage());
		}
	}
}