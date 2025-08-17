package lk.pahana_billing_system.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int qtyOnHand;

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public int getQtyOnHand() { return qtyOnHand; }
    public void setQtyOnHand(int qtyOnHand) { this.qtyOnHand = qtyOnHand; }
}
