package org.example;

import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
//import lombok.Data;
//
//import java.io.UnsupportedEncodingException;
//
//@Data
public class CourseraConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

//    @NotEmpty
//    private String jwtTokenSecret = "fdg1j2k45df6lg3flj";
//
//    public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
//        return jwtTokenSecret.getBytes("UTF-8");
//    }

//    @NotEmpty
//    private String template;
//
//    @NotEmpty
//    private String defaultName = "Stranger";
//
//    @JsonProperty
//    public String getTemplate() {
//        return template;
//    }
//
//    @JsonProperty
//    public void setTemplate(String template) {
//        this.template = template;
//    }

//    @JsonProperty
//    public String getDefaultName() {
//        return defaultName;
//    }
//
//    @JsonProperty
//    public void setDefaultName(String name) {
//        this.defaultName = name;
//    }
}