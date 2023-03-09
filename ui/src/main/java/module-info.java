module com.netgroup.ui {
    requires spring.web;
    requires spring.security.core;
    requires com.netgroup.usecase;
    requires lombok;
    requires spring.context;
    requires spring.data.commons;
    exports com.netgroup.web.client;
}