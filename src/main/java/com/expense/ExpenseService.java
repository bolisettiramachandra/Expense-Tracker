package com.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository repo;
	
	public List<Expense> listAll(){
		return repo.findAll();
	}
	
	public void save(Expense expense){
		repo.save(expense);
	}
	
	public Expense get(Long id){
		return repo.findById(id).get();
	}
	
	public void delete(Long id){
		repo.deleteById(id);
	}
}
