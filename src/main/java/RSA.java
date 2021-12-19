import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private PrivateKey privateKey;
    public PublicKey publicKey;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSA() {
        int publicKey = 2;
        Random random1 = new Random();
        Random random2 = new Random();
        BigInteger p = BigInteger.probablePrime(32, random1);
        BigInteger q = BigInteger.probablePrime(32, random2);
        BigInteger n = p.multiply(q);
        BigInteger pMinusOne = p.subtract(new BigInteger("1"));
        BigInteger qMinusOne = q.subtract(new BigInteger("1"));
        BigInteger m = pMinusOne.multiply(qMinusOne);
        while(true) {
            BigInteger GCD = m.gcd(new BigInteger("" + publicKey));
            if (GCD.equals(BigInteger.ONE)) break;
            publicKey++;
        }
        BigInteger bigPublicKey = new BigInteger("" + publicKey);
        this.publicKey = new PublicKey(bigPublicKey, n);
        BigInteger bigPrivateKey = new BigInteger("" + bigPublicKey.modInverse(m));
        this.privateKey = new PrivateKey(bigPrivateKey, n);
    }


}
