package au.gov.nsw.records.digitalarchive.metadata.web;

import au.gov.nsw.records.digitalarchive.metadata.model.ProfileCategory;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/profilecategorys")
@Controller
@RooWebScaffold(path = "profilecategorys", formBackingObject = ProfileCategory.class)
public class ProfileCategoryController {
}
