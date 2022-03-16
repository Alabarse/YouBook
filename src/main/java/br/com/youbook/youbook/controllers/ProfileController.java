package br.com.youbook.youbook.controllers;

import br.com.youbook.youbook.models.Users;
import br.com.youbook.youbook.repository.UsersRepository;
import br.com.youbook.youbook.security.MyUserPrincipal;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class ProfileController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserPrincipal myUserPrincipal;

    private static String pathImage = "D:/Mateus/Estudos Java/youbook/src/main/java/br/com/youbook/youbook/userProfilePictures/";

    @GetMapping("/profile/{username}")
    public String profilePage(@PathVariable("username") String username) {
        return "profile";
    }

    @GetMapping("imagem/{username}")
    @ResponseBody
    public byte[] imageProfile(@PathVariable("username") String username) throws IOException {
        Users user = usersRepository.findByUsername(username);
        try {
            if (user.getPerfilImage() != null) {
                File archiveImage = new File(pathImage + user.getPerfilImage());
                return Files.readAllBytes(archiveImage.toPath());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        File archiveDefaullt = new File(pathImage + "userDefault.png");
        return Files.readAllBytes(archiveDefaullt.toPath());

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

    @PostMapping("/profile/{username}")
    public String savePerfilImage(Users user, @PathVariable("username") String username, @RequestParam("image") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) throws IOException {

        if (!multipartFile.isEmpty()) {
            user = usersRepository.findByUsername(username);

            byte[] bytesOfImage = multipartFile.getBytes();
            Path path = Paths.get(pathImage + user.getUsername() + "_" + multipartFile.getOriginalFilename());
            Files.write(path, bytesOfImage);

            user.setPerfilImage(String.valueOf(user.getUsername() + "_" + multipartFile.getOriginalFilename()));

            usersRepository.saveAndFlush(user);
            return "redirect:/profile/{username}";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Favor selecionar imagem");
        return "redirect:/profile/{username}";
    }

    @PostMapping("/profile/edit-profile")
    public String editProfile(@Valid Users user, BindingResult bindingResult) {

        return "redirect:/profile";
    }

}
