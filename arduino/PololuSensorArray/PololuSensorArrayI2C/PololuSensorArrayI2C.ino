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

const int NUM_SENSORS = 1;
const int TIMEOUT = 2500;  // waits for 2500 microseconds for sensor outputs to go low
const int EMITTER_PIN = 2;

// digital pins
QTRSensorsRC qtrrc((unsigned char[]) {
  4
},
NUM_SENSORS, TIMEOUT, EMITTER_PIN);
unsigned int sensorValues[NUM_SENSORS];

void setup() {
  delay(500);
  Serial.begin(9600); // set the data rate in bits per second for serial data transmission
  delay(1000);
  Wire.begin(2);                // join i2c bus with address #2
  Wire.onRequest(requestEvent); // register event
}

void loop() {
  // read raw sensor values
  qtrrc.read(sensorValues);
}


// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
volatile int sensorValue = 0;

void requestEvent() {

  for (int sensorNumber = 0; sensorNumber < NUM_SENSORS; ++sensorNumber) {
    Serial.println(sensorValues[sensorNumber]);
    ATOMIC_BLOCK(ATOMIC_RESTORESTATE)
    {
      sensorValue = sensorValues[sensorNumber];
    }
    for (int i = 3; i >= 0; --i) {
      byte b = sensorValue >> (i * 8) & 0xFF;
      Wire.write(b);
    }
  }
}
