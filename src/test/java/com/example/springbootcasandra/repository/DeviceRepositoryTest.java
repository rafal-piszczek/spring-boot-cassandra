package com.example.springbootcasandra.repository;

import com.datastax.driver.core.utils.UUIDs;
import com.example.springbootcasandra.config.CassandraConfig;
import com.example.springbootcasandra.entity.Device;
import com.example.springbootcasandra.respository.DeviceRepository;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class DeviceRepositoryTest {

    private static final Log LOGGER = LogFactory.getLog(DeviceRepositoryTest.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void whenSavingDevice_thenMustBeAvailableOnRetrieval() {

        UUID uuid = UUIDs.timeBased();
        final Device device = new Device(uuid, "device 1", ImmutableMap.of("sensor_1", "123"));

        deviceRepository.save(device);
        Optional<Device> deviceFromDb = deviceRepository.findById(uuid);

        assertTrue(deviceFromDb.isPresent());
        assertEquals(device.getId(), deviceFromDb.get().getId());
    }

    @Test
    public void whenSavingDevice_thenMustBePossibleToSearchAndDelete() {

        UUID uuid = UUIDs.timeBased();
        final Device device = new Device(uuid, "device 1", ImmutableMap.of("sensor_2", "123"));

        deviceRepository.save(device);
        Optional<Device> deviceFromDb = deviceRepository.findById(uuid);

        assertTrue(deviceFromDb.isPresent());
        assertEquals(device.getId(), deviceFromDb.get().getId());

        deviceRepository.delete(deviceFromDb.get());

        deviceFromDb = deviceRepository.findById(uuid);

        assertFalse(deviceFromDb.isPresent());
    }

    @Test
    public void whenSavingMultipleDevices_thenMustBeAvailableOnFindAll() {
        UUID uuid_1 = UUIDs.timeBased();
        UUID uuid_2 = UUIDs.timeBased();
        UUID uuid_3 = UUIDs.timeBased();
        List<Device> deviceList = new ArrayList<>(3);

        deviceList.add(new Device(uuid_1, "device 1", ImmutableMap.of("sensor_1", "123")));
        deviceList.add(new Device(uuid_2, "device 2", ImmutableMap.of("sensor_2", "123")));
        deviceList.add(new Device(uuid_3, "device 3", ImmutableMap.of("sensor_3", "123")));

        deviceRepository.saveAll(deviceList);

        List<Device> devices = deviceRepository.findAll();

        assertTrue(devices.containsAll(deviceList));
    }
}
