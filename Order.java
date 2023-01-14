package com.example.supplychain;

public class Order {
    public static boolean placeSingeOrder(Product product,String customer){
        String orderQuery = String.format("INSERT INTO orders(quantity,customer_id,product_id,status) VALUE(1, (select id from customer where email = '%s'),%s,'ORDERED');",customer.trim(),product.getId());

        DatabaseConnection dbConn = new DatabaseConnection();
        if(dbConn.insertData(orderQuery)>=1){
            return true;
        }
        System.out.println(orderQuery);
        return false;
    }
}
