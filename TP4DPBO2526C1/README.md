## TP4DPBO2526C1

Janji
Saya Sheva Desprianty Hermawan dengan NIM 2511645 mengerjakan TP 4 dalam mata kuliah desain pemrograman berorientasi objek untuk keberkahannya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Amin
---

## Deskripsi Program
Aplikasi **PeopleData** merupakan sistem manajemen data personel/karyawan berbasis antarmuka grafis (**Desktop GUI**) yang dibangun menggunakan pemrograman **Java** dan pustaka **Java Swing (Java Foundation Classes)**.

Aplikasi ini memungkinkan pengguna untuk melakukan pengelolaan data secara interaktif dengan operasi **CRUD** (*Create, Read, Update, Delete*) lengkap secara *in-memory* menggunakan struktur data `ArrayList`, serta dilengkapi validasi input dan dialog konfirmasi keamanan data.

---

## Spesifikasi Atribut & Fitur Utama

### 1. Struktur Atribut Data (`Person.java`)
Setiap data person memiliki 5 atribut utama:
* **ID** (`String`): Identitas unik person/karyawan (contoh: `EMP001`).
* **Nama** (`String`): Nama lengkap person.
* **Tahun Lahir** (`int`): Tahun lahir (dengan validasi tipe data angka integer).
* **Kategori** (`String`): Kategori kepegawaian (`Full-time`, `Contract`, `Part-time`, `Internship`) yang dipilih melalui `JComboBox`.
* **Jenis Kelamin** (`String`): **[FITUR BONUS +20]** Atribut tambahan berupa `JComboBox` (komponen non-JTextField) dengan opsi `Laki-laki` dan `Perempuan`.

---

### 2. Fitur Operasional CRUD
**Read Data (Tampil Tabel)**:
    * Menampilkan seluruh daftar person pada `JTable` secara otomatis saat aplikasi pertama kali dijalankan dari daftar awal (*dummy data*).
**Create Data (Tambah)**:
    * Menambahkan data person baru ke dalam tabel dan list melalui form input.
    * Dilengkapi validasi input kosong dan *error handling* `NumberFormatException` jika input tahun lahir bukan angka.
**Update Data (Ubah)**:
    * Mengubah data person yang sudah ada.
    * Saat baris tabel diklik, seluruh data otomatis terisi ke dalam form input, tombol berubah status menjadi **Update**, dan tombol **Delete** dimunculkan.
**Delete Data (Hapus) + Confirmation Prompt**:
    * Menghapus data dari list dan tabel.
    * **Fitur Keamanan:** Menampilkan pop-up dialog konfirmasi (`JOptionPane.showConfirmDialog`) sebelum data benar-benar dihapus untuk mencegah penghapusan yang tidak disengaja.
**Cancel / Clear Form**:
    * Mengosongkan seluruh field input, mereset `JComboBox`, menyembunyikan tombol **Delete**, serta mengembalikan tombol ke fungsi **Add**.

---

##  Alur Kerja Program (*Process Flow*)

1. Inisialisasi Aplikasi**:
    * Program dijalankan melalui method `main` pada `PeopleMenu.java`.
    * Window `JFrame` diatur ukurannya ($750 \times 650$), diletakkan di tengah layar, dan diberi warna Latar Belakang Putih.
    * Method `populateList()` dijalankan untuk mengisi 5 data awal secara otomatis.
    * Method `setTable()` mengompilasi list data menjadi `DefaultTableModel` untuk ditampilkan di `JTable`.

2. Alur Tambah Data (Create)**:
    * Pengguna mengisi form `ID`, `Nama`, `Tahun Lahir`, serta memilih `Kategori` dan `Jenis Kelamin`.
    * Pengguna menekan tombol **Add**.
    * Program memvalidasi input. Jika valid, objek `Person` baru ditambahkan ke `ArrayList`, tabel diperbarui, form dibersihkan, dan pop-up sukses ditampilkan.

3. Alur Pilih & Edit Data (Update)**:
    * Pengguna mengklik salah satu baris pada `JTable`.
    * Event `MouseListener` menangkap indeks baris (`selectedIndex`) dan memindahkan data dari baris tersebut ke komponen form input.
    * Tombol **Add** berganti teks menjadi **Update**, dan tombol **Delete** muncul di layar.
    * Pengguna mengubah nilai di form lalu menekan tombol **Update** untuk memperbarui data pada `ArrayList` dan tabel.

4. Alur Hapus Data (Delete)**:
    * Pengguna memilih data dari tabel lalu menekan tombol **Delete**.
    * Pop-up dialog peringatan (*Warning Message*) muncul menanyakan: *"Apakah Anda yakin ingin menghapus data [Nama]?"*.
    * Jika pengguna memilih **Yes**, data dihapus dari `ArrayList`, tabel di-refresh, dan form di-reset.

---

## Struktur Folder Project

```text
PeopleData/
├── src/
│   ├── Person.java          # Class Model Data Person
│   ├── PeopleMenu.java      # Class Logika GUI, Event Listener, & Main Method
│   └── PeopleMenu.form      # UI Designer XML Form
├── Dokumentasi/
│   └── demo.gif             # Rekaman/Gambar Pengujian Operasi CRUD
└── README.md                # Dokumentasi Proyek