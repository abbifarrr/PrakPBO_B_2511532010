package Praktikum4_2511532010_HabibAlFaruq;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;
        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI ===");

        while (isRunning) {
            System.out.println("\nMenu Utama : ");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Cetak Mutasi");
            System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu :");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();
                    System.out.print("Masukkan Nama Pemilik : ");
                    String nama = input.nextLine();
                    System.out.print("Masukkan Saldo Awal : ");
                    double saldo = input.nextDouble();
                    input.nextLine();
                    System.out.print("Masukkan PIN : ");
                    String pin = input.nextLine();
                    System.out.print("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis : ");
                    String pilihanProduk = input.nextLine();
                   

                    if (pilihanProduk.equals("1")) {
                        System.out.print("Masukkan Suku Bunga (%): ");
                        double sukuBunga = input.nextDouble();
                        input.nextLine();
                        RekeningTabungan akunBaru = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
                        daftarRekening.add(akunBaru);
                        akunAktif = akunBaru;
                        break;
                    } else if (pilihanProduk.equals("2")) {
                        System.out.print("Masukkan Batas Overdraft : ");
                        double batasOverdraft = input.nextDouble();
                        input.nextLine();
                        RekeningGiro akunBaru = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
                        daftarRekening.add(akunBaru);
                        akunAktif = akunBaru;
                        break;
                    } else {
                        System.out.println("Pilihan produk tidak valid!");
                    }

                    

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error : Mohon maaf, Anda belum memiliki nomor Rekening!");
                    } else {
                        System.out.print("Masukkan nominal Setor : ");
                        double setor = input.nextDouble();
                        akunAktif.setorTunai(setor);
                    }
                    break;

                case 3:
                	System.out.print ("Maukkan PIN : ");
                	String PIN = input.nextLine();
                    if (akunAktif == null) {
                        System.out.println("Error : Mohon maaf, Anda belum memiliki nomor Rekening!");
                    } else if (akunAktif.otentikasi(PIN)==true) {
                    	 System.out.print("Masukkan nominal tarik tunai : "); 
                    	 double tarik = input.nextDouble();                   
                    	 akunAktif.tarikTunai(tarik);                         
                    	
                    }else {System.out.println("Akses Ditolak : PIN yang anda masukkan salah");
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum membuka Rekening!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 5:
                    if (daftarRekening.isEmpty()) {
                        System.out.println("Error : Belum ada rekening yang terdaftar!");
                    } else {
                        System.out.print("Masukkan No Rekening yang ingin digunakan : ");
                        String noCari = input.nextLine();
                        boolean ditemukan = false;

                        for (Rekening rek : daftarRekening) {
                            if (rek.getNomorRekening().equals(noCari)) {
                                akunAktif = rek;
                                ditemukan = true;
                                System.out.println("Berhasil! Akun aktif sekarang adalah: " + rek.getNamaPemilik());
                                break;
                            }
                        }

                        if (!ditemukan) {
                            System.out.println("Error : Rekening dengan nomor " + noCari + " tidak ditemukan!");
                        }
                    }
                    break;

                case 6:
                	System.out.print ("Masukkan PIN : "); 
                    String pin2 = input.nextLine();       
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum membuka Rekening!");
                    } else if(akunAktif.otentikasi(pin2)==true) {
                        akunAktif.cetakMutasi();}
                    else {System.out.print("Akses Ditolak : PIN yang anda masukkan salah");
                    }
                    break;

                case 7 :
                    if (akunAktif instanceof RekeningTabungan){
                        ((RekeningTabungan) akunAktif).tambahBungaAkhirBulan();
                    }else {
                        System.out.println("Gagal : fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan");
                    }
                    break;

                case 0:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terimakasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak Valid!");
            }
        }
        input.close();
    }
}