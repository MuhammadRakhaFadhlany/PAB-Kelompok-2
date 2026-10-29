# AutoParts Hub

AutoParts Hub adalah aplikasi Android sisi pelanggan (client-side) yang dirancang untuk mempermudah pencarian, pemilihan, dan pemesanan suku cadang otomotif secara transparan, serta dilengkapi dengan simulasi layanan purna jual (aftersales). 

Aplikasi ini dikembangkan sebagai bagian dari tugas praktikum akademik dengan fokus pada pemenuhan indikator fungsionalitas tanpa menggunakan database server eksternal (menggunakan penyimpanan data lokal).

---

## 1. Informasi Proyek & Tim

* **Nama Proyek:** Spare Parts Store
* **Nama Aplikasi:** AutoParts Hub
* **Platform:** Android (Kotlin, XML Layout)
* **Mata Kuliah:** Praktikum Pemrograman Aplikasi Mobile

---

## 2. Fitur & Modul Utama (In-Scope)

Aplikasi ini terdiri dari 7 modul utama:

1. **Modul 1: Splash Screen & Autentikasi**
   * Tampilan awal (Splash) selama 2 detik.
   * Fitur registrasi akun baru dan login dengan validasi input yang disimpan secara lokal via SharedPreferences.
2. **Modul 2: Katalog & Filter**
   * Menampilkan daftar suku cadang otomotif (Kategori: Mesin, Rem, Oli, Kelistrikan).
   * Pencarian real-time berdasarkan nama, kode barang, maupun kompatibilitas tipe kendaraan.
3. **Modul 3: Detail Produk & Opsi Layanan**
   * Informasi lengkap produk (stok, deskripsi, kompatibilitas).
   * Pemilihan kuantitas (1-10 item) beserta opsi penambahan biaya Garansi Perpanjangan dan Jasa Pasang.
4. **Modul 4: Keranjang Belanja**
   * Daftar item belanja menggunakan ListView.
   * Opsi ubah jumlah belanja secara dinamis atau hapus item dengan konfirmasi dialog pop-up.
5. **Modul 5: Toolbar & Options Menu**
   * Menu navigasi cepat di bagian atas aplikasi untuk akses Keranjang, Bantuan, Tentang Aplikasi, dan Keluar.
6. **Modul 6: Service Center & Bantuan CS**
   * Fitur menghubungi Customer Service via Telepon secara langsung (Implicit Intent ACTION_DIAL).
   * Integrasi navigasi lokasi ke bengkel mitra via Google Maps atau Browser.
7. **Modul 7: Checkout & Status Pesanan**
   * Pengisian alamat pengiriman dan opsi kurir (Reguler / Express) dengan rincian biaya transparan.
   * Simulasi pelacakan status pesanan bertahap (Diproses, Dikemas, Dikirim, Selesai) serta fitur berbagi (share) teks invoice belanja.


## 3. Detail Penggunaan & Alur Kerja Aplikasi

### Registrasi & Masuk Akun
* Saat pertama kali membuka aplikasi, pengguna dialihkan otomatis dari SplashActivity ke LoginActivity.
* Jika belum punya akun, pilih tautan daftar untuk membuka RegisterActivity. Isi Nama, Email, dan Password (minimal 6 karakter).
* Setelah registrasi berhasil, kembali ke halaman Login dan masukkan data yang sudah didaftarkan.

### Menjelajah Katalog & Memilih Produk
* Setelah berhasil masuk, pengguna berada di MainActivity (Katalog).
* Gunakan kolom pencarian di bagian atas atau filter dropdown (Spinner) untuk menyaring kategori suku cadang (contoh: kategori "Rem" atau kata kunci "Avanza").
* Ketuk salah satu item produk untuk masuk ke DetailActivity.

### Mengatur Opsi Layanan & Keranjang
* Di halaman Detail Produk, tentukan jumlah barang yang ingin dibeli.
* Opsional: Centang kotak Garansi Perpanjangan (+Rp 25.000/item) atau Jasa Pasang (+Rp 50.000/item) jika ingin menambahkan layanan purna jual. Subtotal akan terhitung otomatis di layar.
* Klik Tambah ke Keranjang untuk dialihkan ke CartActivity. Pengguna bisa menghapus item di sini dengan menekan tombol hapus dan menyetujui konfirmasi AlertDialog.

### Proses Checkout & Simulasi Pesanan
* Dari halaman Keranjang, klik tombol Checkout untuk membuka CheckoutActivity.
* Isi alamat pengiriman lengkap (minimal 10 karakter).
* Pilih metode pengiriman: Reguler (Rp 15.000) atau Express (Rp 35.000). Total harga akan langsung diperbarui secara transparan.
* Selesaikan pembayaran simulasi dengan mengikuti petunjuk rekening dummy yang tertera, lalu klik Buat Pesanan.
* Pengguna akan diarahkan ke OrderStatusActivity. Klik tombol "Simulasi: Lanjut Tahap" untuk mensimulasikan pergerakan status pengiriman barang hingga selesai. 
* Gunakan tombol Share Invoice untuk membagikan detail pesanan teks ke aplikasi lain.

### Mengakses Layanan Bantuan (Service Center)
* Di pojok kanan atas aplikasi (Katalog, Detail, Keranjang, Status), ketuk ikon titik tiga lalu pilih Bantuan untuk membuka ServiceCenterActivity.
* Klik tombol Hubungi CS untuk membuka dialer telepon secara otomatis.
* Ketuk salah satu daftar bengkel mitra tertera untuk membuka aplikasi navigasi peta (Google Maps) menuju lokasi tersebut.

## 4. Batasan Sistem (Out-of-Scope)
Untuk kelancaran pengujian akademik, mohon diperhatikan beberapa batasan sistem berikut:
* Tidak ada panel admin/gudang: Data suku cadang bersifat statis sebanyak 20 produk bawaan aplikasi (Lampiran A).
* Tidak ada integrasi Payment Gateway nyata: Transaksi dilakukan dengan metode transfer simulasi manual menggunakan informasi rekening dummy.
* Tidak memakai Database Server/API: Seluruh penyimpanan data bersifat lokal dan disimpan di memori aplikasi selama sesi berjalan.

---
*Politeknik STMI Jakarta.*
