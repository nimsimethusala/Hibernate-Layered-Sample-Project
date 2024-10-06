package org.example.bo;

import org.example.dto.OrderDTO;
import org.example.tm.OrderTm;

import java.util.List;

public interface PlaceOrderBO extends SuperBO{

    boolean placeOrder(List<OrderTm> orderTmList, OrderDTO orderDTO);
}
