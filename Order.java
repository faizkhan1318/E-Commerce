package com.example.ecomm;


public class Order {
    public static boolean myorder(Customer customer, Product product) {
        try {
            //String MyOrder ="SELECT products.pid, products.name, products.price FROM orders INNER JOIN products ON orders.product_id = products.pid WHERE customer_id= " + customer.getId();
            String MyOrder="INSERT INTO orders(customer_id, product_id, name, price) VALUES(" +customer.getId()+","+ product.getId() + ", '" + product.getName() + "'," + product.getPrice() +")";
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(MyOrder);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean removeorder(Customer customer, Product product) {
        try {
            String MyOrder="Delete from orders where product_id=" + product.getId() + " and customer_id=" +customer.getId();
            System.out.println(product.getId()+" "+ customer.getId());
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(MyOrder);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean placeOrder(Customer customer, Product product) {
        try {
            //String placeOrder = "INSERT INTO orders(customer_id, product_id, status, price) VALUES(" + customer.getId() + "," + product.getId() + ", 'Ordered'" + "," + product.getPrice() + ")";

            String placeOrder = "INSERT INTO orders(customer_id, product_id, status, name, price) VALUES(" + customer.getId() + "," + product.getId() + ", 'Ordered', '" + product.getName() + "', " + product.getPrice() + ")";
            DatabaseConnection dbConn = new DatabaseConnection();
            System.out.println( product.getId() + " "+product.getName()+" "+product.getPrice());
            return dbConn.insertUpdate(placeOrder);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
