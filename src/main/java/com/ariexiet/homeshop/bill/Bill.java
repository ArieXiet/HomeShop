package com.ariexiet.homeshop.bill;

import com.ariexiet.homeshop.Customer;
import com.ariexiet.homeshop.delivery.Delivery;
import com.ariexiet.homeshop.product.NoProductInBillException;
import com.ariexiet.homeshop.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Bill {
    private Customer customer;
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private Delivery delivery;
    private Product product;
    private Integer quantity;

    /**
     * Add a product with a quantity in the bill
     * @param product product to add
     * @param quantity quantity of the product
     */
    public void addProduct(Product product, Integer quantity) {
        this.products.put(product,quantity);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Bill(Customer customer, Delivery delivery) {
        this.customer = customer;
        this.delivery = delivery;
    }

    /**
     * Generate an output for the current Bill
     * @param writer object in charge of writing
     */
    public void generate(Writer writer) {
        if (products.isEmpty())
            throw new NoProductInBillException();
        else {
            writer.start();
            writer.writeLine("HomeShop compagnie");
            writer.writeLine("1 Place Charles de Gaulle, 75008 Paris");
            writer.writeLine("");
            writer.writeLine("Facture à l'attention de : ");
            writer.writeLine(customer.getFullname());
            writer.writeLine(customer.getAddress());
            writer.writeLine("");
            writer.writeLine("Mode de livraison : " + delivery.getInfo());
            writer.writeLine("");
            writer.writeLine("Produits : ");
            writer.writeLine("-----------------------------------------------------");
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                writer.writeLine(product.getName() + " - " + product.getPrice() + " - " + quantity + " unité(s)");
                writer.writeLine(product.getDescription());
                writer.writeLine("");
            }
            writer.writeLine("Livraison : " + delivery.getPrice());
            writer.writeLine("-----------------------------------------------------");
            writer.writeLine("Total : " + this.getTotal());
            writer.stop();
        }    }

    /**
     * get the total price for the current bill, including products and delivery cost
     * @return total price
     */
    public double getTotal() {
        double total = delivery.getPrice();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }

}
