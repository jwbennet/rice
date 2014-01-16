/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.xml.spring;

import org.kuali.common.util.execute.Executable;
import org.kuali.common.util.metainf.service.MetaInfUtils;
import org.kuali.common.util.metainf.spring.RiceXmlConfig;
import org.kuali.common.util.spring.env.EnvironmentService;
import org.kuali.rice.xml.ingest.IngestXmlExecutable;
import org.kuali.rice.xml.project.XmlProjectConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Central configuration file for launching the workflow XML ingestion process.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Configuration
@Import({ IngestXmlConfig.class })
public class IngestXmlExecConfig {

	@Autowired
	EnvironmentService env;

	private static final String SKIP_KEY = "rice.ingest.skip";
	private static final String RESOURCES_KEY = "rice.ingest.resources";

	@Bean
	public Executable ingestXmlExecutable() {

		// Setup the executable
		boolean skip = env.getBoolean(SKIP_KEY, false);
		String defaultLocationListing = MetaInfUtils.getClasspathResource(XmlProjectConstants.ID, RiceXmlConfig.INGEST_FILENAME);
		String locationListing = env.getString(RESOURCES_KEY, defaultLocationListing);
		return IngestXmlExecutable.builder(locationListing).skip(skip).build();
	}
}