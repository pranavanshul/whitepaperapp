package com.cgi.whitepaperapp.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.
* */
@Data
@Entity
@Table(name="member_paper")
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt")),
        @SqlResultSetMapping(name = "SqlApprovedResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})

@NamedQueries({
        @NamedQuery(name = "WhitePaper.findOpenMsgs",
                query = "SELECT c FROM WhitePaper c WHERE c.status = :status"),
        @NamedQuery(name = "WhitePaper.findApprovedMsgs",
                query = "SELECT c FROM WhitePaper c WHERE c.status = :status"),
        @NamedQuery(name = "WhitePaper.updateMsgStatus",
                query = "UPDATE WhitePaper c SET c.status = ?1 WHERE c.whitePaperId = ?2")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "WhitePaper.findOpenMsgsNative",
                query = "SELECT * FROM member_paper c WHERE c.status = :status"
                ,resultClass = WhitePaper.class),
        @NamedNativeQuery(name = "WhitePaper.findApprovedMsgsNative",
                query = "SELECT * FROM member_paper c WHERE c.status = :status"
                ,resultClass = WhitePaper.class),
        @NamedNativeQuery(name = "WhitePaper.findOpenMsgsNative.count",
                query = "select count(*) as cnt from member_paper c where c.status = :status",
                resultSetMapping = "SqlResultSetMapping.count"),
        @NamedNativeQuery(name = "WhitePaper.findApprovedMsgsNative.count",
                query = "select count(*) as cnt from member_paper c where c.status = :status",
                resultSetMapping = "SqlApprovedResultSetMapping.count"),
        /*Spring Data JPA doesn’t support dynamic sorting for native queries.
        Doing that would require Spring Data to analyze the provided statement and generate
        the ORDER BY clause in the database-specific dialect. This would be a very complex operation
        and is currently not supported by Spring Data JPA.*/
        @NamedNativeQuery(name = "WhitePaper.updateMsgStatusNative",
                query = "UPDATE member_paper c SET c.status = ?1 WHERE c.whitepaper_id = ?2")
})
public class WhitePaper extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "whitepaper_id")
    private int whitePaperId;

    /*
    * @NotNull: Checks if a given field is not null but allows empty values & zero elements inside collections.
      @NotEmpty: Checks if a given field is not null and its size/length is greater than zero.
      @NotBlank: Checks if a given field is not null and trimmed length is greater than zero.
    * */
    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNum;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Subject must not be blank")
    @Size(min=5, message="Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message="Message must not be blank")
    @Size(min=10, message="Message must be at least 10 characters long")
    private String s3_url;

    private String status;

}