package lk.pahana_billing_system.model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
    private String orderId;
    private Date date;
    private String customerId;
    private String customerName;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
