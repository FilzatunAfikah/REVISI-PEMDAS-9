import java.util.ArrayList;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String password;
    private ArrayList<Grade> daftarNilai = new ArrayList<>();

    public Mahasiswa(String nim, String nama, String password) {
        this.nim = nim;
        this.nama = nama;
        this.password = password;
    }

    public void tambahGrade(Grade nilai) {
        daftarNilai.add(nilai);
    }

    public boolean login(String inputPass) {
        return this.password.equals(inputPass);
    }

    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public ArrayList<Grade> getDaftarNilai() { return daftarNilai; }
}