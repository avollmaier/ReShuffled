# Protokoll Nr. 7 ![](https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/HTL_Kaindorf_Logo.svg/300px-HTL_Kaindorf_Logo.svg.png)

Übungsdatum: **02.04.2019**  
Name: Vollmaier Alois    
KNr.: 15   
Klasse: 4AHME    
Gruppe:   

Anwesenheit:

|Anwesend|Abwesend|
|--|--|
|Alois Vollmaier, Patrick Wegl, Matthias Winter, Thomas Winter|Sarah Vezonik, Mercedes Wesonig|
----------

### Themen-Übersicht

-   **[1]**-Grundlagen
    -   [1.1] -  _Konfiguration des ADC genauer beschrieben_  
        – [1.1.1] -  _Grundlagen Temperaturauswertung_
       -   [1.2] -  _Wiederholung Modbus ASCII_  
        – [1.2.1] -  _Modbus ASCII  anhand eines Beispieles_
-   **[2]**  - Grundlagen einer CPU
	- [2.1] - _Quellcode app.c_
	- [2.2] - _Quellcode app.h_
	- [2.3] - _Quellcode sys.c_
	- [2.4] - _Beschreibung des Programmes_

## \[1\] Grundlagen

\[1.1\] - **Konfiguration des internen ADC**  
Folgende Bits müssen gesetzt werden:
``` c
void app_init (void) {
  memset((void *) &app, 0, sizeof (app));

  ADMUX = 8; // Multiplexer ADC8 = Temperature (1000)
  ADMUX |= (1 << REFS0) + (1 << REFS1); // Nutzt die interne Referenzspannung VRef = 1.1V
  ADMUX |= (1 << ADLAR); // Left Adj, -> Result in ADCH

  ADCSRA = (1 << ADEN); //Enable the adc
  ADCSRA |= 7; // fADC = 125 kHz

}
```
\[1.1.1\] - **Grundlagen Temperaturauswertung**  

Ziel der Stunde war es, die bereits funktionstüchtige Temperaturmesseinheit zu verbessern und neu zu programmieren. Verwendet wurde dazu der integrierte Temperatursensor des Atmega 328p des Arduino Nano Boards. Siehe  [Datenblatt](http://ww1.microchip.com/downloads/en/DeviceDoc/Atmel-7810-Automotive-Microcontrollers-ATmega328P_Datasheet.pdf)

Die meisten neuen AVR-Chips verfügen über einen internen Temperatursensor. Es wird nicht oft verwendet, da er nicht genau ist. Es gibt jedoch eine Reihe von Situationen, in denen dieser Sensor eingesetzt werden kann.

**Beliebte AVR-Chips, die über einen internen Temperatursensor verfügen:**

ATmega8 :  **Nein**  
ATmega8L :  **Nein**  
ATmega8A :  **Nein**  
ATmega168 :  **Nein**  
ATmega168A :  **Ja**  
ATmega168P :  **Ja**  
ATmega328 :  **Ja**  
ATmega328P :  **Ja**  
ATmega1280 (Arduino Mega) :  **Nein**  
ATmega2560 (Arduino Mega 2560) :  **Nein**  
ATmega32U4 (Arduino Leonardo) :  **Ja**

**Verwendung**  
Die Innentemperatur ist die Temperatur im Inneren des Chips, genau wie die CPU-Temperatur eines Computers. Wenn der Arduino nicht im Idle ist, steigt diese Temperatur. Wenn Ausgangspins zur Stromzufuhr verwendet werden (z.B. zur Ansteuerung einer LED), steigt die Innentemperatur weiter an.

Der interne Temperatursensor ist direkt im Chip verbaut ähnlich wie bei einem PC. Während der Arduino arbeitet/rechnet oder wenn Strom durch die PINs fließt, steigt die Temperatur des Chips an. Daher lässt sich mit dem internen Sensor nicht die Umgebungstemperatur der Luft messen, außer wenn man den Arduino gerade einschaltet (er muss dafür aber mindestens 10 min. ausgeschaltet gewesen sein)

**Genauigkeit**  
Laut Datenblatt kann die Temperatur um 10 Grad Celsius fallen. Aber eine Genauigkeit von etwa 2 Grad Celsius ist möglich, wenn Verstärkung und Offset gemessen werden.

Für genaue Temperaturen sollte jeder Chip kalibriert werden. Wenn einige Arduinos des gleichen Typs verwendet werden, kann sich der interne Temperatursensor dennoch unterscheiden.

Verstärkung und Offset  
Die Verstärkung und der Offset sind für die verschiedenen Typen unterschiedlich. Es ist auch bei älteren und neueren Chips anders.

Benutzen Sie dies, um mit den ATmega328-Typen zu beginnen (Temperatur in Grad Celsius):**Temperatur = (ADCW - 324.31) / 1.22**
### [1.2] -  **Wiederholung Modbus ASCII**

Dieser Modus zeichnet sich vor allem dadurch aus, dass er  **anstatt im Binär-Code**  zu übertragen, seine Daten  **in ASCII sendet**, so dass sie von Menschen gelesen werden können.

Dies ist bei dem Senden von binär verschlüsselten Codes wesentlich schwieriger zu bewerkstelligen.  **Der Aufbau des Codes ist immer gleich**:

Die Nachricht beginnt mit einem Doppelpunkt, gefolgt von der Adresse des Empfängers (in ASCII). Danach wird der auszuführende Befehl dargestellt, woraufhin die eigentliche Nachricht folgt. Diese kann in der Länge variieren. Zur Fehlerprüfung wird ein sogenannter LRC-Befehl angehängt. Jede ASCII-Nachricht endet mit den Zeichen CRLF.

|Start|Adresse|Funktion|Daten|LCR|Ende|
|--|--|--|--|--|--|
|Zeichenanzahl|2 Zeichen (:)|2 Zeichen|n Zeichen|2 Zeichen|2 Zeichen (CRLF)|

**Standartmäßig werden nur 7 Daten-Bits verwendet!  
Eine Abänderung des Übertragungsprotokolles ist immer möglich! Grund: OPEN SOURCE**

----------

\[1.2.1\] - **Modbus ASCII**  anhand eines Beispieles zur Datenübertragung:

> :01 04 0000 0001 __ <[CR]><[LF]>

`:` -> Start Frame  
`01` -> Adresse des Geräts am Bus  
`04` -> Read Input Register  
`0000` -> Inputregister 1 für die Temperatur  
`0001` -> Anzahl der Gewählten Input Register  
`__` -> LRC/Prüfsumme  
`<CR><LF>` -> End-Frame

Am Ende sollte sich durch die Übertragung über Modbus folgendes Muster bilden, welches durch ein Oszilloskop ersichtlich wird:

Die Daten werden als Reihe von Einsen und Nullen also Bits gesendet. Eine typische Übertragungsgeschwindigkeit beträgt  **9600 Baud (Bits pro Sekunde)**

#### [](https://github.com/HTLMechatronics/m15-la1-sx/blob/volalm15/protokoll_g3_volalm15_2018-12-18.md#wie-wird-die-pr%C3%BCfsummelrc-gebildet)Wie wird die Prüfsumme(LRC) gebildet?

Anhand eines Beispieles bilden wir nun die Prüfsumme

> **:0401000A000868**

Übertragen werden folgende Bytes:

0x3a  **0x30 0x34 0x30 0x31 0x30 x030 0x30 0x41 0x30 0x30 0x30 0x38**  0x36 0x38 0x0d 0x0a

Die Summe aller fett geschriebenen Bytes als 8-Bit Addition ohne Berücksichtigung des Überlaufs ergibt:  **0x98**
**Das Zweierkomplement ergibt: 0xff-0x98+1 = 0x68**

----------

## \[2\] Quellcode des Programms
[2.1] - **Quellcode app.c**

``` c
void app_init (void)
{
  memset((void *)&app, 0, sizeof(app));
  ADMUX = (1<<REFS1) | (1<<REFS0) | (1<<ADLAR) | 0x08; 
  ADCSRA = (1<<ADEN) | 7; 
  app.modbus.frameIndex = -1;
}


void app_main (void)
{

  printf("-----\n\r");
  printf("  frameIndex: %d\n\r",app.modbusForPrint.frameIndex);
  printf("  frameError: %d\n\r",app.modbusForPrint.frameError);
  printf("ErrorCounter: %d\n\r",app.modbusForPrint.errorcounter);
  printf("  invalidBytes: %d\n\r",app.modbusForPrint.invalidByteCount);
  printf("------\n\r");
  for (int i=0; i <sizeof(app.modbusForPrint.frame);i++)
  {
    printf("%02x",(uint8_t)app.modbusForPrint.frame);
  }
    
    printf("\n\r");
}


void app_task_1ms (void) 
{
  app_handleUartByte();
}
void app_task_2ms (void) {}
void app_task_4ms (void) {}
void app_task_8ms (void) {}
void app_task_16ms (void) {
  app.adch=ADCH;
  ADCSRA |= (1<< ADSC); //Starte ADC
  
}
void app_task_32ms (void) {}
void app_task_64ms (void) {}
void app_task_128ms (void) {}
void app_parseModbusFrame(){
  app.modbusForPrint = app.modbus;
  sys_setEvent(APP_EVENT_MODBUS);
}
void app_handleUartByte (char c)
{
  struct Modbus *p = &app.modbus;
  
  if (p ->frameIndex <0){
    if(c==':'){
      p->frameIndex =0;
      p->frameError =0;
    } else {
      if(p -> invalidByteCount < 0xffff){
        p->invalidByteCount++;
      }
    }
    
  } else {
    if(c==':'){
      p->frameIndex=0;
      p->frameError = 0; 
      if(p->errorcounter < 0xffff)
        p->errorcounter++;
      
    } else {
    
    //in buffer speichern
    if (p->frameIndex < sizeof p->frame)
    p->frame [p->frameIndex++] = c;
    
    else {
      p-> frameError = 1;
    }
    if(c== '\n')
      app_parseModbusFrame();
      p->frameIndex = -1;
      p->frameError = 0; 
    }
  }
}
```
___
[2.2] - **Quellcode app.h**
```c
#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED

// declarations
struct Modbus
{
  char frame[16];
  uint8_t frameIndex;
  uint16_t errorcounter;
  uint8_t frameError;
  uint16_t invalidByteCount;
};
struct App
{
  uint8_t adch;
  struct Modbus modbus;
  struct Modbus modbusForPrint;
};

extern volatile struct App app;


// defines

#define APP_EVENT_MODBUS   0x01
#define APP_EVENT_1   0x02
#define APP_EVENT_2   0x04
#define APP_EVENT_3   0x08
#define APP_EVENT_4   0x10
#define APP_EVENT_5   0x20
#define APP_EVENT_6   0x40
#define APP_EVENT_7   0x80


// functions

void app_init (void);
void app_main (void);

void app_task_1ms   (void);
void app_task_2ms   (void);
void app_task_4ms   (void);
void app_task_8ms   (void);
void app_task_16ms  (void);
void app_task_32ms  (void);
void app_task_64ms  (void);
void app_task_128ms (void);

void app_handleUartByte(char c);

#endif // APP_H_INCLUDED

```
___
[2.3] - **Quellcode sys.c**

```c
int sys_uart_getch (FILE *f)
{
  if (f != stdin)
    return _FDEV_EOF;
  if (sys.uart.wpos_u8 == sys.uart.rpos_u8)
    return _FDEV_EOF;
  uint8_t c = sys.uart.rbuffer_u8[sys.uart.rpos_u8++];
  if (sys.uart.rpos_u8>=GLOBAL_UART_RECBUFSIZE)
    sys.uart.rpos_u8 = 0;
  return (int) c;
}


int sys_uart_putch (char c, FILE *f)
{
  if (f != stdout)
    return _FDEV_EOF;
  while (!SYS_UART_UDR_IS_EMPTY);
  SYS_UDR = c;
  return (int)c;
}

  sys_sei();

Sys_Event sys_clearEvent (Sys_Event event)
{
  uint8_t eventIsPending = 0;
  sys_cli();
  if (sys.eventFlag & event)
    eventIsPending = 1;
  sys.eventFlag &= ~event;
  sys_sei();

  return eventIsPending;
}


Sys_Event sys_isEventPending (Sys_Event event)
{
  return (sys.eventFlag & event) != 0;
}
```
___
[2.4] - **Beschreibung des Programmes**

Der Analog-Digital-Wandler wandelt eine analoge Eingangsspannung durch succsessive Approximation in einen 10-bit Digitalwert. Der kleinste Wert enstpricht GND, der maximale Wert entspricht der ausgewählten Referenzspannung minus ein LSB.

Die Referenzspannung für den Analog-Digital-Wandler kann durch die Bits  **REFS1**  und  **REFS0**  im  **ADMUX**-Register ausgewählt werden, die Referenzspannung liegt dann auch am AVCC Pin an. Möglich sind VCC oder die interne Referenzspannung von  **2,56V**.

Der Analog-Digital-Wandler erzeugt ein 10-bit Ergebnis, das in den ADC Data Registern  **ADCH**  und  **ADCL**  abgelegt wird. Normalerweise wird das Ergebnis rechtsbündig in den beiden Registern abgelegt, optional kann das Ergebnis aber auch linksbündig in  **ADCH**  und  **ADCL**  geschrieben werden. Die Einstellung erfolgt mit dem ADLAR-Bit. 

In der unteren **SVG-Grafik** wird dieses Verhalten veranschaulicht:

![Alt text](https://www.semiversus.com/dic/hardwarenahe_programmierung/avr_adc_admux.svg)

Zuerst wurden diese Register des ADC’s initialisiert und der app.modbus.frameIndex auf -1 gesetzt um in einen Idle Zustand zu gehen.

Im nächsten Schritt wird der linksbündige Wert des ADCH Registers in eine Zwischenvariable gespeichert und der ADC gestartet indem das ADCH Register gesetzt wird.

Am Ende erfolgt noch die Übertragung des Temperaturwertes auf die Konsole des PC’s.
