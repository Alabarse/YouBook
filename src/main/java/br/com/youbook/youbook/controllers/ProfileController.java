package br.com.youbook.youbook.controllers;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import br.com.youbook.youbook.utils.FileUploadUtil;
import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
@Controller
@Log4j2
public class ProfileController {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private FileUploadUtil fileUploadUtil;
    
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return "redirect:/login";
    }
    
    @PostMapping("/profile/save") 
    public RedirectView savePerfilImage(Users user, @RequestParam("image") MultipartFile multipartFile) throws IOException{
        
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        
        Users newUser = new Users();
        newUser = user;
        usersRepository.delete(user);
        newUser.setPerfilImage(fileName);
        usersRepository.save(newUser);
        
        String uploadDir =  "user-photos/" + newUser.getUsername();
        
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        
        
        return new RedirectView("/profile", true);
    }
}
