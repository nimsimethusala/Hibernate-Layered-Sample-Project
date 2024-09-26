package org.example.bo.impl;

import org.example.bo.ItemBO;
import org.example.dao.CustomerDAO;
import org.example.dao.DAOFactory;
import org.example.dao.ItemDAO;
import org.example.dto.CustomerDTO;
import org.example.dto.ItemDTO;
import org.example.entity.Customer;
import org.example.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean SaveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getItemId(), itemDTO.getItemName(), itemDTO.getCount(), itemDTO.getPrice()));
    }

    @Override
    public String generateNextItemId() {
        return itemDAO.generateNextId();
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        ArrayList<Item> items = itemDAO.getAll();

        for (Item item : items) {
            ItemDTO itemDTO = new ItemDTO(item.getItemId(), item.getItemName(), item.getCount(), item.getPrice());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId);
    }

    @Override
    public ItemDTO searchByItemId(String itemiId) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.searchById(itemiId);
        return new ItemDTO(item.getItemId(), item.getItemName(), item.getCount(), item.getPrice());
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        return itemDAO.update(new Item(itemDTO.getItemId(), itemDTO.getItemName(), itemDTO.getCount(), itemDTO.getPrice()));
    }
}
