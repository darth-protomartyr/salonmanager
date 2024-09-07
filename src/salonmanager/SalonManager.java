package salonmanager;

import java.util.logging.Level;
import java.util.logging.Logger;
import salonmanager.entidades.bussiness.User;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JFrame;
import salonmanager.persistencia.DAOConfig;

public class SalonManager {

    private static ArrayList<JFrame> framesOpen = new ArrayList<>();
    private static User userIn = new User();
    private static String passIn = "";
    private static final String keySecret = "MySecurePasswordForKeyDerivation"; // Debe ser una contraseña segura
    private static final String salt = "SomeSaltValue"; // Debe ser un valor seguro y único
    private static final SecretKeySpec keySpec = generateSecretKey(keySecret, salt);
    private static DAOConfig daoC = new DAOConfig();
    private static final String ALGORITHM = "AES";
    private static String empty = "";

    public static void main(String[] args) {
        try {
            new LandingFrame();
        } catch (Exception ex) {
            Logger.getLogger(SalonManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUserIn(User userIn) {
        this.userIn = userIn;
    }

    public void setPassIn(String passIn) {
        this.passIn = passIn;
    }

    private static SecretKeySpec generateSecretKey(String password, String salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secretKey = factory.generateSecret(spec);
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la clave AES", e);
        }
    }

    public static String keyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey stringToKey(String keyStr) {
        byte[] decodedKey = Base64.getDecoder().decode(keyStr);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        data = data.trim();
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String encryptDouble(double number) {
        String numberStr = Double.toString(number);
        StringBuilder encrypted = new StringBuilder();
        for (char c : numberStr.toCharArray()) {
            int encryptedValue = (int) c + 1;
            encrypted.append((char) encryptedValue);
        }
        return encrypted.toString();
    }

    public static double decryptDouble(String encryptedString) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedString.toCharArray()) {
            int decryptedValue = (int) c - 1;
            decrypted.append((char) decryptedValue);
        }
        String decryptedStr = decrypted.toString();
        return Double.parseDouble(decryptedStr);
    }

    public static String encryptInt(int number) {
        String numberStr = Integer.toString(number);
        StringBuilder encrypted = new StringBuilder();
        for (char c : numberStr.toCharArray()) {
            int encryptedValue = (int) c + 1;
            encrypted.append((char) encryptedValue);
        }
        return encrypted.toString();
    }

    public static int decryptInt(String encryptedString) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedString.toCharArray()) {
            int decryptedValue = (int) c - 1;
            decrypted.append((char) decryptedValue);
        }
        String decryptedStr = decrypted.toString();
        return Integer.parseInt(decryptedStr);
    }

//    public static SecretKeySpec getKeyFromPassword(String password) throws Exception {
//        MessageDigest sha = MessageDigest.getInstance("SHA-256");
//        byte[] key = password.getBytes("UTF-8");
//        key = sha.digest(key);
//        key = java.util.Arrays.copyOf(key, 16);
//        return new SecretKeySpec(key, "AES");
//    }
    public static String encryptBoolean(boolean value) throws Exception {
        String bool;
        String encrypt = encrypt(keySecret);
        String encrypf = encrypt.substring(0, 15) + "f" + encrypt.substring(16);
        if (value) {
            bool = encrypt;
        } else {
            bool = encrypf;
        }
        return bool;
    }

    public static boolean decryptBoolean(String encryptedValue) throws Exception {
        Boolean bool = null;
        String encrypt = encrypt(keySecret);
        String encrypf = encrypt.substring(0, 15) + "f" + encrypt.substring(16);
        if (encryptedValue.equals(encrypt)) {
            bool = true;
        }
        if (encryptedValue.equals(encrypf)) {
            bool = false;
        }
        return bool;
    }

    public static String encryptTs(Timestamp ts) throws Exception {
        String st = encrypt("null");
        if (ts != null) {
            st = encrypt(ts.toString());
        }
        return st;
    }

    public static Timestamp decryptTs(String encSt) throws Exception {
        Timestamp ts = null;
        if (!encSt.equals(encrypt("null"))) {
            encSt = decrypt(encSt);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                Date parsedDate = dateFormat.parse(encSt);
                ts = new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ts;
    }

    public void salir() throws Exception {
        setPassIn("");
        setUserIn(null);
        JFrame[] ventanasAbiertas = (JFrame[]) JFrame.getFrames();
        if (ventanasAbiertas.length> 0) {
            windowCloser();
        }
    }

    private static void windowCloser() {
        JFrame[] ventanasAbiertas = (JFrame[]) JFrame.getFrames();
        for (JFrame ventana : ventanasAbiertas) {
            ventana.dispose();
        }
    }

    public boolean rolPermission(int auth) throws Exception {
        boolean rol = false;
        String r = userIn.getRol();
        boolean adm = true;
        boolean man = false;
        boolean cash = false;
        switch (auth) {
            case 1:
                adm = true;
                man = false;
                cash = false;
                break;
            case 2:
                adm = true;
                man = true;
                cash = false;
                break;
            case 3:
                adm = true;
                man = true;
                cash = true;
                break;
        }
        if (r.equals("ADMIN")) {
            rol = adm;
        } else if (r.equals("MANAGER")) {
            rol = man;
        } else if (r.equals("CASHIER")) {
            rol = cash;
        }
        return rol;
    }

    public void addFrame(JFrame of) {
        framesOpen.add(of);
    }

    public static void frameCloser() {
        for (JFrame of : framesOpen) {
            of.dispose();
        }
    }

}
