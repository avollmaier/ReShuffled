#include "global.h"

#include <stdio.h>
#include <string.h>

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#include "app.h"
#include "sys.h"

// defines
// ...


// declarations and definations

volatile struct App app;


// functions


void app_init (void)
{
  memset((void *) &app, 0, sizeof (app));
  //Enable LED Output
  DDRA &= ~(1 << PA5);
  DDRA &= ~(1 << PA6);
  DDRA &= ~(1 << PA7);
  
  
  DDRC |= (1 << PC1) | (1 << PC2) | (1 << PC3) | (1 << PC4);
}


//--------------------------------------------------------


void app_main (void)
{
  PORTA &= ~(1 << PA5);
  PORTA &= ~(1 << PA6);
  PORTA &= ~(1 << PA7);
  
   if(PINA & (1 << PA5)) {
        PORTC |= (1 << PC1);
    }
  
  if(PINA & (1 << PA6)) {
        PORTC |= (1 << PC2);
    }
  
  if(!(PINA & (1 << PA7))) {
        PORTC |= (1 << PC3);
    }


}

//--------------------------------------------------------


void app_task_1ms (void)
{
  
  enum App_state nextState = app.state;
  
  switch (app.state)
  {
  case IDLE:
    sys_setEvent(APP_EVENT_IDLE);
    PORTC |= (1 << PC1);
    break;

  case PARSE:
    sys_setEvent(APP_EVENT_PARSE);
    PORTC |= (1 << PC1);
    break;

  case INIT:
    sys_setEvent(APP_EVENT_INIT);
    PORTC |= (1 << PC1);
    break;
    
    
  }
  
  app.state = nextState;


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



