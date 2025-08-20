package lk.pahana_billing_system.service;

import lk.pahana_billing_system.model.Item;
import lk.pahana_billing_system.service.impl.ItemServiceImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemServiceTest {

    private class DummyItemService extends ItemServiceImpl {
        @Override
        public boolean addItem(Item item) throws SQLException {
            return true;
        }

        @Override
        public List<Item> getAllItems() throws SQLException {
            List<Item> list = new ArrayList<>();
            Item item = new Item();
            item.setItemCode("I001");
            item.setDescription("Test Item");
            list.add(item);
            return list;
        }

        @Override
        public Item getItemByCode(String itemCode) throws SQLException {
            Item item = new Item();
            item.setItemCode(itemCode);
            item.setDescription("Test Item");
            return item;
        }

        @Override
        public boolean updateItem(Item item) throws SQLException {
            return true;
        }

        @Override
        public boolean deleteItem(String itemCode) throws SQLException {
            return true;
        }
    }

    private final DummyItemService itemService = new DummyItemService();

    @Test
    public void testAddItem() throws SQLException {
        Item item = new Item();
        boolean result = itemService.addItem(item);
        assertTrue(result);
    }

    @Test
    public void testGetAllItems() throws SQLException {
        List<Item> items = itemService.getAllItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("I001", items.get(0).getItemCode());
    }

    @Test
    public void testGetItemByCode() throws SQLException {
        Item item = itemService.getItemByCode("I001");
        assertNotNull(item);
        assertEquals("I001", item.getItemCode());
        assertEquals("Test Item", item.getDescription());
    }

    @Test
    public void testUpdateItem() throws SQLException {
        Item item = new Item();
        boolean result = itemService.updateItem(item);
        assertTrue(result);
    }

    @Test
    public void testDeleteItem() throws SQLException {
        boolean result = itemService.deleteItem("I001");
        assertTrue(result);
    }
}
