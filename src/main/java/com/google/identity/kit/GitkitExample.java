/*
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.identity.kit;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.SessionHandler;

import com.google.identity.kit.servlets.LoginServlet;
import com.google.identity.kit.servlets.WidgetServlet;

public class GitkitExample {

	public static void main(String[] args) throws Exception {
		// set up JETTY server instance on LOCALHOST port 4567
		Server server = new Server(4567);
		
		// create Servlet and Session handlers
		ServletHandler servletHandler = new ServletHandler();
		SessionHandler sessionHandler = new SessionHandler();
		sessionHandler.setHandler(servletHandler);
		server.setHandler(sessionHandler);
		
		// set Servlet request mappings
		servletHandler.addServletWithMapping(LoginServlet.class, "/login");
		servletHandler.addServletWithMapping(WidgetServlet.class, "/gitkit");
		servletHandler.addServletWithMapping(LoginServlet.class, "/");
	
		// start up
		server.start();
		server.join();
	}
	
}
