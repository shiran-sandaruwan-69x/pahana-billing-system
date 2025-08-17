package lk.pahana_billing_system.service.impl;

import lk.pahana_billing_system.dao.ItemDAO;
import lk.pahana_billing_system.dao.impl.ItemDAOImpl;
import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.service.ItemService;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    private ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean addItem(Item item) throws SQLException {
        return itemDAO.addItem(item);
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        return itemDAO.getAllItems();
    }

    @Override
    public Item getItemByCode(String itemCode) throws SQLException {
        return itemDAO.getItemByCode(itemCode);
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        return itemDAO.updateItem(item);
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException {
        return itemDAO.deleteItem(itemCode);
    }
}
