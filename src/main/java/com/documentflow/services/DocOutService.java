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

    public boolean createOnePdf(Long id, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        DocOut docOut = docOutRepository.findOneById(id);

        Document document = new Document(PageSize.A4, 15, 15, 45, 30);

        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "docOuts" + ".pdf"));
            BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Font font2 = new Font(times, 16, Font.NORMAL, BaseColor.BLACK);
            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Out Document № " + docOut.getId().toString(), mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5);
            table.setSpacingAfter(5);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell one = new PdfPCell(new Paragraph("Place for logo", tableHeader));
            one.setBorderColor(BaseColor.BLACK);
            one.setPaddingLeft(10);
            one.setBorderWidth(0);
            one.setHorizontalAlignment(Element.ALIGN_CENTER);
            one.setVerticalAlignment(Element.ALIGN_CENTER);
            one.setBackgroundColor(BaseColor.GRAY);
            one.setExtraParagraphSpace(5f);
            table.addCell(one);

            PdfPCell two = new PdfPCell(new Paragraph("        ", tableHeader));
            two.setBorderColor(BaseColor.BLACK);
            two.setPaddingLeft(10);
            two.setBorderWidth(0);
            two.setHorizontalAlignment(Element.ALIGN_CENTER);
            two.setVerticalAlignment(Element.ALIGN_CENTER);
            two.setBackgroundColor(BaseColor.GRAY);
            two.setExtraParagraphSpace(5f);
            table.addCell(two);

            PdfPCell three = new PdfPCell(new Paragraph(docOut.getState().toString(), tableHeader));
            three.setBorderColor(BaseColor.BLACK);
            three.setPaddingLeft(10);
            three.setBorderWidth(0);
            three.setHorizontalAlignment(Element.ALIGN_CENTER);
            three.setVerticalAlignment(Element.ALIGN_CENTER);
            three.setBackgroundColor(BaseColor.GRAY);
            three.setExtraParagraphSpace(5f);
            table.addCell(three);


            PdfPCell four = new PdfPCell(new Paragraph("            ", tableBody));
            four.setBorderColor(BaseColor.BLACK);
            four.setPaddingLeft(10);
            four.setBorderWidth(0);
            four.setHorizontalAlignment(Element.ALIGN_CENTER);
            four.setVerticalAlignment(Element.ALIGN_CENTER);
            four.setBackgroundColor(BaseColor.WHITE);
            four.setExtraParagraphSpace(5f);
            table.addCell(four);

            PdfPCell five = new PdfPCell(new Paragraph(docOut.getContent(), tableBody));
            five.setBorderColor(BaseColor.BLACK);
            five.setPaddingLeft(10);
            five.setBorderWidth(0);
            five.setFixedHeight(70);
            five.setHorizontalAlignment(Element.ALIGN_CENTER);
            five.setVerticalAlignment(Element.ALIGN_CENTER);
            five.setBackgroundColor(BaseColor.WHITE);
            five.setExtraParagraphSpace(5f);
            table.addCell(five);

            PdfPCell six = new PdfPCell(new Paragraph("           ", tableBody));
            six.setBorderColor(BaseColor.BLACK);
            six.setPaddingLeft(10);
            six.setBorderWidth(0);
            six.setHorizontalAlignment(Element.ALIGN_CENTER);
            six.setVerticalAlignment(Element.ALIGN_CENTER);
            six.setBackgroundColor(BaseColor.WHITE);
            six.setExtraParagraphSpace(5f);
            table.addCell(six);

            PdfPCell seven = new PdfPCell(new Paragraph(docOut.getCreateDate().toString(), tableBody));
            seven.setBorderColor(BaseColor.BLACK);
            seven.setPaddingLeft(10);
            seven.setBorderWidth(0);
            seven.setHorizontalAlignment(Element.ALIGN_CENTER);
            seven.setVerticalAlignment(Element.ALIGN_CENTER);
            seven.setBackgroundColor(BaseColor.WHITE);
            seven.setExtraParagraphSpace(5f);
            table.addCell(seven);

            PdfPCell eight = new PdfPCell(new Paragraph("                ", tableBody));
            eight.setBorderColor(BaseColor.BLACK);
            eight.setPaddingLeft(10);
            eight.setBorderWidth(0);
            eight.setHorizontalAlignment(Element.ALIGN_CENTER);
            eight.setVerticalAlignment(Element.ALIGN_CENTER);
            eight.setBackgroundColor(BaseColor.WHITE);
            eight.setExtraParagraphSpace(5f);
            table.addCell(eight);

            PdfPCell nine = new PdfPCell(new Paragraph(docOut.getSigner().toString(), tableBody));
            nine.setBorderColor(BaseColor.BLACK);
            nine.setPaddingLeft(10);
            nine.setBorderWidth(0);
            nine.setHorizontalAlignment(Element.ALIGN_CENTER);
            nine.setVerticalAlignment(Element.ALIGN_CENTER);
            nine.setBackgroundColor(BaseColor.WHITE);
            nine.setExtraParagraphSpace(5f);
            table.addCell(nine);

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}



















