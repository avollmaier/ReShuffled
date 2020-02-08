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

  //Sensoren{

  DDRC |= (1 << PC1) | (1 << PC2) | (1 << PC3) | (1 << PC4);
  //Schrittmotor

  DDRB |= (1 << PB4) | (1 << PB1) | (1 << PB2) | (1 << PB3) | (1 << PB0);
  DDRA |= (1 << PA0) | (1 << PA1) | (1 << PA2) | (1 << PA3);
  DDRD |= (1 << PD4) | (1 << PD5);
  app.ledState=0;

}


//--------------------------------------------------------


void app_main (void)
{
  
  /*
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
   */


//Enable lifting magnet
/*
  PORTD |= (1 << PD4);
  PORTD |= (1 << PD5);
*/

  //Direction Pin
PORTB |= (1 << PB4);

  //SLP
  PORTB |= (1 << PB2);
  //RST
  PORTB |= (1 << PB1);

  //M2
  PORTB |= (1 << PB0);

  //M1
  PORTA |= (1 << PA0);

  //M0
  PORTA |= (1 << PA1);

  //Enable



}
void initPWM(void){

}
//--------------------------------------------------------


void app_task_1ms (void)
{



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
  if (app.ledState == 0)
  {
    PORTC |= (1 << PC1);
    PORTC |= (1 << PC2);
    PORTC |= (1 << PC3);
    PORTC |= (1 << PC4);
    app.ledState = 1;
  }else
  {
    PORTC &= ~(1 << PC1);
    PORTC &= ~(1 << PC2);
    PORTC &= ~(1 << PC3);
    PORTC &= ~(1 << PC4);
      app.ledState = 0;
  }
}


void app_task_64ms (void)
{

}



void app_task_128ms (void)
{

  
  

}





