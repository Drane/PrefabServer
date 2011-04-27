package com.prefabsoft.spring.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

import com.prefabsoft.model.PrefabResource;

public final class ResourceView extends AbstractView
{
    private static final Log _log = LogFactory.getLog(ResourceView.class.getName());
    public static final String RESOURCE_KEY = "RESOURCE";

//    private PrefabResource resource;

//    public ResourceView(PrefabResource resource)
//    {
//    	this.resource = resource;
//    	setContentType(resource.getContentType());
//    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, 
     *         javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map model, 
                    HttpServletRequest request, HttpServletResponse response)
                    throws Exception
    {
        ServletOutputStream out = null;
        
        PrefabResource resource = (model.containsKey(RESOURCE_KEY)?
        		(PrefabResource)model.get(RESOURCE_KEY):null);
        
        if(null == resource)
        	throw new IOException("This resource does not exist!");

        if (null == resource.getContent())
        {
            if (_log.isWarnEnabled())
                _log.warn("Resource contents are not available!");
            return;
        }

        try
        {
        	setContentType(resource.getContentType());
        	
            if (_log.isInfoEnabled())
                _log.info("Streaming (" + getContentType() + ") size " 
                            + resource.getContent().length + " bytes!");
            
            out = response.getOutputStream();
            response.setContentType(getContentType());
            response.setContentLength(resource.getContent().length);
            out.write(resource.getContent());
            out.flush();
        }
        catch (IOException ex)
        {
            if (_log.isErrorEnabled())
                _log.error("Unable to stream resource data!", ex);
        }
        finally
        {
            if (out != null) out = null;
        }
    }
}