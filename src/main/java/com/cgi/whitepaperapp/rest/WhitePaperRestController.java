package com.cgi.whitepaperapp.rest;

import com.cgi.whitepaperapp.constants.WhitePaperAppConstants;
import com.cgi.whitepaperapp.model.WhitePaper;
import com.cgi.whitepaperapp.model.Response;
import com.cgi.whitepaperapp.repository.WhitePaperRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*")
public class WhitePaperRestController {

    @Autowired
    WhitePaperRepository whitePaperRepository;

    @GetMapping("/getMessagesByStatus")
    //@ResponseBody
    public List<WhitePaper> getMessagesByStatus(@RequestParam(name = "status")  String status){
        return whitePaperRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    //@ResponseBody
    public List<WhitePaper> getAllMsgsByStatus(@RequestBody WhitePaper contact){
        if(null != contact && null != contact.getStatus()){
            return whitePaperRepository.findByStatus(contact.getStatus());
        }else{
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    // @ResponseBody
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody WhitePaper contact){
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        whitePaperRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<WhitePaper> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        WhitePaper contact = requestEntity.getBody();
        whitePaperRepository.deleteById(contact.getWhitePaperId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody WhitePaper contactReq){
        Response response = new Response();
        Optional<WhitePaper> contact = whitePaperRepository.findById(contactReq.getWhitePaperId());
        if(contact.isPresent()){
            contact.get().setStatus(WhitePaperAppConstants.APPROVED);
            whitePaperRepository.save(contact.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/viewMsg")
    //@ResponseBody
    public Optional<WhitePaper> viewMessage(@RequestParam int whitePaperId){
        if(whitePaperId!=0){
            return whitePaperRepository.findById(whitePaperId);
        }else{
            return null;
        }
    }
}
