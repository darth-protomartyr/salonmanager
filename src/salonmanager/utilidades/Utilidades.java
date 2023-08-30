package salonmanager.utilidades;


import salonmanager.entidades.User;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

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

//    public DefaultComboBoxModel<String> rubComboModelReturn(ArrayList<Rubro> rubs) {
//        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
//        for (Rubro r : rubs) {
//            modeloCombo.addElement(r.getNombre());
//        }
//        return modeloCombo;
//    }

//    public DefaultComboBoxModel<String> ingreComboModelReturn(ArrayList<Ingrediente> listMayor, ArrayList<Ingrediente> listMenor) {
//        ArrayList<Ingrediente> lma = listMayor;
//        ArrayList<Ingrediente> lme = listMenor;
//        if (lme != null) {
//            Iterator<Ingrediente> iterator = lma.iterator();
//            while (iterator.hasNext()) {
//                Ingrediente ingre = iterator.next();
//                if (lme.contains(ingre)) {
//                    iterator.remove();
//                }
//            }
//        }
//        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
//        for (Ingrediente i : lma) {
//            modeloCombo.addElement(i.getNombre());
//        }
//        return modeloCombo;
//    }





//    public DefaultListModel<String> ingreListModelReturn(ArrayList<Ingrediente> listMayor, ArrayList<Ingrediente> listMenor) {
//        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
//        ArrayList<Ingrediente> lma = listMayor;
//        ArrayList<Ingrediente> lme = listMenor;
//        if (lme != null) {
//            Iterator<Ingrediente> iterator = lma.iterator();
//            while (iterator.hasNext()) {
//                Ingrediente ingre = iterator.next();
//                for (int i = 0; i < lme.size(); i++) {
//                    if (ingre.getId() == lme.get(i).getId()) {
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//
//        for (Ingrediente i : lma) {
//            modeloLista.addElement(i.getNombre());
//        }
//        return modeloLista;
//    }


//    public Rubro rubSelReturn(String rub, ArrayList<Rubro> rubroDB) {
//        Rubro r = new Rubro();
//        for (Rubro ru : rubroDB) {
//            if (rub.equals(ru.getNombre())) {
//                r = ru;
//            }
//        }
//        return r;
//    }
//
//    public Ingrediente ingreSelReturn(String ingre, ArrayList<Ingrediente> ingreDB) {
//        Ingrediente ing = new Ingrediente();
//        for (Ingrediente i : ingreDB) {
//            if (ingre.equals(i.getNombre())) {
//                ing = i;
//            }
//        }
//        return ing;
//    }


    public User userSelReturn(String usr, ArrayList<User> userDB) {
        User user = new User();
        for (User u : userDB) {
            if (usr.equals(u.getNombre() + "(" + u.isAlta() + ")")) {
                user = u;
            }
        }
        return user;
    }

//    public String listarTxtIngre(ArrayList<Ingrediente> ingredientesR, ArrayList<Integer> cantidades, ArrayList<Double> mermas) {
//        String txt = "";
//        txt = txt + "INGREDIENTES:\n";
//        if (ingredientesR.size() > 0) {
//            for (int i = 0; i < ingredientesR.size(); i++) {
//                Ingrediente ingre = ingredientesR.get(i);
//                Integer in = cantidades.get(i);
//                Double despe = mermas.get(i * 2);
//                Double mer = mermas.get((i * 2) + 1);
//                String u = ingre.getUnidadMedicion();
//                txt += mayusculaInicial(strShorter(ingre.getNombre(), 25)) + "(c: " + in + u + ", d:" + despe + "%, m: " + mer + "%).\n";
//            }
//        }
//        return txt;
//    }

//    public String listarTxtSbr(ArrayList<Subreceta> subrecetasR, ArrayList<Integer> porcionesSbr) {
//        String txt = "";
//        if (subrecetasR.size() > 0) {
//            txt = "SUBRECETAS:\n";
//            for (int i = 0; i < subrecetasR.size(); i++) {
//                Subreceta sr = subrecetasR.get(i);
//                Integer u = porcionesSbr.get(i);
//                txt += mayusculaInicial(strShorter(sr.getNombre(), 25)) + "(" + u + "uni.).\n";
//            }
//        }
//        return txt;
//    }

    public String strShorter(String str, int limite) {
        String st = "";
        if (str.length() > limite) {
            st = str.substring(0, (limite - 1)) + ".";
        } else {
            st = str;
        }
        return st;
    }

//    public boolean testIgualdadArrayI(ArrayList<Ingrediente> ingredientesSbr, ArrayList<Integer> cantidades, ArrayList<Double> mermas) {
//        boolean test = false;
//        if (ingredientesSbr.size() == cantidades.size() && cantidades.size() == (mermas.size() / 2)) {
//            test = true;
//        }
//        return test;
//    }


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

    public ArrayList<Integer> arrayIntConversor(String string) {
        String st = string;
        ArrayList<Integer> cantidades = new ArrayList();
        String[] cant = st.split("-");
        if (!st.equals("")) {
            for (String s : cant) {
                cantidades.add(Integer.parseInt(s));
            }
        } else {
            cantidades = null;
        }
        return cantidades;
    }

    public ArrayList<Double> arrayDoubleConversor(String string) {
        String st = string;
        ArrayList<Double> mermas = new ArrayList();
        String[] merms = string.split("-");
        if (!string.equals("")) {
            for (String s : merms) {
                mermas.add(Double.parseDouble(s));
            }
        } else {
            mermas = null;
        }
        return mermas;
    }

//    public boolean ingreRepeat(String ing, ArrayList<Ingrediente> ingres, Ingrediente ingre) {
//        boolean bool = false;
//        ing = stringPlain(ing);
//        for (Ingrediente in : ingres) {
//            if (ingre == null) {
//                if (ing.equals(stringPlain(in.getNombre()))) {
//                    bool = true;
//                }
//            } else {
//                if (!ingre.getNombre().equals(in.getNombre())) {
//                    if (ing.equals(stringPlain(in.getNombre()))) {
//                        bool = true;
//                    }
//                }
//            }
//        }
//        return bool;
//    }


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

//    public String listarTxtSbrIngre(ArrayList<Subreceta> sbrIngres) {
//        String txt = "Subrecetas que contienen al ingrediente consultado:\n";
//        if (sbrIngres.size() > 0) {
//            for (int i = 0; i < sbrIngres.size(); i++) {
//                txt += i + 1 + "-" + sbrIngres.get(i).getNombre().toUpperCase() + "\n";
//            }
//        } else {
//            txt = "El ingrediente no forma parte de ninguna subreceta";
//        }
//        return txt;
//    }

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

    ComboBoxModel captionComboModelReturn(ArrayList<String> captionsDB) {
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<String>();
        for (String i : captionsDB) {
            modeloCombo.addElement(i);
        }
        return modeloCombo;
    }
}
