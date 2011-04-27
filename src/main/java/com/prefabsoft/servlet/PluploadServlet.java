package com.prefabsoft.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * Handles the multi-part MIME encoded POST from Plupload.
 * 
 * @author Shad Aumann
 */
public class PluploadServlet extends HttpServlet {
	static private final long serialVersionUID = 3447685998419256747L;
	static private final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : null, \"id\" : \"id\"}";
	static private final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	static public final String SEP = System.getProperty("file.separator");
	static public final String TMP = System.getProperty("java.io.tmpdir");
	static public final String JSON = "application/json";
	static public final int BUF_SIZE = 4096;

	/**
	 * Handles an HTTP POST request from Plupload.
	 * 
	 * @param req
	 *            The HTTP request
	 * @param resp
	 *            The HTTP response
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String name = req.getParameter("name");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Hello, " + name + "</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("Hello, " + name);
		out.println("</BODY></HTML>");
	}

	public String getServletInfo() {
		return "A servlet that knows the name of the person to whom it's"
				+ "saying hello";
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String responseString = RESP_SUCCESS;
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		System.out.println("*** >doPost> isMultipart" + isMultipart);

		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload();

			try {
				FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					String name = item.getFieldName();
					InputStream input = item.openStream();

					// Handle a form field.
					if (item.isFormField()) {
						System.out.println("name=" + name + ", value="
								+ Streams.asString(input));
					}

					// Handle a multi-part MIME encoded file.
					else {
						saveUploadFile(input, item);
					}
				}
			} catch (Exception e) {
				responseString = RESP_ERROR;
				e.printStackTrace();
			}
		}

		// Not a multi-part MIME request.
		else {
			responseString = RESP_ERROR;
		}

		resp.setContentType(JSON);
		byte[] responseBytes = responseString.getBytes();
		resp.setContentLength(responseBytes.length);
		ServletOutputStream output = resp.getOutputStream();
		output.write(responseBytes);
		output.flush();
	}

	/**
	 * Saves the given file item (using the given input stream) to the web
	 * server's local temp directory.
	 * 
	 * @param input
	 *            The input stream to read the file from
	 * @param item
	 *            The multi-part MIME encoded file
	 */
	private void saveUploadFile(InputStream input, FileItemStream item)
			throws IOException {
		System.out
				.println("*** >saveUploadFile> " + TMP + SEP + item.getName());
		File localFile = new File(TMP + SEP + item.getName());
		BufferedOutputStream output = new BufferedOutputStream(
				new FileOutputStream(localFile));
		byte[] data = new byte[BUF_SIZE];

		int count;
		while ((count = input.read(data, 0, BUF_SIZE)) != -1) {
			output.write(data, 0, count);
		}

		input.close();
		output.flush();
		output.close();
	}
}
