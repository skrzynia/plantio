#!/usr/bin/python3

import time
import paho.mqtt.client as paho
from paho import mqtt
import sys



class ClientSub:
# Define event callbacks
    def __init__(self, temperature, humidity, pressure):

        self.temperature = temperature
        self.humidity = humidity
        self.pressure = pressure

    # setting callbacks for different events to see if it works, print the message etc.
        def on_connect(client, userdata, flags, rc, properties=None):
            print("CONNACK received with code %s." % rc)

    # with this callback you can see if your publish was successful
        def on_publish(client, userdata, mid, properties=None):
            print("mid: " + str(mid))

    # print which topic was subscribed to
        def on_subscribe(client, userdata, mid, granted_qos, properties=None):
            print("Subscribed: " + str(mid) + " " + str(granted_qos))

    # print message, useful for checking if it was successful
        def on_message(client, userdata, msg):
            print(msg.topic + " " + str(msg.qos) + " " + str(msg.payload))

        client = paho.Client(client_id="", userdata=None, protocol=paho.MQTTv5)
        client.on_connect = on_connect

        client.tls_set(tls_version=mqtt.client.ssl.PROTOCOL_TLS)
        client.username_pw_set("skrzynia", "9RB#kx!Hgc33Hv#")
        client.connect("039647b9e22e4235953dd482038220c7.s2.eu.hivemq.cloud", 8883)

        client.on_subscribe = on_subscribe
        client.on_message = on_message
        client.on_publish = on_publish

        # client.loop_start()

        # while True:

        client.subscribe("weather", qos=1)

        client.publish("weather", payload=f"temperature={temperature}\nhumidity={humidity}\npressure={pressure}", qos=1)

        time.sleep(15)


if __name__ == "__main__":
   cs = ClientSub()