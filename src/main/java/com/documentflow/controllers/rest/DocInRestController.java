package com.documentflow.controllers.rest;

import com.documentflow.entities.dto.DocInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/docIn")
public class DocInRestController {

    private DocInRestControllerFacade dircFacade;

    @Autowired
    public DocInRestController(DocInRestControllerFacade dircFacade) {
        this.dircFacade = dircFacade;
    }

    @GetMapping("")
    public ResponseEntity<Page<DocInDto>> getPage(HttpServletRequest request,
                                                  @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        return dircFacade.getPage(request, currentPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocInDto> getDocIn(@PathVariable("id") Long id, Principal principal) {
        return dircFacade.getDocIn(id, principal.getName());
    }

    @PostMapping("")
    public ResponseEntity<?> saveDocIn(@ModelAttribute(name = "doc") DocInDto docInDto) {
        return dircFacade.saveDocIn(docInDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocIn(@PathVariable("id") Long id) {
        return dircFacade.deleteDocIn(id);
    }

    @GetMapping("/file/{id}")
    public void getFile(@PathVariable("id") Long id, HttpServletResponse response) {
        dircFacade.getFile(id, response);
    }
}
