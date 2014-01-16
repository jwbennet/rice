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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.kuali.common.jdbc.project.spring.JdbcPropertyLocationsConfig;
import org.kuali.common.util.project.ProjectUtils;
import org.kuali.common.util.properties.Location;
import org.kuali.common.util.properties.PropertiesService;
import org.kuali.common.util.properties.spring.DefaultPropertiesServiceConfig;
import org.kuali.common.util.spring.service.PropertySourceConfig;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.sql.spring.SourceSqlPropertyLocationsConfig;
import org.kuali.rice.xml.ingest.RiceConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * Holds the property source for all of the different properties needed for the workflow XML ingestion process.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Configuration
@Import({ JdbcPropertyLocationsConfig.class, DefaultPropertiesServiceConfig.class, SourceSqlPropertyLocationsConfig.class, IngestXmlPropertyLocationsConfig.class })
public class IngestXmlPSC implements PropertySourceConfig {

	@Autowired
	JdbcPropertyLocationsConfig jdbcConfig;

	@Autowired
	SourceSqlPropertyLocationsConfig sourceSqlConfig;

	@Autowired
	IngestXmlPropertyLocationsConfig ingestXmlConfig;

	@Autowired
	PropertiesService service;

	@Override
	@Bean
	public PropertySource<?> propertySource() {
		// Making sure Rice properties go in last
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(jdbcConfig.jdbcPropertyLocations());
		locations.addAll(sourceSqlConfig.riceSourceSqlPropertyLocations());
		locations.addAll(ingestXmlConfig.riceIngestXmlPropertyLocations());

		Properties properties = service.getProperties(locations);

		String location = ProjectUtils.getPath(RiceXmlProperties.APP.getResource());
		Config riceConfig = RiceConfigUtils.parseAndInit(location);
		RiceConfigUtils.putProperties(riceConfig, properties);
		return new PropertiesPropertySource("properties", riceConfig.getProperties());
	}

}