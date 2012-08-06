package au.gov.nsw.records.digitalarchive.metadata.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.gov.nsw.records.digitalarchive.metadata.model.Profile;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RequestMapping("/profiles.xml")
@Controller
public class ProfileToXmlController {

	@RequestMapping(method = RequestMethod.GET, produces = "application/xml")
	public  @ResponseBody String xmlx(Model uiModel) {
		List<Profile> profs = Profile.findAllProfiles();
		XStream xStream = new XStream(new DomDriver());
		// xStream.registerConverter(new ProfileConverter());
		
		xStream.alias("profiles", List.class);
		xStream.alias("profile", Profile.class);
		//xStream.aliasField("minOccurence", Profile.class, "minimumOccurrence");
		//xStream.useAttributeFor(Profile.class, "minimumOccurrence");
		
		return xStream.toXML(profs);
	}
}
