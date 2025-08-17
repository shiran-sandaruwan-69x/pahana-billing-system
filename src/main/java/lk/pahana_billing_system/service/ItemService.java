package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.Item;
import java.util.List;
import java.sql.SQLException;

public interface ItemService {
    boolean addItem(Item item) throws SQLException;
    List<Item> getAllItems() throws SQLException;
    Item getItemByCode(String itemCode) throws SQLException;
    boolean updateItem(Item item) throws SQLException;
    boolean deleteItem(String itemCode) throws SQLException;
}
