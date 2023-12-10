package salonmanager;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import salonmanager.entidades.User;
import salonmanager.entidades.Workshift;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JFrame;
import salonmanager.entidades.Config;
import salonmanager.entidades.Session;
import salonmanager.persistencia.DAOConfig;

public class SalonManager {
    private static ArrayList<JFrame> framesOpen = new ArrayList<JFrame>();
    private static Salon salon = null;
    private static User userIn = new User();
    private static String passIn = "";
    private static final String SECRET_KEY = "HappyWhenItRains";
    private static Workshift workshiftActual = null;
    private static Session sessionActual = null;
    private static DAOConfig daoC = new DAOConfig();
//  FrameGeneral fg = new FrameGeneral();
    
    Color bluSt = new Color(3, 166, 136);
    Color narSt = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    Config cfg = new Config();
    
    public static void main(String[] args) {
        try {
            new LandingFrame();
        } catch (Exception ex) {
            Logger.getLogger(SalonManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUserIn() {
        return userIn;
    }

    public void setUserIn(User userIn) {
        this.userIn = userIn;
    }

    public String getPassIn() {
        return passIn;
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
        new LandingFrame();
    }

    private static void windowCloser() {
        JFrame[] ventanasAbiertas = (JFrame[]) JFrame.getFrames();
        for (JFrame ventana : ventanasAbiertas) {
            ventana.dispose();
        }
    }

    public boolean rolPermission(int i) throws Exception {
        boolean rol = false;
        String r = userIn.getRol();
        if (userIn.getRol() != "CAJERO") {
            if (r.equals("ADMIN")) {
                rol = true;
            }
            if (r.equals("MANAGER") && i == 2) {
                rol = true;
            }    
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
    
    public void workshiftBacker(Workshift ws) {
        workshiftActual = ws;
    }
    
    public void workshiftEraser() {
        workshiftActual = null;
    }
    
    public void sessionBacker(Session ses) {
        sessionActual = ses;
    }
    
    public void sessionEraser() {
        sessionActual = null;
    }
    
    public Config getConfig() throws Exception {
        cfg = daoC.consultarConfig();
        return cfg;
    }
}