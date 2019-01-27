// Wire Slave Sender
// by Nicholas Zambetti <http://www.zambetti.com>

// Demonstrates use of the Wire library
// Sends data as an I2C/TWI slave device
// Refer to the "Wire Master Reader" example for use with this

// Created 29 March 2006

// This example code is in the public domain.


#include <util/atomic.h>
#include <Wire.h>
#include <QTRSensors.h>

const int NUM_SENSORS = 8;
const int TIMEOUT = 2000;  // waits for 2500 microseconds for sensor outputs to go low
const int EMITTER_PIN = 2;
bool requested = false;

// digital pins
QTRSensorsRC qtrrc((unsigned char[]) {
  3, 4, 5, 6, 7, 8, 9, 10
},
NUM_SENSORS, TIMEOUT, EMITTER_PIN);
unsigned int sensorValues[NUM_SENSORS];

void setup() {
  for (int sensorNumber = 0; sensorNumber < NUM_SENSORS; ++sensorNumber) {
    sensorValues[sensorNumber] = 0;
  }
  delay(500);
  Serial.begin(9600); // set the data rate in bits per second for serial data transmission
  delay(1000);
  Wire.begin(2);                // join i2c bus with address #2
  Wire.onRequest(requestEvent); // register event
}

void loop() {
  if(requested){
      qtrrc.read(sensorValues);
      requested = false;
  }
}

// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
int sensorValue = 0;

void requestEvent() {
  for (int sensorNumber = 0; sensorNumber < NUM_SENSORS; ++sensorNumber) {
    sensorValue = sensorValues[sensorNumber];
    Serial.print(sensorValues[sensorNumber]);
    Serial.print('\t');
    for (int i = 1; i >= 0; --i) {
      byte b = sensorValue >> (i * 8) & 0xFF;
      Wire.write(b);
    }
  }
  requested = true;
  Serial.println();
}
