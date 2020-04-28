package com.example.springbootcasandra.service;

import com.datastax.driver.core.utils.UUIDs;
import com.example.springbootcasandra.entity.Device;
import com.example.springbootcasandra.respository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void saveAll(List<Device> deviceList) {

        deviceRepository.saveAll(
                deviceList
                        .stream()
                        .peek(
                                device -> device.setId(UUIDs.timeBased())
                        ).collect(Collectors.toList()
                )
        );
    }

    public void save(Device device) {
        device.setId(UUIDs.timeBased());

        deviceRepository.save(device);
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Optional<Device> findById(String uuid) {
        return deviceRepository.findById(UUID.fromString(uuid));
    }

    public Optional<Device> findById(UUID uuid) {
        return deviceRepository.findById(uuid);
    }
}
