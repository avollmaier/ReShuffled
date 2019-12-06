#include "global.h"

#include <stdio.h>
#include <string.h>

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "avr/eeprom.h"
#include "app.h"
#include "sys.h"


volatile struct App app;


// functions


void app_init (void)
{
  memset((void *) &app, 0, sizeof (app));
}


//--------------------------------------------------------


unsigned int crc32b (unsigned char *message)
{
  int i, j;
  unsigned int byte, crc, mask;

  i = 0;
  crc = 0xFFFFFFFF;
  while (message[i] != 0)
  {
    byte = message[i]; // Get next byte.
    crc = crc ^ byte;
    for (j = 7; j >= 0; j--)
    { // Do eight times.
      mask = -(crc & 1);
      crc = (crc >> 1) ^ (0xEDB88320 & mask);
    }
    i = i + 1;
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
  printf("Received a request: %s", app.modbusBuffer);
  char *b = app.modbusBuffer;
  char *c = app.modbusBuffer;
  uint8_t size = app.bufferIndex;

  if (size != 13)
  {
    return 1;
  }
  if (b[0] != ':')
  {
    return 2;
  }
  if (b[size - 1] != '\n')
  {
    return 3;
  }

  for (uint8_t i = 1; i < (size - 1); i++)
  {
    char c = b[i];
    if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c = '#'))) return 4;
  }

  crc32b(strcat(b[1], b[2]));

 

  printf("request check ok\n");


  return 0;
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
}