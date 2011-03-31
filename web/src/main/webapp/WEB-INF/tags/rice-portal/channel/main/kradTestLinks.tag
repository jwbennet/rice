<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="KRAD Testing" />
<div class="body">
  <strong>Screen Element Testing</strong>
  <ul class="chan">
	 <li><portal:portalLink displayTitle="true" title="Test View 1" url="${ConfigProperties.application.url}/spring/uitest?viewId=Travel-testView1&methodToCall=start" /></li>
     <li><portal:portalLink displayTitle="true" title="Test View 2" url="${ConfigProperties.application.url}/spring/uitest?viewId=Travel-testView2&methodToCall=start" /></li>
   </ul>
   <br/>
   <strong>BO Class Tests</strong>
   <ul class="chan">
     <li><portal:portalLink displayTitle="true" title="Travel Account Inquiry" url="${ConfigProperties.application.url}/spring/inquiry?methodToCall=start&number=a14&dataObjectClassName=edu.sampleu.travel.bo.TravelAccount"/></li>
     <li><portal:portalLink displayTitle="true" title="Travel Account Maintenance (New)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=start&dataObjectClassName=edu.sampleu.travel.bo.TravelAccount"/></li> 
     <li><portal:portalLink displayTitle="true" title="Travel Account Maintenance (Edit)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=maintenanceEdit&number=a14&dataObjectClassName=edu.sampleu.travel.bo.TravelAccount"/></li> 
     <li><portal:portalLink displayTitle="true" title="Travel Account Lookup" url="${ConfigProperties.application.url}/spring/lookup?methodToCall=start&dataObjectClassName=edu.sampleu.travel.bo.TravelAccount&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&returnFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Travel Account Type Lookup" url="${ConfigProperties.application.url}/spring/lookup?methodToCall=start&dataObjectClassName=edu.sampleu.travel.bo.TravelAccountType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&returnFormKey=88888888" /></li>
  </ul>
  <br/>
  <strong>Non BO Class Tests</strong>
  <ul class="chan">
    <li><portal:portalLink displayTitle="true" title="FiscalOfficerInfo Inquiry" url="${ConfigProperties.application.url}/spring/inquiry?methodToCall=start&id=2&dataObjectClassName=edu.sampleu.travel.dto.FiscalOfficerInfo"/></li>
    <li><portal:portalLink displayTitle="true" title="FiscalOfficerInfo Lookup" url="${ConfigProperties.application.url}/spring/lookup?methodToCall=start&dataObjectClassName=edu.sampleu.travel.dto.FiscalOfficerInfo" /></li>
    <li><portal:portalLink displayTitle="true" title="FiscalOfficerInfo Maintenance (New)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=start&dataObjectClassName=edu.sampleu.travel.dto.FiscalOfficerInfo"/></li> 
    <li><portal:portalLink displayTitle="true" title="FiscalOfficerInfo Maintenance (Edit)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=maintenanceEdit&id=2&dataObjectClassName=edu.sampleu.travel.dto.FiscalOfficerInfo"/></li> 
  </ul>
  
</div>
<channel:portalChannelBottom />
