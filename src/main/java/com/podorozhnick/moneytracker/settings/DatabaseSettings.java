package com.podorozhnick.moneytracker.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@Setter
@ToString
@Component
@Slf4j
public class DatabaseSettings {

    @JsonProperty
    private String url;

    @JsonProperty
    private String driverClassName;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    private String dialect;

    @JsonProperty
    private boolean showSql;

    @JsonProperty
    private boolean formatSql;

    @JsonProperty
    private String hbmToDdl = "update";

    @Value("${database.config.path}")
    @JsonIgnore
    private String path;

    @Value("${database.default.config.path}")
    @JsonIgnore
    private String defaultPath;

    private Yaml yaml = new Yaml();

    @PostConstruct
    public void init() {
        try(InputStream in = Files.newInputStream(Paths.get(path))) {
            DatabaseSettings databaseSettings = yaml.loadAs(in, DatabaseSettings.class);
            updateSettings(databaseSettings);
        } catch (IOException e) {
            log.warn("Error in loading Database settings from %s", path);
            loadDefaultSettings();
        }
    }

    private void loadDefaultSettings() {
        try(InputStream in = Files.newInputStream(Paths.get(defaultPath))) {
            DatabaseSettings databaseSettings = yaml.loadAs(in, DatabaseSettings.class);
            updateSettings(databaseSettings);
        } catch (IOException e) {
            log.error("Error in loading Default Database settings from %s", defaultPath);
        }
    }

    private void updateSettings(DatabaseSettings databaseSettings) {
        BeanUtils.copyProperties(databaseSettings, this);
    }

}
