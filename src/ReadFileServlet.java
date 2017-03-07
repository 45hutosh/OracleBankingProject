import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("ReadFile")
public class ReadFileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		
		
		String filepath = (String) req.getAttribute("filename");
		//String filename = filepath.substring(filepath.lastIndexOf("\\")+1);
		FileReader fr= new FileReader(filepath);
	    BufferedReader br= new BufferedReader(fr);
	    String line;
	    while ((line = br.readLine()) != null) {
	    	String[] delimitedString = line.split(",");
	    	for(int i=0;i<delimitedString.length;i++){
	    		
	    	}
	    }
	    
	    System.out.println("In ReadFile. Control in Servlet two.");
	    br.close();
	    fr.close();   
	
	}
}

