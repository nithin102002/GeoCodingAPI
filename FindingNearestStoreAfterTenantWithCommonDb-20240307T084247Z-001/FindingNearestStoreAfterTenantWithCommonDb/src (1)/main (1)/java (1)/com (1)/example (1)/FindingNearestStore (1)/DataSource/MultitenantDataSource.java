package com.example.FindingNearestStore.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {
    @Override
    public String determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }
}
