import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    // Komponen GUI (Bound dari PeopleMenu.form)
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JComboBox<String> genderComboBox; // Atribut/Komponen Baru (Bonus +20)
    private JTable personTable;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JButton cancelButton;

    // Data Storage & Variable State
    private ArrayList<Person> listPerson;
    private int selectedIndex = -1;

    // Main Method (Diperlukan agar program bisa di-run)
    public static void main(String[] args) {
        // Buat object window
        PeopleMenu menu = new PeopleMenu();
        // Atur ukuran window
        menu.setSize(750, 650);
        // Letakkan window di tengah layar
        menu.setLocationRelativeTo(null);
        // Isi window dengan panel utama
        menu.setContentPane(menu.mainPanel);
        // Ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);
        // Tampilkan window
        menu.setVisible(true);
        // Agar program ikut berhenti saat window ditutup
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Constructor
    public PeopleMenu() {
        // Inisialisasi listPerson
        listPerson = new ArrayList<>();

        // Isi listPerson awal dengan data dummy
        populateList();

        // Isi tabel orang
        personTable.setModel(setTable());

        // Ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Atur isi combo box Kategori
        String[] kategoriData = { "--- Pilih Kategori ---", "Full-time", "Contract", "Part-time", "Internship" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Atur isi combo box Jenis Kelamin
        String[] genderData = { "--- Pilih Jenis Kelamin ---", "Laki-laki", "Perempuan" };
        genderComboBox.setModel(new DefaultComboBoxModel<>(genderData));

        // Sembunyikan button delete awal
        deleteButton.setVisible(false);

        // Listener: Tombol Add / Update
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Listener: Tombol Delete (Dilengkapi Prompt Konfirmasi)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    // Prompt konfirmasi sebelum menghapus data
                    int confirm = JOptionPane.showConfirmDialog(
                            mainPanel,
                            "Apakah Anda yakin ingin menghapus data " + listPerson.get(selectedIndex).getNama() + "?",
                            "Konfirmasi Hapus Data",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteData();
                    }
                }
            }
        });

        // Listener: Tombol Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Listener: Klik Baris Tabel
        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = personTable.getSelectedRow();

                if (selectedIndex != -1) {
                    // Simpan value dari baris tabel yang dipilih
                    String curId = personTable.getModel().getValueAt(selectedIndex, 1).toString();
                    String curNama = personTable.getModel().getValueAt(selectedIndex, 2).toString();
                    String curTahun = personTable.getModel().getValueAt(selectedIndex, 3).toString();
                    String curKategori = personTable.getModel().getValueAt(selectedIndex, 4).toString();
                    String curGender = personTable.getModel().getValueAt(selectedIndex, 5).toString();

                    // Ubah isi textfield dan combo box
                    idField.setText(curId);
                    namaField.setText(curNama);
                    tahunLahirField.setText(curTahun);
                    kategoriComboBox.setSelectedItem(curKategori);
                    genderComboBox.setSelectedItem(curGender);

                    // Ubah button "Add" menjadi "Update"
                    addUpdateButton.setText("Update");

                    // Tampilkan button delete
                    deleteButton.setVisible(true);
                }
            }
        });
    }

    // Method untuk mengisi data dummy awal
    public void populateList() {
        listPerson.add(new Person("EMP001", "Budi Santoso", 1992, "Full-time", "Laki-laki"));
        listPerson.add(new Person("EMP002", "Siti Aminah", 1997, "Full-time", "Perempuan"));
        listPerson.add(new Person("EMP003", "Rian Pratama", 1995, "Contract", "Laki-laki"));
        listPerson.add(new Person("EMP004", "Dewi Lestari", 1998, "Part-time", "Perempuan"));
        listPerson.add(new Person("EMP005", "Ahmad Fauzi", 2001, "Internship", "Laki-laki"));
    }

    // Method untuk menyusun tabel
    public DefaultTableModel setTable() {
        // Tentukan kolom tabel
        Object[] cols = { "No", "ID", "Nama", "Tahun Lahir", "Kategori", "Jenis Kelamin" };

        // Buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        // Isi tabel dengan listPerson
        for (int i = 0; i < listPerson.size(); i++) {
            Person p = listPerson.get(i);
            Object[] row = {
                    i + 1,
                    p.getId(),
                    p.getNama(),
                    p.getTahunLahir(),
                    p.getKategori(),
                    p.getJenisKelamin()
            };
            tmp.addRow(row);
        }

        return tmp;
    }

    // Method Tambah Data
    public void insertData() {
        try {
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String tahunStr = tahunLahirField.getText().trim();

            if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Semua field input wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (kategoriComboBox.getSelectedIndex() == 0 || genderComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(mainPanel, "Silakan pilih Kategori dan Jenis Kelamin yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int tahunLahir = Integer.parseInt(tahunStr);
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String jenisKelamin = genderComboBox.getSelectedItem().toString();

            // Tambahkan data ke dalam list
            listPerson.add(new Person(id, nama, tahunLahir, kategori, jenisKelamin));

            // Update tabel
            personTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(mainPanel, "Data berhasil ditambahkan!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Tahun Lahir harus berupa angka!", "Error Format", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method Update Data
    public void updateData() {
        try {
            if (selectedIndex < 0 || selectedIndex >= listPerson.size()) return;

            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String tahunStr = tahunLahirField.getText().trim();

            if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Semua field input wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (kategoriComboBox.getSelectedIndex() == 0 || genderComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(mainPanel, "Silakan pilih Kategori dan Jenis Kelamin yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int tahunLahir = Integer.parseInt(tahunStr);
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String jenisKelamin = genderComboBox.getSelectedItem().toString();

            // Ubah data person di list
            listPerson.get(selectedIndex).setId(id);
            listPerson.get(selectedIndex).setNama(nama);
            listPerson.get(selectedIndex).setTahunLahir(tahunLahir);
            listPerson.get(selectedIndex).setKategori(kategori);
            listPerson.get(selectedIndex).setJenisKelamin(jenisKelamin);

            // Update tabel
            personTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(mainPanel, "Data berhasil diubah!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Tahun Lahir harus berupa angka!", "Error Format", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method Hapus Data
    public void deleteData() {
        if (selectedIndex >= 0 && selectedIndex < listPerson.size()) {
            // Hapus data dari List
            listPerson.remove(selectedIndex);

            // Update tabel
            personTable.setModel(setTable());

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Delete berhasil");
            JOptionPane.showMessageDialog(mainPanel, "Data berhasil dihapus!");
        }
    }

    // Method Reset Form
    public void clearForm() {
        // Kosongkan semua textfield dan combo box
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        genderComboBox.setSelectedIndex(0);

        // Ubah button "Update" kembali menjadi "Add"
        addUpdateButton.setText("Add");

        // Sembunyikan button delete
        deleteButton.setVisible(false);

        // Reset selectedIndex menjadi -1
        selectedIndex = -1;
    }
}