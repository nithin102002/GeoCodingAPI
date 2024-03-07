//package com.example.FindingNearestStore.Config;
//
//import com.example.FindingNearestStore.DataSource.MultitenantDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//
//@Slf4j
//@Configuration
//public class MultitenantConfiguration {
//
//    @Value("${defaultTenant}")
//    private String defaultTenant;
//
//    @Bean
//    @ConfigurationProperties(prefix = "tenants")
//
//    public DataSource dataSource(){
//        File[] files= Paths.get("allTenants").toFile().listFiles();
//        Map<Object,Object> resolvedDataSource= new HashMap<>();
//
//        for(File propertiesFile: files){
//            Properties properties= new Properties();
//            DataSourceBuilder dataSourceBuilder= DataSourceBuilder.create();
//            log.info("HI");
//            try{
//
//                properties.load(new FileInputStream(propertiesFile));
//                String tenantId= properties.getProperty("name");
//                dataSourceBuilder.driverClassName(properties.getProperty("datasource.driver-class-name"));
//                dataSourceBuilder.username(properties.getProperty("username"));
//                dataSourceBuilder.password(properties.getProperty("password"));
//                dataSourceBuilder.url(properties.getProperty("datasource.url"));
//                dataSourceBuilder.build();
//                resolvedDataSource.put(tenantId,dataSourceBuilder);
//
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//        AbstractRoutingDataSource dataSource= new MultitenantDataSource();
//        dataSource.setDefaultTargetDataSource(resolvedDataSource.get(defaultTenant));
//        dataSource.setTargetDataSources(resolvedDataSource);
//        dataSource.afterPropertiesSet();
//        return dataSource;
//    }
//
//}



package com.example.FindingNearestStore.Config;

import com.example.FindingNearestStore.DataSource.MultitenantDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class MultitenantConfiguration {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        File[] files = Paths.get("allTenants").toFile().listFiles();
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (File propertiesFile : files) {
            Properties tenantProperties = new Properties();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

            try {
                tenantProperties.load(new FileInputStream(propertiesFile));
                String tenantId = tenantProperties.getProperty("name");

                // Correctly build and store DataSource objects:
                DataSource dataSource = dataSourceBuilder
                        .driverClassName(tenantProperties.getProperty("datasource.driverClassName"))
                        .username(tenantProperties.getProperty("datasource.username"))
                        .password(tenantProperties.getProperty("datasource.password"))
                        .url(tenantProperties.getProperty("datasource.url"))
                        .build(); // Call build() to create the actual DataSource

                resolvedDataSources.put(tenantId, dataSource);

            } catch (FileNotFoundException e) {
                log.error("Tenant file not found: {}", propertiesFile.getName(), e);
                throw new RuntimeException("Tenant file not found: " + propertiesFile.getName(), e);
            } catch (IOException e) {
                log.error("Error reading tenant file: {}", propertiesFile.getName(), e);
                throw new RuntimeException("Error reading tenant file: " + propertiesFile.getName(), e);
            }
        }

        AbstractRoutingDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    }
}
