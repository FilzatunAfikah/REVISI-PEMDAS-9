public class Grade {
    private Mahasiswa mahasiswa;
    private MataKuliah mataKuliah;
    private Kelas kelas;
    private int score;

    public Grade(Mahasiswa mahasiswa, MataKuliah mataKuliah, Kelas kelas, int score) {
        this.mahasiswa = mahasiswa;
        this.mataKuliah = mataKuliah;
        this.kelas = kelas;
        this.score = score;
    }

    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public MataKuliah getMataKuliah() { return mataKuliah; }
    public Kelas getKelas() { return kelas; }
    public int getScore() { return score; }
}