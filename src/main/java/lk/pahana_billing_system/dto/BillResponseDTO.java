package lk.pahana_billing_system.dto;

import java.util.List;

public class BillResponseDTO {
    private String customerName;
    private String orderNo;
    private List<OrderDetailResponseDTO> orderDetails;
    private double fullTotal;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public List<OrderDetailResponseDTO> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetailResponseDTO> orderDetails) { this.orderDetails = orderDetails; }
    public double getFullTotal() { return fullTotal; }
    public void setFullTotal(double fullTotal) { this.fullTotal = fullTotal; }
}
