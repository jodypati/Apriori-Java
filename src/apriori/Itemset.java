/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;

/**
 *
 * @author Sausa JodyPati
 */
public class Itemset {

    private String itemset;
    private String rule;
    private double confidence;
    private int support;
    private int key;
    private ArrayList<String> array_topik = new ArrayList<>();
    private ArrayList<Integer> array_jum_comment = new ArrayList<>();

    public Itemset(String itemset, int support, int key) {
        this.itemset = itemset;
        this.support = support;
        this.key = key;
    }

    public Itemset(String itemset, int key) {
        this.itemset = itemset;
        this.key = key;  
    }

    public Itemset(String rule, double confidence) {
        this.rule = rule;
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getRule() {
        return rule;
    }
    
    public Itemset(ArrayList<Integer> array_jum_comment,ArrayList<String> array_topik) {
        this.array_jum_comment = array_jum_comment;
        this.array_topik = array_topik;
    }

    public ArrayList<Integer> getArray_jum_comment() {
        return array_jum_comment;
    }


    public ArrayList<String> getArray_topik() {
        return array_topik;
    }

    public void setKey(int key) {
        this.key = key;
    }

    
    

    public String getItemset() {
        return itemset;
    }

    public int getKey() {
        return key;
    }

    public int getSupport() {
        return support;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
    
}
