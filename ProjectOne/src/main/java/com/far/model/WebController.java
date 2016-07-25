package com.far.model;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebController {

	private int idDel = 0;
	private boolean delete = false;

	@Autowired
	PersonRepository personRepository;

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String formGetMethod(Model model) {
		
		model.addAttribute("person", new Person());
		return "form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String formGetMethod(@PathVariable Integer id, Model model) {
		if (personRepository.exists(id)) {
			this.delete = true;
			this.idDel = id;
			model.addAttribute("person", personRepository.findOne(id));
		}
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String formPostMethod(@Valid Person person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "form";
		}
		if (idDel >0 && delete == true){
			personRepository.delete(this.idDel);
			this.idDel = 0;
			this.delete = false;
		}
		person.setCreatedDate();
		personRepository.save(person);
		return "redirect:/";
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String resultGetMethod(Model model) {
		model.addAttribute("persons", personRepository.findAll());
		return "result";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String resultGetMethod(@PathVariable int id, Model model) {
		if (personRepository.exists(id)) {
			personRepository.delete(id);
		}
		return "redirect:/result";
	}

}
