import com.netgroup.domain.client.api.ClientService;

module com.netgroup.ui {
    requires spring.web;
    requires com.netgroup.domain;
    requires lombok;
    uses ClientService;
    exports com.netgroup.web;
}