module de.lgblaumeiser.ptm.rest {
	requires spring.beans;
	requires spring.core;
	requires spring.context;
	requires spring.web;
	requires spring.webmvc;
	requires spring.boot;
	requires spring.boot.autoconfigure;

	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;

	requires org.apache.commons.io;
	requires slf4j.api;

	requires de.lgblaumeiser.ptm.datamanager;
}