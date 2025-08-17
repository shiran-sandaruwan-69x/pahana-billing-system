package lk.pahana_billing_system.dto;

public class OrderDetailDTO {
    private String itemCode;
    private int qty;
    private double unitPrice;

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
