package com.prefabsoft.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Index;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.prefabsoft.security.model.PrefabUser;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPrefabResourcesByFilePathEquals" })
public class PrefabResource {

    
    /**http://static.springsource.org/spring/docs/2.5.x/api/org/springframework/core/io/Resource.html
     * Method Summary
 Resource	createRelative(String relativePath) 
          Create a resource relative to this resource.
 boolean	exists() 
          Return whether this resource actually exists in physical form.
 String	getDescription() 
          Return a description for this resource, to be used for error output when working with the resource.
 File	getFile() 
          Return a File handle for this resource.
 String	getFilename() 
          Return a filename for this resource, i.e. typically the last part of the path: for example, "myfile.txt".
 URI	getURI() 
          Return a URI handle for this resource.
 URL	getURL() 
          Return a URL handle for this resource.
 boolean	isOpen() 
          Return whether this resource represents a handle with an open stream.
 boolean	isReadable() 
          Return whether the contents of this resource can be read, e.g. via InputStreamSource.getInputStream() or getFile().
 long	lastModified() 
          Determine the last-modified timestamp for this resource.
     */
    
    private static final Log log = LogFactory.getLog(PrefabResource.class);

    public PrefabResource() {
        super();
    }

    public PrefabResource(MultipartFile multipartFile, UserDetails owner) {
        super();
        if (owner != null) this.owner = PrefabUser.findPrefabUsersByEmailAddressLike(owner.getUsername()).getSingleResult();
        setName(multipartFile.getOriginalFilename());
        setContentType(multipartFile.getContentType());
        setSize(multipartFile.getSize());
        try {
            setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setActive(true);
    }

    public PrefabResource(MultipartFile multipartFile, String path, UserDetails userDetails) {
        super();
        if (owner != null) this.owner = PrefabUser.findPrefabUsersByEmailAddressLike(owner.getUsername()).getSingleResult();
        setName(multipartFile.getOriginalFilename());
        setFilePath(path);
        setContentType(multipartFile.getContentType());
        setSize(multipartFile.getSize());
        try {
            setContent(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setActive(true);
    }

    @ManyToOne
    private PrefabUser owner;

    @Size(max = 255)
    private String fileName;

    @Index(name = "filePathIndex")
    private String filePath;

    private String fileExtension;

    private String mediaType;

    private String mediaSubType;

    private Long size;
    
    private Integer permission;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    private Boolean active;

    @NotNull
    @Value("#{new java.util.Date()}")
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateUpdate;

    public String getName() {
        return fileName + "." + fileExtension;
    }

    public void setName(String name) {
        fileName = FilenameUtils.getBaseName(name);
        fileExtension = FilenameUtils.getExtension(name);
    }

    public String getContentType() {
        return mediaType + "/" + mediaSubType;
    }

    public void setContentType(String contentType) {
        mediaType = contentType.substring(0, contentType.indexOf('/'));
        mediaSubType = contentType.substring(contentType.indexOf('/') + 1, contentType.length());
    }

    @Override
    public String toString() {
        return "PrefabResource [owner=" + owner + ", fileName=" + fileName + ", fileExtension=" + fileExtension + ", contentMainType=" + mediaType + ", contentSubType=" + mediaSubType + ", size=" + size + ", content=" + Arrays.toString(content) + ", active=" + active + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

    @PreUpdate
    private void onUpdate() {
        dateUpdate = new Date();
    }
}
