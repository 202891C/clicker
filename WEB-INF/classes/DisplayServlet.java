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
      out.println("<div>");

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");  
         Statement stmt = conn.createStatement();
      ) {
        Double total;
        int questionNo=1;
        String sqlTot = "SELECT count(*) AS total FROM responses WHERE questionNo="+questionNo+";";
        ResultSet rst = stmt.executeQuery(sqlTot);
        rst.next();
        total=Double.parseDouble(rst.getString("total"));
        rst.close();

        out.println("<nav>Q" + questionNo + ". Who is the coolest Marvel Hero?");
                out.println("<ol type='A'>");
                    out.println("<li>Captain America</li>");
                    out.println("<li>Iron Man</li>");
                    out.println("<li>Black Widow</li>");
                    out.println("<li>Thor</li>");
                out.println("</ol></nav>");

        out.println("<div class='vbar'>");
        String sqlStr = "SELECT choice, COUNT(*) AS score FROM responses WHERE questionNo=1 GROUP BY choice order by choice;";
        ResultSet rset = stmt.executeQuery(sqlStr);
        int count = 0;
        String value;
         while(rset.next()) {
            count++;
            if(count == 1){
                value="Captain America";
            }
            else if(count == 2){
                value="Iron Man";
            }
            else if(count == 3){
                value="Black Widow";
            }
            else{
                value="Thor";
            }
            out.println("<div id='"+rset.getString("choice")+"' style='height:" + 
                (Double.parseDouble(rset.getString("score"))/total)*100 +"%'" 
                +"'>"+ value + ": " + rset.getString("score")+"</div>");
            
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