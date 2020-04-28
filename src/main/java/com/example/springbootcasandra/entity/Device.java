package com.example.springbootcasandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @PrimaryKey
    private UUID id;
    @Column
    private String name;
    @Column
    private Map<String, String> sensorsStats = new HashMap<>();

    public Device(String name, Map<String, String> sensorsStats) {
        this.name = name;
        this.sensorsStats = sensorsStats;
    }
}
