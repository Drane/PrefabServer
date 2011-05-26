package com.prefabsoft.security.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.extensions.validator.crossval.annotation.Equals;
import org.apache.myfaces.extensions.validator.crossval.annotation.MessageTarget;
import org.hibernate.validator.constraints.Email;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RooJavaBean
@RooToString
@Component
@Scope("session")
@RooEntity(finders = { "findPrefabUsersByEmailAddressLike", "findPrefabUsersByUserNameEquals", "findPrefabUsersByEmailAddressEquals" })
public class PrefabUser implements UserDetails {

    private static final Log logger = LogFactory.getLog(PrefabUser.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(unique = true)
    @Pattern(regexp = "^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$", message = "Only letters, digits, dots, underscores and hyphens allowed!")
    private String userName;

    @NotNull
    @Email
    @Size(min = 6, max = 256)
    @Column(unique = true)
    private String emailAddress;

    @Transient
    @Equals(value = "emailAddress", validationErrorMsgTarget = MessageTarget.source, validationErrorMsgKey = "email_not_matched")
    private String confirmEmailAddress;

    @NotNull
    @Size(min = 6, max = 20)
    @Pattern(regexp="^([1-zA-Z0-1@.]{6,20})$", message="Only letters, digits, underscore, @ and dot allowed.")
    private String password;

    @Transient
    @Equals(value = "password", validationErrorMsgTarget = MessageTarget.source, validationErrorMsgKey = "password_not_matched")
    private String confirmPassword;

    @NotNull
    private Boolean enabled = true;

    @NotNull
    private Boolean accountNonExpired = true;

    @NotNull
    private Boolean accountNonLocked = true;

    @NotNull
    private Boolean credentialsNonExpired = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prefabUser", fetch = FetchType.EAGER)
    private Set<PrefabAuthority> authorities = new HashSet<PrefabAuthority>();

    private Boolean active = false;

    @NotNull
    @Value("#{new java.util.Date()}")
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateUpdate;

    @Override
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        Iterator<PrefabAuthority> it = authorities.iterator();
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        while (it.hasNext()) {
            grantedAuthorities.add(it.next());
        }
        return grantedAuthorities;
    }

    /*public String register() {
        logger.warn("registering "+this.toString());
        persist();
        flush();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registration done.", "Thank you for joining!")); 
        return "/views/done";
    }*/
    
    public void register(ActionEvent actionEvent) {  
    	RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;  
        boolean registrationOk = false;
        
        logger.warn("registering "+this.toString());
        persist();
        flush();
        
        registrationOk = true;
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Registration done.", "Thank you for joining "+ userName +" !");
		
		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("registrationOk", registrationOk);
    }

    public void validate(ComponentSystemEvent e) {
    	logger.info("in validate");
    	 UIForm form = (UIForm) e.getComponent();
    	 UIInput nameInput = (UIInput) form.findComponent("name");
    	 UIInput emailInput = (UIInput) form.findComponent("email");
    	 
    	 validateUserName(nameInput);
    	 validateEmailAddress(emailInput);
    	 FacesContext fc = FacesContext.getCurrentInstance();
    	 if(fc.isValidationFailed())
    		 fc.renderResponse();
    }

    public void validateUserNameChange(ValueChangeEvent e) {
    	validateUserName((UIInput) e.getComponent());
//    	FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void validateUserName(UIInput input) {
        String error;
//        UIInput input = (UIInput) e.getComponent();
        String value = (String) input.getValue();
        logger.info("in validateUserName with value: "+value);
        
        if (value.contains("_")) error = "Name cannot contain underscores"; 
        else if (value.equals("")) error = "Name cannot be blank"; 
        else if (PrefabUser.findPrefabUsersByUserNameEquals(value).getResultList().size() > 0) 
        	error = "Username already taken. Please choose different one."; 
        else return;
        
        logger.info("error: "+error);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.validationFailed();
        fc.addMessage(input.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
    }
    
    public void validateEmailAddressChange(ValueChangeEvent e) {
    	validateEmailAddress((UIInput) e.getComponent(	));
//    	FacesContext.getCurrentInstance().renderResponse();
    }
   	public void validateEmailAddress(UIInput input) {
    	String error;
//    	UIInput input = (UIInput) e.getComponent();
    	String value = (String) input.getValue();
    	logger.info("in validateEmailAddress with value: "+value);
    	
    	if (value.contains("_")) error = "Name cannot contain underscores"; 
    	else if (value.equals("")) error = "Name cannot be blank"; 
    	else if (PrefabUser.findPrefabUsersByEmailAddressLike(value).getResultList().size() > 0) 
    		error = "Email address already taken. Please choose different one."; 
    	else return;
    	logger.info("error: "+error);
    	
    	FacesContext fc = FacesContext.getCurrentInstance();
    	fc.validationFailed();
    	fc.addMessage(input.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
