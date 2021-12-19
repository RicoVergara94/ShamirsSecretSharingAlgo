import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EncryptedString {
    private ArrayList<BigInteger> encryptedList = new ArrayList<>();
    private String encryptedString = "";

    EncryptedString(String message, RSA rsa) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

        for(int i = 0; i < message.length(); i++) {
            int asciiVal = bytes[i];
            BigInteger val = new BigInteger("" + asciiVal);
            BigInteger encryptedVal = val.modPow(rsa.publicKey.e, rsa.publicKey.n);
            encryptedString += encryptedVal.toString();
            encryptedList.add(encryptedVal);
        }
    }

    public ArrayList<BigInteger> getEncryptedList() {
        return encryptedList;
    }

    public String getEncryptedString() {
        return encryptedString;
    }
}
