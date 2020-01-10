[![MIT license](https://badgen.net/github/license/micromatch/micromatch.svg)](http://opensource.org/licenses/MIT)

[!https://badgen.net/lgtm/lines/g/apache/cloudstack/java.svg]

## Informatik-Konzept
Den informatischen Teil der Arbeit kann man grundsätzlich in 2 Bereiche aufteilen. Es soll
eine einfache grafische Benutzeroberfläche gestaltet werden, auf welcher man den Mischvorgang
steuern sowie grundlegende Einstellungen des Spieles vornehmen kann. Aus programmtechnischen
Gründen wird hierfür die Programmiersprache Java und das GUI-Toolkit Swing verwendet.
Die Hardwarekomponenten setzen sich aus einem Raspberry Pi 3B+ und einem 7"LCD Touchscreen
der Firma Elecrow zusammen.

Zusätzlich besteht dieser Teilbereich der Arbeit auch aus der hardwarenahen Programmierung der
Hauptplatine. Mithilfe der Programmiersprache C sollte der verbaute 8-Bit Mikrocontroller der
AVR-Familie namens ATmega324P programmiert werden. Dieser steuert den gesamten Ablauf
der Maschiene.
Der Datenaustausch zwischen der Hauptplatine und dem Raspberry PI erfolgt über die serielle
Schnittstelle namens UART. Im Hintergrund wird gleichzeitig eine Json Logdatei geschrieben.
## Installation
TODO

# Dokumentation
## Wichtigste Funktionen
### Raspberry

 - Steuerung der gesamten Maschine
 - Grafische Benutzeroberfläche
 - Übertragung der Daten per UART  
 - Auswertung der Daten

### Motherboard
 * Einlesen der Sensordaten  
 * Übertragung der Daten per UART  
 * Auswertung der Daten
 * Steuerung des Motors sowie der Hubmagnete
## Arten von Daten und deren Erfassung
  
 * 3x Kapazitiver Sensor (DIGITAL)

## Protokoll
Das Protokoll ist verbindungslos und Request-Response- (Master-Slave) -orientiert. Die Datenübertragung erfolgt textuell wobei nur Großbuchstaben verwendet werden dürfen, die CRC32-Prüfsumme wird mit einer im Raspberry gespeicherte Tabelle realisiert und nur über die Daten und dem Trennzeichen `#` berechnet. 

### Aufbau:
| Doppelpunkt `:` | Daten (ASCII)| Trennzeichen `#` | CRC32-Prüfsumme | Semicolon `\n` |
| --------------- | ------------------------------------------------ | ----------- | --------------- | ------------- |
| 8-Bit | 16-Bit | 8-Bit | 32-Bit | 8-Bit


### Requests (mit Response-Beispielen)
| Request | Request-String Rx | Response | Response-String Tx |
| ------- | ------------- | -------- | ------------------ |
| INIT | `IN` | Initialisierungs-Zustand | `:OK#CRC;` oder`:E<code>#CRC;` |
| SHUFFLE | `SH` | setzt State "Shuffle" | `:OK#CRC;` oder`:E<code>#CRC;` |
| DEAL | `D<Anzahl der karten>` | setzt State "Deal" | `:OK#CRC;` oder`:E<code>#CRC;` 
| AUTODEAL | `A<Anzahl der karten>` | setzt State "AutoDeal" (standartmäßig 4x5) | `:OK#CRC;` oder`:E<code>#CRC;` |
| SHUTDOWN | `XX` | setzt State "Shutdown" | `:OK#CRC;` oder`:E<code>#CRC;` |
  

### Requests (mit Response-Beispielen)
Als Response kann entweder `:OK#CRC;` als Zeichen für ein gültiges Verarbeiten des Befehls am Mainboard oder ein `:E<code>#CRC;` zurückgegeben werden.  Dieser Errorcode sagt folgendes aus:


 - `:E<1>#CRC;` -->  TODO
 - `:E<2>#CRC;` -->  TODO
 - `:E<3>#CRC;` -->  TODO

# CommLog-Files
  Im Entwicklunsmodus die gesamte Kommunikation als .json-Datei in das Verzeichnis `/home/pi/ReShuffled_PI/reshuffled.log` gespeichert.
  
  Diese Datei ist so zu lesen:  
### REQUESTS
`Zeitstempel : Name des Requests`
### RESPONSES
`Zeitstempel : Response CRC: *empfangener CRC* <-> *berechneter CRC*`



