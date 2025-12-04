import java.util.ArrayList;

public class Kelas {
    private String namaKelas; 
    private int tahun;
    private int semester;
    private MataKuliah mataKuliah;
    private Civitas dosenPengajar;
    private ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();

    public Kelas(String namaKelas, int tahun, int semester, MataKuliah mk, Civitas dosen) {
        this.namaKelas = namaKelas;
        this.tahun = tahun;
        this.semester = semester;
        this.mataKuliah = mk;
        this.dosenPengajar = dosen;
    }

    public String getNamaKelas() { return namaKelas; }
    public int getTahun() { return tahun; }
    public int getSemester() { return semester; }
    public MataKuliah getMataKuliah() { return mataKuliah; }
    public Civitas getDosen() { return dosenPengajar; }

    public String getInfoLengkap() {
        return mataKuliah.getNamaMK() + " - Kls " + namaKelas + " (" + dosenPengajar.getNama() + ")";
    }

    public void tambahMahasiswa(Mahasiswa m) {
        if (!daftarMahasiswa.contains(m)) {
            daftarMahasiswa.add(m);
        }
    }

    public void hapusMahasiswa(Mahasiswa m) {
        daftarMahasiswa.remove(m);
    }

    public ArrayList<Mahasiswa> getDaftarMahasiswa() {
        return daftarMahasiswa;
    }
}