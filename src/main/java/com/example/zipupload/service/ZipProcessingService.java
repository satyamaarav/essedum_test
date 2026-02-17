package com.example.zipupload.service;

import com.example.zipupload.entity.FileEntity;
import com.example.zipupload.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipProcessingService {

    private final FileRepository fileRepository;

    public ZipProcessingService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void unzipAndStore(MultipartFile zipFile) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String fullPath = entry.getName();
                    String fileName = Paths.get(fullPath).getFileName().toString();

                    byte[] fileData = zis.readAllBytes();

                    FileEntity entity = new FileEntity();
                    entity.setPath(fullPath);
                    entity.setFileName(fileName);
                    entity.setData(fileData);

                    fileRepository.save(entity);
                }
                zis.closeEntry();
            }
        }
    }
}
