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
public class BookManagedBean {

    private String maSach;
    private String maLoai;
    private String tenSach;
    private String tomTat;
    private String maTG;
    private String maNXB;
    private String tenLoai;
    private String tenTacGia;
    private String tenNXB;

    public BookManagedBean() {
    }

    public BookManagedBean(String maSach, String maLoai, String tenSach, String tomTat, String maTG, String maNXB) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenSach = tenSach;
        this.tomTat = tomTat;
        this.maTG = maTG;
        this.maNXB = maNXB;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTomTat() {
        return tomTat;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
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
            Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<BookManagedBean> GetAllBook() throws SQLException {

        ArrayList<BookManagedBean> arr = new ArrayList<>();
        str = "select * from Sach";
        getConnection();
        try {
            psmt = conn.prepareStatement(str);
            rs = psmt.executeQuery();
            while (rs.next()) {
                BookManagedBean bk = new BookManagedBean();
                bk.setMaSach(rs.getString("MaSach"));
                bk.setMaLoai(rs.getString("MaLoai"));
                bk.setTenSach(rs.getString("TenSach"));
                bk.setTomTat(rs.getString("TomTat"));
                bk.setMaTG(rs.getString("MaTG"));
                bk.setMaNXB(rs.getString("MaNXB"));
                arr.add(bk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public ArrayList<BookManagedBean> GetAllBookInfo() throws SQLException {

        ArrayList<BookManagedBean> arr = new ArrayList<>();
        str = "select * from ThongtinSach";
        getConnection();
        try {
            psmt = conn.prepareStatement(str);
            rs = psmt.executeQuery();
            while (rs.next()) {
                BookManagedBean bk = new BookManagedBean();
                bk.setMaSach(rs.getString("MaSach"));                
                bk.setTenSach(rs.getString("TenSach"));
                bk.setTomTat(rs.getString("TomTat"));
                bk.setTenLoai(rs.getString("Tenloai"));
                bk.setTenTacGia(rs.getString("Tentacgia"));
                bk.setTenNXB(rs.getString("TenNXB"));
                arr.add(bk);
                
                System.out.println(tenLoai);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public void EditBook1() throws SQLException {
        String idBook;
        ArrayList<BookManagedBean> arrlist = GetAllBook();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        idBook = request.getParameter("maSach");

        for (BookManagedBean bookManagedBean : arrlist) {
            if (bookManagedBean.maSach.equals(idBook)) {
                this.setMaSach(bookManagedBean.getMaSach());
                this.setMaLoai(bookManagedBean.getMaLoai());
                this.setTenSach(bookManagedBean.getTenSach());
                this.setTomTat(bookManagedBean.getTomTat());
                this.setMaTG(bookManagedBean.getMaTG());
                this.setMaNXB(bookManagedBean.getMaNXB());
            }
        }        
    }

    public void AddBook() throws SQLException {
        getConnection();
        str = "insert into Sach(MaSach,MaLoai,TenSach,TomTat,MaTG,MaNXB) values (?,?,?,?,?,?)";
        psmt = conn.prepareStatement(str);
        psmt.setString(1, this.getMaSach());
        psmt.setString(2, this.getMaLoai());
        psmt.setString(3, this.getTenSach());
        psmt.setString(4, this.getTomTat());
        psmt.setString(5, this.getMaTG());
        psmt.setString(6, this.getMaNXB());
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update Success!");
        }
        closeAll(conn, psmt, rs);
    }

    public void EditBook() throws SQLException {
        getConnection();
        str = "update Sach set MaLoai=?,TenSach=?,TomTat=?,MaTG=?,MaNXB=? where MaSach=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idBook = request.getParameter("maSach");
        psmt = conn.prepareStatement(str);

        psmt.setString(1, this.getMaLoai());
        psmt.setString(2, this.getTenSach());
        psmt.setString(3, this.getTomTat());
        psmt.setString(4, this.getMaTG());
        psmt.setString(5, this.getMaNXB());
        psmt.setString(6, idBook);

        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Update Success!");
        }
        closeAll(conn, psmt, rs);
    }
    
    public void DeleteBook() throws SQLException {
        getConnection();
        str = "delete Sach where MaSach=?";
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String idBook = request.getParameter("maSach");
        psmt = conn.prepareStatement(str);      
        psmt.setString(1, idBook);
        int executeUpdate = psmt.executeUpdate();
        if (executeUpdate > 0) {
            System.out.println("Delete Success!");
        }
        closeAll(conn, psmt, rs);
    }       
}
