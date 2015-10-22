@ECHO OFF
SET ZVW_PATH=C:\daten\users\DE_gel2fr\Local_Workspace\Zeitverwaltung\release
SET CLASSPATH=%ZVW_PATH%\standard_modell.jar;%ZVW_PATH%\standard_ui.jar;
SET CLASSPATH=%CLASSPATH%;%ZVW_PATH%\xmldateihandling.jar;%ZVW_PATH%\zeitverwaltung.jar
SET CLASSPATH=%CLASSPATH%;%ZVW_PATH%\standard_auswertungen.jar;%ZVW_PATH%\standard_auswertungen_ui.jar
SET CLASSPATH=%CLASSPATH%;%ZVW_PATH%\nachocalendar-0.23.jar
java com.bosch.zeitverwaltung.Zeitverwaltung