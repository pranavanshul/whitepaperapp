package com.cgi.whitepaperapp.repository;

import com.cgi.whitepaperapp.model.WhitePaperClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitePaperClassRepository extends JpaRepository<WhitePaperClass, Integer> {

}
