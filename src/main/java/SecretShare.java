import java.math.BigInteger;

public class SecretShare {
    private int x;
    private BigInteger y;

    SecretShare() {}

    SecretShare(int x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getY() {
        return y;
    }
}
