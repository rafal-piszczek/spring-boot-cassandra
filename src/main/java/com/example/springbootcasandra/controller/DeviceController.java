package com.example.springbootcasandra.controller;

import com.example.springbootcasandra.entity.Device;
import com.example.springbootcasandra.exception.DeviceNotFound;
import com.example.springbootcasandra.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Device device) {
        deviceService.save(device);
    }

    @PostMapping("/collection")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody List<Device> devices) {
        deviceService.saveAll(devices);
    }

    @GetMapping
    public List<Device> findAll() {
        return deviceService.findAll();
    }

    @GetMapping("/{uuid}")
    public Device findByUuid(@PathVariable String uuid) throws DeviceNotFound {
        return deviceService.findById(uuid).orElseThrow(DeviceNotFound::new);
    }
}
