#!/usr/bin/python3
import paho.mqtt.client as mqtt
from urllib.parse import urlparse
import sys
import time
from sense_hat import SenseHat
from dotenv import dotenv_values
import logging
import os
from gpiozero import CPUTemperature
import storeFileFB
from client_sub import ClientSub


class ThingPub:

    sense = SenseHat()
    sense.clear()
    config = dotenv_values("week10/.env")
    cpu = CPUTemperature().temperature

    logging.basicConfig(level=logging.INFO)

    def __init__(self) :
        pass
        
        

    def on_connect(client, userdata, flags, rc,what):
        logging.info("Connection Result: " + str(rc))

    def on_publish(client, obj, mid,what):
        logging.info("Message Sent ID: " + str(mid))


    def run(self):

        mqttc = mqtt.Client(client_id=self.config["clientId"])

        
        mqttc.on_connect = self.on_connect
        mqttc.on_publish = self.on_publish

        
        url = urlparse(self.config["url"])
        base_topic = url.path[1:]

        
        mqttc.username_pw_set(self.config["username"], self.config["password"])

        
        mqttc.connect(url.hostname, url.port)
        mqttc.loop_start()

        
        topic = "channels/"+self.config["channelId"]+"/publish"

            
        while True:
            try:
                temp=round(self.sense.get_temperature_from_humidity() - ((self.cpu - self.sense.get_temperature_from_humidity())/1.5),2)
                pressure=round(self.sense.pressure,2)
                humidity=round(self.sense.humidity,2)
                payload=f"field1={temp}&field2={humidity}&field3={pressure}"
                mqttc.publish(topic, payload)
                client_sub = ClientSub(temperature=temp, humidity=humidity, pressure=pressure)
                print(topic)
                print(payload)
                time.sleep(15)
            except:
                logging.info('Interrupted')