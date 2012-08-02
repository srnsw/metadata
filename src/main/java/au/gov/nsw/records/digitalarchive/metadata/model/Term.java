package au.gov.nsw.records.digitalarchive.metadata.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findTermsByUsageNoteLikeOrLabelLike", "findTermsByLabelEquals" })
//@XStreamAlias("term") 
public class Term {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    private String label;

    @NotNull
    @NotEmpty
    private String usageNote;

    private ResourceType resourceType;

    private String domainURI;

    private String rangeURI;

    private String seeAlso;

    private String coinedBy;

    @DateTimeFormat(style = "M-")
    private Date coined;

    private String lastModifiedBy;

    @DateTimeFormat(style = "M-")
    private Date lastModified;

    private String deprecatedBy;

    @DateTimeFormat(style = "M-")
    private Date deprecated;

	public static TypedQuery<Term> findTermsByUsageNoteLikeOrLabelLike(String search) {
	    	if (search == null || search.length() == 0) {
	    		search = "*";
	    	}
	    	
        search = search.replace('*', '%');
        if (search.charAt(0) != '%') {
            search = "%" + search;
        }
        if (search.charAt(search.length() - 1) != '%') {
            search = search + "%";
        }
        
        EntityManager em = Term.entityManager();
        TypedQuery<Term> q = em.createQuery("SELECT o FROM Term AS o WHERE LOWER(o.usageNote) LIKE LOWER(:search)  OR LOWER(o.label) LIKE LOWER(:search)", Term.class);
        q.setParameter("search", search);
        return q;
    }
}
