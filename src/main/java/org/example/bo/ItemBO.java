package org.example.bo;

import org.example.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO{
    boolean SaveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    String generateNextItemId();

    List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException;

    ItemDTO searchByItemId(String itemiId) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO);

    String getItemName(String itemId);

    double getPrice(String itemId);
}
