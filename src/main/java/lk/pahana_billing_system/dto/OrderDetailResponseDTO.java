package lk.pahana_billing_system.dto;

public class OrderDetailResponseDTO {
    private String itemNo;
    private double itemPrice;
    private int qty;
    private double price;

    public String getItemNo() { return itemNo; }
    public void setItemNo(String itemNo) { this.itemNo = itemNo; }
    public double getItemPrice() { return itemPrice; }
    public void setItemPrice(double itemPrice) { this.itemPrice = itemPrice; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
