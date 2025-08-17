package lk.pahana_billing_system.dao;

import lk.pahana_billing_system.model.Item;
import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    boolean addItem(Item item) throws SQLException;
    List<Item> getAllItems() throws SQLException;
    Item getItemByCode(String itemCode) throws SQLException;
    boolean updateItem(Item item) throws SQLException;
    boolean deleteItem(String itemCode) throws SQLException;
}
