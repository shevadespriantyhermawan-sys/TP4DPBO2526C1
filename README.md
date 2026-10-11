# TP 4 DPBO — PeopleData (Data Karyawan / Person)

Tugas Praktikum 4 Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO).

Program ini mengelola data karyawan/person (`People`) dengan GUI Java Swing, mengikuti pola `ProductData`: form input di atas, tabel data di bawah, dengan operasi CRUD penuh plus validasi dan dialog konfirmasi hapus.

## Janji

Saya [Isi Nama Anda] dengan NIM [Isi NIM Anda] mengerjakan Tugas Praktikum 4 pada Mata Kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

## Struktur Folder

```text
TP4DPBO2526C1/
├── README.md
├── src/
│   ├── Person.java
│   ├── PeopleMenu.java
│   └── PeopleMenu.form
└── Dokumentasi/
    └── demo.gif
```

## Desain / Fungsi Program

**Tema:** Data Karyawan / Person — setiap `Person` adalah satu data person dengan 5 atribut.

### Atribut `Person.java` (`src/Person.java`):

| # | Atribut | Tipe | Komponen GUI | Keterangan |
|---|---|---|---|---|
| 1 | **ID** | `String` | `JTextField` (`idField`) | Format ID unik, contoh: `EMP001` |
| 2 | **Nama** | `String` | `JTextField` (`namaField`) | Nama lengkap person |
| 3 | **Tahun Lahir** | `int` | `JTextField` (`tahunLahirField`) | Angka tahun lahir (misal: `1995`) |
| 4 | **Kategori** | `String` | `JComboBox` (`kategoriComboBox`) | Pilihan: `Full-time`, `Contract`, `Part-time`, `Internship` |
| 5 | **Jenis Kelamin** | `String` | `JComboBox` (`genderComboBox`) | Pilihan: `Laki-laki`, `Perempuan` **[Fitur Bonus]** |

### File program:
* **`src/Person.java`** — model data + getter/setter semua atribut.
* **`src/PeopleMenu.java`** — `JFrame` utama: CRUD (`insertData`, `updateData`, `deleteData`), `setTable()`, `populateList()`, `clearForm()`, dan semua event listener.
* **`src/PeopleMenu.form`** — binding GUI Designer IntelliJ.

### Fungsi tiap komponen:
* **Form (`JTextField` / `JComboBox`)** untuk input dan edit data.
* **`addUpdateButton`** berlabel `Add` saat `selectedIndex == -1`, berubah jadi `Update` setelah row diklik.
* **`deleteButton`** disembunyikan (`setVisible(false)`) saat mode Add, dimunculkan setelah row diklik.
* **`cancelButton`** memanggil `clearForm()` untuk reset form.
* **`personTable` (`JTable` + `DefaultTableModel`)** untuk Read: klik row mengisi seluruh form via `mousePressed`.

## Method Program

| Method | Fungsi |
|---|---|
| `main` | Titik masuk: set `ContentPane`, judul, ukuran $750 \times 650$, `EXIT_ON_CLOSE`, tampilkan window. |
| `PeopleMenu()` | Constructor: inisialisasi `listPerson`, panggil `populateList()`, pasang model tabel & combo box, serta listener tombol/tabel. |
| `populateList()` | Mengisi `listPerson` dengan 5 data awal (`EMP001` - `EMP005`), reset `selectedIndex = -1`. |
| `setTable()` | Membuat `DefaultTableModel` dengan kolom `No`, `ID`, `Nama`, `Tahun Lahir`, `Kategori`, `Jenis Kelamin`, mengisi dari `listPerson`. |
| `insertData()` | **Create**: ambil nilai form, validasi input, `listPerson.add(new Person(...))`, refresh tabel, `clearForm()`, dialog sukses. |
| `updateData()` | **Update**: cek `selectedIndex` valid, ambil & validasi nilai form, `listPerson.get(selectedIndex).set...`, refresh tabel, `clearForm()`. |
| `deleteData()` | **Delete**: cek `selectedIndex` valid, `listPerson.remove(selectedIndex)`, refresh tabel, `clearForm()`. Dipanggil setelah konfirmasi `YES_OPTION`. |
| `clearForm()` | **Reset form**: kosongkan text field, combo ke index 0, tombol kembali ke `Add`, sembunyikan `Delete`, `selectedIndex = -1`. |
| **Listener tombol + tabel** | `showConfirmDialog` (YES/NO) pada tombol delete, `cancelButton` memanggil `clearForm()`, `mousePressed` pada tabel mengisi form + ubah mode ke Update. |

## Penjelasan Alur

1. **Program jalan**: constructor memanggil `populateList()` mengisi 5 data, `setTable()` menampilkan data di tabel, combo box diinisialisasi, `clearForm()` set mode Add.
2. **Read (tampilan awal)**: tabel menampilkan 5 baris `EMP001` - `EMP005` (`No`, `ID`, `Nama`, `Tahun Lahir`, `Kategori`, `Jenis Kelamin`).
3. **Create**: isi form -> klik **Add** (`selectedIndex == -1`) -> `insertData()` validasi -> lolos: tambah ke list -> `setTable()` refresh -> `clearForm()` -> dialog *"Data berhasil ditambahkan!"*.
4. **Select**: klik row tabel -> `mousePressed` set `selectedIndex = row` -> `idField` / `namaField` / `tahunLahirField` / `kategoriComboBox` / `genderComboBox` terisi -> tombol jadi **Update**, tombol **Delete** muncul.
5. **Update**: edit form -> klik **Update** -> `updateData()` validasi -> lolos: `listPerson.get(selectedIndex)` di-set ulang -> `setTable()` -> `clearForm()` kembali ke mode Add -> dialog *"Data berhasil diubah!"*.
6. **Delete**: klik row -> klik **Delete** -> `showConfirmDialog("Apakah Anda yakin...?", YES_NO_OPTION)` -> **YES**: `deleteData()` hapus dari list -> `setTable()` -> `clearForm()` -> dialog sukses; **NO**: batal, data tetap.
7. **Cancel**: kapan saja klik **Cancel** -> `clearForm()`: field kosong, combo ke index 0, tombol **Add**, tombol **Delete** hidden, `selectedIndex = -1`.
8. **Data Storage**: Data tersimpan di `ArrayList` selama program berjalan.

## Validasi / Error Handling

| Bagian | Kondisi tidak valid | Penanganan |
|---|---|---|
| **Field kosong** | `ID` / `Nama` / `Tahun Lahir` kosong | `WARNING_MESSAGE`: *"Semua field input wajib diisi!"* |
| **Combo Box** | `Kategori` atau `Jenis Kelamin` belum dipilih (`index == 0`) | `WARNING_MESSAGE`: *"Silakan pilih Kategori dan Jenis Kelamin yang valid!"* |
| **Tahun Lahir** | Input bukan angka (terjadi `NumberFormatException`) | `ERROR_MESSAGE`: *"Tahun Lahir harus berupa angka!"* |
| **Konfirmasi Hapus** | Menekan tombol **Delete** | `showConfirmDialog` (`YES_NO_OPTION`): Tampil dialog konfirmasi sebelum menghapus. |

## Dokumentasi Demonstrasi Operasi CRUD

![Demo CRUD Program](Dokumentasi/demo.gif)
