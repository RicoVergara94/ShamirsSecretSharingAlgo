import java.math.BigInteger;

public class PublicKey {
    BigInteger e;
    BigInteger n;

    PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }
}
