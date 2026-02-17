package com.example.zipupload.controller;

import com.example.zipupload.service.ZipProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/zip")
public class ZipController {

    private final ZipProcessingService zipProcessingService;

    public ZipController(ZipProcessingService zipProcessingService) {
        this.zipProcessingService = zipProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadZip(@RequestParam("file") MultipartFile file) {
        try {
            zipProcessingService.unzipAndStore(file);
            return ResponseEntity.ok("ZIP file processed and all files stored successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
