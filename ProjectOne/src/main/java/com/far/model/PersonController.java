package com.far.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/form")
    public String formGetMethod(Model model) {

        model.addAttribute("model", new Person());
        return "form";
    }

    @GetMapping(value = "/edit/{id}")
    public String formGetMethod(@PathVariable Long id, Model model) {
        if (personRepository.existsById(id)) {
            model.addAttribute("model", personRepository.findById(id));
        }
        return "form";
    }

    @DeleteMapping(value = "/form")
    public String formPostMethod(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        Person personl = new Person();
        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            personl = personRepository.findById(person.getId()).get();
        }
        personRepository.save(personl);
        return "redirect:/";
    }

    @GetMapping(value = "/result")
    public String resultGetMethod(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "result";
    }

    @GetMapping(value = "/delete/{id}")
    public String resultGetMethod(@PathVariable Long id, Model model) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
        return "redirect:/result";
    }

}
