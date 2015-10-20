/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model_controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author BinhNguyen
 */
@ManagedBean
@RequestScoped
public class AuthorManagedBean {

    private String maTG;
    private String tentacgia;
    private String diachi;
    private String sdt;
    private String email;

    public AuthorManagedBean(String maTG, String tentacgia, String diachi, String sdt, String email) {
        this.maTG = maTG;
        this.tentacgia = tentacgia;
        this.diachi = diachi;
        this.sdt = sdt;
        this.email = email;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public AuthorManagedBean() {
    }
    
    public static Connection conn = null;
    public static PreparedStatement psmt = null;
    public static ResultSet rs = null;
    private String str = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=Library", "sa", "1234$");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(AuthorManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(AuthorManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AuthorManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<AuthorManagedBean> GetAllAuth() throws SQLException {

        ArrayList<AuthorManagedBean> arr = new ArrayList<>();
        str = "select * from Tacgia";
        getConnection();
        try {
            psmt = conn.prepareStatement(str);
            rs = psmt.executeQuery();
            while (rs.next()) {
                AuthorManagedBean pl = new AuthorManagedBean();
                pl.setMaTG(rs.getString("MaTG"));
                pl.setTentacgia(rs.getString("Tentacgia"));
                pl.setDiachi(rs.getString("DiaChi"));
                pl.setSdt(rs.getString("SDT"));                
                pl.setEmail(rs.getString("Email"));
                arr.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }        

    public void EditAuth1() throws SQLException {
        String idAuth;
        ArrayList<AuthorManagedBean> arrlist = GetAllAuth();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idAuth = request.getParameter("maTG");

        for (AuthorManagedBean pubManagedBean : arrlist) {
            if (pubManagedBean.maTG.equals(idAuth)) {
                this.setMaTG(pubManagedBean.getMaTG());
                this.setTentacgia(pubManagedBean.getTentacgia());
                this.setDiachi(pubManagedBean.getDiachi());
                this.setSdt(pubManagedBean.getSdt());
                this.setEmail(pubManagedBean.getEmail());                
            }
        }        
    }

    public void AddAuth() throws SQLException {
        getConnection();
        str = "insert into Tacgia(MaTG,Tentacgia,DiaChi,SDT,Email) values (?,?,?,?,?)";
        psmt = conn.prepareStatement(str);
        psmt.setString(1, this.getMaTG());
        psmt.setString(2, this.getTentacgia());
        psmt.setString(3, this.getDiachi());
        psmt.setString(4, this.getSdt());
        psmt.setString(5, this.getEmail());
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Add Success!");
        }
        closeAll(conn, psmt, rs);
    }

    public void EditAuth() throws SQLException {
        getConnection();
        str = "update Tacgia set MaTG=?,Tentacgia=?,DiaChi=?,SDT=?,Email=? where MaTG=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idAuth = request.getParameter("maTG");
        psmt = conn.prepareStatement(str);

        psmt.setString(1, this.getMaTG());
        psmt.setString(2, this.getTentacgia());
        psmt.setString(3, this.getDiachi());
        psmt.setString(4, this.getSdt());        
        psmt.setString(5, this.getEmail());  
        psmt.setString(6, idAuth);

        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update Success!");
        }
        closeAll(conn, psmt, rs);
    }
    
    public void DeleteAuth() throws SQLException {
        getConnection();
        str = "delete Tacgia where MaTG=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idAuth = request.getParameter("maTG");
        psmt = conn.prepareStatement(str);      
        psmt.setString(1, idAuth);
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete Success!");
        }
        closeAll(conn, psmt, rs);
    }
}
