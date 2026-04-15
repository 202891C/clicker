import java.io.*;
import java.sql.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/displayservlet")   
public class DisplayServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>View Results</title>");
      out.println("<link rel='stylesheet' href='css/barchart.css'>");
      out.println("<body>");
      out.println("<div class='vbar'>");

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");  
         Statement stmt = conn.createStatement();
      ) {
        Double total;
        String sqlTot = "SELECT count(*) AS total FROM responses WHERE questionNo=1;";
        ResultSet rst = stmt.executeQuery(sqlTot);
        rst.next();
        total=Double.parseDouble(rst.getString("total"));
        rst.close();

        String sqlStr = "SELECT choice, COUNT(*) AS score FROM responses WHERE questionNo=1 GROUP BY choice order by choice;";
        ResultSet rset = stmt.executeQuery(sqlStr);
        int count = 0;
         while(rset.next()) {
            out.println("<div id='"+rset.getString("choice")+"' style='height:" + (Double.parseDouble(rset.getString("score"))/total)*100 +"%' id='"+ count+1 +"'>"+rset.getString("score")+"</div>");
            // out.println("Test");
            count++;
         }
        out.println("</div>");
         //-----------------------------------------------------------------------------------------------------------------
      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
      }  
 
      out.println("</body></html>");
      out.close();
   }
   
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response); 
   }
}