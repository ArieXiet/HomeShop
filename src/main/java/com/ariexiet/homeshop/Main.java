package com.ariexiet.homeshop;

import com.ariexiet.homeshop.bill.Bill;
import com.ariexiet.homeshop.bill.FileWriter;
import com.ariexiet.homeshop.delivery.RelayDelivery;
import com.ariexiet.homeshop.product.Fridge;
import com.ariexiet.homeshop.product.NoProductInBillException;
import com.ariexiet.homeshop.product.Product;
import com.ariexiet.homeshop.product.Television;

public class Main {

    public static void main(String[] args) {
        Product cafe = new Product("Philips HD7866/61", "Philips SENSEO Quadrante, Noir - 1 ou 2 tasses", 79.99);
        Product tv = new Television("TV Samsung UE49MU6292", "Smart TV LED incurvée 49\"", 599, 49, "LED");
        Fridge fridge = new Fridge("BEKO TSE 1042 F", "Réfrigérateur BEKO 130L - Classe A+ - blanc", 189, 130, false);

        Customer customer = new Customer("Juste Leblanc", "19 rue Germain Pilon, Paris");

        Bill bill = new Bill(customer, new RelayDelivery(27));

        bill.addProduct(cafe, 1);
        bill.addProduct(tv, 1);
        bill.addProduct(fridge, 1);
        try {
            bill.generate(new FileWriter("facture_leblanc"));
        } catch (NoProductInBillException e) {
            System.err.println("Pas de produit dans la facture");
        }

    }

}
