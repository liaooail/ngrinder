/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ngrinder.agent.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ngrinder.AbstractNGrinderTransactionalTest;
import org.ngrinder.agent.model.AgentInfo;
import org.ngrinder.infra.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Class description.
 * 
 * @author Mavlarn
 * @since 3.0
 */
public class AgentManagerControllerTest extends AbstractNGrinderTransactionalTest {

	@Autowired
	AgentManagerController agentController;

	@Autowired
	private Config config;
	
	@Before
	public void setMockRequest() {
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addHeader("User-Agent", "Win");
		SecurityContextHolderAwareRequestWrapper reqWrapper = new SecurityContextHolderAwareRequestWrapper(
						req, "U");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(reqWrapper));
	}

	@After
	public void reSetRequest() {
		RequestContextHolder.resetRequestAttributes();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAgentList() {
		//create a temp download dir and file for this function
		File directory = config.getHome().getDownloadDirectory();
		if (!directory.exists()) {
			try {
				FileUtils.forceMkdir(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File tmpDownFile;
		try {
			tmpDownFile = File.createTempFile("ngrinder", "zip", directory);
			FileUtils.writeStringToFile(tmpDownFile, "test data");
			tmpDownFile.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ModelMap model = new ModelMap();
		agentController.getAgentList(model);
		List<AgentInfo> agents = (List<AgentInfo>) model.get("agents");
		if (agents.size() > 0) {
			AgentInfo testAgt = agents.get(0);
			model.clear();
			agentController.getAgent(model, testAgt.getId());
			AgentInfo agentInDB = (AgentInfo) model.get("agent");
			assertThat(agentInDB.getId(), is(testAgt.getId()));
			assertThat(agentInDB.getIp(), is(testAgt.getIp()));
			assertThat(agentInDB.getPort(), is(testAgt.getPort()));

		}
	}

	@Test
	public void testDownloadAgent() {
		agentController.downloadAgent("helloworld", null);
	}

}
