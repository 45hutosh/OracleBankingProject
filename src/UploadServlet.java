

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Refreshable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
		int filesize=0;
		String status;
		String fileName=null;
		res.setContentType("text/html");  
		PrintWriter out = res.getWriter();  
		
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
			            filesize=(int) fi.getSize();
			            status ="P";
			            //Restricting Filetype
			            if(fileName.substring(fileName.lastIndexOf(".")+1).equals("txt")){
			            // Write the file
			              if( fileName.lastIndexOf("\\") >= 0 ){
				            file = new File("D:/ServletUploads/"+fileName.substring( fileName.lastIndexOf("\\"))) ;
				            }else{
				            file = new File("D:/ServletUploads/"+fileName.substring(fileName.lastIndexOf("\\")+1)) ;
				            }
			            
			          /*  File tmpFile= new File("D:/ServletUploads/" + file.getName());
			            if(tmpFile.exists())
				            {
				            	System.out.println("File already exists with same name. Deleting old file.");
				            	//tmpFile.delete();
				            }*/
			            fi.write( file ) ;
			            int rowsModified=addFileInfo(fileName.substring( fileName.lastIndexOf("\\")+1),filesize,status);    
			            if(rowsModified ==1){
			            	req.setAttribute("filename", fileName);
			            	RequestDispatcher rd=req.getRequestDispatcher("readfile");  		  
				    		rd.forward(req, res);
			            }
			            else {
			            	out.println("<h1 style='color:red'>Duplicates Not Allowed. Go back and Try again");
			            }
			    		
			    		//System.out.println("Control Returned");
			          }
			            else
			            	//System.out.println("Only Text Files Allowed"); 
			            	out.println("<h1 style='color:red'>Only Text Files Allowed");
				           	fi.delete();
					}
			           
			      }
	
			   }
		   catch(Exception ex) {
			       System.out.println(ex);
			       ex.printStackTrace();
			   }
		   
		  

	
	}

	
	int addFileInfo(String filename,int filesize,String status){
		int rowsModified=0;
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");  
		//Statement stmt=con.createStatement();  
		//stmt.executeUpdate("insert into fileinfo values('filename',filesize,'status')"); 
		PreparedStatement stmt=con.prepareStatement("insert into FileInfo values(?,?,?)");  
		stmt.setString(1,filename);//1 specifies the first parameter in the query  
		stmt.setInt(2,filesize);  
		stmt.setString(3,status);
		  
		rowsModified=stmt.executeUpdate();
		System.out.println(rowsModified+" entry added");
		con.close();
		
//			System.out.println(filename);
//			System.out.println(filesize);
//			System.out.println(status);
		}
		catch (SQLIntegrityConstraintViolationException sice){
			System.out.println("Duplicates not Allowed");
		}
		catch (SQLException | ClassNotFoundException se){
			se.printStackTrace();
		}
		return rowsModified;
	}
}
