# Protokoll Nr. 8 ![](https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/HTL_Kaindorf_Logo.svg/300px-HTL_Kaindorf_Logo.svg.png)

Übungsdatum: **11.05.2019**  
Name: Vollmaier Alois    
KNr.: 14
Klasse: 4AHME    
Gruppe: 3

Anwesenheit:

|Anwesend|Abwesend|
|--|--|
|Alois Vollmaier, Patrick Wegl, Matthias Winter, Thomas Winter, Sarah Vezonik, Mercedes Wesonig|---|
----------

### Themen-Übersicht

-   **[1]** - Grundlagen
    -   [1.1] -  _Erklärung des Programmcodes
## \[1\] Erklärung des Programmcodes

### Modbus Request Handler
 

 **Fehler Handling**

Zuerst wird geprüft ob das  **ertse ankommende Byte ein Startbyte** nähmlich ein `:` ist. Falls dies nicht der Fall ist muss sollte im **`ErrorCount`** hochgezählt werden. 

Wenn im Buffer bereits ein Startbyte `:` vorhanden ist, darf kein Zweites innerhalb der Request geschrieben werden, da dies sonst zu Fehlern führen könnte. Falls ein solcher Fehler auftritt, sollte in einer Funktion ein **`ErrorCount`** hochgezählt werden.

Werden danach mehr Zeichen in den Buffer geschrieben als Platz ist, wird ebenfalls der **`ErrorCount`** hochgezählt.

Sobalt beim Beenden der Request mit **`\n`** ein Fehler auftritt, so soll auch der **`ErrorCount`** hochgezählt werden. 

Ist nach dem Übertragen der **`ErrorCount`** größer als 0, so soll die Anzahl der Fehler ausgegeben werden.
w1r
````C
void app_handleUartByte (char c)
{
  if (c == ':')
  { //Wenn Zeichen kommt
    if (app.bufferindex > 0)
    { //Wenn im Bufferindex bereits ein ":" ist, wird der ErrorCount hochgezählt
      app.ErrorCount = app_inc16BitCount(app.ErrorCount);
    }
    app.modbusBuffer[0] = c;
    app.bufferindex = 1; //Buffer Frame wird auf 1 gesetzt
  }
  else if (app.bufferindex == 0)
  { //Wenn ein ungünstiges Zeichen geschickt wird ( alles außer ':')
    app.ErrorCount = app_inc16BitCount(app.ErrorCount); //ErrorCount wird erhöht / LED in funktion wird gesetzt
  }
  else if (app.bufferindex >= (sizeof (app.modbusBuffer)))
  { //Wenn zu speichernder Wert größer als Buffergröße ist
    app.ErrorCount = app_inc16BitCount(app.ErrorCount);
  }
  else
  { //Wenn alles richtig läuft
    app.modbusBuffer[app.bufferindex++] = c; //Input wird eingelesen
    if (c == '\n')
    { //Wenn Übertragung beendet wird durch '\n'
      uint8_t errorCode = app_handleModbusRequest();
      if (errorCode > 0)
      {
        //printf("Fehler %u \n \r", err
    app.modbusBuffer[app.bufferindex++] = c; //Input wird eingelesenorCode);
        app.ErrorCount = app_inc16BitCount(app.ErrorCount);
      }

      app_handleModbusRequest(); //in Funktion app_handleModbusRequest springen
      app.bufferindex = 0; //bufferindex auf  setzen
    }
  }
}
````

### 2.  ErrorCount

````C
uint16_t app_inc16BitCount(uint16_t cnt) {
  sys_setLed(1); //Wenn ein Fehler auftritt, leuchtet LED 1
  return cnt == 0xffff ? cnt : cnt + 1; //Count wird hochgezählt
}
````

Zuerst muss im app.c die Funktion app_inc16BitCount erstellt werden, um den Counter hochzuzählen.

````C
struct App
{
  uint8_t flags_u8;
  char modbusBuffer[32];
  uint8_t bufferindex;
  uint16_t ErrorCount;
  uint16_t mbInputRegister;
};
````

Im app.h muss danach die varaiable ErrorCount erstellt werden

### 3. Aufleuchten der LED bei Fehler 
Um den Fehler nicht nur im Terminal zu sehen, wäre es Vorteilhaft eine LED beim eintreffen eines Fehlers zu aktivieren. Diese LED sollte aber nicht dauerhaft leuchten, da man sonst nur den ersten Fehler erkennen könnte. Um dies zu erreichen muss im 1ms Task die LED für 2 Sekunden zum leuchten gebracht werden.
````C
void app_task_1ms (void) {
  static uint16_t oldErrorCount = 0; //Alten ErrorCount erstellen
  static uint16_t timer = 0; //Timer-Variable erstellen und auf 0 setzen
  if(app.ErrorCount != oldErrorCount) { //Wenn der neue errorcount nicht den alten entspricht geht man in die if{}
    oldErrorCount = app.ErrorCount; //Alte errorcount wird mit app.ErrorCount gleichgesetzt
    timer = 2000; //Timer wird auf den Wert 200 gesetzt
    sys_setLed(1); //Led wird auf 1 gesetzt (eingeschalten)
  } 
  if(timer > 0) { //Wenn Timer auf 2000 gesetzt wurde / bzw. größer als 0 ist spingt er in die if{}
    timer--; //Timer wird bei jedem durchlauf um 1 vermindert
    if(timer==0) { //Wenn timer gleich 0 ist springt er in die if{}
      sys_setLed(0); //LED wir auf 0 gesetzt (ausgeschaltet)
    }
  }
}
````

### 4. HEX2INT Funktion
Die  Funktion hex2int wandelt den Hex-Wert des LCR in einen Dezimalwert umwandelt. 

Hierbei wird zuerst überprüft ob das Zeichen in einem Gültigen Wertebereich liegt. Anschließend werden Character im Hexadezimalformat um zu einer Dezimalzahl umgewandelt.
````C
uint8_t hex2int (char h) { //Funktion zum Umwandeln von Hex in Integer
  if(h >= '0' && h <= '9') { //Wenn h zwischen 0 und 9 liegt springt man in die if
    return h - '0';
  }
  
  if(h >= 'A' && h <= 'F') { //Wenn h zwischen A und F liegt springt man in die if
    return h - 'A' + 10; //Wert wird von A - F auf 10 - 15 Umgewandelt
  }
}
````

### 5. Funktion zur Verarbeitung der eingetroffenen Daten

Nachdem der Buffer gefüllt ist wird die Funktion app_handleModbusRequest aufgerufen die sich jetzt um diese Anfrage kümmert.

Die Funktion macht folgendes:

-   Sie schaut ob die Zeichenkette einer validen Modbus Request entspricht.
-   Danach schaut die ob die Adresse mit der Adresse des µC übereinstimmt.
-   Wenn diese Adresse übereinstimmt kann der µC eine Response schicken.

````C
uint8_t app_handleModbusRequest() { //Funktion zur verarbeitung der eingetroffenen Daten
  //printf("Request eingetroffen: %s", app.modbusBuffer);
  
  //Prüfen ob gültiger Modbus Frame eintrifft
  
  char *b = app.modbusBuffer;
  uint8_t size = app.bufferindex;
  
  if(size < 9) {return 1;} //Wenn Modbuslänge kleiner als 9 ist - return 1 - Fehler
  if(b[0] != ':') {return 2;} //Wenn erstes Zeichen kein : ist - Fehler
  if(b[size - 1] != '\n') {return 3;} //Wenn letztes Zeichen kein '\n' ist - Fehler
  if(b[size - 2] != '\r') {return 4;} //Wenn vorletzes Zeihen kein '\r' ist - Fehler
  if( (size - 3) % 2 != 0) {return 5;} //Wenn Summe der Zeichen zwischen : und 'B9' gerade ist
  for (uint8_t i = 1; i < (size - 2); i++) { //Schleife für jedes Zeichen
    char c = b[i];
    if(! ((c >= '0' && c <= '9') || //Überprüfung ob Zahlen zwischen 0 und 9 liegen
           (c >= 'A' && c <= 'F'))) //Überprüfung ob Zeichen zwischen A und F liegen
      return 6;
  }
  
  uint8_t lrc = 0; //lrc wird mit Wert 0 erstellt
  for(uint8_t i = 1; i < (size - 4); i++) { //
    lrc += b[i];
  }
  lrc = (uint8_t)(-(int8_t)lrc); //Wandle wert auf unsigned interger um, füge minus hinzu, wandle wert wieder auf interger um
  
  char s[3];
  snprintf(s, sizeof s, "%02X", lrc); //Immer mit sicheren stringfunktionen arbeiten
  if (b[size - 4] != s[0]) {return 7;} //Wenn lrc falsch - Fehlercode 7
  if (b[size - 3] != s[1]) {return 7;} //Wenn lrc falsch - Fehlercode 7

  //printf("Request richtig \n");
  uint8_t i, j; 
  for(i = 1, j = 0; i < (size -4); i += 2, j++) {
    uint8_t hn = hex2int(b[i]); //HighnNible wird gesetzt
    uint8_t ln = hex2int(b[i+1]); //LowNible wird gesetzt
    if(hn == 0xff || ln == 0xff) { //Wenn Werte stimmen springt er in die if
      return 0;
    }
    uint8_t value = hn * 16 + ln;
    b[j] = value;
  }
  size = j;
  
  uint8_t deviceAddress = b[0]; //device Adress wird erzeugt
  
  if(deviceAddress != 1) {
    return 0;
  }
  
  uint8_t functionCode = b[1];
  switch(functionCode) {
    case 0x04: {
      uint16_t startAddress = b[2] << 8 |b[3];
      uint16_t quantity = b[4] << 8 | b[5];
      if(quantity < 1 || quantity > 0x7d) {
        b[1] = 0x80 | b[1]; //errer
        b[2] = 0x03; //Quantity out of range
        size = 3;
      } else if(startAddress != 1 | quantity != 1) {
        b[1] = 0x80 | b[1]; //errer
        b[2] = 0x02; //Falsche start-Adresse
        size = 3;
      } else {
        b[2] = 2; //Antowrt 2 Bytes
        b[3] = app.mbInputRegister >> 8;
        b[4] = app.mbInputRegister & 0xff;
        size = 5;
      }
      break;
    }
  default: {
        b[1] = 0x80 | b[1]; //errer
        b[2] = 0x01; //Function Code not Supported
        size = 3;
	  }
  }  
  
  lrc = 0;
  printf(":");
  for(i = 0; i < size; i++) {
    printf("%02X", (uint8_t)b[i]);
    lrc += b[i];
  }
  lrc = (uint8_t)(-(int8_t)lrc);
  printf("%02X", lrc);
  printf("\r\n");
  return 0;
}
````

