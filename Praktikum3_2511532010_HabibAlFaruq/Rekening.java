package Praktikum3_2511532010_HabibAlFaruq;

import java.util.ArrayList;

public class Rekening {
    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private String pin;
    private ArrayList<Transaksi> riwayatTransaksi;

    //MODIFIKASI KONSTRUKTOR UNTUK MENERIMA PIN AWAL
    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        
        
        if (pinAwal.length()==6) {
        	this.pin = pinAwal;
        }else {
        	System.out.println("Peringatan: PIN harus 6 digit! Menggunakan pin default 123456");
        	this.pin ="123456";
        }
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat");
    }

    //GETTER UNTUK ATRIBUT YANG DIIZINKAN DIBACA PUBLIC
    public String getNomorRekening() {return nomorRekening;}
    public String getNamaPemilik() {return namaPemilik;}
    
    //OTENTIKASI INTERNAL VALIDASI 
    public boolean otentikasi(String inputPin) {
    	return this.pin.equals(inputPin);
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini : Rp" + saldo);
        } else {
            System.out.println("Gagal : Nominal setor harus lebih dari 0!");
        }
    }

    public void tarikTunai(double nominal) {
        
    	if (nominal < 10000) {
            System.out.println("Tarik tunai gagal : nominal penarikan minimal 10.000");
        } else if (saldo < nominal) {
            System.out.println("Saldo tidak boleh lebih kecil dari nominal yang ditarik");
        } else {
            saldo -= nominal;
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Tarik tunai Rp " + nominal + " berhasil. Saldo saat ini : Rp " + saldo);
        }
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir : Rp " + saldo);
        System.out.println("---------------------");
    }

    public void cetakMutasi() {
        System.out.println("\n--- MUTASI REKENING ---");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
        } else {
            for (Transaksi trx : riwayatTransaksi) {
                trx.cetakDetail();
            }
        }
        System.out.println("-----------------------");
    }
}

