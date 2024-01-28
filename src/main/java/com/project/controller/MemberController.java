package com.project.controller;

import com.project.model.Member;
import com.project.service.EmailSender;
import com.project.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private MemberService imageService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello_world(){
        return "Bem vindo";
    }

   //  display image
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Member image = imageService.viewById(id);
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }


    // view All images
    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        List<Member> imageList = imageService.viewAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    @GetMapping("/memberlist")
    public ModelAndView home2(){
        ModelAndView mv = new ModelAndView("memberlist");
        List<Member> imageList = imageService.viewAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    @GetMapping("/memberDetails")
    public String displayMemberDetails(@RequestParam("id") long id, Model model) throws SQLException {
        Member member = imageService.viewById(id);

        if (member != null) {
            model.addAttribute("member", member);
            return "memberdetails";
        } else {
            return "redirect:/members"; // Redirecionar para a página de lista de membros
        }
    }



    // add image - get
    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addimage");
    }

    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request,
                               @RequestParam("nome") String nome,
                               @RequestParam("sobrenome") String sobrenome,
                               @RequestParam("endereco") String endereco,
                               @RequestParam("contato") String contato,
                               @RequestParam("data_nascimento") String dataNascimento,
                               @RequestParam("observacoes") String observacoes,
                               @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException {

        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        Member member = new Member();
        member.setNome(nome);
        member.setSobrenome(sobrenome);
        member.setEndereco(endereco);
        member.setContato(contato);
        member.setData_nascimento(dataNascimento);
        member.setObservacoes(observacoes);
        member.setImage(blob);

        // Envia e-mail de boas-vindas
        String toEmail = member.getContato();
        String body = "sejas bem vindo "+member.getNome();;
        String subject = "Boas-vindas";

        emailSender.sendMail(toEmail, body, subject);

        imageService.create(member);

        return "redirect:/";
    }

}
