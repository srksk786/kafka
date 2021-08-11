package com.github.shahrukh.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoWithCallbacks {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallbacks.class);
        String bootstrapServers = "127.0.0.1:9092";
        //Steps
        //1. Create producer properties
        //2. Create the producer
        //3. send data

        //1. Create producer properties
        Properties properties = new Properties();

        //New ways for clear code
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //2. Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 10; i++) {
            // create a producer record

            String topic = "first_topic";
            String value = "hello world printing from intellij " + Integer.toString(i);
            String key = "id_" + Integer.toString(i);

            ProducerRecord<String, String> record =
                    new ProducerRecord<String, String>(topic, key, value);

            logger.info("Key: " + key); //log the key

            //3. send data -asynchronous
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    //executes everytime  a record is sent successfully or an error is caused
                    if (e == null) {
                        //the record was successfully sent
                        logger.info("Received new metadata. \n" +
                                "Topic : " + recordMetadata.topic() + "\n" +
                                "Partition : " + recordMetadata.partition() + "\n" +
                                "Offset : " + recordMetadata.offset() + "\n" +
                                "Timestamp : " + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            }).get(); //block send, to make it synchronous. It will not be fetch by consumer if u use .get and add exception by alt+enter
        }
            //flush producer
            producer.flush();
            //flush and close producer
            producer.close();
        }
}
