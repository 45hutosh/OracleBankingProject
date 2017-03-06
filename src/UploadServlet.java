

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		File file;
		 String fileName=null;
		res.setContentType("text/html");  
		PrintWriter out = res.getWriter();   
/*		try{ 
			MultipartRequest multipart=new MultipartRequest(req,"c:/ServletUploads"); 
			Part filePart = (Part) req.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			System.out.println();
			//System.out.println(filename[0]);
		
			
		}
		
		catch(Exception e ){
			System.out.println("Exception In MultiPart");
		}*/
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024);
		factory.setRepository(new File("D:/ServletUploads/"));
		   try{ 
			      // Parse the request to get file items.
			 	  ServletFileUpload upload = new ServletFileUpload(factory);
			      List fileItems = upload.parseRequest(req);
				
			      // Process the uploaded file items
			      Iterator i = fileItems.iterator();
			      while ( i.hasNext () ) 
			      {
			         FileItem fi = (FileItem)i.next();
			         if ( !fi.isFormField () )	
			         {
			            // Get the uploaded file parameters
			            fileName = fi.getName();
			            // Write the file
			            System.out.println("filename is " + fileName);
			            if( fileName.lastIndexOf("\\") >= 0 ){
			               file = new File("D:/ServletUploads/"+fileName.substring( fileName.lastIndexOf("\\"))) ;
			            }else{
			               file = new File("D:/ServletUploads/"+fileName.substring(fileName.lastIndexOf("\\")+1)) ;
			            }
			            File tmpFile= new File("D:/ServletUploads/" + file.getName());
			            if(tmpFile.exists())
			            {
			            	System.out.println("File already exists with same name.. Deleting old file..");
			            	//tmpFile.delete();
			            }
			            fi.write( file ) ;
			            System.out.println(file.getName());
			            
			          out.println("Uploaded Filename: " + fileName + "<br>");
			         }
			      }
	
			   }
		   catch(Exception ex) {
			       System.out.println(ex);
			       ex.printStackTrace();
			   }
		   req.setAttribute("filename", fileName);
		RequestDispatcher rd=req.getRequestDispatcher("readfile");  		  
		rd.forward(req, res);
	
	}

}
