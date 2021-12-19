import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


/* Unit test involving these steps:

*   1. Creates the RSA key pair with a Private Key broken into 5 shards.
*   2. Encrypts a random plain text string using the RSA Public Key.
*   3. Reassembles the Private Key using shard 2 &amp; 5.
*   4. Decrypts the cypher text back into the plain text using the reassembled Private Key.
*   5. Asserts the decrypted plain text is equal to the original random plain text in Step 2.
*/



public class UnitTest {

    RSA rsa = new RSA(); // RSA Key Pair Created
    PrivateKey targetPrivateKey = rsa.getPrivateKey();
    Threshold threshold = new Threshold(2,5);
    SecretShares secretShares = new SecretShares();
    Polynomial polynomial = new Polynomial();
    EncryptedString encryptedString;
    int [] chosenKFiles = {2, 5};




    @Test
    void createRSAKeyPairBrokenIntoFiveShards() throws Exception {

        polynomial.addSecret(rsa.getPrivateKey().getD());
        polynomial.addCoefficients(threshold.getK());
        polynomial.addSecretShares(threshold, secretShares); // Private key divided into five shards
        int numberOfShards= secretShares.getSecretSharesList().size();
        assert(rsa != null);
        assertEquals(5, numberOfShards); // Step one complete -- Private key is divided into 5 shares


        String generatedString = generateRandomString(); // A random string is generated within a range of character and with a random length ranging to 32 characters.
        encryptedString = new EncryptedString(generatedString, rsa); // The generated string is encrypted using the public key
        assert(encryptedString.getEncryptedList() != null);
        assert(encryptedString.getEncryptedString() != generatedString); // Step two is complete -- original String is encrypted


        secretShares.createSecretShareTextFiles(); // Separate text files named Shard[k].txt are stored in the "SecretShares" directory, each file carries a share with data
        secretShares.extractKeysFromTextFiles(chosenKFiles); // The 2nd and 5th file's shares are chosen to be used in Lagrange's Basis Interpolation equation
        BigInteger privateKeyD = polynomial.combineSharesToGetSecret(secretShares); // The privateKey secret value (d) is calculated
        PrivateKey reassembledPrivateKey = new PrivateKey(privateKeyD, rsa.publicKey.n); // Adding back the publicly known mod or n value back to private key object for reassembly

        assertEquals(reassembledPrivateKey.getD(), targetPrivateKey.getD()); // Step three is complete -- we have obtained the correct secret value

        DecryptedString decryptedString = new DecryptedString(encryptedString.getEncryptedList(), reassembledPrivateKey); // Step four is complete -- Encrypted String is decrypted back into plain text using reassembled private Key

        assert(decryptedString.getDecryptedMessage().equals(generatedString)); // Step five is complete -- we have asserted that the decrypted plain text is equal to the original random plain text in step 2


    }


    private String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = Math.abs(new Random().nextInt(32));
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
