package com.example.springbootcasandra.controller;

import com.example.springbootcasandra.entity.Device;
import com.example.springbootcasandra.service.DeviceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc
public class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    DeviceController deviceController;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Device> devicesList = new ArrayList<>(3);

    @Before
    public void setUp() {
        devicesList.add(new Device("device 1", ImmutableMap.of("sensror 1", "data 123")));
        devicesList.add(new Device("device 2", ImmutableMap.of("sensror 2", "data 456")));
        devicesList.add(new Device("device 3", ImmutableMap.of("sensror 3", "data 556")));

        Mockito.when(deviceService.findAll()).thenReturn(devicesList);
        Mockito.when(deviceService.findById(anyString())).thenReturn(Optional.of(devicesList.get(0)));
    }

    @Test
    public void whenDeviceControllerInjected_thenNotNull() throws Exception {
        assertThat(deviceController).isNotNull();
    }

    @Test
    public void whenPostCreateDevice_thenMustBeAvailableOnGetByUUID() throws Exception {
        Device device = new Device("device 1", ImmutableMap.of("sensror 1", "data 123"));

        String json = objectMapper.writeValueAsString(device);

        mvc.perform(
                MockMvcRequestBuilders.post("/device")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void whenAddDevices_thenMustBeAvailableOnGet() throws Exception {

        String json = objectMapper.writeValueAsString(devicesList);

        mvc.perform(
                MockMvcRequestBuilders.post("/device/collection")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/device").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        String response = resultActions.andReturn().getResponse().getContentAsString();
        List<Device> devices = objectMapper.readValue(
                response,
                new TypeReference<List<Device>>() {}
                );

        assertTrue(devices.containsAll(devicesList));
    }

}