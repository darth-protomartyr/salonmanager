package salonmanager.utilidades;

import static java.lang.Integer.parseInt;
import salonmanager.entidades.User;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import salonmanager.entidades.ItemCarta;

public class Utilidades {

    public double toNumberD(String str) {
        double d = -1;
        if (str != "") {
            d = Double.parseDouble(str);
        }
        if (str == "") {
            d = 0;
        }
        return d;
    }

    public int toNumberI(String str) {
        int i = -1;
        if (str != "") {
            i = Integer.parseInt(str);
        }
        if (str == "") {
            i = 0;
        }
        return i;
    }

    public double doubleShorter(double d, int zeros) {
        int z = 1;
        int num = 10;
        while (z < zeros) {
            num *= 10;
            z += 1;
        }
        double sh = Math.round(d * num);
        sh = sh / num;
        return sh;
    }

    public User userSelReturn(String usr, ArrayList<User> userDB) {
        User user = new User();
        for (User u : userDB) {
            if (usr.equals(u.getNombre() + " " + u.getApellido())) {
                user = u;
            }
        }
        return user;
    }

    public User userSelReturnAlta(String usr, ArrayList<User> userDB) {
        User user = new User();
        for (User u : userDB) {
            if (usr.equals(u.getNombre() + "(" + u.isAlta() + ")")) {
                user = u;
            }
        }
        return user;
    }

    public String strShorter(String str, int limite) {
        String st = "";
        if (str.length() > limite) {
            st = str.substring(0, (limite - 1)) + ".";
        } else {
            st = str;
        }
        return st;
    }

    public <T> String conversorCadena(ArrayList<T> lista) {
        String listaStr = "";
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                String str = lista.get(i).toString();
                listaStr += str + "-";
            }
        } else {
            listaStr = "";
        }
        return listaStr.trim();
    }

    public String stringPlain(String st) {
        st.replaceAll("\\s", "");
        st.toLowerCase();
        return st;
    }

    public double reductorDecimales(double costo, int f) {
        String cost = "" + costo;
        String c = "";
        int counter = 0;
        boolean coma = false;
        for (int i = 0; i < cost.length(); i++) {
            String num = String.valueOf(cost.charAt(i));
            if (coma == true && counter < f) {
                c += num;
                counter++;
            }
            if (num.equals(".")) {
                coma = true;
            }
        }
        costo = Double.parseDouble(c);
        return costo;
    }

    public String mayusculaInicial(String string) {
        String str = string.substring(0, 1).toUpperCase();
        str += string.substring(1, string.length());
        return str;
    }

    public String idRandom() {
        String id = "";
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        id = randomString.toString();
        return id;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String barrReplace(String absolutePath) {
        String path = absolutePath;
        path = path.replace("\\", "|");
        return path;
    }

    public String barrReplaceInverse(String absolutePath) {
        String path = absolutePath;
        path = path.replace("|", "\\");
        return path;
    }

    public DefaultListModel<String> usersListModelReturn(ArrayList<User> listMayor, ArrayList<User> listMenor, boolean mail) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<User> lma = listMayor;
        ArrayList<User> lme = listMenor;
        if (lme != null) {
            Iterator<User> iterator = listMayor.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                if (listMenor.contains(user)) {
                    iterator.remove();
                }

                if (user.getRol().equals("ADMIN")) {
                    iterator.remove();
                }
            }
        }

        if (mail) {
            for (User u : lma) {
                modeloLista.addElement(u.getMail());
            }
        } else {
            for (User u : lma) {
                modeloLista.addElement(u.getNombre() + "(" + u.isAlta() + ")");
            }
        }
        return modeloLista;
    }

    public ArrayList<String> captionList() {
        ArrayList<String> st = new ArrayList<String>();
        String a = "BEBIDAS";
        String b = "PLATOS";
        String c = "CAFETERIA";
        String d = "OTROS";
        st.add(a);
        st.add(b);
        st.add(c);
        st.add(d);
        return st;
    }

    public ComboBoxModel captionComboModelReturn(ArrayList<String> captionsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (String i : captionsDB) {
            modeloCombo.addElement(i);
        }
        return modeloCombo;
    }

    public ComboBoxModel itemsComboModelReturnWNull(ArrayList<ItemCarta> itemsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (ItemCarta ic : itemsDB) {
            modeloCombo.addElement(ic.getName());
        }
        modeloCombo.addElement("");
        return modeloCombo;
    }

    public ComboBoxModel itemsComboModelReturn(ArrayList<ItemCarta> itemsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (ItemCarta ic : itemsDB) {
            modeloCombo.addElement(ic.getName());
        }
        modeloCombo.addElement("");
        return modeloCombo;
    }

    public ComboBoxModel userComboModelReturn(ArrayList<User> users) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (User user : users) {
            modeloCombo.addElement(user.getNombre() + " " + user.getApellido());
        }
        return modeloCombo;
    }

    public String selectorCaption(int comboC) {
        String caption = "";
        switch (comboC) {
            case 0:
                caption = "BEBIDAS";
                break;
            case 1:
                caption = "PLATOS";
                break;
            case 2:
                caption = "CAFETERIA";
                break;
            case 3:
                caption = "OTROS";
                break;
        }
        return caption;
    }

    public boolean itemCartaRepeat(String ic, ArrayList<ItemCarta> items, ItemCarta itemCarta) {
        boolean bool = false;
        ic = stringPlain(ic);
        for (ItemCarta i : items) {
            if (itemCarta == null) {
                if (ic.equals(stringPlain(i.getName()))) {
                    bool = true;
                }
            }
        }
        return bool;
    }

    ListModel itemsListModelReturn(ArrayList<ItemCarta> listMayor, ArrayList<ItemCarta> listMenor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<ItemCarta> lma = listMayor;
        ArrayList<ItemCarta> lme = listMenor;
        if (lme != null) {
            Iterator<ItemCarta> iterator = lma.iterator();
            while (iterator.hasNext()) {
                ItemCarta ic = iterator.next();
                for (int i = 0; i < lme.size(); i++) {
                    if (ic.getId() == lme.get(i).getId()) {
                        iterator.remove();
                    }
                }
            }
        }

        for (ItemCarta i : lma) {
            modeloLista.addElement(i.getName());
        }
        return modeloLista;
    }

    public ItemCarta itemSelReturn(String item, ArrayList<ItemCarta> itemsDB) {
        ItemCarta ic = null;
        for (ItemCarta i : itemsDB) {
            if (item.equals(i.getName())) {
                ic = i;
            }
        }
        return ic;
    }

    public String booleanStringBack(boolean altaTip) {
        String yn = "NO";
        if (altaTip) {
            yn = "SÍ";
        }
        return yn;
    }

    public String arrayIntToStr(ArrayList<Integer> numTab) {
        String str = "";
        for (Integer i : numTab) {
            str += i + "-";
        }
        return str;
    }

    public ArrayList<Integer> strToArrayInt(String str) {
        ArrayList<Integer> arrayInt = new ArrayList<Integer>();
        String strAux = "";
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (s.equals("-")) {
                int num = parseInt(strAux);
                arrayInt.add(num);
                strAux = "";
            } else {
                strAux += s;
            }
        }
        return arrayInt;
    }

    public String arrayStrToStr(ArrayList<String> strPane) {
        String str = "";
        for (String s : strPane) {
            str += s + "-";
        }
        return str;
    }

    public ArrayList<String> strToArrayStr(String str) {
        ArrayList<String> arrayStr = new ArrayList<String>();
        String strAux = "";
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (s.equals("-")) {
                arrayStr.add(strAux);
                strAux = "";
            } else {
                strAux += s;
            }
        }
        return arrayStr;
    }

    public ItemCarta itemCartaBacker(String carta, ArrayList<ItemCarta> itemsDB) {
        ItemCarta ic = null;
        for (ItemCarta i : itemsDB) {
            if (i.getName().equals(carta)) {
                ic = i;
            }
        }
        return ic;
    }

    public ListModel itemListModelReturn(ArrayList<ItemCarta> listMayor, ArrayList<ItemCarta> listMenor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<ItemCarta> lma = listMayor;
        ArrayList<ItemCarta> lme = listMenor;
        if (lme != null) {
            Iterator<ItemCarta> iterator = lma.iterator();
            while (iterator.hasNext()) {
                ItemCarta ingre = iterator.next();
                for (int i = 0; i < lme.size(); i++) {
                    if (ingre.getId() == lme.get(i).getId()) {
                        iterator.remove();
                    }
                }
            }
        }

        for (ItemCarta ic : lma) {
            modeloLista.addElement(ic.getName());
        }
        return modeloLista;
    }

    public ListModel itemListModelReturnMono(ArrayList<ItemCarta> listMayor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<ItemCarta> lma = listMayor;
        for (ItemCarta ic : lma) {
            modeloLista.addElement(ic.getName());
        }
        return modeloLista;
    }

    public boolean requiredPerm(char[] pass) {
        boolean perm = false;
        String p;
        if (pass == null) {
            p = "";
        } else {
            p = new String(pass);
        }
        String w = "papa";
        if (p.equals(w)) {
            perm = true;
        }
        return perm;
    }

    public String friendlyDate(Timestamp timeInitSes) {
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date(timeInitSes.getTime());
        time = dateFormat.format(date);
        return time;
    }

    public String stringMsgFrd(String st) {
        String stFrd = "";
        String jump = "<br><br>";
        int limit = 0;
        
        if (st.length() <= 25) {
            limit = 25; 
        } else if (st.length() >= 25)  {
            limit = 15;
        }
        
        if (st.length() > 60) {
            jump = "<br>";
        }
        
        int counter = 0;
        for (int i = 0; i < st.length(); i++) {
            String letra = st.substring(i, i + 1);
            stFrd = stFrd + letra;
            counter += 1;
            if (counter >= limit) {
                if (letra.equals(" ")) {
                    stFrd += jump;
                    counter = 0;
                }
            }
        }
        
        stFrd = "<html>" + stFrd + "</html>";
        return stFrd;
    }
}
