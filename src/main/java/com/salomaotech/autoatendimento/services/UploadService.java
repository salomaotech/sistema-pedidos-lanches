package com.salomaotech.autoatendimento.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class UploadService {

    private static final Path pathRaiz = Paths.get("./uploads");

    private static String extensao(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (index > 0) {

            return "." + fileName.substring(index + 1);

        } else {

            return "";

        }

    }

    public static String storage(MultipartFile file) {

        try {

            String fileName = UUID.randomUUID() + extensao(file.getOriginalFilename());
            Files.createDirectories(pathRaiz);
            Files.copy(file.getInputStream(), pathRaiz.resolve(fileName));
            return fileName;

        } catch (IOException ex) {

            return null;

        }

    }

    public static String storage(ByteArrayInputStream file, String originalFilename) {

        try {

            String fileName = UUID.randomUUID() + extensao(originalFilename);
            Files.createDirectories(pathRaiz);
            Files.copy(file, pathRaiz.resolve(fileName));
            return fileName;

        } catch (IOException ex) {

            return null;

        }

    }

    public static void remove(String fileName) {

        try {

            Path pathFile = Paths.get(pathRaiz + "/" + fileName);
            File file = new File(pathFile.toString());
            file.delete();

        } catch (Exception ex) {

        }

    }

    public static ResponseEntity resource(String path) {

        ResponseEntity response = ResponseEntity.badRequest().build();

        try {

            Resource resource = new UrlResource(pathRaiz.resolve(path).toUri());

            if (resource.exists()) {

                HttpHeaders headers = new HttpHeaders();
                headers.setContentDispositionFormData("attachment", resource.getFilename());
                response = ResponseEntity.ok().headers(headers).body(resource);

            }

        } catch (MalformedURLException ex) {

        }

        return response;

    }

}
