package br.com.youbook.youbook.controllers;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class ProfileController {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }
    
    @GetMapping("/register") 
    public String registerPage() {
        return "register";
    }
    
   @PostMapping("/register")
    public String addUser(@Valid Users user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("Passou aqui");
            redirectAttributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/register";
        }
        log.info("Não entrou na condicional");
        usersRepository.save(user);
        return "redirect:/register";
    }
}
