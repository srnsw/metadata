package au.gov.nsw.records.digitalarchive.metadata.web;

import java.text.SimpleDateFormat;

import au.gov.nsw.records.digitalarchive.metadata.model.Profile;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ProfileConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(Profile.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Profile profile = (Profile) value;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
	  
		writer.addAttribute("att", "test attribute"); 
		writer.startNode("label");
    //writer.setValue(profile.getLabel());
	    writer.startNode("added");
	    writer.setValue(formatter.format(profile.getAdded()));
	    writer.endNode();
    writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		// don't use
		return null;
	}

}
