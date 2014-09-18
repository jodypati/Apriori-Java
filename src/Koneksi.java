/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Nanda w Perdana
 */
public class Koneksi {

    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    private String db_name = "TA";
    private String db_user = "root";
    private String db_password = "";
    private String url = "jdbc:mysql://localhost/" + db_name; //mysql
//    private String url = "jdbc:odbc:" + db_name; //ms access

    /**
     * prosedur memanggil driver MySQL, membuka database
     */
    public void bukaKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //mysql
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //ms access
        } catch (ClassNotFoundException x) {
            JOptionPane.showMessageDialog(null, "Driver gagal dipanggil");
        }

        try {
            conn = DriverManager.getConnection(url, "", "");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException x) {
            System.out.println(x.getMessage());
            //JOptionPane.showMessageDialog(null, "Database tidak bisa dibuka");
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
            //JOptionPane.showMessageDialog(null, "Error execute query: " + ex.getMessage());
        }
        return rs;
    }

    /**
     * prosedur untuk mengeksekusi query insert, update, delete
     *
     * @param query
     * @return
     */
    public int manipulasiData(String query) {
        int jmlRecord = 0;
        try {
            jmlRecord = stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error execute update" + ex.getMessage());
        }
        return jmlRecord;
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
}
