package review.fixed;

import shop.payment.PaymentMethod;
import shop.shipping.ShippingService;

// 1. PERBAIKAN EXPRESS SHIPPING (Menghapus override calculateCost yang salah)
class ExpressShipping extends ShippingService {
    public ExpressShipping() { 
        super("EKSPRES"); 
    }
    
    @Override 
    protected long ratePerKg() { 
        return 18000; 
    }
}

// 2. PERBAIKAN ADDRESS (Menjadikan class final, field private final, dan hapus setter)
final class Address {
    private final String street;
    private final String city;
    
    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }
    
    public String getStreet() { return street; }
    public String getCity() { return city; }
}

// Class pendukung untuk mensimulasikan Order
class Order {
    private String id;
    private Address address;
    
    public Order(String id, Address address) { 
        this.id = id; 
        this.address = address; 
    }
    
    public Address getAddress() { return address; }
    public String getId() { return id; }

    // Inner class Receipt
    class Receipt {
        public void print() {
            System.out.println(Order.this.getId() + " -> " + Order.this.getAddress().getStreet() + ", " + Order.this.getAddress().getCity());
        }
    }
}

// 3. PERBAIKAN GIFT VOUCHER (Menghapus 'implements Shippable' yang bikin crash)
class GiftVoucher implements PaymentMethod {
    private long balance;
    
    public GiftVoucher(long balance) { 
        this.balance = balance; 
    }
    
    @Override 
    public String getName() { return "Voucher"; }
    
    @Override 
    public boolean pay(long amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }
    
    public long getBalance() { return balance; }
}

// 4. PERBAIKAN FLASH SALE (Enkapsulasi harga dengan private & setter)
class FlashSale {
    private long hargaFlashSale;
    
    public void setHargaFlashSale(long harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak valid");
        this.hargaFlashSale = harga;
    }
    
    public long getHargaFlashSale() { return hargaFlashSale; }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        System.out.println("Total cart: 170000");

        // Test 1: Ongkir Ekspres
        ExpressShipping kurir = new ExpressShipping();
        System.out.println("Ongkir Ekspres 2 kg: " + kurir.calculateCost(2000));

        // Test 2 & 5: Address Immutability dan Pemanggilan Inner Class
        Address alamat1 = new Address("Jl. Sudirman 10", "Padang");
        Order ord1 = new Order("ORD-1", alamat1);
        
        Address alamat2 = new Address("Jl. Sudirman 10", "Bukittinggi");
        Order ord2 = new Order("ORD-2", alamat2);

        // Memanggil inner class dengan benar: order.new Receipt()
        Order.Receipt struk1 = ord1.new Receipt();
        struk1.print();
        
        Order.Receipt struk2 = ord2.new Receipt();
        struk2.print();

        // Test 4: Flash Sale via Setter
        FlashSale promo = new FlashSale();
        promo.setHargaFlashSale(70000);
        System.out.println("Harga flash sale: " + promo.getHargaFlashSale());

        // Test 3: Pembayaran Voucher
        GiftVoucher voucher = new GiftVoucher(140000);
        boolean success = voucher.pay(promo.getHargaFlashSale());
        System.out.println(success + " | sisa Rp" + voucher.getBalance());
    }
}