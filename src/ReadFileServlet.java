import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
		PrintWriter out=resp.getWriter();
		out.print("Reading File");
		
	   /* FileInputStream fin=new FileInputStream("C:\\.txt");    
	    int i=fin.read();  
	    System.out.print((char)i);    
	    fin.close();*/    
	
	}
}

