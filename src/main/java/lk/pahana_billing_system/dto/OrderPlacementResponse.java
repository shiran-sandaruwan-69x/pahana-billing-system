package lk.pahana_billing_system.dto;

public class OrderPlacementResponse {
    private String orderId;

    public OrderPlacementResponse(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}
