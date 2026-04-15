import java.io.*;
import java.sql.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/display")   
public class DisplayServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>View Results</title>");
    //   out.println("<link rel='stylesheet' href='css/barchart.css'>");
      out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css'>");
      
      out.println("</head><body>");
    //   out.println("<div>");

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

        out.println("<div><nav>Q" + questionNo + ". Who is the coolest Marvel Hero?");
                out.println("<ol type='A'>");
                    out.println("<li>Captain America</li>");
                    out.println("<li>Iron Man</li>");
                    out.println("<li>Black Widow</li>");
                    out.println("<li>Thor</li>");
                out.println("</ol></nav></div>");

        // out.println("<div class='vbar'>");
        out.println("<canvas id='canvas'></canvas>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js'></script>");

        String sqlStr = "SELECT choice, COUNT(*) AS score FROM responses WHERE questionNo=1 GROUP BY choice order by choice;";
        ResultSet rset = stmt.executeQuery(sqlStr);
        int count = 0;
        String value;

        out.println("<script>");
        out.println("const canvas = ");
        out.println("document.getElementById('canvas').getContext('2d');");
        out.println("let chart = new Chart(canvas, {");
        out.println("   type: 'bar',");
        out.println("   data: {");
        out.println("       labels: ['Captain America', 'Iron Man', 'Black Widow', 'Thor'],");
        out.println("       datasets: [{");
        out.println("           label: 'Favourite character',");
        out.print("           data: [");

        while(rset.next()) {
            if(count>0){
                out.print(", ");
            }
            out.print(Double.parseDouble(rset.getString("score")));
            count++;
            // if(count == 1){
            //     value="Captain America";
            // }
            // else if(count == 2){
            //     value="Iron Man";
            // }
            // else if(count == 3){
            //     value="Black Widow";
            // }
            // else{
            //     value="Thor";
            // }
            // out.println("<div id='"+rset.getString("choice")+"' style='height:" + 
            //     (Double.parseDouble(rset.getString("score"))/total)*100 +"%'" 
            //     +"'>"+ value + ": " + rset.getString("score")+"</div>");
         }
        out.println("],");
        out.print("           backgroundColor: [");
        out.print("'blue', 'red', 'black', 'yellow'");
        out.print("]");
        out.println("}]");
        out.print("}");
        out.print(", options: {scales: {yAxes: ");
        out.print("[{ ticks: {beginAtZero: true, stepSize: 1}}]");
        out.print("},legend: {display: true}}");
        out.println("})</script>");

        // out.println("</div>");
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