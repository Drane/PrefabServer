package com.prefabsoft.spring.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

public final class ImageView extends AbstractView
{
    private static final Log _log = LogFactory.getLog(ImageView.class.getName());

    private byte[] _bytData = null;

    public ImageView(byte[] data, String type)
    {
        _bytData = data;
        setContentType("image/" + type);
    }

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

        if (null == _bytData)
        {
            if (_log.isWarnEnabled())
                _log.warn("Image contents are not available!");
            return;
        }

        try
        {
            if (_log.isInfoEnabled())
                _log.info("Streaming (" + getContentType() + ") size " 
                            + _bytData.length + " bytes!");
            out = response.getOutputStream();
            response.setContentType(getContentType());
            response.setContentLength(_bytData.length);
            out.write(_bytData);
            out.flush();
        }
        catch (IOException ex)
        {
            if (_log.isErrorEnabled())
                _log.error("Unable to stream image data!", ex);
        }
        finally
        {
            if (out != null) out = null;
        }
    }
}