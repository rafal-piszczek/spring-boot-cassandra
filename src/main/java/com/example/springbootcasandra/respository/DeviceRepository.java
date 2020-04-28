package com.example.springbootcasandra.respository;

import com.example.springbootcasandra.entity.Device;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends CassandraRepository<Device, UUID> {
    Optional<Device> findById(UUID id);
}
