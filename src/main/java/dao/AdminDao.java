package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import entity.Admin;
import entity.Cost;
import util.DBUtil;

public class AdminDao implements Serializable {
	public Admin findByCode(String code){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from ghb_admin_info where ADMIN_CODE=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,code);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Admin admin = new Admin();
				admin.setAdminCode(rs.getString("admin_code"));
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setEmail(rs.getString("email"));
				admin.setPassword(rs.getString("password"));
				admin.setName(rs.getString("name"));
				admin.setTelephone(rs.getString("telephone"));
				admin.setEnrolldate(rs.getDate("enrolldate"));
				return admin;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		return null;
	}
	public void kaitong(int id){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update ghb_cost set startime=sysdate,status=1 where cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
}
