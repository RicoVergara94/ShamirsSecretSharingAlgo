import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Polynomial {
    BigInteger prime = BigInteger.probablePrime(32, new Random());
    boolean secretAdded = false;
    ArrayList <BigInteger> polynomialList = new ArrayList<>();
    private BigInteger secret;


    Polynomial(){};

    void addSecret(BigInteger secret) {
        if (!secretAdded) {
            this.secret = secret;
            polynomialList.add(secret);
            secretAdded = true;
        }
    }

    void addCoefficients(int minimumSharesNeeded) {
        if (secretAdded) {
            for(int i = 0; i < minimumSharesNeeded; i++) {
                BigInteger coefficient;
                while(true) {

                    coefficient = new BigInteger(prime.bitLength(), new Random());

                    if(coefficient.compareTo(BigInteger.ZERO) > 0 && coefficient.compareTo(prime) < 0) break;

                }
                polynomialList.add(coefficient);
            }
        }
    }

    void addSecretShares(Threshold threshold, SecretShares secretShares) {
        for(int x = 1; x <= threshold.getN(); x++) {

            BigInteger accum = secret;

            for (int j = 1; j < threshold.getK(); j++) {
                accum = accum.add(polynomialList.get(j).multiply(BigInteger.valueOf(x).pow(j).mod(prime)));
            }
            SecretShare secretShare = new SecretShare(x, accum);
            secretShares.addSecretShareToList(secretShare);
        }

    }

    public BigInteger combineSharesToGetSecret(SecretShares secretShares) {
        BigInteger accum = BigInteger.ZERO;

        for(int formula = 0; formula < secretShares.getCombinedSecretSharesList().size(); formula++)
        {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for(int count = 0; count < secretShares.getCombinedSecretSharesList().size(); count++)
            {
                if(formula == count)
                    continue;

                int startposition = secretShares.getCombinedSecretSharesList().get(count).getX();
                int nextposition = secretShares.getCombinedSecretSharesList().get(formula).getX();

                numerator = numerator.multiply(new BigInteger("" + startposition));

                denominator = denominator.multiply(new BigInteger("" + startposition).subtract(new BigInteger("" + nextposition)));
            }
            BigInteger value = secretShares.getCombinedSecretSharesList().get(formula).getY();
            BigInteger tmp = value.multiply(numerator).divide(denominator);
            accum = accum.add(tmp);
        }



        return accum;
    }






}
