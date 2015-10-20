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
public class PublisherManagedBean {

    private String maNXB;
    private String tenNXB;
    private String sdt;
    private String diachi;
    private String email;

    public PublisherManagedBean(String maNXB, String tenNXB, String sdt, String diachi, String email) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.sdt = sdt;
        this.diachi = diachi;
        this.email = email;
    }
        
    public PublisherManagedBean() {
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
            Logger.getLogger(PublisherManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PublisherManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<PublisherManagedBean> GetAllPub() throws SQLException {

        ArrayList<PublisherManagedBean> arr = new ArrayList<>();
        str = "select * from NhaXB";
        getConnection();
        try {
            psmt = conn.prepareStatement(str);
            rs = psmt.executeQuery();
            while (rs.next()) {
                PublisherManagedBean pl = new PublisherManagedBean();
                pl.setMaNXB(rs.getString("MaNXB"));
                pl.setTenNXB(rs.getString("TenNXB"));
                pl.setDiachi(rs.getString("DiaChi"));
                pl.setSdt(rs.getString("SDT"));                
                pl.setEmail(rs.getString("Email"));
                arr.add(pl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublisherManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }        

    public void EditPub1() throws SQLException {
        String idPub;
        ArrayList<PublisherManagedBean> arrlist = GetAllPub();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idPub = request.getParameter("maNXB");

        for (PublisherManagedBean pubManagedBean : arrlist) {
            if (pubManagedBean.maNXB.equals(idPub)) {
                this.setMaNXB(pubManagedBean.getMaNXB());
                this.setTenNXB(pubManagedBean.getTenNXB());
                this.setDiachi(pubManagedBean.getDiachi());
                this.setSdt(pubManagedBean.getSdt());
                this.setEmail(pubManagedBean.getEmail());                
            }
        }        
    }

    public void AddPub() throws SQLException {
        getConnection();
        str = "insert into NhaXB(MaNXB,TenNXB,DiaChi,SDT,Email) values (?,?,?,?,?)";
        psmt = conn.prepareStatement(str);
        psmt.setString(1, this.getMaNXB());
        psmt.setString(2, this.getTenNXB());
        psmt.setString(3, this.getDiachi());
        psmt.setString(4, this.getSdt());
        psmt.setString(5, this.getEmail());
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Add Success!");
        }
        closeAll(conn, psmt, rs);
    }

    public void EditPub() throws SQLException {
        getConnection();
        str = "update NhaXB set MaNXB=?,TenNXB=?,DiaChi=?,SDT=?,Email=? where MaNXB=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idPub = request.getParameter("maNXB");
        psmt = conn.prepareStatement(str);

        psmt.setString(1, this.getMaNXB());
        psmt.setString(2, this.getTenNXB());
        psmt.setString(3, this.getDiachi());
        psmt.setString(4, this.getSdt());        
        psmt.setString(5, this.getEmail());  
        psmt.setString(6, idPub);

        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update Success!");
        }
        closeAll(conn, psmt, rs);
    }
    
    public void DeletePub() throws SQLException {
        getConnection();
        str = "delete NhaXB where MaNXB=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idPub = request.getParameter("maNXB");
        psmt = conn.prepareStatement(str);      
        psmt.setString(1, idPub);
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete Success!");
        }
        closeAll(conn, psmt, rs);
    }
}
