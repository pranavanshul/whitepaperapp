package com.cgi.whitepaperapp.service;

import com.cgi.whitepaperapp.constants.WhitePaperAppConstants;
import com.cgi.whitepaperapp.model.Person;
import com.cgi.whitepaperapp.model.Roles;
import com.cgi.whitepaperapp.repository.PersonRepository;
import com.cgi.whitepaperapp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(WhitePaperAppConstants.BASIC_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
