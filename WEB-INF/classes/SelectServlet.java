// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/selectservlet")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SelectServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Query Response</title>");
    //   out.println("<style> table{ border: 1px solid black;}th, td{padding: 5px;box-shadow: 0px 0px 5px black;} img{width:100px}</style></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
        //  String choice = request.getParameter("choice");
      ) {
        int count;
         // Step 3 & 4: Execute a SQL SELECT query and Process the query result

        String sqlStr = "INSERT INTO responses (questionNo, choice) VALUES (1, '" + request.getParameter("choice") + "')";
        count = stmt.executeUpdate(sqlStr);
      //   out.println("<p>" + count + " record updated.</p>");
        out.println("<h3>Your vote has been recorded.</h3>");
        out.println("<h3> "+
            "<form action='displayservlet' method='get'>"+
                "<button type='submit'>View results</button>"+
            "</form><h3>");
         //-----------------------------------------------------------------------------------------------------------------

         // === Step 4 ends HERE - Do NOT delete the following codes ===
      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
        //  ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
   
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);  // Re-direct POST request to doGet()
   }
}