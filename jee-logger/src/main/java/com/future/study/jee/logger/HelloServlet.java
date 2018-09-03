package com.future.study.jee.logger;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * @author Dexterleslie.Chan
 *
 */
public class HelloServlet extends HttpServlet {
	private final static Logger logger=Logger.getLogger(HelloServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("输出到控制台日志");
		logger.info("输出到文件日志");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("hello");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1777115807145268074L;

}
