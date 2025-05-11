package com.food.ordering.system.kafka.config.data;

import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaConfigData {
    private String bootstrapServers;
    private String schemaRegistryUrlKey;
    private String schemaRegistryUrl;
    private Integer numOfPartitions;
    private Short replicationFactor;
}
