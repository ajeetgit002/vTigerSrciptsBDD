package evs.vTiger.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	Properties configpro;
	
public	Config() {
	configpro=new Properties();
	try {
		FileInputStream file=new FileInputStream("configuration.properties");
		configpro.load(file);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
}
	public String getBrowser() {
		String browsername =configpro.getProperty("browser");
		if(browsername!=null) {
			return browsername;
		}else {
			
		 throw new RuntimeException("Browser is not found");
		}
		
	}
	
	

	
}
