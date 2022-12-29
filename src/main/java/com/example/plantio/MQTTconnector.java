package com.example.plantio;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import android.os.Build;

public class MQTTconnector {

    final String host = "039647b9e22e4235953dd482038220c7.s2.eu.hivemq.cloud";
    final String username = "skrzynia";
    final String password = "9RB#kx!Hgc33Hv#";

    MQTTconnector() {}

     {
         final Mqtt5BlockingClient client = MqttClient.builder()
                 .useMqttVersion5()
                 .serverHost(host)
                 .serverPort(8883)
                 .sslWithDefaultConfig()
                 .buildBlocking();

         // connect to HiveMQ Cloud with TLS and username/pw
         client.connectWith()
                 .simpleAuth()
                 .username(username)
                 .password(UTF_8.encode(password))
                 .applySimpleAuth()
                 .send();

         System.out.println("Connected successfully");

         // subscribe to the topic "my/test/topic"
         client.subscribeWith()
                 .topicFilter("weather")
                 .send();

         // set a callback that is called when a message is received (using the async API style)
         client.toAsync().publishes(ALL, publish -> {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 System.out.println("Received message: " +
                         publish.getTopic() + " -> " +
                         UTF_8.decode(publish.getPayload().get()));
             }

             // disconnect the client after a message was received
             client.disconnect();
         });

         // publish a message to the topic "my/test/topic"
         client.publishWith()
                 .topic("my/test/topic")
                 .payload(UTF_8.encode("Hello"))
                 .send();

    }

}
