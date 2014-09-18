/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.List;
import javax.xml.transform.Result;
import Model.Database;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Sausa JodyPati
 */
public class Support {

    private Result rs = null;
    Database db = new Database();
    private ArrayList<Dataset> data = new ArrayList<Dataset>();
    private ArrayList<String> user = new ArrayList<String>();
    private ArrayList<String> topik = new ArrayList<String>();
    private int MINsup;
    private ArrayList<ArrayList<Integer>> item_awal = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> item = new ArrayList<ArrayList<Integer>>();


    public Support(int sup) throws SQLException {
        data = db.getJumlah();
        user = db.getUser();
        topik = db.getTopik();
        this.MINsup = sup;
        item_awal = itemset_awal();
        item = Generate_itemset(item_awal);
    }

    public ArrayList<ArrayList<Integer>> itemset_awal() {
        ArrayList<ArrayList<Integer>> items = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < user.size(); i++) {
            ArrayList<Integer> CurrentItem = new ArrayList<Integer>();
            for (int j = 0; j < topik.size(); j++) {
                CurrentItem.add(j, 0);
                for (int k = 0; k < data.size(); k++) {
                    if ((topik.get(j).equals(data.get(k).getTopik())) && (user.get(i).equals(data.get(k).getUsername()))) {

                        CurrentItem.remove(j);
                        if ((data.get(k).getJum() >= 1) && (data.get(k).getJum() <= 6)) {
                            CurrentItem.add(j, 1);
                        } else if ((data.get(k).getJum() >= 7) && (data.get(k).getJum() <= 12)) {
                            CurrentItem.add(j, 7);
                        } else {
                            CurrentItem.add(j, 13);
                        }
                    }
                }
            }
            items.add(i, CurrentItem);
        }
        return items;
    }

    public ArrayList<ArrayList<Integer>> Generate_itemset(ArrayList<ArrayList<Integer>> itemset) {

        ArrayList<ArrayList<Integer>> itemsetFix = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < itemset.get(0).size(); i++) {
            ArrayList<Integer> current = new ArrayList<Integer>();
            for (int j = 0; j < itemset.size(); j++) {
                current.add(j, itemset.get(j).get(i));
            }
            itemsetFix.add(i, current);
        }
        return itemsetFix;
    }

    public String[] reverse(String[] rule) {
        String temp;
        for (int i = 0; i < rule.length / 2; i++) {
            temp = rule[i];
            //System.out.println(rule[rule.length - (i)]);
            rule[i] = rule[rule.length - (i + 1)];
            rule[rule.length - (i + 1)] = temp;
            //System.out.print(rule[i]+","+i);
        }
        return rule;
    }

    public ArrayList<Itemset> Generate_MINsup1(ArrayList<ArrayList<Integer>> itembro) {
        ArrayList<ArrayList<Itemset>> minsupn = new ArrayList<ArrayList<Itemset>>();

        ArrayList<Itemset> CurrentItem = new ArrayList<Itemset>();
        int index = 0;
        for (int i = 0; i < itembro.size(); i++) {
            Set<Integer> unique = new HashSet<Integer>(itembro.get(i));
            for (int key : unique) {
                if (key != 0) {
                    Itemset itm = new Itemset(Integer.toString(i + 1) + "-" + Integer.toString(key), Collections.frequency(itembro.get(i), key), index);
                    CurrentItem.add(itm);
                    //minsupn.add(i, CurrentItem);
                    index++;
                }
            }
        }
        return CurrentItem;

    }

    public ArrayList<Itemset> getJumTopik(ArrayList<Itemset> min_sup_start, int i, ArrayList<Itemset> kombinasi, int j) {
        ArrayList<String> array_topik = new ArrayList<>();
        ArrayList<Integer> array_jum_comment = new ArrayList<>();
        ArrayList<String> array_kombinasi = new ArrayList<>();
        ArrayList<Itemset> data = new ArrayList<Itemset>();
        if (min_sup_start.get(i).getKey() < kombinasi.get(j).getKey()) {
            Itemset itm = new Itemset(min_sup_start.get(i).getItemset() + "," + kombinasi.get(j).getItemset(), kombinasi.get(j).getKey());
            data.add(itm);
            String[] split_itemset = data.get(0).getItemset().split(",");

            for (int k = 0; k < split_itemset.length; k++) {
                String[] split_topik = split_itemset[k].split("-");
                array_topik.add(split_topik[0]);
                array_jum_comment.add(Integer.parseInt(split_topik[1]));
            }

            Itemset itm_jum_topik = new Itemset(array_jum_comment, array_topik);
            data.add(itm_jum_topik);


        } else {
            data.add(null);
        }
        return data;
    }

    public ArrayList<Itemset> generate_minsupn(ArrayList<Itemset> min_sup_start, ArrayList<Itemset> min_sup_n) {
        ArrayList<ArrayList<Itemset>> result = new ArrayList<ArrayList<Itemset>>();
        ArrayList<Itemset> arr_kombinasi = new ArrayList<Itemset>();
        ArrayList<Itemset> jum_dan_topik = new ArrayList<Itemset>();
        ArrayList<Itemset> kombinasi = new ArrayList<Itemset>();
        kombinasi = min_sup_n;
        int idx = 0;
        for (int i = 0; i < min_sup_start.size(); i++) {
            for (int j = 0; j < kombinasi.size(); j++) {
                if ((!min_sup_start.isEmpty()) || (!min_sup_start.isEmpty())) {
                    jum_dan_topik = getJumTopik(min_sup_start, i, kombinasi, j);

                    if (!(jum_dan_topik.get(0) == null)) {
                        if (!(jum_dan_topik.get(1) == null)) {
                            int jum = countSupport(jum_dan_topik.get(1).getArray_jum_comment(), jum_dan_topik.get(1).getArray_topik());
                            Itemset itm = new Itemset(jum_dan_topik.get(0).getItemset(), jum, jum_dan_topik.get(0).getKey());
                            arr_kombinasi.add(idx, itm);
                            idx++;
                        }
                    }
                }
            }
        }
        arr_kombinasi = prune(arr_kombinasi);
        return arr_kombinasi;

    }

    public int countSupport(ArrayList<Integer> array_jum_comment, ArrayList<String> array_topik) {
        int jum = 0;
        ArrayList<ArrayList<Integer>> itemsets = new ArrayList<ArrayList<Integer>>();
        itemsets = this.item_awal;

        for (int i = 0; i < itemsets.size(); i++) {
            int param = 0;
            //System.out.println(array_topik.size());
            for (int j = 0; j < array_topik.size(); j++) {
                int coba = Integer.parseInt(array_topik.get(j));
                if (itemsets.get(i).get(coba - 1) == array_jum_comment.get(j)) {
                    param = param + 1;
                }
            }
            if (param == array_topik.size()) {
                jum = jum + 1;
            }

        }
        return jum;
    }

    public ArrayList<Itemset> prune(ArrayList<Itemset> arrayInput) {
        Iterator<Itemset> it = arrayInput.iterator();
        while (it.hasNext()) {
            if (it.next().getSupport() + 0 < MINsup) {
                it.remove();
            }
        }
        return arrayInput;
    }

    public ArrayList<ArrayList<Itemset>> generatesupportItem() throws FileNotFoundException, UnsupportedEncodingException {
        int head = 0;
        ArrayList<ArrayList<Integer>> item = new ArrayList<ArrayList<Integer>>();
        item = Generate_itemset(item_awal);

        ArrayList<Itemset> min_sup_1 = new ArrayList<Itemset>();
        min_sup_1 = Generate_MINsup1(item);

        min_sup_1 = prune(min_sup_1);
        ArrayList<ArrayList<Itemset>> result = new ArrayList<ArrayList<Itemset>>();
        ArrayList<Itemset> kombinasi = new ArrayList<Itemset>();
        kombinasi = min_sup_1;
        result.add(kombinasi);
        int i = 0;
        while ((!kombinasi.isEmpty())) {

            kombinasi = generate_minsupn(result.get(i), min_sup_1);
            System.out.println(kombinasi.size() + " ");
            result.add(kombinasi);
            i++;
        }
        if (result.size() >= 3) {
            head = result.size() - 2;
            System.out.println(head);
            result.get(head).addAll(itemForRule(result));
        }
        try (PrintWriter writer = new PrintWriter("C:/Users/Sausa JodyPati/Documents/NetBeansProjects/Apriori/60 user/rule "+MINsup+".txt", "UTF-8")) {
            for (int j = 0; j < result.size(); j++) {
                writer.println(j);
                for (int k = 0; k < result.get(j).size(); k++) {
                    writer.println("\tItemset:" + result.get(j).get(k).getItemset());
                    writer.println("\tSupport:" + result.get(j).get(k).getSupport());
                    writer.println("\tKey:" + result.get(j).get(k).getKey());
                }

            }
        }

        return result;
    }

    public int intersect1(ArrayList<String> a, List<String> b) {
        HashSet<String> hs = new HashSet<String>();
        ArrayList<String> intersect = new ArrayList<>();
        for (int i = 0; i < b.size(); i++) {
            hs.add(b.get(i));
        }

        for (int i = a.size() - 1; i >= 0; i--) {
            if (hs.contains(a.get(i))) {
                intersect.add(a.get(i));
            }
        }


        return intersect.size();
    }

    public ArrayList<Itemset> itemForRule(ArrayList<ArrayList<Itemset>> result) {
        ArrayList<String> newList = new ArrayList<String>();
        ArrayList<Itemset> forRule = new ArrayList<Itemset>();

        String[] splitItem = null;
        int head = result.size() - 2;
        for (int i = 0; i < result.get(head).size(); i++) {
            newList.addAll(Arrays.asList(result.get(head).get(i).getItemset().split(",")));
        }

        HashSet<String> hs = new HashSet<String>();
        hs.addAll(newList);
        newList.clear();
        newList.addAll(hs);

        forRule = validasiSubset(newList, result);

        return forRule;


    }

    public ArrayList<Itemset> validasiSubset(ArrayList<String> ruler, ArrayList<ArrayList<Itemset>> result) {
        ArrayList<String> split = new ArrayList<String>();
        ArrayList<Itemset> forRule = new ArrayList<Itemset>();
        ArrayList<ArrayList<Itemset>> ress = new ArrayList<ArrayList<Itemset>>();
        String[] splitItem = null;
        for (int i = result.size() - 3; i >= 1; i--) {
            for (int j = 0; j < result.get(i).size(); j++) {

                //Arrays.asList(result.get(i).get(j).getItemset().split(","));
                int jum = intersect1(ruler, Arrays.asList(result.get(i).get(j).getItemset().split(",")));
                if (jum < result.get(i).get(j).getItemset().split(",").length) {
                    Itemset itm = new Itemset(result.get(i).get(j).getItemset(), result.get(i).get(j).getSupport(), result.get(i).get(j).getKey());
                    forRule.add(itm);
                    ruler.addAll(Arrays.asList(result.get(i).get(j).getItemset().split(",")));
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(ruler);
                    ruler.clear();
                    ruler.addAll(hs);
                }
            }

        }

        return forRule;
    }

    
}
