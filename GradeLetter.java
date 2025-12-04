public enum GradeLetter {
    A(4.0),
    B(3.0),
    C(2.0),
    D(1.0),
    E(0.0);

    private final double bobot;

    GradeLetter(double bobot) {
        this.bobot = bobot;
    }

    public double getBobot() {
        return bobot;
    }
}