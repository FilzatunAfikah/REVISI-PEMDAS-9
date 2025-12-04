public class Civitas {
    private String nip;
    private String nama;
    private String password;
    private String jenis; 

    public Civitas(String nip, String nama, String password, String jenis) {
        this.nip = nip;
        this.nama = nama;
        this.password = password;
        this.jenis = jenis;
    }

    public String getNip() { return nip; }
    public String getNama() { return nama; }
    public String getJenis() { return jenis; }

    public boolean login(String passwordInput) {
        return this.password.equals(passwordInput);
    }
}