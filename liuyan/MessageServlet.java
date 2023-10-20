package liuyan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MessageServlet", urlPatterns = {"/MessageServlet"})
public class MessageServlet extends HttpServlet {


    private static final ArrayList<String[]> messages = new ArrayList<>();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        String name = request.getParameter("name");
        String message = request.getParameter("message");

        name=new String(name.getBytes("iso8859-1"),"UTF-8");
        message=new String(message.getBytes("iso8859-1"),"UTF-8");

        // 将留言信息保存到静态变量中
        synchronized (messages) {
            messages.add(new String[]{name, message});
        }

        // 返回跳转页面的HTML0代码
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
        html.append("<script>window.location.href='MessageServlet';</script>");
        html.append("</head><body></body></html>");

        response.getWriter().write(html.toString());
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>留言板</title></head><body>");

        synchronized (messages) {
            // 构建留言列表的HTML代码
            for (String[] message : messages) {
                String name = message[0];
                String content = message[1];
                html.append("<div><strong>").append(name).append(":</strong> ").append(content).append("</div>");
            }
        }

        html.append("<p><a href='http://localhost:8080/message_war_exploded/'>返回留言界面</a></p></body></html>");
        response.getWriter().write(html.toString()); // 返回留言列表HTML页面
    }
}
