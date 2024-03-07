package com.example.FindingNearestStore.DataSource;

public class TenantContext {

    private static  final ThreadLocal<String> Current_Tenant= new ThreadLocal<>();

    public static  String getCurrentTenant(){
        return Current_Tenant.get();
    }

    public static void setCurrentTenant(String tenant){
        Current_Tenant.set(tenant);
    }
}
