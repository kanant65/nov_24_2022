package com.spring.core.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.spring.core.jdbc.model.Product;

public class ProductDAOImpl implements ProductDAO{

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;	
	}
	
	
	@Override
	public void save(Product product) {
		String query = " insert products(id, name, price, unitsInStock, discontinued) values (?, ?, ?, ?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] values = new Object[] {
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getUnitsInStock(),
			product.isDiscontinued()
		};
		
		int out = jdbcTemplate.update(query, values);
		

		if(out != 0)
			System.out.println("Product saved with id = " + product.getId());
		else
			System.out.println("Product save failed with id =" + product.getId());
			
	
		
	}

	@Override
	public Product getById(int id) {
		String query = "select id, name, price, unitsInStock,discontinued from products where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
					
	
		Product pro = jdbcTemplate.queryForObject(query, 
				new Object[]{id}, new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Product p1 = new Product();
				p1.setId(rs.getInt("id"));
				p1.setName(rs.getString("name"));
				p1.setPrice(rs.getDouble("price"));
				p1.setUnitsInStock(rs.getInt("unitsInStock"));
				p1.setDiscontinued(rs.getBoolean("discontinued"));
				return p1;
			}});
		return pro;
	}

	@Override
	public void update(Product product) {
		String query = "update products set name=? , price=?, unitsInStock=?,discontinued=? where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] {
				product.getName(), 
				product.getPrice(), 
				product.getUnitsInStock(),
				product.isDiscontinued()
		};
		
		int out = jdbcTemplate.update(query, args);
		
		if(out !=0){
			System.out.println("Product updated with id="+product.getId());
		}else System.out.println("No Product found with id="+product.getId());
	
	}

	@Override
	public void deleteById(int id) {
		String query = "delete from products where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		int out = jdbcTemplate.update(query, id);
		
		if(out !=0){
			System.out.println("Product deleted with id="+id);
		}
		else 
			System.out.println("No Product found with id="+id);
		
	
		
	}

	@Override
	public List<Product> getAll() {
		String query = "select id, name, price ,unitsInStock ,discontinued from products";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> proList = new ArrayList<Product>();

		List<Map<String,Object>> proRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> proRow : proRows){
			Product pro = new Product();
			
			pro.setId(Integer.parseInt(String.valueOf(proRow.get("id"))));
			pro.setName(String.valueOf(proRow.get("name")));
			pro.setPrice(Double.parseDouble(String.valueOf(proRow.get("price"))));
			pro.setUnitsInStock(Integer.parseInt(String.valueOf(proRow.get("unitsInStock"))));
			pro.setDiscontinued(Boolean.valueOf(String.valueOf(proRow.get("dicsontinued"))));
			
			
			proList.add(pro);
		}
		return proList;
	}

	@Override
	public Product getByName(String name) {
		String query = "select id, name, price,unitsInStock,discontinued from products where name = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Product pro = jdbcTemplate.queryForObject(query, 
				new Object[]{name}, new RowMapper<Product>(){

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product p1 = new Product();
				
				p1.setId(rs.getInt("id"));
				p1.setName(rs.getString("name"));
				p1.setPrice(rs.getDouble("price"));
				p1.setUnitsInStock(rs.getInt("unitsInStock"));
				p1.setDiscontinued(rs.getBoolean("discontinued"));
				return p1;
			}});
		
		return pro;
	}

	@Override
	public List<Product> getByNames(String substring) {
		String query = "select id, name, description from categories where name like ?%";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> proList = new ArrayList<Product>();

		List<Map<String,Object>> proRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> proRow : proRows){
			Product pro = new Product();
			
			pro.setId(Integer.parseInt(String.valueOf(proRow.get("id"))));
			pro.setName(String.valueOf(proRow.get("name")));
			pro.setPrice(Double.parseDouble(String.valueOf(proRow.get("price"))));
			pro.setUnitsInStock(Integer.parseInt(String.valueOf(proRow.get("unitsInStock"))));
			pro.setDiscontinued(Boolean.valueOf(String.valueOf(proRow.get("dicsontinued"))));
			
			proList.add(pro);
		}
		
		return proList;
	}

	@Override
	public List<Product> getByBetweenPrice(double iPrice, double oPrice) {
		String query = "select id, name, price, unitsInStock, discontinued from products where price between ? and ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> proList = new ArrayList<Product>();

		List<Map<String,Object>> proRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> proRow : proRows){
			Product pro = new Product();
			
			pro.setId(Integer.parseInt(String.valueOf(proRow.get("id"))));
			pro.setName(String.valueOf(proRow.get("name")));
			pro.setPrice(Double.parseDouble(String.valueOf(proRow.get("price"))));
			pro.setUnitsInStock(Integer.parseInt(String.valueOf(proRow.get("unitsInStock"))));
			//pro.setDiscontinued(String.valueOf(b);
			
			proList.add(pro);
		}
		
		return proList;
	}

	@Override
	public List<Product> getDiscontinuedProducts() {
		String query = "select id, name, price, unitsInStock, discontinued from products where disconitinued = true";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Product> proList = new ArrayList<Product>();

		List<Map<String,Object>> proRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> proRow : proRows){
			Product pro = new Product();
			
			pro.setId(Integer.parseInt(String.valueOf(proRow.get("id"))));
			pro.setName(String.valueOf(proRow.get("name")));
			pro.setPrice(Double.parseDouble(String.valueOf(proRow.get("price"))));
			pro.setUnitsInStock(Integer.parseInt(String.valueOf(proRow.get("unitsInStock"))));
			//pro.setDiscontinued(String.valueOf(b);
			
			proList.add(pro);
		}
		
		return proList;
	}

}