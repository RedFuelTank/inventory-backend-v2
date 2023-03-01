module com.netgroup.infrastructure {
    requires lombok;
    requires com.netgroup.usecase;
    requires jakarta.persistence;
    requires com.netgroup.entity;
    requires spring.context;
    requires spring.tx;
    requires spring.security.crypto;

}