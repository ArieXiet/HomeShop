package com.ariexiet.homeshop;

import com.ariexiet.homeshop.bill.Bill;
import com.ariexiet.homeshop.bill.Writer;
import com.ariexiet.homeshop.delivery.Delivery;
import com.ariexiet.homeshop.delivery.RelayDelivery;
import com.ariexiet.homeshop.product.Fridge;
import com.ariexiet.homeshop.product.NoProductInBillException;
import com.ariexiet.homeshop.product.Product;
import com.ariexiet.homeshop.product.Television;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {
    private String output;
    private Writer writerMock = new Writer() {
        @Override
        public void start() {
            output = "";
        }

        @Override
        public void writeLine(String line) {
            output += line + "\n";
        }

        @Override
        public void stop() {
        }
    };
    private Product cafe = new Product("Philips HD7866/61", "Philips SENSEO Quadrante, Noir - 1 ou 2 tasses", 79.99);
    private Product tv = new Television("TV Samsung UE49MU6292", "Smart TV LED incurvée 49\"", 599, 49, "LED");
    private Fridge fridge = new Fridge("BEKO TSE 1042 F", "Réfrigérateur BEKO 130L - Classe A+ - blanc", 189, 130, false);
    private Customer customer = new Customer("Juste Leblanc", "19 rue Germain Pilon, Paris");
    private Delivery lowCostRelayDelivery = new RelayDelivery(27);

    @Test
    public void Given_2productsAndDelivery_When_generatingBill_Then_getGoodLineNumber() {
        Bill bill = new Bill(customer, lowCostRelayDelivery);
        bill.addProduct(cafe, 1);
        bill.addProduct(tv, 1);
        bill.generate(writerMock);
        int lineNumber = output.split("\n").length;
        assertEquals(20, lineNumber);
    }

    @Test
    public void Given_3productsAndDelivery_When_generatingBill_Then_getGoodTotal() {
        Bill bill = new Bill(customer, lowCostRelayDelivery);
        bill.addProduct(cafe, 1);
        bill.addProduct(tv, 1);
        bill.addProduct(fridge, 1);
        assertEquals(870.98, bill.getTotal(), 0.01);
    }

    @Test
    public void Given_emptyProductList_generatingBill_Then_throwsException() {
        Bill bill = new Bill(customer, lowCostRelayDelivery);
        assertThrows(NoProductInBillException.class, () -> bill.generate(writerMock));
    }
}