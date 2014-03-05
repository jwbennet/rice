/*
 * Copyright 2007-2008 The Kuali Foundation
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
package org.kuali.rice.kns.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class ParameterPermissionTypeServiceImpl extends
		NamespaceWildcardAllowedAndOrStringExactMatchPermissionTypeServiceImpl {
    
    {
        requiredAttributes.add(KimAttributes.PARAMETER_NAME);
        requiredAttributes.add(KimAttributes.COMPONENT_NAME);
        checkRequiredAttributes = true;
    }
    
    @Override
    protected List<KimPermissionInfo> performPermissionMatches(AttributeSet requestedDetails, List<KimPermissionInfo> permissionsList) {
        String requestedParameterName = requestedDetails.get(KimAttributes.PARAMETER_NAME);
        String requestedComponentName = requestedDetails.get(KimAttributes.COMPONENT_NAME);
        List<KimPermissionInfo> matchingPermissions = new ArrayList<KimPermissionInfo>();
        for ( KimPermissionInfo kpi : permissionsList ) {
            String parameterName = kpi.getDetails().get(KimAttributes.PARAMETER_NAME);
            String componentName = kpi.getDetails().get(KimAttributes.COMPONENT_NAME);
            if ( (StringUtils.isBlank(parameterName)
                    || StringUtils.equals(requestedParameterName, parameterName)) 
                &&(StringUtils.isBlank(componentName)
                        || StringUtils.equals(requestedComponentName, componentName))) {
                matchingPermissions.add(kpi);
            }
        }
        return super.performPermissionMatches(requestedDetails, matchingPermissions);
    }    
}