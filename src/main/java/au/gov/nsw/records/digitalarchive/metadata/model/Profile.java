package au.gov.nsw.records.digitalarchive.metadata.model;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
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
@RooJpaActiveRecord(finders = { "findProfilesByUsageNoteLikeOrLabelLike", "findProfilesByLabelEquals" })
public class Profile {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    private String label;

    @NotEmpty
    private String usageNote;

    private StandaloneType standalone = StandaloneType.Both;

    private ResourceType resourceType;

    @ManyToOne
    private au.gov.nsw.records.digitalarchive.metadata.model.Profile useWith;

    private String resourceURI;

    @ManyToOne
    private ProfileCategory category;
    
    private ValueType valueType;

    private String reference;

    private String uriEncodingScheme;

    private String chooseFrom;

    private String syntaxEncodingScheme;

    private int minimumOccurrence = 0;

    private int maximumOccurrence = 0;

    private String addedBy;

    @DateTimeFormat(style = "M-")
    private Date added;

    private String lastModifiedBy;

    @DateTimeFormat(style = "M-")
    private Date lastModified;

    private String deprecatedBy;

    @DateTimeFormat(style = "M-")
    private Date deprecated;

    public static TypedQuery<au.gov.nsw.records.digitalarchive.metadata.model.Profile> findProfilesByUsageNoteLikeOrLabelLike(String query) {
        if (query == null || query.length() == 0) {
            query = "*";
        }
        query = query.replace('*', '%');
        if (query.charAt(0) != '%') {
            query = "%" + query;
        }
        if (query.charAt(query.length() - 1) != '%') {
            query = query + "%";
        }
        EntityManager em = Profile.entityManager();
        TypedQuery<Profile> q = em.createQuery("SELECT o FROM Profile AS o WHERE LOWER(o.usageNote) LIKE LOWER(:query)  OR LOWER(o.label) LIKE LOWER(:query)", Profile.class);
        q.setParameter("query", query);
        return q;
    }
}
