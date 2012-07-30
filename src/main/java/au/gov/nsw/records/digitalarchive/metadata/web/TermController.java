package au.gov.nsw.records.digitalarchive.metadata.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.nsw.records.digitalarchive.metadata.model.ResourceType;
import au.gov.nsw.records.digitalarchive.metadata.model.Term;

@RequestMapping("/terms")
@Controller
@RooWebScaffold(path = "terms", formBackingObject = Term.class)
public class TermController {

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Term term, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, term);
            return "terms/create";
        }
        
        try{
        	Term.findTermsByLabelEquals(term.getLabel()).getSingleResult();
        	bindingResult.addError(new ObjectError("label", String.format("Label '%s' already exist", term.getLabel())));
          populateEditForm(uiModel, term);
          return "terms/create";
        }catch(EmptyResultDataAccessException e){
    			// not found, good
    		}
        
        if (term.getResourceType() == ResourceType.Class){
        	term.setDomainURI(null);
        	term.setRangeURI(null);
        }
        
        // auto populate coined and coined by
        term.setCoined(new Date());
        term.setCoinedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        uiModel.asMap().clear();
        term.persist();
        return "redirect:/terms/" + encodeUrlPathSegment(term.getLabel(), httpServletRequest);
    }

	@Secured("ROLE_ADMIN")
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Term());
        return "terms/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String label, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        try{
	        uiModel.addAttribute("term", Term.findTermsByLabelEquals(label).getSingleResult());
	        uiModel.addAttribute("itemId", Term.findTermsByLabelEquals(label).getSingleResult().getId());
        }catch(EmptyResultDataAccessException e){
        	// not found
        }
        return "terms/show";
    }
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Term term, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        
				Term original = Term.findTerm(Long.parseLong(httpServletRequest.getParameter("id")));
        // auto populate deprecated by
        if (term.getDeprecatedBy()!=null && !term.getDeprecatedBy().isEmpty()){
        	term = original;
        	term.setDeprecated(new Date());
          term.setDeprecatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
          
        }else{
        	if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, term);
            return "terms/create";
        	}
        	
	        if (term.getResourceType() == ResourceType.Class){
	        	term.setDomainURI(null);
	        	term.setRangeURI(null);
	        }
	        
	        // retain coined and coined by
	        term.setCoined(original.getCoined());
	        term.setCoinedBy(original.getCoinedBy());
	        
        	// auto populate updated and updated by
          term.setLastModified(new Date());
          term.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());          
        }
        
        uiModel.asMap().clear();
        term.merge();
        return "redirect:/terms/" + encodeUrlPathSegment(term.getLabel(), httpServletRequest);
    }
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") String label, Model uiModel) {
				try{
		        populateEditForm(uiModel, Term.findTermsByLabelEquals(label).getSingleResult());
				}catch(EmptyResultDataAccessException e){
		    	// not found
		    }
        return "terms/update";
    }
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        // no deletion
        return "redirect:/terms";
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
  public String search(@RequestParam("q") String query, Model model) {
      model.addAttribute("terms", Term.findTermsByUsageNoteLikeOrLabelLike(query).getResultList());
      model.addAttribute("q", query);
      return "terms/list";
  }
	
}
