package salonmanager.utilidades;

import static java.lang.Integer.parseInt;
import salonmanager.entidades.bussiness.User;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.config.ConfigGeneral;

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
            if (usr.equals(u.getName() + " " + u.getLastName())) {
                user = u;
            }
        }
        return user;
    }

    public Table tabSelReturn(String tabStr, ArrayList<Table> tabs) {
        Table tab = new Table();
        for (Table t : tabs) {
            if (tabStr.equals(t.getId())) {
                tab = t;
            }
        }
        return tab;
    }

    public User userSelReturnAlta(String usr, ArrayList<User> userDB) {
        User user = new User();
        for (User u : userDB) {
            if (usr.equals(u.getName() + "(" + u.isActiveUser() + ")")) {
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
                modeloLista.addElement(u.getName() + "(" + u.isActiveUser() + ")");
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

    public ComboBoxModel itemsComboModelReturnWNull(ArrayList<Itemcard> itemsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (Itemcard ic : itemsDB) {
            modeloCombo.addElement(ic.getName());
        }
        modeloCombo.addElement("");
        return modeloCombo;
    }

    public ComboBoxModel itemsComboModelReturn(ArrayList<Itemcard> itemsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (Itemcard ic : itemsDB) {
            modeloCombo.addElement(ic.getName());
        }
        modeloCombo.addElement("");
        return modeloCombo;
    }

    public ComboBoxModel userComboModelReturnWNull(ArrayList<User> users) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (User user : users) {
            modeloCombo.addElement(user.getName() + " " + user.getLastName());
        }
        modeloCombo.addElement("");
        return modeloCombo;
    }

    public ComboBoxModel tableComboModelReturn(ArrayList<Table> tabs) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (Table tab : tabs) {
            modeloCombo.addElement(tab.getId());
        }
        return modeloCombo;
    }

    public ComboBoxModel consumerComboModelReturnWNull(ArrayList<String> cmrs) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (String cmr : cmrs) {
            modeloCombo.addElement(cmr);
        }
        modeloCombo.addElement("");
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

    public boolean itemcardRepeat(String ic, ArrayList<Itemcard> items) {
        boolean bool = false;
        ic = stringPlain(ic);
        for (Itemcard i : items) {
            if (i == null) {
                if (ic.equals(stringPlain(i.getName()))) {
                    bool = true;
                }
            }
        }
        return bool;
    }

    public boolean stringRepeat(String phone, ArrayList<String> phones) {
        boolean bool = false;
        phone = stringPlain(phone);
        for (String ph : phones) {
            if (!phone.equals("")) {
                if (ph.equals(phone)) {
                    bool = true;
                }
            }
        }
        return bool;
    }

    ListModel itemsListModelReturn(ArrayList<Itemcard> listMayor, ArrayList<Itemcard> listMenor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<Itemcard> lma = listMayor;
        ArrayList<Itemcard> lme = listMenor;
        if (lme != null) {
            Iterator<Itemcard> iterator = lma.iterator();
            while (iterator.hasNext()) {
                Itemcard ic = iterator.next();
                for (int i = 0; i < lme.size(); i++) {
                    if (ic.getId() == lme.get(i).getId()) {
                        iterator.remove();
                    }
                }
            }
        }

        for (Itemcard i : lma) {
            modeloLista.addElement(i.getName());
        }
        return modeloLista;
    }

    public Itemcard itemSelReturn(String item, ArrayList<Itemcard> itemsDB) {
        Itemcard ic = null;
        for (Itemcard i : itemsDB) {
            if (item.equals(i.getName())) {
                ic = i;
            }
        }
        return ic;
    }

    public String booleanStringBack(boolean activeTip) {
        String yn = "NO";
        if (activeTip) {
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

    public String emptyToStr(String stEmtSp) {
        StringBuilder str = new StringBuilder();
        // Recorrer cada caracter de la cadena
        for (int i = 0; i < stEmtSp.length(); i++) {
            char caracter = stEmtSp.charAt(i);

            // Reemplazar espacio por el caracter especificado
            if (caracter == ' ') {
                str.append("_");
            } else {
                str.append(caracter);
            }
        }

        return str.toString();
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

    public Itemcard ItemcardBacker(String Card, ArrayList<Itemcard> itemsDB) {
        Itemcard ic = null;
        for (Itemcard i : itemsDB) {
            if (i.getName().equals(Card)) {
                ic = i;
            }
        }
        return ic;
    }

    public ListModel itemListModelReturn(ArrayList<Itemcard> listMayor, ArrayList<Itemcard> listMenor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<Itemcard> lma = listMayor;
        ArrayList<Itemcard> lme = listMenor;
        if (lme != null) {
            Iterator<Itemcard> iterator = lma.iterator();
            while (iterator.hasNext()) {
                Itemcard ingre = iterator.next();
                for (int i = 0; i < lme.size(); i++) {
                    if (ingre.getId() == lme.get(i).getId()) {
                        iterator.remove();
                    }
                }
            }
        }

        for (Itemcard ic : lma) {
            modeloLista.addElement(ic.getName());
        }
        return modeloLista;
    }

    public ListModel itemListModelReturnMono(ArrayList<Itemcard> listMayor) {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        ArrayList<Itemcard> lma = listMayor;
        for (Itemcard ic : lma) {
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

    public String friendlyDate1(Timestamp timeInitSes) {
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date(timeInitSes.getTime());
        time = dateFormat.format(date);
        return time;
    }

    public String friendlyDate2(Timestamp timeInitSes) {
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date date = new Date(timeInitSes.getTime());
        time = dateFormat.format(date);
        return time;
    }

    public String friendlyDate3(Timestamp timeInitSes) {
        String time = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date(timeInitSes.getTime());
        time = dateFormat.format(date);
        return time;
    }

    public String stringMsgFrd(String st, int lim, int j) {
        String stFrd = "";
        String jump = "";
        if (j == 1) {
            jump = "<br>";
        } else {
            jump = "<br><br>";
        }
        int limit = lim;
        int counter = 0;
        int counterEmpty = 0;

        if (st.length() > limit) {
            for (int i = 0; i < st.length(); i++) {
                String letra = st.substring(i, i + 1);
                if (letra.equals(" ")) {
                    counterEmpty = i;
                }

                if (counter >= limit && counter <= limit + 10) {
                    if (letra.equals(" ")) {
                        letra = letra.replace(letra, jump);
                        counter = 0;
                    }
                }

                if (counter >= limit + 10) {
                    String trash = stFrd.substring(counterEmpty, stFrd.length());
                    letra = letra.replace(trash, jump);
                    counter = 0;
                }
                stFrd = stFrd + letra;
                counter++;
            }
        } else {
            stFrd = st;
        }
        stFrd = "<html>" + stFrd + "</html>";
        return stFrd;
    }

    public String labelStOposite(String left, String right) {
        String labelText = "<html><div style='display: inline-block; text-align: left;'>" + left + "</div><div style=' display: inline-block; text-align: right;'>" + right + "</div></html>";
        return labelText;
    }

    public String listarItems(Table ta) {
        String st = "";
        st += "LISTA TOTAL DE ITEMS PEDIDOS";
        ArrayList<Itemcard> listOr = ta.getOrder();
        if (listOr.size() > 0) {
            st += listarItemsQuant(listOr);
        }

        st += "<br>LISTA DE ITEMS OBSEQUIADOS";
        if (ta.getGifts().size() > 0) {
            st += listarItemsQuant(ta.getGifts());
        } else {
            st += "<br>No se registraron items obsequiados.<br>";
        }

        if (ta.getComments().equals("")) {
            st += "<br>COMENTARIOS:<br>" + "No se registraron.";
        } else {
            st += "<br>COMENTARIOS:" + stringMsgFrd(ta.getComments(), 30, 1);
        }

        st = "<html>" + st + "</html>";
        return st;
    }

    public String listarItemsQuant(ArrayList<Itemcard> ali) {
        String st = "<br>";
        int counter = 0;
        String ref = "";
        ArrayList<String> iSt = new ArrayList<String>();
        for (Itemcard ic : ali) {
            iSt.add(ic.getName());
        }

        iSt.sort((s1, s2) -> s1.compareTo(s2));

        HashSet<String> itemsSt = new HashSet<String>(iSt);

        iSt = new ArrayList<String>(itemsSt);
        ArrayList<Integer> cant = new ArrayList<Integer>();

        counter = 0;
        for (String icSt : iSt) {
            for (int i = 0; i < ali.size(); i++) {
                if (icSt.equals(ali.get(i).getName())) {
                    counter++;
                }
            }
            cant.add(counter);
            counter = 0;
        }

        for (int i = 0; i < itemsSt.size(); i++) {
            st += iSt.get(i) + " - cant: " + cant.get(i) + "<br>";
        }
        return st;
    }

    public String friendlyHour(Timestamp tsp) {
        String hour = "";
        Timestamp ts = tsp;

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        String horaFormateada = formatoHora.format(ts);

        hour = "Hora: " + horaFormateada;
        return hour;
    }

    ArrayList<Itemcard> unRepeatItems(ArrayList<Itemcard> items) {
        ArrayList<Itemcard> unRepeatItems = new ArrayList<Itemcard>();
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for (Itemcard item : items) {
            boolean repeat = false;
            for (Integer inte : ints) {
                if (item.getId() == inte) {
                    repeat = true;
                    break;
                }
            }
            if (repeat == false) {
                ints.add(item.getId());
                unRepeatItems.add(item);
            }
        }
        return unRepeatItems;
    }

    public String getTabPos(String itemSaleTabPos) throws Exception {
        String tabPos = itemSaleTabPos;
        if (tabPos.equals("tab")) {
            tabPos = "Mesa";
        }

        if (tabPos.equals("barra")) {
            tabPos = "Barra";
        }

        if (tabPos.equals("delivery")) {
            tabPos = "Delivery";
        }

        return tabPos;
    }

    public String reduxSt(String n, int k) {
        String name = n;
        int limit = 20;
        String[] words = name.split(" ");
        int length = words.length;

        int w1 = 7;
        int w2 = 10;
        int w3 = 3;
        int w4 = 3;
        
        if (length == 3) {
            w1 = 5;
            w2 = 5; 
            w3 = 5;
        } else if(length == 4) {
            w1 = 1;
            w2 = 1; 
            w3 = 1;
            w4 = 10;
        }

        if (k == 2 ) {
            limit = 8;

            w1 = 1;
            w2 = 4; 
            w3 = 1;
            w4 = 1;
            
            if (length > 2) {
                w2 = 1;
            }   
        }

        if (name.length() > limit) {
            String firstWord = words[0];
            if (firstWord.length() > w1) {
                firstWord = firstWord.substring(0, w1) + ".";
            }
            words[0] = firstWord;

            if (length > 1) {
                String secondWord = words[1];
                if (secondWord.length() > 4) {
                    secondWord = secondWord.substring(0, w2) + ".";
                }
                words[1] = secondWord;
            }

            if (length > 2) {
                String thirdWord = words[2];
                if (thirdWord.length() > 4) {
                    thirdWord = thirdWord.substring(0, w3) + ".";
                }
                words[3] = thirdWord;
            }
            
            if (length > 3) {
                String thirdWord = words[3];
                if (thirdWord.length() > 4) {
                    thirdWord = thirdWord.substring(0, w4) + ".";
                }
                words[3] = thirdWord;
            }
            
            name = String.join(" ", words); 
        }
        
        return name;
    }
}
