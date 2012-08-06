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
import org.springframework.web.bind.annotation.ResponseBody;

import au.gov.nsw.records.digitalarchive.metadata.model.Profile;
import au.gov.nsw.records.digitalarchive.metadata.model.ResourceType;
import au.gov.nsw.records.digitalarchive.metadata.model.ValueType;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RequestMapping("/profiles")
@Controller
@RooWebScaffold(path = "profiles", formBackingObject = Profile.class)
public class ProfileController {

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Profile profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, profile);
			return "profiles/create";
		}

		try{
			Profile.findProfilesByLabelEquals(profile.getLabel()).getSingleResult();
			bindingResult.addError(new ObjectError("label", String.format("Label '%s' already exist", profile.getLabel())));
			populateEditForm(uiModel, profile);
			return "profiles/create";
		}catch(EmptyResultDataAccessException e){
			// not found, good
		}

		profile = conditionalCleanUpFields(profile);

		// auto populate added and added by
		profile.setAdded(new Date());
		profile.setAddedBy(SecurityContextHolder.getContext().getAuthentication().getName());

		uiModel.asMap().clear();
		profile.persist();
		return "redirect:/profiles/" + encodeUrlPathSegment(profile.getLabel(), httpServletRequest);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Profile profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {

		Profile original = Profile.findProfile(Long.parseLong(httpServletRequest.getParameter("id")));
		// auto populate deprecated by
		if (profile.getDeprecatedBy()!=null && !profile.getDeprecatedBy().isEmpty()){
			
			profile = original;
			profile.setDeprecated(new Date());
			profile.setDeprecatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
		}else{
			if (bindingResult.hasErrors()) {
				populateEditForm(uiModel, profile);
				return "profiles/update";
			}

			profile = conditionalCleanUpFields(profile);

			// retain original
			profile.setAdded(original.getAdded());
			profile.setAddedBy(original.getAddedBy());
			
			// auto populate updated and updated by
			profile.setLastModified(new Date());
			profile.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());          
		}
		uiModel.asMap().clear();
		profile.merge();
		return "redirect:/profiles/" + encodeUrlPathSegment(profile.getLabel(), httpServletRequest);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") String label, Model uiModel) {
		try{
			populateEditForm(uiModel, Profile.findProfilesByLabelEquals(label).getSingleResult());
		}catch(EmptyResultDataAccessException e){
	  	// not found
	  }
		return "profiles/update";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		// no deletion
		return "redirect:/profiles";
	}

	@RequestMapping(value = "/{id}.xml", method = RequestMethod.GET, produces = "application/xml")
	public  @ResponseBody String xml(@PathVariable("id") String label, Model uiModel) {
		Profile prof = Profile.findProfilesByLabelEquals(label).getSingleResult();
		XStream xStream = new XStream(new DomDriver());
		
		xStream.alias("profile", Profile.class);
		//xStream.aliasField("minOccurence", Profile.class, "minimumOccurrence");
		//xStream.useAttributeFor(Profile.class, "minimumOccurrence");
		return xStream.toXML(prof);
	}
	
	private Profile conditionalCleanUpFields(Profile profile){
		if (profile.getResourceType() == ResourceType.Class){
			profile.setUseWith(null);
			profile.setValueType(null);

			profile.setReference(null);
			profile.setUriEncodingScheme(null);
			profile.setChooseFrom(null);
			profile.setSyntaxEncodingScheme(null);

		} else {
			profile.setStandalone(null);
			if (profile.getValueType() == ValueType.ProfileReference ){
				profile.setUriEncodingScheme(null);
				profile.setChooseFrom(null);
				profile.setSyntaxEncodingScheme(null);
			}else if (profile.getValueType() == ValueType.NonLiteral ){
				profile.setReference(null);
				profile.setChooseFrom(null);
				profile.setSyntaxEncodingScheme(null);
			}else {
				profile.setReference(null);
				profile.setUriEncodingScheme(null);
			}
		}
		return profile;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
  public String search(@RequestParam("q") String query, Model model) {
      model.addAttribute("profiles", Profile.findProfilesByUsageNoteLikeOrLabelLike(query).getResultList());
      model.addAttribute("q", query);
      return "profiles/list";
  }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String label, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        try{
	        uiModel.addAttribute("profile", Profile.findProfilesByLabelEquals(label).getSingleResult());
	        uiModel.addAttribute("itemId", Profile.findProfilesByLabelEquals(label).getSingleResult().getId());
        }catch(EmptyResultDataAccessException e){
		    	// not found
		    }
        return "profiles/show";
    }
}
