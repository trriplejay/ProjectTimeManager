/*
 * Copyright 2015 Lars Geyer-Blaumeiser
 */

/**
 * This package contains the data model of the project time manager. The basic architecture
 * expects that all objects are PoJos and are only created from within the package. Factory
 * methods provide access to objects. All base data types are value objects which cannot be changed
 * after creation. Object representing higher level objects like day objects summarizing a set
 * of bookings are changeable by adding and removing base objects.
 */
package de.lgblaumeiser.ptm.datamanager.model;