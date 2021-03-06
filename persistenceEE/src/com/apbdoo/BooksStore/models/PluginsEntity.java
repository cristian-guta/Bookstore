package com.apbdoo.BooksStore.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "PLUGINS", schema = "information_schema", catalog = "")
public class PluginsEntity {
    private String pluginName;
    private String pluginVersion;
    private String pluginStatus;
    private String pluginType;
    private String pluginTypeVersion;
    private String pluginLibrary;
    private String pluginLibraryVersion;
    private String pluginAuthor;
    private String pluginDescription;
    private String pluginLicense;
    private String loadOption;

    @Basic
    @Column(name = "PLUGIN_NAME")
    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Basic
    @Column(name = "PLUGIN_VERSION")
    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    @Basic
    @Column(name = "PLUGIN_STATUS")
    public String getPluginStatus() {
        return pluginStatus;
    }

    public void setPluginStatus(String pluginStatus) {
        this.pluginStatus = pluginStatus;
    }

    @Basic
    @Column(name = "PLUGIN_TYPE")
    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    @Basic
    @Column(name = "PLUGIN_TYPE_VERSION")
    public String getPluginTypeVersion() {
        return pluginTypeVersion;
    }

    public void setPluginTypeVersion(String pluginTypeVersion) {
        this.pluginTypeVersion = pluginTypeVersion;
    }

    @Basic
    @Column(name = "PLUGIN_LIBRARY")
    public String getPluginLibrary() {
        return pluginLibrary;
    }

    public void setPluginLibrary(String pluginLibrary) {
        this.pluginLibrary = pluginLibrary;
    }

    @Basic
    @Column(name = "PLUGIN_LIBRARY_VERSION")
    public String getPluginLibraryVersion() {
        return pluginLibraryVersion;
    }

    public void setPluginLibraryVersion(String pluginLibraryVersion) {
        this.pluginLibraryVersion = pluginLibraryVersion;
    }

    @Basic
    @Column(name = "PLUGIN_AUTHOR")
    public String getPluginAuthor() {
        return pluginAuthor;
    }

    public void setPluginAuthor(String pluginAuthor) {
        this.pluginAuthor = pluginAuthor;
    }

    @Basic
    @Column(name = "PLUGIN_DESCRIPTION")
    public String getPluginDescription() {
        return pluginDescription;
    }

    public void setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
    }

    @Basic
    @Column(name = "PLUGIN_LICENSE")
    public String getPluginLicense() {
        return pluginLicense;
    }

    public void setPluginLicense(String pluginLicense) {
        this.pluginLicense = pluginLicense;
    }

    @Basic
    @Column(name = "LOAD_OPTION")
    public String getLoadOption() {
        return loadOption;
    }

    public void setLoadOption(String loadOption) {
        this.loadOption = loadOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginsEntity that = (PluginsEntity) o;
        return Objects.equals(pluginName, that.pluginName) &&
                Objects.equals(pluginVersion, that.pluginVersion) &&
                Objects.equals(pluginStatus, that.pluginStatus) &&
                Objects.equals(pluginType, that.pluginType) &&
                Objects.equals(pluginTypeVersion, that.pluginTypeVersion) &&
                Objects.equals(pluginLibrary, that.pluginLibrary) &&
                Objects.equals(pluginLibraryVersion, that.pluginLibraryVersion) &&
                Objects.equals(pluginAuthor, that.pluginAuthor) &&
                Objects.equals(pluginDescription, that.pluginDescription) &&
                Objects.equals(pluginLicense, that.pluginLicense) &&
                Objects.equals(loadOption, that.loadOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pluginName, pluginVersion, pluginStatus, pluginType, pluginTypeVersion, pluginLibrary, pluginLibraryVersion, pluginAuthor, pluginDescription, pluginLicense, loadOption);
    }
}
