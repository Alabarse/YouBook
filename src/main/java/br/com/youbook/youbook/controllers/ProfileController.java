package br.com.youbook.youbook.controllers;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import br.com.youbook.youbook.utils.FileUploadUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private static String caminhoImagem = "D:/Mateus/Estudos Java/imagens/";

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/profile/{imagem}")
    @ResponseBody
    public byte[] imageReturn(@PathVariable("imagem") String imagem) throws IOException {
        System.out.println(imagem);
        File imagemArquivo = new File(caminhoImagem+imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            System.out.println("Dentro da condicional " + imagem);
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
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

    @PutMapping("/profile/save")
    public String savePerfilImage(Users user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
    
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPerfilImage(fileName);

        Users savedUser = usersRepository.save(user);
        
        String uploadDir = "user-photos/" + savedUser.getUsername();
        
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);    
        
        return "redirect:/profile";
    }
    
    @PutMapping("/profile/edit-profile")
    public String editProfile(@Valid Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/profile";
        }
            if (usersRepository.findByUsername(user.getUsername()) != null ) {
                usersRepository.deleteByUsername(user.getUsername());
            }
            
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            usersRepository.saveAndFlush(user);
        return "redirect:/profile";
    }
    
}
