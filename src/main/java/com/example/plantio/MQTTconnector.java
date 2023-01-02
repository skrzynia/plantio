package com.example.plantio;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import android.os.Build;

public class MQTTconnector {

    private final String host = "039647b9e22e4235953dd482038220c7.s2.eu.hivemq.cloud";
    private final String username = "skrzynia";
    private final String password = "9RB#kx!Hgc33Hv#";
    private static StringBuilder temp = new StringBuilder("0");
    private static StringBuilder humidity = new StringBuilder("0");
    private static StringBuilder pressure = new StringBuilder("0");
    MQTTconnector() {

    }

    public StringBuilder getTemp() {
        return temp;
    }

    public void setTemp(StringBuilder temp) {
        this.temp = temp;
    }

    public StringBuilder getHumidity() {
        return humidity;
    }

    public void setHumidity(StringBuilder humidity) {
        this.humidity = humidity;
    }

    public StringBuilder getPressure() {
        return pressure;
    }

    public void setPressure(StringBuilder pressure) {
        this.pressure = pressure;
    }

    private String[] getTokens(String text)
    {
        return text.split("\\R");
    }

    {

        final Mqtt5BlockingClient  client = MqttClient.builder()
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

        client.toAsync().publishes(ALL, publish -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                 System.out.println("Received message: " +
//                         publish.getTopic() + " -> " +
//                         UTF_8.decode(publish.getPayload().get()));
                String tokens[] = getTokens(String.valueOf(UTF_8.decode(publish.getPayload().get())));

                setTemp(new StringBuilder(tokens[0]));
                setHumidity(new StringBuilder(tokens[1]));
                setPressure(new StringBuilder(tokens[2]));
            }

            // disconnect the client after a message was received
            client.disconnect();
        });


        client.subscribeWith()
                .topicFilter("weather")
                .send();

        client.publishWith()
                .topic("my/test/topic")
                .payload(UTF_8.encode("Hello"))
                .send();

    }

}
