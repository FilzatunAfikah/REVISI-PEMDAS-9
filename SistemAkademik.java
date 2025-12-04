import java.util.ArrayList;
import java.util.Scanner;

public class SistemAkademik {
    ArrayList<Mahasiswa> daftarMhs = new ArrayList<>();
    ArrayList<Civitas> daftarCivitas = new ArrayList<>();
    ArrayList<MataKuliah> daftarMK = new ArrayList<>();
    ArrayList<Kelas> daftarKelas = new ArrayList<>();

    Scanner in = new Scanner(System.in);
    
    // Session (Siapa yang lagi login)
    Civitas userLogin = null;    // Diisi jika Admin/Dosen login
    Mahasiswa mhsLogin = null;   // Diisi jika Mahasiswa login

    public static void main(String[] args) {
        SistemAkademik sistem = new SistemAkademik();
        sistem.initDataAwal(); // Load data dummy (atau data contoh awal)
        sistem.jalankan();
    }

    public void jalankan() {
        System.out.println("=== SISTEM AKADEMIK KAMPUS ===");
        while (true) {
            // Jika belum ada yang login, minta login
            if (userLogin == null && mhsLogin == null) {
                prosesLogin();
            } else {
                tampilkanMenuUtama();
            }
        }
    }

    public void prosesLogin() {
        System.out.println("\n--- SILAKAN LOGIN ---");
        System.out.print("ID (NIP/NIM): ");
        String id = in.nextLine();
        
        System.out.print("Password: ");
        String pass = in.nextLine();

        boolean foundCivitas = false;
        for (int i = 0; i < daftarCivitas.size(); i++) {
            Civitas c = daftarCivitas.get(i);
            // Validasi ID dan Password
            if (c.getNip().equals(id) && c.login(pass)) {
                userLogin = c;
                System.out.println("Login Berhasil! Halo, " + c.getNama() + " (" + c.getJenis() + ")");
                foundCivitas = true;
                break;
            }
        }

        if (foundCivitas) return;

        boolean foundMhs = false;
        for (int i = 0; i < daftarMhs.size(); i++) {
            Mahasiswa m = daftarMhs.get(i);
            if (m.getNim().equals(id) && m.login(pass)) {
                mhsLogin = m;
                System.out.println("Login Berhasil! Halo, " + m.getNama() + " (MAHASISWA)");
                foundMhs = true;
                break;
            }
        }

        if (!foundMhs) {
            System.out.println("Gagal Login: ID atau Password salah.");
        }
    }

    public void tampilkanMenuUtama() {
        if (userLogin != null) {
            System.out.println("\n=== MENU UTAMA (" + userLogin.getJenis() + ") ===");
            
            if (userLogin.getJenis().equals("ADMIN")) {
                System.out.println("1. Tambah Mahasiswa");
                System.out.println("2. Tambah Civitas (Dosen/Admin)");
                System.out.println("3. Tambah Mata Kuliah");
                System.out.println("4. Buka Kelas Baru");
                System.out.println("5. Input Nilai (Mode Admin)");
                System.out.println("6. Cetak Laporan (KHS)");
                System.out.println("0. Logout");
            } else {
                // Menu Dosen 
                System.out.println("1. Input Nilai ");
                System.out.println("2. Lihat Kelas Saya");
                System.out.println("0. Logout");
            }

            System.out.print("Pilih: ");
            int pilih = in.nextInt(); 
            in.nextLine(); 

            if (userLogin.getJenis().equals("ADMIN")) {
                if (pilih == 1) tambahMahasiswa();
                else if (pilih == 2) tambahCivitas();
                else if (pilih == 3) tambahMataKuliah();
                else if (pilih == 4) tambahKelas();
                else if (pilih == 5) inputNilai(true); // true = admin (bisa semua)
                else if (pilih == 6) cetakLaporanAdmin();
                else if (pilih == 0) userLogin = null;
                else System.out.println("Pilihan tidak valid.");
            } 
            else {
                if (pilih == 1) inputNilai(false); // false = dosen (dibatasi)
                else if (pilih == 2) lihatKelasDosen();
                else if (pilih == 0) userLogin = null;
                else System.out.println("Pilihan tidak valid.");
            }

        } 
        else if (mhsLogin != null) {
            System.out.println("\n=== MENU MAHASISWA ===");
            System.out.println("1. Lihat Kartu Hasil Studi (KHS)");
            System.out.println("0. Logout");
            System.out.print("Pilih: ");
            
            int pilih = in.nextInt();
            in.nextLine(); 

            if (pilih == 1) lihatKHSMahasiswa();
            else if (pilih == 0) mhsLogin = null;
            else System.out.println("Pilihan tidak valid.");
        }
    }

    public void tambahMahasiswa() {
        System.out.print("NIM: ");
        String nim = in.nextLine();
        System.out.print("Nama: ");
        String nama = in.nextLine();
        System.out.print("Password: ");
        String pass = in.nextLine();
        
        daftarMhs.add(new Mahasiswa(nim, nama, pass));
        System.out.println("Mahasiswa berhasil disimpan.");
    }

    public void tambahCivitas() {
        System.out.print("NIP: ");
        String nip = in.nextLine();
        System.out.print("Nama: ");
        String nama = in.nextLine();
        System.out.print("Password: ");
        String pass = in.nextLine();
        System.out.print("Role (ADMIN/DOSEN): ");
        String role = in.nextLine().toUpperCase();
        
        daftarCivitas.add(new Civitas(nip, nama, pass, role));
        System.out.println("User berhasil disimpan.");
    }

    public void tambahMataKuliah() {
        System.out.print("Kode MK: ");
        String kode = in.nextLine();
        System.out.print("Nama MK: ");
        String nama = in.nextLine();
        System.out.print("SKS: ");
        int sks = in.nextInt();
        in.nextLine();
        
        daftarMK.add(new MataKuliah(kode, nama, sks));
        System.out.println("Mata Kuliah berhasil disimpan.");
    }

    public void tambahKelas() {
        if (daftarMK.isEmpty() || daftarCivitas.isEmpty()) {
            System.out.println("Error: Data MK atau Dosen masih kosong.");
            return;
        }

        // Pilih MK
        System.out.println("--- Pilih Mata Kuliah ---");
        for(int i=0; i < daftarMK.size(); i++) 
            System.out.println((i+1) + ". " + daftarMK.get(i).getNamaMK());
        System.out.print("Pilih No: ");
        int idxMK = in.nextInt() - 1;
        in.nextLine(); 

        // Pilih Dosen 
        ArrayList<Civitas> listDosenOnly = new ArrayList<>();
        for(int i=0; i<daftarCivitas.size(); i++) {
            if(daftarCivitas.get(i).getJenis().equals("DOSEN")) {
                listDosenOnly.add(daftarCivitas.get(i));
            }
        }
        
        System.out.println("--- Pilih Dosen Pengajar ---");
        for(int i=0; i<listDosenOnly.size(); i++) 
            System.out.println((i+1) + ". " + listDosenOnly.get(i).getNama());
        System.out.print("Pilih No: ");
        int idxDosen = in.nextInt() - 1;
        in.nextLine();

        System.out.print("Nama Kelas : ");
        String namaKelas = in.nextLine();
        System.out.print("Tahun: ");
        int tahun = in.nextInt();
        in.nextLine();
        System.out.print("Semester: ");
        int smt = in.nextInt();
        in.nextLine();

        Civitas dosenDipilih = listDosenOnly.get(idxDosen);
        MataKuliah mkDipilih = daftarMK.get(idxMK);
        Kelas kelasBaru = new Kelas(namaKelas, tahun, smt, mkDipilih, dosenDipilih);
        daftarKelas.add(kelasBaru);

        // Tambahkan mahasiswa ke kelas baru 
        System.out.println("\n--- Tambah Mahasiswa ke Kelas ---");
        System.out.println("Ketik 0 untuk selesai");
        while (true) {
            System.out.println("\nDaftar Mahasiswa:");
            for(int i=0; i < daftarMhs.size(); i++) 
                System.out.println((i+1) + ". " + daftarMhs.get(i).getNama() + " (" + daftarMhs.get(i).getNim() + ")");
            System.out.print("Pilih No (0 selesai): ");
            int pilih = in.nextInt();
            in.nextLine();

            if (pilih == 0) break;

            int idxMhs = pilih - 1;
            if (idxMhs >= 0 && idxMhs < daftarMhs.size()) {
                kelasBaru.tambahMahasiswa(daftarMhs.get(idxMhs));
                System.out.println("Mahasiswa " + daftarMhs.get(idxMhs).getNama() + " ditambahkan.");
            } else {
                System.out.println("Nomor tidak valid.");
            }
        }

        System.out.println("Kelas Berhasil Dibuka!");
    }


    public void inputNilai(boolean isAdmin) {
        ArrayList<Kelas> kelasTersedia = new ArrayList<>();
        
        if (isAdmin) {
            // Admin bisa lihat semua kelas
            kelasTersedia = daftarKelas;
        } else {
            // Dosen hanya bisa lihat kelas yang diajar
            for (int i = 0; i < daftarKelas.size(); i++) {
                Kelas k = daftarKelas.get(i);
                if (k.getDosen().getNip().equals(userLogin.getNip())) {
                    kelasTersedia.add(k);
                }
            }
        }

        if (kelasTersedia.isEmpty()) {
            System.out.println("Tidak ada kelas yang tersedia untuk Anda.");
            return;
        }

        System.out.println("\n--- Pilih Kelas ---");
        for (int i = 0; i < kelasTersedia.size(); i++) {
            System.out.println((i + 1) + ". " + kelasTersedia.get(i).getInfoLengkap());
        }
        System.out.print("Pilih No: ");
        int idxKelas = in.nextInt() - 1;
        in.nextLine();
        
        Kelas kelasDipilih = kelasTersedia.get(idxKelas);

        // Tampilkan hanya mahasiswa yang terdaftar di kelas ini
        ArrayList<Mahasiswa> mhsKelas = kelasDipilih.getDaftarMahasiswa();
        
        if (mhsKelas.isEmpty()) {
            System.out.println("Tidak ada mahasiswa di kelas ini.");
            return;
        }

        System.out.println("\n--- Pilih Mahasiswa ---");
        for (int i = 0; i < mhsKelas.size(); i++) {
            System.out.println((i + 1) + ". " + mhsKelas.get(i).getNama() + " (" + mhsKelas.get(i).getNim() + ")");
        }
        System.out.print("Pilih No: ");
        int idxMhs = in.nextInt() - 1;
        in.nextLine();
        
        Mahasiswa m = mhsKelas.get(idxMhs);

        System.out.print("Masukkan Nilai Angka (0-100): ");
        int score = in.nextInt();
        in.nextLine();

        m.tambahGrade(new Grade(m, kelasDipilih.getMataKuliah(), kelasDipilih, score));
        System.out.println("Nilai berhasil disimpan!");
    }

    public void lihatKelasDosen() {
        System.out.println("\n--- Kelas Anda ---");
        boolean found = false;
        for (int i = 0; i < daftarKelas.size(); i++) {
            Kelas k = daftarKelas.get(i);
            if (k.getDosen().getNip().equals(userLogin.getNip())) {
                System.out.println("- " + k.getInfoLengkap());
                found = true;
            }
        }
        if(!found) System.out.println("Belum ada kelas.");
    }


    public void cetakLaporanAdmin() {
        System.out.println("\n--- Pilih Mahasiswa ---");
        for (int i = 0; i < daftarMhs.size(); i++) {
            System.out.println((i + 1) + ". " + daftarMhs.get(i).getNama() + " (" + daftarMhs.get(i).getNim() + ")");
        }
        System.out.print("Pilih No: ");
        int idx = in.nextInt() - 1;
        in.nextLine();
        
        cetakKHS(daftarMhs.get(idx));
    }

    public void lihatKHSMahasiswa() {
        cetakKHS(mhsLogin);
    }

    private void cetakKHS(Mahasiswa m) {
        if (m.getDaftarNilai().isEmpty()) {
            System.out.println("\nMahasiswa ini belum memiliki nilai.");
            return;
        }

        Grade dataPertama = m.getDaftarNilai().get(0);
        Kelas kelasSampel = dataPertama.getKelas();

        System.out.println("\n============================================");
        System.out.println("            KARTU HASIL STUDI (KHS)");
        System.out.println("============================================");
        System.out.println("Nama      : " + m.getNama());
        System.out.println("NIM       : " + m.getNim());
        System.out.println("Tahun     : " + kelasSampel.getTahun());
        System.out.println("Semester  : " + kelasSampel.getSemester());
        System.out.println("--------------------------------------------");
        System.out.printf("%-30s | %-6s | %-3s | %-5s | %-5s\n", "Mata Kuliah", "Kode", "SKS", "Nilai", "Huruf");
        System.out.println("--------------------------------------------");

        double totalMutu = 0;
        int totalSKS = 0;

        for (int i = 0; i < m.getDaftarNilai().size(); i++) {
            Grade g = m.getDaftarNilai().get(i);
            MataKuliah mk = g.getMataKuliah();
            int score = g.getScore();
            GradeLetter letter = toLetter(score);

            System.out.printf("%-30s | %-6s | %-3d | %-5d | %-5s\n", 
                mk.getNamaMK(), mk.getKodeMK(), mk.getSKS(), score, letter);

            totalMutu += (letter.getBobot() * mk.getSKS());
            totalSKS += mk.getSKS();
        }

        System.out.println("--------------------------------------------");
        double ipk = (totalSKS == 0) ? 0 : totalMutu / totalSKS;
        System.out.printf("Total SKS : %d\n", totalSKS);
        System.out.printf("IPK       : %.2f\n", ipk);
        System.out.println("============================================");
    }

    private GradeLetter toLetter(int nilai) {
        if (nilai >= 85) return GradeLetter.A;
        if (nilai >= 70) return GradeLetter.B;
        if (nilai >= 55) return GradeLetter.C;
        if (nilai >= 40) return GradeLetter.D;
        return GradeLetter.E;
    }

    // Data Dummy atau Data Awal
    public void initDataAwal() {
        // Buat User Admin & Dosen
        daftarCivitas.add(new Civitas("admin", "Super Admin", "admin123", "ADMIN"));
        
        Civitas dosen1 = new Civitas("101", "Pak Abas", "dosen123", "DOSEN");
        Civitas dosen2 = new Civitas("102", "Pak Arwan", "dosen123", "DOSEN");
        Civitas dosen3 = new Civitas("103", "Pak Heru", "dosen123", "DOSEN");
        Civitas dosen4 = new Civitas("104", "Pak Agi", "dosen123", "DOSEN");
        Civitas dosen5 = new Civitas("105", "Pak Ridho", "dosen123", "DOSEN");
        Civitas dosen6 = new Civitas("106", "Pak Khalid", "dosen123", "DOSEN");
        Civitas dosen7 = new Civitas("107", "Pak Sigit", "dosen123", "DOSEN");

        daftarCivitas.add(dosen1);
        daftarCivitas.add(dosen2);
        daftarCivitas.add(dosen3);
        daftarCivitas.add(dosen4);
        daftarCivitas.add(dosen5);
        daftarCivitas.add(dosen6);
        daftarCivitas.add(dosen7);

        // Buat Mahasiswa
        Mahasiswa mhs1 = new Mahasiswa("25501", "Albani", "noGloryNoPride");
        Mahasiswa mhs2 = new Mahasiswa("25502", "Afikah", "mhs123");
        Mahasiswa mhs3 = new Mahasiswa("25503", "Najih", "bukankahIniMy");
        
        daftarMhs.add(mhs1);
        daftarMhs.add(mhs2);
        daftarMhs.add(mhs3);

        // Buat Mata Kuliah
        MataKuliah mk1 = new MataKuliah("COM60016", "PKK", 2);
        MataKuliah mk2 = new MataKuliah("COM60014", "PEMDAS", 5);
        MataKuliah mk3 = new MataKuliah("COM60011", "AOK", 3);
        MataKuliah mk4 = new MataKuliah("CIF61101", "RPL", 4);
        MataKuliah mk5 = new MataKuliah("COM60025", "Kalkulus", 2);
        MataKuliah mk6 = new MataKuliah("MPK60001-5", "Agama", 2);
        MataKuliah mk7 = new MataKuliah("CIF61302", "Statistika", 2);

        daftarMK.add(mk1);
        daftarMK.add(mk2);
        daftarMK.add(mk3);
        daftarMK.add(mk4);
        daftarMK.add(mk5);
        daftarMK.add(mk6);
        daftarMK.add(mk7);

        // Buka Kelas 
        Kelas kelas1 = new Kelas("TIF-A", 2025, 1, mk1, dosen1);
        Kelas kelas2 = new Kelas("TIF-B", 2025, 1, mk2, dosen2);
        
        // Tambahkan mahasiswa ke kelas
        kelas1.tambahMahasiswa(mhs1);
        kelas1.tambahMahasiswa(mhs2);
        
        kelas2.tambahMahasiswa(mhs3);
        
        daftarKelas.add(kelas1);
        daftarKelas.add(kelas2);
    }
}