package com.example.rockclass.controller;

import com.example.rockclass.service.KlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class KlassController {
    @Autowired
    private KlassService klassService;

    @PutMapping("/class/{classId}")
    public void reImportClassStudentExcel(@PathVariable("classId") Long classId, @RequestParam("file") MultipartFile file, HttpServletResponse httpServletResponse) throws IOException {
        int status = klassService.importClassStudentExcel(file, classId);
        httpServletResponse.setStatus(status);
    }

    @DeleteMapping("/class/{classId}")
    public void deleteClass(@PathVariable("classId") Long classId, HttpServletResponse httpServletResponse)
    {
        klassService.deleteClass(classId);
        httpServletResponse.setStatus(200);
    }
}
