public class Threshold {
    private int n = 0;
    private int k = 0;

    Threshold(){}

    Threshold(int k, int n) {
        this.k = k;
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setK(int k) {
        this.k = k;
    }
}
