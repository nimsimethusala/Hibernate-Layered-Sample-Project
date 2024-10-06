package org.example.bo;

import org.example.bo.impl.CustomerBoImpl;
import org.example.bo.impl.ItemBoImpl;
import org.example.bo.impl.OrderBoImpl;
import org.example.bo.impl.PlaceOrderBoImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBoImpl();
            case ITEM:
                return new ItemBoImpl();
            case ORDER:
                return new OrderBoImpl();
            case ORDER_DETAIL:
                return new PlaceOrderBoImpl();
            default:
                return null;
        }
    }
}
