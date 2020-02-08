#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED

// declarations
struct PWMGen {
  float f; // frequenz
  float amp; // Amplitude
  uint8_t running; // 1 = run 2 = stop

  uint32_t t; //in ms
};

enum App_state {
  IDLE, INIT, PARSE
};

struct App {
  int ledState; //0 = low //1=high
  uint8_t flags_u8;
  char modbusBuffer[32];
  char toSend[64];
  uint16_t bufferIndex;
  uint16_t errorCount;
  int16_t mbInputRegister1;
  
  uint16_t ms;
  enum App_state state;
};

extern volatile struct App app;


// defines

#define APP_EVENT_IDLE   0x01
#define APP_EVENT_INIT   0x02
#define APP_EVENT_PARSE   0x04
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

#endif // APP_H_INCLUDED
