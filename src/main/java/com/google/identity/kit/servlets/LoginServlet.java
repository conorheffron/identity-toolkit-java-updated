package com.google.identity.kit.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitClientException;
import com.google.identitytoolkit.GitkitUser;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6215223552042897767L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// This check prevents the "/" handler from handling all requests by
		// default
		if (!"/".equals(request.getServletPath())) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try {
			// get server config and validate request token for google identity kit user
			GitkitUser gitkitUser = null;
			GitkitClient gitkitClient = GitkitClient.createFromJson("src/main/resources/gitkit-server-config.json");
			gitkitUser = gitkitClient.validateTokenInRequest(request);
			
			// get user info to display if login success
			String userInfo = null;
			if (gitkitUser != null) {
				// login success
				userInfo = "Welcome back!<br><br> Email: " + gitkitUser.getEmail() + "<br> Id: "
						+ gitkitUser.getLocalId() + "<br> Provider: " + gitkitUser.getCurrentProvider();
			}

			// set view and set login welcome message depending on login status
			File indexFile = new File("src/main/webapp/templates/index.html");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(indexFile, "UTF-8");
			response.getWriter().print(scanner.useDelimiter("\\A").next()
					.replaceAll("WELCOME_MESSAGE", userInfo != null ? userInfo : "You are not logged in").toString());
			
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (FileNotFoundException | GitkitClientException | JSONException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().print(e.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}