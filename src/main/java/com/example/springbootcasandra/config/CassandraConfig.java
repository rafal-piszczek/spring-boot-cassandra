package com.example.springbootcasandra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Collections;
import java.util.Objects;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = "com.example.springbootcasandra")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public CassandraClusterFactoryBean clusterFactoryBean() {
        CassandraClusterFactoryBean cluster = super.cluster();

        cluster.setContactPoints(
                Objects.requireNonNull(environment.getProperty("cassandra.database.contact_point"))
        );

        cluster.setPort(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("cassandra.database.port")))
        );
        cluster.setJmxReportingEnabled(false);
        cluster.setKeyspaceCreations(
                Collections.singletonList(
                        CreateKeyspaceSpecification
                                .createKeyspace(
                                        Objects.requireNonNull(environment.getProperty("cassandra.database.keyspace"))
                                )
                                .ifNotExists()
                )
        );

        return cluster;
    }

    @Override
    protected String getKeyspaceName() {
        return Objects.requireNonNull(environment.getProperty("cassandra.database.keyspace"));
    }

    @Override
    public SchemaAction getSchemaAction() { return SchemaAction.RECREATE_DROP_UNUSED; }

    @Override
    protected boolean getMetricsEnabled() { return false; }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"com.example.springbootcasandra.entity"};
    }
}
