package com.cgi.whitepaperapp.service;

import com.cgi.whitepaperapp.config.WhitePaperProps;
import com.cgi.whitepaperapp.constants.WhitePaperAppConstants;
import com.cgi.whitepaperapp.model.WhitePaper;
import com.cgi.whitepaperapp.repository.WhitePaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
public class WhitePaperService {

    @Autowired
    private WhitePaperRepository whitePaperRepository;

    @Autowired
    WhitePaperProps whitePaperProps;

    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(WhitePaper contact){
        boolean isSaved = false;
        contact.setStatus(WhitePaperAppConstants.SUBMITTED);
        WhitePaper savedContact = whitePaperRepository.save(contact);
        if(null != savedContact && savedContact.getWhitePaperId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<WhitePaper> findMsgsWithOpenStatus(int pageNum,String sortField, String sortDir){
        int pageSize = whitePaperProps.getPageSize();
        log.info("findMsgsWithOpenStatus method in ContactService ");
        log.info("pageNum:"+pageNum);
        log.info("sortField:"+sortField);
        log.info("sortDir:"+sortDir);
        if(null!= whitePaperProps.getWhitepaper() && null!= whitePaperProps.getWhitepaper().get("pageSize")){
            pageSize = Integer.parseInt(whitePaperProps.getWhitepaper().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        log.info("calling contactRepository. findByStatusWithQuery with SUBMITTED stated");
        Page<WhitePaper> msgPage = whitePaperRepository.findByStatusWithQuery(
                WhitePaperAppConstants.SUBMITTED,pageable);
        log.info("returning msgPage from ContactService");
        return msgPage;
    }

    public Page<WhitePaper> findMsgsWithApprovedStatus(int pageNum,String sortField, String sortDir){
        int pageSize = whitePaperProps.getPageSize();
        if(null!= whitePaperProps.getWhitepaper() && null!= whitePaperProps.getWhitepaper().get("pageSize")){
            pageSize = Integer.parseInt(whitePaperProps.getWhitepaper().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        log.info("calling contactRepository. findByStatusWithQuery with SUBMITTED stated");
        Page<WhitePaper> msgPage = whitePaperRepository.findByStatusWithQuery(
                WhitePaperAppConstants.APPROVED,pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int whitePaperId){
        boolean isUpdated = false;
        int rows = whitePaperRepository.updateMsgStatusNative(WhitePaperAppConstants.APPROVED,whitePaperId);
        if(rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }

}
