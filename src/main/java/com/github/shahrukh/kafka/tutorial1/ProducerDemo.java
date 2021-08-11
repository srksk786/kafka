package com.github.shahrukh.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) {
        String bootstrapServers = "127.0.0.1:9092";
        //Steps
        //1. Create producer properties
        //2. Create the producer
        //3. send data

        //1. Create producer properties
        Properties properties = new Properties();
//        Traditional ways down
//        properties.setProperty("bootstrap.servers", bootstrapServers);
//        properties.setProperty("key.serializer", StringSerializer.class.getName());
//        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //New ways for clear code
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //2. Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // create a producer record
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>("first_topic", "hello world printing from intellij");

        //3. send data -asynchronous
        producer.send(record);
        //flush producer
        producer.flush();
        //flush and close producer
        producer.close();
    }
}
