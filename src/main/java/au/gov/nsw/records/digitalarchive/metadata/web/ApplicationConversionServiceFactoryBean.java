package au.gov.nsw.records.digitalarchive.metadata.web;


import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import au.gov.nsw.records.digitalarchive.metadata.model.Profile;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Profile, String> getProfileToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<au.gov.nsw.records.digitalarchive.metadata.model.Profile, java.lang.String>() {
			public String convert(Profile profile) {
				return profile.getLabel();
			}
		};
	}
}
