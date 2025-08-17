package lk.pahana_billing_system.model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
    private String orderId;
    private Date date;
    private String customerId;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
}
