package salonmanager;

import java.util.logging.Level;
import java.util.logging.Logger;
import salonmanager.entidades.bussiness.User;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JFrame;
import salonmanager.persistencia.DAOConfig;

public class SalonManager {
    private static ArrayList<JFrame> framesOpen = new ArrayList<>();
    private static User userIn = new User();
    private static String passIn = "";
    private static final String SECRET_KEY = "HappyWhenItRains";
    private static DAOConfig daoC = new DAOConfig();
    
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

    //Encriptamineto
    public static String encrypt(String data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
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

    public static String encryptInteger(int number) {
        String numberStr = Integer.toString(number);
        StringBuilder encrypted = new StringBuilder();
        for (char c : numberStr.toCharArray()) {
            int encryptedValue = (int) c + 1; // Shift each character's ASCII value by 1
            encrypted.append((char) encryptedValue);
        }
        return encrypted.toString();
    }

    public static int decryptInteger(String encryptedString) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedString.toCharArray()) {
            int decryptedValue = (int) c - 1; // Reverse the shift from encryption
            decrypted.append((char) decryptedValue);
        }
        String decryptedStr = decrypted.toString();
        return Integer.parseInt(decryptedStr);
    }

    public void salir() throws Exception {
        setPassIn("");
        setUserIn(null);
        windowCloser();       
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
        } else if (r.equals("CASHIER")){
            rol = cash;
        }
        
        return rol;
    }


    public void addFrame(JFrame of) {
        framesOpen.add(of);
    }
    
    public void frameCloser() {
        for (JFrame of : framesOpen) {
            of.dispose();
        }
    }   
}