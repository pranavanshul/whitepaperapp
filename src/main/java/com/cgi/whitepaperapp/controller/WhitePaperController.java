package com.cgi.whitepaperapp.controller;

import com.cgi.whitepaperapp.model.WhitePaper;
import com.cgi.whitepaperapp.service.WhitePaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
public class WhitePaperController {

    private final WhitePaperService whitePaperService;

    @Autowired
    public WhitePaperController(WhitePaperService whitePaperService) {
        this.whitePaperService = whitePaperService;
    }





    /*@RequestMapping(value = "/saveMsg",method = POST)
    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum,
                                    @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
        log.info("Name : " + name);
        log.info("Mobile Number : " + mobileNum);
        log.info("Email Address : " + email);
        log.info("Subject : " + subject);
        log.info("Message : " + message);
        return new ModelAndView("redirect:/contact");
    }*/



    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<WhitePaper> paperPage = whitePaperService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<WhitePaper> whitePapers = paperPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", paperPage.getTotalPages());
        model.addAttribute("totalMsgs", paperPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("whitePapers",whitePapers);
        return modelAndView;
    }

    @RequestMapping("/displayApprovedMessages/page/{pageNum}")
    public ModelAndView displayApprovedMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,@RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<WhitePaper> paperPage = whitePaperService.findMsgsWithApprovedStatus(pageNum,sortField,sortDir);
        List<WhitePaper> whitePapers = paperPage.getContent();
        ModelAndView modelAndView = new ModelAndView("viewMessages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", paperPage.getTotalPages());
        model.addAttribute("totalMsgs", paperPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("whitePapers",whitePapers);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id) {
        whitePaperService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }



}
