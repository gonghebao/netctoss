package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtil;

public class CostDao implements Serializable {
	public Cost findById(int id){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from ghb_cost where cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Cost c = new Cost();
				creatCost(rs, c);
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		return null;
	}
	public List<Cost> findAll() {

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from ghb_cost order by cost_id";
			Statement smt = conn.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			List<Cost> list = new ArrayList<Cost>();
			while (rs.next()) {
				Cost c = new Cost();
				creatCost(rs, c);
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询资费失败", e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void addCost(Cost c) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into ghb_cost values(seq_ghb_cost.nextval,?,?,?,?,0,?,sysdate,null,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,c.getName());
			ps.setObject(2,c.getBaseDuration());
			ps.setObject(3, c.getBaseCost());
			ps.setObject(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6,c.getCostType());
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void modi(Cost c){
		Connection conn = null;
		try {
			conn=DBUtil.getConnection();
			String sql = "update ghb_cost set name=?,base_duration=?,base_cost=?,"
					+ "unit_cost=?,descr=?,cost_type=? where cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,c.getName());
			ps.setObject(2,c.getBaseDuration());
			ps.setObject(3, c.getBaseCost());
			ps.setObject(4,c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6,c.getCostType());
			ps.setInt(7,c.getCostId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	public void deleteCost(int id){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from ghb_cost where cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void creatCost(ResultSet rs, Cost c) throws SQLException {
		c.setCostId(rs.getInt("cost_id"));
		c.setName(rs.getString("name"));
		c.setBaseDuration(rs.getInt("base_duration"));
		c.setBaseCost(rs.getDouble("base_cost"));
		c.setUnitCost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreatime(rs.getTimestamp("creatime"));
		c.setStartime(rs.getTimestamp("startime"));
		c.setCostType(rs.getString("cost_type"));
	}
}
