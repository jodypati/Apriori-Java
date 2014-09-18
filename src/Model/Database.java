package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import apriori.Dataset;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Sausa JodyPati
 */
public class Database {

    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    private String db_name = "TA";
    private String db_user = "root";
    private String db_password = "";
    private String url = "jdbc:mysql://localhost/" + db_name;
    private String query;
    private ArrayList<String> topik = new ArrayList<String>();
    private ArrayList<String> username = new ArrayList<String>();
    private ArrayList<Dataset> dataset = new ArrayList<Dataset>();

    /**
     * prosedur memanggil driver MySQL, membuka database
     */
    public void bukaKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //mysql
        } catch (ClassNotFoundException x) {
            JOptionPane.showMessageDialog(null, "Driver gagal dipanggil");
        }

        try {
            conn = DriverManager.getConnection(url, db_user, db_password);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }
    }

    /**
     * prosedur untuk mengeksekusi query select
     *
     * @param query
     * @return ResultSet
     */
    public ResultSet getResultData(String query) {
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error execute query: " + ex.getMessage());
        }
        return rs;
    }

    public void kueri(String sql) throws SQLException {
        try {
            bukaKoneksi();
            stmt.executeUpdate(sql);
            tutupKoneksi();
            JOptionPane.showMessageDialog(null, "truncate table berhasil");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error execute query: " + ex.getMessage(),
    "truncate error",
    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * prosedur untuk mengeksekusi query insert, update, delete
     *
     * @param query
     * @return
     */
    public void manipulasiData(String subset, String antiSubset, double confidence, double sup) throws FileNotFoundException, UnsupportedEncodingException, SQLException {
        try {
            bukaKoneksi();
            String query = "INSERT INTO asosiasi VALUES (NULL," + "'" + subset + "'" + "," + "'" + antiSubset + "'" + "," + confidence + "," + sup + ");";
            stmt.executeUpdate(query);

            tutupKoneksi();
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error execute query: " + ex.getMessage(),
    "insert error",
    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void insertThread(String judul, String topik, String username) throws FileNotFoundException, UnsupportedEncodingException, SQLException {
        try {
            bukaKoneksi();
            String query = "INSERT INTO corpus VALUES (NULL," + "'" + judul + "','" + topik + "');";
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (username != "") {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    System.out.println(newId);
                    String query1 = "INSERT INTO visit VALUES ('" + newId + "','" + username + "');";
                    stmt.executeUpdate(query1);
                }
            }
            tutupKoneksi();
            JOptionPane.showMessageDialog(null, "Insert berhasil");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error execute query: " + ex.getMessage(),
    "insert error",
    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * prosedur untuk menutup koneksi database
     */
    public void tutupKoneksi() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error close database " + ex.getMessage());
            }
        }
    }

    public ArrayList<Dataset> getJumlah() throws SQLException {
        bukaKoneksi();
        query = "SELECT username ,topik,count(topik) jum FROM `corpus` join visit using (DocID) group by username,topik order by username";
        rs = getResultData(query);
        while (rs.next()) {
            Dataset d = new Dataset(rs.getInt("jum"), rs.getString("topik"), rs.getString("username"));
            dataset.add(d);
        }
        return dataset;

    }

    public ArrayList<String> getUser() throws SQLException {
        bukaKoneksi();
        query = "SELECT username FROM `user` order by username";
        rs = getResultData(query);
        while (rs.next()) {
            username.add(rs.getString("username"));
        }
        return username;

    }

    public ArrayList<String> getTopik() throws SQLException {
        bukaKoneksi();
        query = "SELECT distinct topik  FROM `corpus`";
        rs = getResultData(query);

        while (rs.next()) {
            topik.add(rs.getString("topik"));
        }
        return topik;

    }
//    public static void main(String[] args) {
//        Database db = new Database();
//        db.bukaKoneksi();
//        db.getResultData("select * from corpus");
//    }
}
