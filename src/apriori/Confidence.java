/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import Model.Database;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Sausa JodyPati
 */
public class Confidence {

    private double MINconf;
    private String path;

    public Confidence(double conf, String path) {
        this.MINconf = conf;
        this.path = "C:/Users/Sausa JodyPati/Documents/NetBeansProjects/Apriori/"+path;

    }


    public ArrayList<Itemset> subset(ArrayList<Itemset> kombinasi, ArrayList<Itemset> item_awal) {
        int iterasi = 0;
        ArrayList<Itemset> result = new ArrayList<Itemset>();
        iterasi = kombinasi.size();
        for (int i = 0; i < iterasi; i++) {
            String item2 = "";
            for (int j = 0; j < item_awal.size(); j++) {
                if (kombinasi.get(i).getKey() < item_awal.get(j).getKey()) {
                    String item = kombinasi.get(i).getItemset() + "," + item_awal.get(j).getItemset();
                    //System.out.println(item);
                    Itemset itm = new Itemset(item, item_awal.get(j).getKey());
                    result.add(itm);
                }
            }
        }
        return result;
    }

    public String antiSubset(String subset, ArrayList<Itemset> itemAwal) {
        String[] split = null;
        split = subset.split(",");
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < itemAwal.size(); i++) {
            temp.add(itemAwal.get(i).getItemset());
        }
        for (int i = 0; i < split.length; i++) {
            for (int j = 0; j < itemAwal.size(); j++) {
                if (itemAwal.get(j).getItemset().equals(split[i])) {
                    temp.remove(itemAwal.get(j).getItemset());
                }
            }
        }
        String string = "";
        for (int i = 0; i < temp.size(); i++) {
            if (i == 0) {
                string = temp.get(i);
            } else {
                string = string + "," + temp.get(i);

            }
        }
        return string;
    }

    public ArrayList<ArrayList<Itemset>> generateSubset(ArrayList<Itemset> item_awal) {
        ArrayList<ArrayList<Itemset>> result = new ArrayList<ArrayList<Itemset>>();
        ArrayList<Itemset> kombinasi = new ArrayList<Itemset>();
        kombinasi = item_awal;
        result.add(kombinasi);

        int k = 0;
        while (k < item_awal.size() - 2) {
            kombinasi = subset(kombinasi, item_awal);
            result.add(kombinasi);
            k++;
        }

        return result;
    }

    public void generateRule(ArrayList<Itemset> item_awal) {
        ArrayList<ArrayList<Itemset>> subsetItem = new ArrayList<ArrayList<Itemset>>();
        subsetItem = generateSubset(item_awal);
    }

    public double countConfidence(String subset, ArrayList<ArrayList<Itemset>> supportItem, int supPembagi) {
        String[] split = subset.split(",");
        double conf = 0.0;

        if (split.length < supportItem.size()) {
            for (int i = 0; i < supportItem.get(split.length - 1).size(); i++) {
                if (supportItem.get(split.length - 1).get(i).getItemset().equals(subset)) {
                    //System.out.println(supportItem.get(split.length - 1).get(i).getSupport()/supPembagi);
                    conf = (double) supPembagi / supportItem.get(split.length - 1).get(i).getSupport();

                }

            }
        } else {
        }

        return conf;

    }

    public String getTopikName(String topikCode) {
        String topik = null;
        switch (topikCode) {
            case "1":
                topik = "Supranatural";
                break;
            case "2":
                topik = "Berita dan Politik";
                break;
            case "3":
                topik = "The Lounge";
                break;
            case "4":
                topik = "Lifestyle";
                break;
            case "5":
                topik = "Komputer";
                break;
            case "6":
                topik = "Elektronik";
                break;
            case "7":
                topik = "Regional";
                break;
            case "8":
                topik = "Fashion & Mode";
                break;
            case "9":
                topik = "Heart to Heart";
                break;
            case "10":
                topik = "Kamera & Aksesoris";
                break;
            case "11":
                topik = "Music";
                break;
            case "12":
                topik = "Anime & Manga";
                break;
            case "13":
                topik = "Education";
                break;
            case "14":
                topik = "Computer Stuff";
                break;
            case "15":
                topik = "Perlengkapan Rumah Tangga";
                break;
            case "16":
                topik = "Model Kit & R/C";
                break;
            case "17":
                topik = "Android";
                break;
            case "18":
                topik = "Handphone & PDA";
                break;
            case "19":
                topik = "Health & Medical";
                break;
            case "20":
                topik = "Alat-Alat Olahraga";
                break;
            case "21":
                topik = "CD & DVD";
                break;
            case "22":
                topik = "Video Games";
                break;
            case "23":
                topik = "Buku";
                break;
            case "24":
                topik = "Flora & Fauna";
                break;
            case "25":
                topik = "Travellers";
                break;
            case "26":
                topik = "Can You Solve This Game?";
                break;
            case "27":
                topik = "PILIH PARPOL";
                break;
            case "28":
                topik = "Hewan Peliharaan";
                break;
            case "29":
                topik = "Cooking & Resto";
                break;
            case "30":
                topik = "Alat-Alat Musik";
                break;
            case "31":
                topik = "Otomotif";
                break;
            case "32":
                topik = "Perlengkapan Anak & Bayi";
                break;
            case "33":
                topik = "Koleksi, Hobi & Mainan";
                break;
            case "34":
                topik = "Furniture";
                break;
            case "35":
                topik = "Jasa";
                break;
            case "36":
                topik = "Makanan & Minuman";
                break;
            case "37":
                topik = "Perlengkapan Kantor";
                break;
            case "38":
                topik = "TIket";
                break;
            case "39":
                topik = "Jokes & Cartoon";
                break;
            case "40":
                topik = "Movies";
                break;
        }
        return topik;
    }

    public String translate(String subset) {
        String rule = "";
        String[] subsetSplit;
        subsetSplit = subset.split(",");
        for (int j = 0; j < subsetSplit.length; j++) {
            String[] subsetSplitTopik;
            subsetSplitTopik = subsetSplit[j].split("-");
            for (int l = 0; l < subsetSplitTopik.length; l++) {
                if ((l % 2) != 1) {
                    if ((j != subsetSplit.length - 1)) {
                        //System.out.print(getTopikName(subsetSplitTopik[l]) + ",");
                        rule = rule + getTopikName(subsetSplitTopik[l]) + ";";
                    } else if (j == subsetSplit.length - 1) {
                        //System.out.print(getTopikName(subsetSplitTopik[l]));
                        rule = rule + getTopikName(subsetSplitTopik[l]);
                    }
                }



            }

        }
        return rule;
    }

    public ArrayList<String> makeSubsetList(ArrayList<Itemset> item) {
        ArrayList<ArrayList<Itemset>> subsetItem = new ArrayList<ArrayList<Itemset>>();
        ArrayList<String> subset = new ArrayList<>();
        subsetItem = generateSubset(item);
        for (int i = 0; i < subsetItem.size(); i++) {
            for (int j = 0; j < subsetItem.get(i).size(); j++) {
                subset.add(subsetItem.get(i).get(j).getItemset());

            }
        }
        return subset;
    }

    public int intersect1(ArrayList<Itemset> a, List<String> b) {
        HashSet<String> hs = new HashSet<String>();
        int sup = 0;
        for (int i = 0; i < b.size(); i++) {
            hs.add(b.get(i));
        }

        for (int i = 0; i < a.size(); i++) {
            ArrayList<String> intersect = new ArrayList<>();
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(a.get(i).getItemset().split(",")));

            for (int j = 0; j < list.size(); j++) {
                if (hs.contains(list.get(j))) {
                    intersect.add(list.get(j));
                }

            }
            if (intersect.size() == b.size()) {
                sup = a.get(i).getSupport();
                break;
            }

        }

        return sup;
    }

    public int countSupSubset(String subset, ArrayList<ArrayList<Itemset>> itemset) {
        int idx = subset.split(",").length - 1;
        List<String> a = new ArrayList<String>();
        a = Arrays.asList(subset.split(","));
        int sup = intersect1(itemset.get(idx), a);
        //System.out.println(" " + sup);
        return sup;
    }

    public void writeRule(ArrayList<String> subset, ArrayList<ArrayList<Itemset>> itemset, ArrayList<Itemset> item, int sup, int no) throws FileNotFoundException, UnsupportedEncodingException, IOException, SQLException {
        ArrayList<Itemset> finalResult = new ArrayList<Itemset>();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path + "MIN conf" + MINconf + ".txt", true)))) {
            for (int i = 0; i < subset.size(); i++) {



                String antiSubset = antiSubset(subset.get(i), item);
                sup = countSupSubset(subset.get(i) + "," + antiSubset, itemset);
//                System.out.println(sup)sup = countSupSubset(subset.get(i), itemset);;
                //System.out.print(subset.get(i)+","+antiSubset+" ");
                double confidence = countConfidence(subset.get(i), itemset, sup);
                if (confidence > MINconf) {
//              rule yang telah di translate
                    translate(subset.get(i));
                    translate(antiSubset);
                    //insert ke database
                    Database db = new Database();
                    db.manipulasiData(translate(subset.get(i)), translate(antiSubset), confidence, sup);

                    writer.print("rule :" + subset.get(i) + "=>" + antiSubset);
                    writer.println(" confidence :" + confidence);
                    Itemset itm = new Itemset(subset.get(i) + "=>" + antiSubset, confidence);
                    finalResult.add(itm);
                }
            }

        }
    }

    public void run(int sups) throws SQLException, FileNotFoundException, UnsupportedEncodingException, IOException {
        ArrayList<ArrayList<Itemset>> itemset = new ArrayList<ArrayList<Itemset>>();
        Support support = new Support(sups);
        //item support yang akan di generate rule
        itemset = support.generatesupportItem();
        if (itemset.size() > 2) {
            int sup = itemset.get(itemset.size() - 2).get(0).getSupport();
            //System.out.println(sup + " ini sup");
            int k = 0;
            for (int i = 0; i < itemset.get(itemset.size() - 2).size(); i++) {
                String split[] = itemset.get(itemset.size() - 2).get(i).getItemset().split(",");
                ArrayList<Itemset> item = new ArrayList<Itemset>();
                for (int j = 0; j < split.length; j++) {
                    Itemset itm = new Itemset(split[j], k);
                    item.add(itm);
                    k++;
                }
                ArrayList<String> subset = new ArrayList<>();
                subset = makeSubsetList(item);
                writeRule(subset, itemset, item, sup, i);
            }
            JOptionPane.showMessageDialog(null, "Insert berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Rule 0 tidak ada insert");
        }
    }

  
}
