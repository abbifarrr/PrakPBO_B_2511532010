package shop.app;

import shop.model.*;
import shop.payment.*;
import shop.service.CheckoutService;
import shop.shipping.*;
import shop.notification.*;
import java.util.List;

public class TugasApp {
    public static void main(String[] args) {
        Product ebook = new DigitalProduct("EBK-003", "E-book", 50_000, "url");
        List<Product> cart = List.of(ebook);
        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();
        
        EWalletPayment wallet = new EWalletPayment("Dompetku", 300_000);
        BankTransferPayment transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1_000_000);

        System.out.println("Tugas oleh " + shop.StudentInfo.NAME + " (" + shop.StudentInfo.NIM + ")");
        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");
        System.out.println("E-Wallet Dompetku : Rp" + checkout.grandTotal(cart, express, wallet));
        System.out.println("Transfer Bank Nagari : Rp" + checkout.grandTotal(cart, express, transfer));
        System.out.println("Kartu Kredit (Siti Rahma): Rp" + checkout.grandTotal(cart, express, card));
        System.out.println("Kebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet : " + wallet.policy());

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel(shop.StudentInfo.SMS_MAX_LENGTH)); 
        service.register(new WhatsAppChannel());
        
        service.register(new NotificationChannel() {
            @Override public String channelName() { return "LOG"; }
            @Override public boolean send(CustomerContact contact, String msg) {
                System.out.println("[LOG] " + contact.getName() + ": " + msg);
                return true;
            }
        });

        String msg = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. Nomor resi akan muncul di aplikasi dalam 1x24 jam. Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami! Pesan ini sengaja dibuat panjang agar memicu batas maksimum karakter SMS.";
        
        CustomerContact siti = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        int countSiti = service.broadcast(siti, msg);
        System.out.println("Terkirim ke Siti lewat " + countSiti + " kanal\n");

        CustomerContact budi = new CustomerContact("Budi", "", "+6281298765432");
        int countBudi = service.broadcast(budi, msg);
        System.out.println("Terkirim ke Budi lewat " + countBudi + " kanal");
    }
}