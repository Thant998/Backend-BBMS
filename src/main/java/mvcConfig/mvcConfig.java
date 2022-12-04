package mvcConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer{
	 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path fileUploadDir =Paths.get("./Complete-BBMS-master/src/main/resources/static/");
        String fileUploadPath = fileUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/Complete-BBMS-master/src/main/resources/**").addResourceLocations("file:/"+ fileUploadPath + "/");
    }
}
