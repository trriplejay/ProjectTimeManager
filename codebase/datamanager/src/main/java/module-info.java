module de.lgblaumeiser.ptm.datamanager {
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires org.apache.commons.io;

	exports de.lgblaumeiser.ptm.datamanager.model;
	exports de.lgblaumeiser.ptm.datamanager.service;
	exports de.lgblaumeiser.ptm.analysis;
	exports de.lgblaumeiser.ptm.store;

	exports de.lgblaumeiser.ptm.datamanager.model.internal to com.fasterxml.jackson.databind;
}