 // C++ code
//
void setup()
{
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(11, OUTPUT);
  
  pinMode(4, INPUT);
  pinMode(3, INPUT);
  pinMode(2, INPUT);
  
}//setup

const int LOCKED = 1;
const int WAITING = 2;
const int UNLOCKED = 3;

int state = LOCKED;

int btnPresses = 0;
int btn1Pressed = 0;

void loop()
{
  
  int btn1 = digitalRead(4);
  int btn2 = digitalRead(3);
  
  
  if (btn1 == HIGH || btn2 == HIGH){
    digitalWrite(12,LOW);
    delay(500);
  }
  
  switch(state) {
    
    
  case LOCKED:
    {
      digitalWrite(13, HIGH);
      int val = digitalRead(2);   // read sensor value
      if (val == HIGH) {           // check if the sensor is HIGH
      state = WAITING;  
      }//if
    }//case
    break;
  case WAITING:
    {
      digitalWrite(12, HIGH);   // turn LED ON
      if(btn1 == HIGH){
        btn1Pressed++;
        btnPresses++;
      }//if
      
      if (btn2 == HIGH) {
        btnPresses++;
      }
      
      if (btn2 == HIGH && btn1Pressed == 1) {
            state = UNLOCKED;
            btnPresses++;
      }
      
      if((btn1 == HIGH || btn2 == HIGH) && btnPresses >= 2 && state != UNLOCKED){
      	digitalWrite(13, LOW);
        digitalWrite(12, LOW);
        delay(500);
        btn1Pressed = 0;
        btnPresses = 0;
        state = LOCKED;
      }
    }//case
    break;
    
  case UNLOCKED:
    {
      digitalWrite(11, HIGH);   // turn LED ON
      delay(10000);
      digitalWrite(12, LOW);
      digitalWrite(11, LOW);
      btnPresses = 0;
      state = LOCKED;
    }//case
    break;

}//switch
}//loop 