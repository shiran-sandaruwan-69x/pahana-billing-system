package lk.pahana_billing_system.dto;

import java.util.List;

public class OrderPayloadDTO {
    private String customerId;
    private List<OrderDetailDTO> orderDetails;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderDetailDTO> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetailDTO> orderDetails) { this.orderDetails = orderDetails; }
}
