#include "global.h"

#include <stdio.h>
#include <string.h>

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "avr/eeprom.h"
#include "app.h"
#include "sys.h"
#include <inttypes.h>

volatile struct App app;


// functions


void app_init (void)
{
  memset((void *) &app, 0, sizeof (app));

}


//--------------------------------------------------------


uint32_t
rc_crc32 (uint32_t crc, const char *buf, size_t len)
{
  static uint32_t table[256];
  static int have_table = 0;
  uint32_t rem;
  uint8_t octet;
  int i, j;
  const char *p, *q;

  /* This check is not thread safe; there is no mutex. */
  if (have_table == 0)
  {
    /* Calculate CRC table. */
    for (i = 0; i < 256; i++)
    {
      rem = i; /* remainder from polynomial division */
      for (j = 0; j < 8; j++)
      {
        if (rem & 1)
        {
          rem >>= 1;
          rem ^= 0xedb88320;
        }
        else
          rem >>= 1;
      }
      table[i] = rem;
    }
    have_table = 1;
  }

  crc = ~crc;
  q = buf + len;
  for (p = buf; p < q; p++)
  {
    octet = *p; /* Cast to unsigned octet. */
    crc = (crc >> 8) ^ table[(crc & 0xff) ^ octet];
  }
  return ~crc;
}


uint16_t app_inc16BitCount (uint16_t cnt)
{
  return cnt == 0xffff ? cnt : cnt + 1;
}


uint8_t hex2int (char h)
{
  if (h >= '0' && h <= '9')
  {
    return h - '0';
  }

  if (h >= 'A' && h <= 'F')
  {
    return h - 'A' + 10;
  }

  return 0xff;
}


uint8_t app_handleModbusRequest ()
{
  //printf("Received a request: %s", app.modbusBuffer);
  char *buffer = app.modbusBuffer;
  uint8_t size = app.bufferIndex;

  if (size != 13)
  {
    return 1;
  }
  if (buffer[0] != ':')
  {
    return 2;
  }
  if (buffer[size - 1] != '\n')
  {
    return 3;
  }

  for (uint8_t i = 0; i < (size - 1); i++)
  {
    char c = buffer[i];
    if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c = '#'))) return 4;
  }

  //CHECK CRC
  //char delimiter[] = ":#\n";
  //char *ptr;

  // initialisieren und ersten Abschnitt erstellen
  //ptr = strtok(buffer, delimiter);

  //printf("Abschnitt gefunden: %s\n", ptr);
  // naechsten Abschnitt erstellen

  app_sendUartResponse("OK");

  return 0;
}


void app_sendUartResponse ()
{
  char c[2];
  strncpy(c, "OK",2);
  printf(":%s#%" PRIX32 "\n", c, rc_crc32(0, c, strlen(c)));
}


void app_handleUartByte (char c)
{
  if (c == ':')
  {
    if (app.bufferIndex > 0)
    {
      app.errorCount = app_inc16BitCount(app.errorCount);
    }
    app.modbusBuffer[0] = c;
    app.bufferIndex = 1;
  }
  else if (app.bufferIndex == 0)
  {
    app.errorCount = app_inc16BitCount(app.errorCount);

  }
  else if (app.bufferIndex >= sizeof (app.modbusBuffer))
  {
    app.errorCount = app_inc16BitCount(app.errorCount);
  }
  else
  {
    app.modbusBuffer[app.bufferIndex++] = c;
    if (c == '\n')
    {
      uint8_t errCode = app_handleModbusRequest();
      if (errCode != 0)
      {
        printf("Fehler: %u\r\n", errCode);
        app.errorCount = app_inc16BitCount(app.errorCount);
      }
      app.bufferIndex = 0;
    }
  }
}


void app_main (void)
{

  int c = fgetc(stdin);
  if (c != EOF)
  {
    app_handleUartByte((char) c);
  }



}

//--------------------------------------------------------


void app_task_1ms (void)
{
  static uint16_t oldErrCnt = 0;
  static uint16_t timer = 0;
  if (app.errorCount != oldErrCnt)
  {
    oldErrCnt = app.errorCount;
    timer = 1000;
    sys_setLed(1);
    printf("Error");
  }
  if (timer > 0)
  {
    timer--;
    if (timer <= 0)
    {
      sys_setLed(0);
    }
  }
}


void app_task_2ms (void)
{
  
  

  
}


void app_task_4ms (void)
{
}


void app_task_8ms (void)
{
}


void app_task_16ms (void)
{
}


void app_task_32ms (void)
{
}


void app_task_64ms (void)
{
}


void app_task_128ms (void)
{
 PORTC ^= ( 1 << PC1); 

 
}