import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the number of shares or n you would like the private key divided into.");
        int n = userInput.nextInt();
        if (n < 0) {
            System.out.println("n must be a positive integer.");
            System.out.println("Please enter the number of shares or n you would like the private key divided into.");
            n = userInput.nextInt();
        }
        System.out.println("Please enter the minimum number of shares or k it would require to acquire private key.");
        int k = userInput.nextInt();
        if (k < 0 || k > n) {
            System.out.println("k must be a positive integer and be less than " + n + " .");
            System.out.println("Please enter the minimum number of shares or k it would require to acquire private key.");
            k = userInput.nextInt();
        }
        Threshold threshold = new Threshold(k, n);

        RSA rsa = new RSA();

        File publicKeyFile = new File("src/Public Key/PublicKey.txt");
        FileWriter fileWriter = new FileWriter(publicKeyFile.getAbsoluteFile());
        String publicKey = rsa.publicKey.n.toString();
        fileWriter.write("n: ");
        fileWriter.write(publicKey);
        fileWriter.append("\n");
        publicKey = rsa.publicKey.e.toString();
        fileWriter.write("e: ");
        fileWriter.write(publicKey);
        fileWriter.close();

        SecretShares secretShares = new SecretShares();

        Polynomial polynomial = new Polynomial();
        polynomial.addSecret(rsa.getPrivateKey().getD());
        polynomial.addCoefficients(threshold.getK());
        polynomial.addSecretShares(threshold, secretShares);

        secretShares.createSecretShareTextFiles();

        int [] chosenKFiles = new int[threshold.getK()];

        System.out.println("Please enter the digit corresponding to the text files " +
                "for which to combine the shares to retrieve the secret key using Shamir's secret sharing algorithm." +
                " (For example entering 1 will select the text file Shard1.txt");

        int fileNumber = userInput.nextInt();
        chosenKFiles[0] = fileNumber;

        for (int i = 1; i < threshold.getK(); i++) {
            System.out.println("Please enter the next digit");
            fileNumber = userInput.nextInt();
            chosenKFiles[i] = fileNumber;
        }


        secretShares.extractKeysFromTextFiles(chosenKFiles);

        BigInteger privateKeyD = polynomial.combineSharesToGetSecret(secretShares);
        PrivateKey targetPrivateKey = new PrivateKey(privateKeyD, rsa.publicKey.n);

        if(rsa.getPrivateKey().getD().equals(targetPrivateKey.getD())) {
            System.out.println("Shamir's Algorithm implementation was a success, you've managed to retrieve your RSA" +
                    "private key using the minimum shares.");
        }
        else {
            System.out.println("The application failed.");
        }


        System.out.println("Please enter message to encrypt and decrypt.");
        String message = userInput.next();
        userInput.close();
        EncryptedString encryptedString = new EncryptedString(message, rsa);
        DecryptedString decryptedString = new DecryptedString(encryptedString.getEncryptedList(), targetPrivateKey);


        if(decryptedString.getDecryptedMessage().equals(message)) {
            System.out.println("Message was successfully encrypted and decrypted!");
        }
        else {
            System.out.println("Message was unsuccessfully decrypted.");
        }
    }

}
