import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class DecryptedString {
    private String decryptedMessage = "";
    private ArrayList <Integer> decryptedList = new ArrayList<>();

    DecryptedString(ArrayList<BigInteger> encryptedList, PrivateKey privateKey) {
        for(BigInteger item : encryptedList) {
            BigInteger decryptedVal = item.modPow(privateKey.getD(), privateKey.getN());
            int intDecryptedVal = decryptedVal.intValue();
            this.decryptedList.add(intDecryptedVal);
            this.decryptedMessage += Character.toString((char)intDecryptedVal);
        }
    }

    public String getDecryptedMessage() {
        return decryptedMessage;
    }

    public ArrayList<Integer> getDecryptedList() {
        return decryptedList;
    }
}
