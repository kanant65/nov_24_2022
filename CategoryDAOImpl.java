package com.spring.core.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.core.jdbc.model.Category;

public class CategoryDAOImpl implements CategoryDAO{
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;	
	}

	@Override
	public void save(Category category) {
	String query = " insert categories(id, name, description) values (?, ?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] values = new Object[] {
			category.getId(),
			category.getName(),
			category.getDescription()
		};
		
		int out = jdbcTemplate.update(query, values);
		

		if(out != 0)
			System.out.println("Category saved with id = " + category.getId());
		else
			System.out.println("Category save failed with id =" + category.getId());
			
	}

	@Override
	public Category getById(int id) {
		String query = "select id, name, role from categories where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
					
		
		Category cat = jdbcTemplate.queryForObject(query, 
				new Object[]{id}, new RowMapper<Category>(){

			@Override
			public Category mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Category c1 = new Category();
				c1.setId(rs.getInt("id"));
				c1.setName(rs.getString("name"));
				c1.setDescription(rs.getString("description"));
				return c1;
			}});
		return cat;
	}

	@Override
	public void update(Category category) {
		String query = "update categories set name=?, description=? where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] {
				category.getName(), 
				category.getDescription(), 
				category.getId()
		};
		
		int out = jdbcTemplate.update(query, args);
		
		if(out !=0){
			System.out.println("Category updated with id="+category.getId());
		}else System.out.println("No Category found with id="+category.getId());
		
	}

	@Override
	public void deleteById(int id) {
		String query = "delete from categories where id=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		int out = jdbcTemplate.update(query, id);
		
		if(out !=0){
			System.out.println("Category deleted with id="+id);
		}
		else 
			System.out.println("No Category found with id="+id);
		
	}

	@Override
	public List<Category> getAll() {
		String query = "select * from categories";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Category> catList = new ArrayList<Category>();

		List<Map<String,Object>> catRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> catRow : catRows){
			Category cat = new Category();
			
			cat.setId(Integer.parseInt(String.valueOf(catRow.get("id"))));
			

			
			cat.setName(String.valueOf(catRow.get("name")));
			cat.setDescription(String.valueOf(catRow.get("description")));
			
			catList.add(cat);
		}
		return catList;
	}

	@Override
	public Category getByName(String name) {
		String query = "select id, name, description from categories where name = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Category cat = jdbcTemplate.queryForObject(query, 
				new Object[]{name}, new RowMapper<Category>(){

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category c1 = new Category();
				
				c1.setId(rs.getInt("id"));
				c1.setName(rs.getString("name"));
				c1.setDescription(rs.getString("description"));
				return c1;
			}});
		
		return cat;
	
	}

	@Override
	public List<Category> getByNames(String substring) {
		String query = "select id, name, description from categories where name like '?%'";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Category> catList = new ArrayList<Category>();

		List<Map<String,Object>> catRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> catRow : catRows){
			Category cat = new Category();
			
			cat.setId(Integer.parseInt(String.valueOf(catRow.get("id"))));
			cat.setName(String.valueOf(catRow.get("name")));
			cat.setDescription(String.valueOf(catRow.get("description")));
			
			catList.add(cat);
		}
		
		return catList;
	}
	
	
	
	
	
	
}