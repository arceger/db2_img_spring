package com.project.controller;

import com.project.model.Image;
import com.project.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ClientController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/ping")
    @ResponseBody
    public String hello_world(){
        return "Hello World!";
    }

   //  display image
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Image image = imageService.viewById(id);
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

//    @GetMapping("/display")
//    public ResponseEntity<InputStreamResource> displayImage(@RequestParam("id") long id) {
//        HttpHeaders headers = new HttpHeaders();
//        ByteArrayInputStream byteArrayInputStream = null;
//
//        Blob blob = null;
//        try {
//            Image image = imageService.viewById(id);
//            blob = image.getImage();
//
//            if (blob != null) {
//                int blobLength = (int) blob.length();
//                byte[] imageBytes = blob.getBytes(1, blobLength);
//                byteArrayInputStream = new ByteArrayInputStream(imageBytes);
//                headers.setContentType(MediaType.IMAGE_JPEG);
//            }
//        } catch (Exception e) {
//            System.out.println("Erro ao ler imagens");
//            e.printStackTrace();  // Imprime a stack trace para diagnosticar o problema
//        } finally {
//            // Certifique-se de fechar o Blob após usar
//            if (blob != null) {
//                try {
//                    blob.free();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if (byteArrayInputStream != null) {
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(new InputStreamResource(byteArrayInputStream));
//        } else {
//            // Se o InputStream estiver nulo, pode retornar um ResponseEntity com status de erro
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    // view All images
    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        List<Image> imageList = imageService.viewAll();
        mv.addObject("imageList", imageList);
        return mv;
    }

    // add image - get
    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addimage");
    }

    // add image - post
    @PostMapping("/add")
    public String addImagePost(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        Image image = new Image();
        image.setImage(blob);
        imageService.create(image);
        return "redirect:/";
    }
}
