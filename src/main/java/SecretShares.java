import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class SecretShares {

    private ArrayList<SecretShare> secretSharesList = new ArrayList<>();

    private ArrayList<SecretShare> combinedSecretSharesList = new ArrayList<>();

    SecretShares(){}

    public ArrayList<SecretShare> getCombinedSecretSharesList() {
        return combinedSecretSharesList;
    }

    public void addSecretShareToList(SecretShare secretShare) {
        secretSharesList.add(secretShare);
    }

    public ArrayList<SecretShare> getSecretSharesList() {
        return secretSharesList;
    }

    public void createSecretShareTextFiles() throws IOException {

        for(int i = 1; i <= secretSharesList.size(); i++) {
            File file = new File("src/SecretShares/Shard" + i + ".txt");
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            String x = Integer.toString(secretSharesList.get(i-1).getX());
            fileWriter.write(x);
            fileWriter.append("\n");
            fileWriter.write(secretSharesList.get(i-1).getY().toString());
            fileWriter.close();

            if (file.createNewFile()) {
                System.out.println("File created " + file.getName());
            } else System.out.println("File Already exists but overwritten");

        }
    }
    public void extractKeysFromTextFiles(int [] files) throws IOException, FileNotFoundException {

        for (int item : files) {
            SecretShare secretShare = new SecretShare();
            File file = new File("src/SecretShares/Shard" + item + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            int x = Integer.parseInt(line);
            line = bufferedReader.readLine();
            BigInteger y = new BigInteger(line);
            secretShare.setX(x);
            secretShare.setY(y);
            combinedSecretSharesList.add(secretShare);
            bufferedReader.close();
            fileReader.close();
        }
    }


}
