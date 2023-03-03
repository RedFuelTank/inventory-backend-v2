module com.netgroup.usecase {
    requires lombok;
    requires com.netgroup.entity;
    exports com.netgroup.usecase.client.api;
    exports com.netgroup.usecase.inventory_system.api;
}