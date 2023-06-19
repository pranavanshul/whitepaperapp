package com.cgi.whitepaperapp.repository;

import com.cgi.whitepaperapp.model.WhitePaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface WhitePaperRepository extends JpaRepository<WhitePaper, Integer> {

    List<WhitePaper> findByStatus(String status);

    @Query("SELECT c FROM WhitePaper c WHERE c.status = :status")
    //@Query(value = "SELECT * FROM member_paper c WHERE c.status = :status",nativeQuery = true)
    Page<WhitePaper> findByStatusWithQuery(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE WhitePaper c SET c.status = ?1 WHERE c.whitePaperId = ?2")
    int updateStatusById(String status, int id);

    Page<WhitePaper> findOpenMsgs(@Param("status") String status, Pageable pageable);
    Page<WhitePaper> findApprovedMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<WhitePaper> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Query(nativeQuery = true)
    Page<WhitePaper> findApprovedMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);

}
