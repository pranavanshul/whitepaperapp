package com.cgi.whitepaperapp.controller;

import com.cgi.whitepaperapp.model.*;
import com.cgi.whitepaperapp.repository.WhitePaperClassRepository;
import com.cgi.whitepaperapp.repository.PersonRepository;
import com.cgi.whitepaperapp.repository.WhitePaperClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    WhitePaperClassRepository whitePaperClassRepository;

    @Autowired
    PersonRepository personRepository;










}
