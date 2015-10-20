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
public class CategoryManagedBean {
    private String maLoai;
    private String tenLoai;

    public CategoryManagedBean(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }
    
    public CategoryManagedBean() {
        
    }
    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
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
            Logger.getLogger(CategoryManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<CategoryManagedBean> GetAllCate() throws SQLException {

        ArrayList<CategoryManagedBean> arr = new ArrayList<>();
        str = "select * from LoaiSach";
        getConnection();
        try {
            psmt = conn.prepareStatement(str);
            rs = psmt.executeQuery();
            while (rs.next()) {
                CategoryManagedBean ct = new CategoryManagedBean();
                ct.setMaLoai(rs.getString("MaLoai"));
                ct.setTenLoai(rs.getString("TenLoai"));                
                arr.add(ct);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }        

    public void EditCate1() throws SQLException {
        String idCate;
        ArrayList<CategoryManagedBean> arrlist = GetAllCate();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idCate = request.getParameter("maLoai");

        for (CategoryManagedBean cateManagedBean : arrlist) {
            if (cateManagedBean.maLoai.equals(idCate)) {
                this.setMaLoai(cateManagedBean.getMaLoai());
                this.setTenLoai(cateManagedBean.getTenLoai());                
            }
        }        
    }

    public void AddCate() throws SQLException {
        getConnection();
        str = "insert into LoaiSach(MaLoai,TenLoai) values (?,?)";
        psmt = conn.prepareStatement(str);
        psmt.setString(1, this.getMaLoai());
        psmt.setString(2, this.getTenLoai());       
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Add Success!");
        }
        closeAll(conn, psmt, rs);
    }

    public void EditCate() throws SQLException {
        getConnection();
        str = "update LoaiSach set MaLoai=?,TenLoai=? where MaLoai=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idCate = request.getParameter("maLoai");
        psmt = conn.prepareStatement(str);

        psmt.setString(1, this.getMaLoai());
        psmt.setString(2, this.getTenLoai());       
        psmt.setString(3, idCate);

        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update Success!");
        }
        closeAll(conn, psmt, rs);
    }
    
    public void DeleteCate() throws SQLException {
        getConnection();
        str = "delete LoaiSach where MaLoai=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idCate = request.getParameter("maLoai");
        psmt = conn.prepareStatement(str);      
        psmt.setString(1, idCate);
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete Success!");
        }
        closeAll(conn, psmt, rs);
    }
    
    
}
