package com.documentflow.controllers;

import com.documentflow.services.DocOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.documentflow.entities.DocOut;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class PdfController {

    @Autowired
    private DocOutService docOutService;

    @Autowired
    ServletContext context;

    @GetMapping(value="/")
    public String allDocOuts(Model model) {
        List<DocOut> docOuts = docOutService.findAll();
        model.addAttribute("DocOut", docOuts);
        return "view/docOuts";
    }

    @GetMapping(value = "/createPdf")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) {
        List<DocOut> docOuts = docOutService.findAll();
        boolean isFlag = docOutService.createPdf(docOuts, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "docOuts" + ".pdf");
            filedownload(fullPath, response, "docOuts.pdf");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filenamr=" + fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
