import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class HashingExample {

    public static void main(String[] args)throws NoSuchAlgorithmException, NoSuchProviderException {
        // A static String for the example
        String passwordToHash = "myPassword";

        // Create a salt
        byte[] salt = createSalt();

        // Create a hash
        String securePassword = get_SecurePassword(passwordToHash, salt);
        System.out.println(securePassword);
    }

    // Method to generate a Salt
    private static byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String get_SecurePassword(String passwordToHash, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
