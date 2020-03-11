package com.documentflow.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.documentflow.entities.DocOut;
import com.documentflow.entities.User;
import com.documentflow.repositories.DocOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DocOutService {

    private DocOutRepository docOutRepository;

    @Autowired
    public void setDocOutRepository(DocOutRepository docOutRepository) {
        this.docOutRepository = docOutRepository;
    }

    public DocOut findOneById(Long id) {
        return docOutRepository.findOneById(id);
    }

    public List<DocOut> findAll() {
        return docOutRepository.findAll();
    }

    public Page<DocOut> findAll(Pageable pageable) {
        return docOutRepository.findAll(pageable);
    }

    public User getByCreator(User user) {
        return docOutRepository.getByCreator(user);
    }

    public Page<DocOut> findAllByPagingAndFiltering(Specification<DocOut> specification, Pageable pageable) {
        return docOutRepository.findAll(specification, pageable);
    }

    public <S extends DocOut> S save(S s) {
        return docOutRepository.save(s);
    }

//    public Page<DocOut> findAllByCreator(User creator, Pageable pageable){
//        return docOutRepository.findAllByCreator(creator, pageable);
//    }
//
//    public Page<DocOut> findAllBySigner(User signer, Pageable pageable){
//        return docOutRepository.findAllBySigner(signer, pageable);
//    }
//
//    public Page<DocOut> findAllByCreateDate (LocalDate createDate, Pageable pageable){
//        return docOutRepository.findAllByCreateDate(createDate, pageable);
//    }
//
//    public Page<DocOut> findAllByRegDate (LocalDate regDate, Pageable pageable){
//        return docOutRepository.findAllByRegDate(regDate, pageable);
//    }
//
//    public Page<DocOut> findAllByContent (String content, Pageable pageable){
//        return docOutRepository.findAllByContent(content, pageable);
//    }
//
//    public Page<DocOut> findAllByAppendix (String appendix, Pageable pageable){
//        return docOutRepository.findAllByAppendix(appendix, pageable);
//    }
//
//    public Page<DocOut> findAllByNote (String note, Pageable pageable){
//        return docOutRepository.findAllByNote(note, pageable);
//    }
//
//    public Page<DocOut> findAllByIsGenerated (Boolean isGenerated, Pageable pageable){
//        return docOutRepository.findAllByIsGenerated(isGenerated, pageable);
//    }
//
//    public Page<DocOut> findAllByNumber (String number, Pageable pageable){
//        return docOutRepository.findAllByNumber(number, pageable);
//    }
//
//    public Page<DocOut> findAllByState (State state, Pageable pageable){
//        return docOutRepository.findAllByState(state, pageable);
//    }
//
//    public Page<DocOut> findAllByTask (Task task, Pageable pageable){
//        return docOutRepository.findAllByTask(task, pageable);
//    }


//    public DocOut save(DocOutDTO docOutDTO) {
//        DocOut docOut=new DocOut();
//        docOut.setCreator(docOutDTO.getCreator());
//        docOut.setSigner(docOutDTO.getSigner());
//        docOut.setDocType(docOutDTO.getDocType());
//        docOut.setContent(docOutDTO.getContent());
//        docOut.setPages(docOutDTO.getPages());
//        docOut.setIsGenerated(docOutDTO.getIsGenerated());
//        docOut.setAppendix(docOutDTO.getAppendix());
//        docOut.setNote(docOutDTO.getNote());
//        return docOutRepository.save(docOut);
//
//
////        User creator=docOutDTO.getCreator();
////        User signer=docOutDTO.getSigner();
////        String content = docOutDTO.getContent();
////        Integer pages=docOutDTO.getPages();
////        String appendix = docOutDTO.getAppendix();
////        String note = docOutDTO.getNote();
////        State state = docOutDTO.getState();
////
////        DocOut docOut=new DocOut(creator, signer, content, pages, appendix, note, state);
//  //          return docOutRepository.save(docOut);
//    }

    public void deleteById(Long id) {
        docOutRepository.deleteById(id);
    }

    public void delete(DocOut docOut) {
        docOutRepository.delete(docOut);
    }

//    public DocOut update(DocOutDTO docOutDTO){
//        DocOut docOut=docOutDTO.convertToDocOut();
////        docOut.setCreator(docOutDTO.getCreator());
////        docOut.setSigner(docOutDTO.getSigner());
////        docOut.setDocType(docOutDTO.getDocType());
////        docOut.setContent(docOutDTO.getContent());
////        docOut.setPages(docOutDTO.getPages());
////        docOut.setIsGenerated(docOutDTO.getIsGenerated());
////        docOut.setAppendix(docOutDTO.getAppendix());
////        docOut.setNote(docOutDTO.getNote());
//        return docOutRepository.save(docOut);
//    }

    public boolean createPdf(List<DocOut> docOuts, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "docOuts" + ".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("All employees", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(200);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell first = new PdfPCell(new Paragraph("First name", tableHeader));
            first.setBorderColor(BaseColor.BLACK);
            first.setPaddingLeft(10);
            first.setHorizontalAlignment(Element.ALIGN_CENTER);
            first.setVerticalAlignment(Element.ALIGN_CENTER);
            first.setBackgroundColor(BaseColor.GRAY);
            first.setExtraParagraphSpace(5f);
            table.addCell(first);

            PdfPCell last = new PdfPCell(new Paragraph("Last name", tableHeader));
            last.setBorderColor(BaseColor.BLACK);
            last.setPaddingLeft(10);
            last.setHorizontalAlignment(Element.ALIGN_CENTER);
            last.setVerticalAlignment(Element.ALIGN_CENTER);
            last.setBackgroundColor(BaseColor.GRAY);
            last.setExtraParagraphSpace(5f);
            table.addCell(last);

            PdfPCell email = new PdfPCell(new Paragraph("E-mail", tableHeader));
            email.setBorderColor(BaseColor.BLACK);
            email.setPaddingLeft(10);
            email.setHorizontalAlignment(Element.ALIGN_CENTER);
            email.setVerticalAlignment(Element.ALIGN_CENTER);
            email.setBackgroundColor(BaseColor.GRAY);
            email.setExtraParagraphSpace(5f);
            table.addCell(email);

            PdfPCell phone = new PdfPCell(new Paragraph("Phone number", tableHeader));
            phone.setBorderColor(BaseColor.BLACK);
            phone.setPaddingLeft(10);
            phone.setHorizontalAlignment(Element.ALIGN_CENTER);
            phone.setVerticalAlignment(Element.ALIGN_CENTER);
            phone.setBackgroundColor(BaseColor.GRAY);
            phone.setExtraParagraphSpace(5f);
            table.addCell(phone);

            for (DocOut docOut : docOuts) {

                PdfPCell firstValue = new PdfPCell(new Paragraph(docOut.getContent(), tableBody));
                firstValue.setBorderColor(BaseColor.BLACK);
                firstValue.setPaddingLeft(10);
                firstValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                firstValue.setVerticalAlignment(Element.ALIGN_CENTER);
                firstValue.setBackgroundColor(BaseColor.WHITE);
                firstValue.setExtraParagraphSpace(5f);
                table.addCell(firstValue);

//                PdfPCell lastValue = new PdfPCell(new Paragraph(docOut.getLastNmae(), tableBody));
//                lastValue.setBorderColor(BaseColor.BLACK);
//                lastValue.setPaddingLeft(10);
//                lastValue.setHorizontalAlignment(Element.ALIGN_CENTER);
//                lastValue.setVerticalAlignment(Element.ALIGN_CENTER);
//                lastValue.setBackgroundColor(BaseColor.WHITE);
//                lastValue.setExtraParagraphSpace(5f);
//                table.addCell(lastValue);
//
//                PdfPCell emailValue = new PdfPCell(new Paragraph(docOut.getEmail(), tableBody));
//                emailValue.setBorderColor(BaseColor.BLACK);
//                emailValue.setPaddingLeft(10);
//                emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
//                emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
//                emailValue.setBackgroundColor(BaseColor.WHITE);
//                emailValue.setExtraParagraphSpace(5f);
//                table.addCell(emailValue);
//
//                PdfPCell phoneValue = new PdfPCell(new Paragraph(docOut.getPhoneNumber(), tableBody));
//                phoneValue.setBorderColor(BaseColor.BLACK);
//                phoneValue.setPaddingLeft(10);
//                phoneValue.setHorizontalAlignment(Element.ALIGN_CENTER);
//                phoneValue.setVerticalAlignment(Element.ALIGN_CENTER);
//                phoneValue.setBackgroundColor(BaseColor.WHITE);
//                phoneValue.setExtraParagraphSpace(5f);
//                table.addCell(phoneValue);
            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}



















