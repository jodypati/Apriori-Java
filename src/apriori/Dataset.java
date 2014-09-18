/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

/**
 *
 * @author Sausa JodyPati
 */
public class Dataset {
    private int jum;
    private String topik;
    private String username;

    public Dataset(int jum,String topik,String username ) {
        this.jum = jum;
        this.topik = topik;
        this.username = username;
    }

    public int getJum() {
        return jum;
    }

    public String getTopik() {
        return topik;
    }

    public String getUsername() {
        return username;
    }
    
    
    
    
}
