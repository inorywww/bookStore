package servlets;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/servlets/WXServlet")

public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        //设置请求编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");

        User user = new User();
        //获取微信小程序get的参数值并打印
        user.setAccount(req.getParameter("account"));
        user.setPassword(req.getParameter("password"));
        System.out.println("account="+user.getAccount()+" ,password="+user.getPassword());
        //转成json数据
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", user);
        result.put("msg", "后台已收到");
        //使用Gson类需要导入gson-2.8.0.jar
        String json = new Gson().toJson(result);

        //返回值给微信小程序
        Writer out = resp.getWriter();
        out.write(json);
        out.flush();
    }
}
