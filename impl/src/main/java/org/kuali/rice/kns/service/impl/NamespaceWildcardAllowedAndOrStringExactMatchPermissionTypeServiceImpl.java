/*
 * Copyright 2007-2009 The Kuali Foundation
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.bo.types.dto.KimTypeInfo;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.bo.Namespace;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class NamespaceWildcardAllowedAndOrStringExactMatchPermissionTypeServiceImpl
		extends NamespacePermissionTypeServiceImpl {
	protected static final String NAMESPACE_CODE = KimConstants.UniqueKeyConstants.NAMESPACE_CODE;
	
	protected String exactMatchStringAttributeName;
	protected boolean namespaceRequiredOnStoredAttributeSet;

	@Override
	protected List<KimPermissionInfo> performPermissionMatches(AttributeSet requestedDetails, List<KimPermissionInfo> permissionsList) {
	    List<KimPermissionInfo> matchingPermissions = new ArrayList<KimPermissionInfo>();
        List<KimPermissionInfo> matchingBlankPermissions = new ArrayList<KimPermissionInfo>();
	    String requestedAttributeValue = requestedDetails.get(exactMatchStringAttributeName);
	    for ( KimPermissionInfo kpi : permissionsList ) {
	        String permissionAttributeValue = kpi.getDetails().get(exactMatchStringAttributeName);
	        if ( StringUtils.equals(requestedAttributeValue, permissionAttributeValue) ) {
	            matchingPermissions.add(kpi);
	        } else if ( StringUtils.isBlank(permissionAttributeValue) ) {
	            matchingBlankPermissions.add(kpi);
	        }
	    }
	    // if the exact match worked, use those when checking the namespace
	    // otherwise, use those with a blank additional property value
	    if ( !matchingPermissions.isEmpty() ) {
            List<KimPermissionInfo> matchingWithNamespace = super.performPermissionMatches(requestedDetails, matchingPermissions);
	        if ( !namespaceRequiredOnStoredAttributeSet ) {
	            // if the namespace is not required and the namespace match would have excluded
	            // the results, return the original set of matches
	            if ( matchingWithNamespace.isEmpty() ) {
	                return matchingPermissions;
	            }
	        }
            return matchingWithNamespace;
	    } else if ( !matchingBlankPermissions.isEmpty() ) {
            List<KimPermissionInfo> matchingWithNamespace = super.performPermissionMatches(requestedDetails, matchingBlankPermissions);
            if ( !namespaceRequiredOnStoredAttributeSet ) {
                // if the namespace is not required and the namespace match would have excluded
                // the results, return the original set of matches
                if ( matchingWithNamespace.isEmpty() ) {
                    return matchingBlankPermissions;
                }
            }
            return matchingWithNamespace;
	    }
	    return matchingPermissions; // will be empty if drops to here
	}
	
	public void setExactMatchStringAttributeName(
			String exactMatchStringAttributeName) {
		this.exactMatchStringAttributeName = exactMatchStringAttributeName;
		requiredAttributes.add(exactMatchStringAttributeName);
	}

	public void setNamespaceRequiredOnStoredAttributeSet(
			boolean namespaceRequiredOnStoredAttributeSet) {
		this.namespaceRequiredOnStoredAttributeSet = namespaceRequiredOnStoredAttributeSet;
	}

	/**
	 * Overrides the superclass's version of this method in order to account for "namespaceCode" permission detail values containing wildcards.
	 */
	@Override
	protected Map<String, List<String>> validateReferencesExistAndActive(KimTypeInfo kimType, AttributeSet attributes, Map<String, String> previousValidationErrors) {
		Map<String,List<String>> errors = new HashMap<String,List<String>>();
		AttributeSet nonNamespaceCodeAttributes = new AttributeSet(attributes);
		// Check if "namespaceCode" is one of the permission detail values.
		if (attributes.containsKey(NAMESPACE_CODE)) {
			nonNamespaceCodeAttributes.remove(NAMESPACE_CODE);
			Collection<Namespace> namespaces = KNSServiceLocator.getLookupService().findCollectionBySearchUnbounded(
					Namespace.class, Collections.singletonMap("code", attributes.get(NAMESPACE_CODE)));
			// If the lookup service found at least one namespace, perform exists-and-active checks on each one.
			if (namespaces != null && !namespaces.isEmpty()) {
				for (Namespace namespace : namespaces) {
					errors.putAll(super.validateReferencesExistAndActive(kimType, new AttributeSet(NAMESPACE_CODE, namespace.getNamespaceCode()), previousValidationErrors));
				}
			} else {
				// If no namespaces were found, let the superclass generate an appropriate error.
				errors.putAll(super.validateReferencesExistAndActive(kimType, new AttributeSet(NAMESPACE_CODE, attributes.get(NAMESPACE_CODE)), previousValidationErrors));
			}
		}
		// Validate all non-namespaceCode attributes.
		errors.putAll(super.validateReferencesExistAndActive(kimType, nonNamespaceCodeAttributes, previousValidationErrors));
		return errors;
	}
}