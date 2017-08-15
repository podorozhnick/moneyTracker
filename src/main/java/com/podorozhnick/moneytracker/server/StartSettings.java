package com.podorozhnick.moneytracker.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.podorozhnick.moneytracker.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@Getter
@Setter
@ToString
@Slf4j
public class StartSettings {

    @JsonProperty
    private long tokenExpiration;
    @JsonProperty
    private String host;
    @JsonProperty
    private int port;

    @Value("${config.filePath}")
    @JsonIgnore
    private String path;

    @PostConstruct
    public void init() {
        StartSettings settings = JsonUtils.fromJsonFile(StartSettings.class, new File(path));
        BeanUtils.copyProperties(settings, this, "path");
        log.info(this.toString());
    }

}
