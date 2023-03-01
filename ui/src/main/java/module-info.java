module com.netgroup.ui {
    requires spring.web;
    requires spring.security.core;
    requires com.netgroup.usecase;
    requires lombok;
    exports com.netgroup.web;
}