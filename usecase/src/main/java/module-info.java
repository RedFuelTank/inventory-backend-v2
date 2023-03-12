module com.netgroup.usecase {
    requires lombok;
    requires com.netgroup.entity;
    requires spring.data.commons;
    exports com.netgroup.usecase.client.api;
    exports com.netgroup.usecase.inventory_system.api;
    exports com.netgroup.usecase.payment.api;
    exports com.netgroup.usecase.statistics.api;
    exports com.netgroup.usecase.image.api;

}