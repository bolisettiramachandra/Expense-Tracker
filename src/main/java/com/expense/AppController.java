package com.expense;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController {
	
	@Autowired
	private ExpenseService service;
	
	@RequestMapping("/")
	public String viewHomePage(Model model){
		
		return "index";
	}
	
	@RequestMapping("/display_by_expense")
	public String showDisplayByExpense(Model model){
		
		List<Expense> listExpenses = service.listAll();
		model.addAttribute("listExpenses", listExpenses);
		
		return "display_by_expense";
	}
	
	@RequestMapping("/display_by_table")
	public String displayByTable(Model model){
		
		List<Expense> listExpenses = service.listAll();
		model.addAttribute("listExpenses", listExpenses);
		
		return "display_by_table";
	}
	
	@RequestMapping("/display_by_date")
	public String barGraph(Model model) {
		
		String date = null;
		float cost = 0;
		
		Map<String, Float> surveyMap = new LinkedHashMap<>();
		
		List<Expense> listExpenses = service.listAll();
		
//		for(Expense ex : listExpenses){
//			
//			date = ex.getDate();
//			cost = surveyMap.containsKey(date) ? surveyMap.get(date) : 0;
//			cost += ex.getCost();
//			surveyMap.put(date, cost);
//			
//		}
//		
		listExpenses.forEach((item)->{
		String	date1 = item.getDate();
		float cost1= surveyMap.containsKey(date1) ? surveyMap.get(date1) : 0;
		cost1 += item.getCost();
		
		surveyMap.put(date1, cost1);
		
		});
		
//		listExpenses.forEach( (item) ->{
//			
//			String date = item.getDate();
//			double cost = surveyMap.containsKey(date) ? 
//			
//			surveyMap.put(item.getDate(), item.getCost());
//		});
	
		model.addAttribute("surveyMap", surveyMap);
		return "display_by_date";
	}
	
	@RequestMapping("/display_expense_graph")
	public String displayByExpense(Model model) {
		Map<String, Float> expenseMap = new HashMap<>();
		
		List<Expense> listExpenses = service.listAll();
		
		listExpenses.forEach((item)->{
		String	expense = item.getExpenseType();
		float cost= expenseMap.containsKey(expense) ? expenseMap.get(expense) : 0;
		cost += item.getCost();
		
		expenseMap.put(expense, cost);
		
		});
		
		String maxExpense = expenseMap.entrySet().stream().max((entry1,entry2)-> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		model.addAttribute("maxExpense", maxExpense);
		model.addAttribute("expenseMap",expenseMap );
		return "display_expense_graph";
	}

	
	
	@RequestMapping("/new")
	public String showNewExpenseForm(Model model){
		
		Expense expense = new Expense();
		model.addAttribute("expense", expense);
		return "new_expense";
	}
	
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveExpense(@ModelAttribute("expense") Expense expense){
		service.save(expense);
		
		return "redirect:/";
	}
	
	@RequestMapping("/display")
	public String displayExpenses(Model model){
		
		return "display";
	}
	
	
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditExpenseForm(@PathVariable(name="id") Long id){
		
		ModelAndView mav = new ModelAndView("edit_expense");
		
		Expense expense = service.get(id);
		mav.addObject("expense", expense);
		
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteExpense(@PathVariable(name="id") Long id){
		
		service.delete(id);
		return "redirect:/";
	}
	
	
}
