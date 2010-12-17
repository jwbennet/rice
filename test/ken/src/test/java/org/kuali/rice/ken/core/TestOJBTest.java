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
package org.kuali.rice.ken.core;

import java.sql.Timestamp;

//import org.apache.ojb.broker.query.Criteria;
//import org.kuali.rice.core.jpa.criteria.Criteria;
//import org.kuali.rice.core.jpa.criteria.QueryByCriteria;
import org.kuali.rice.ken.core.SpringNotificationServiceLocator;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.ken.bo.Notification;
import org.kuali.rice.ken.bo.NotificationChannel;
import org.kuali.rice.ken.bo.NotificationMessageDelivery;
import org.kuali.rice.ken.bo.NotificationProducer;
import org.kuali.rice.ken.dao.BusinessObjectDaoTestCaseBase;
import org.kuali.rice.ken.test.util.MockObjectsUtil;
import org.kuali.rice.ken.util.NotificationConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;

/**
 * Scratch test case for testing aspects of OJB under test harness
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@BaselineMode(Mode.CLEAR_DB)
public class TestOJBTest extends BusinessObjectDaoTestCaseBase {
    /**
     * Just prints the SQL generated by a query criteria 
     */
	/*
	@Ignore
    @Test
    public void testJPACriteria() {
        Criteria criteria_STATUS = new Criteria(Notification.class.getName());
        criteria_STATUS.eq(NotificationConstants.BO_PROPERTY_NAMES.MESSAGE_DELIVERY_STATUS, NotificationConstants.MESSAGE_DELIVERY_STATUS.DELIVERED);

        Criteria criteria_UNDELIVERED = new Criteria(Notification.class.getName());
        criteria_UNDELIVERED.eq(NotificationConstants.BO_PROPERTY_NAMES.MESSAGE_DELIVERY_STATUS, NotificationConstants.MESSAGE_DELIVERY_STATUS.UNDELIVERED);

        // now OR the above two together
        criteria_STATUS.or(criteria_UNDELIVERED);

        Criteria criteria_NOTLOCKED = new Criteria(Notification.class.getName());
        criteria_NOTLOCKED.isNull(NotificationConstants.BO_PROPERTY_NAMES.LOCKED_DATE);

        Criteria fullQueryCriteria = new Criteria(Notification.class.getName());
        fullQueryCriteria.and(criteria_NOTLOCKED);
        fullQueryCriteria.lte(NotificationConstants.BO_PROPERTY_NAMES.NOTIFICATION_AUTO_REMOVE_DATE_TIME, new Timestamp(System.currentTimeMillis()));
        // now add in the STATUS check
        fullQueryCriteria.and(criteria_STATUS);


        System.err.println(fullQueryCriteria.toString());

        
        QueryByCriteria q = new QueryByCriteria(KNSServiceLocator.getApplicationEntityManagerFactory().createEntityManager(), fullQueryCriteria);
       
        System.err.println(q.toString());

    }
	*/
	@Test
    public void testCriteria() {
        Criteria criteria_STATUS = new Criteria();
        criteria_STATUS.addEqualTo(NotificationConstants.BO_PROPERTY_NAMES.MESSAGE_DELIVERY_STATUS, NotificationConstants.MESSAGE_DELIVERY_STATUS.DELIVERED);

        Criteria criteria_UNDELIVERED = new Criteria();
        criteria_UNDELIVERED.addEqualTo(NotificationConstants.BO_PROPERTY_NAMES.MESSAGE_DELIVERY_STATUS, NotificationConstants.MESSAGE_DELIVERY_STATUS.UNDELIVERED);

        // now OR the above two together
        criteria_STATUS.addOrCriteria(criteria_UNDELIVERED);

        Criteria criteria_NOTLOCKED = new Criteria();
        criteria_NOTLOCKED.addIsNull(NotificationConstants.BO_PROPERTY_NAMES.LOCKED_DATE);

        Criteria fullQueryCriteria = new Criteria();
        fullQueryCriteria.addAndCriteria(criteria_NOTLOCKED);
        fullQueryCriteria.addLessOrEqualThan(NotificationConstants.BO_PROPERTY_NAMES.NOTIFICATION_AUTO_REMOVE_DATE_TIME, new Timestamp(System.currentTimeMillis()));
        // now add in the STATUS check
        fullQueryCriteria.addAndCriteria(criteria_STATUS);


        System.err.println(fullQueryCriteria.toString());

        Query q = QueryFactory.newQuery(Notification.class, fullQueryCriteria);
        System.err.println(q.toString());

    }
    @Test
    public void testUpdateRelationships() {
        NotificationChannel channel1 = MockObjectsUtil.getTestChannel1();
        NotificationChannel channel2 = MockObjectsUtil.getTestChannel2();
        NotificationProducer mockProducer1 = MockObjectsUtil.getTestProducer1();

        businessObjectDao.save(mockProducer1);
        assertEquals(0, mockProducer1.getChannels().size());

        // add in a notification channel producer join object
        channel1.getProducers().add(mockProducer1);

        businessObjectDao.save(channel1);

        assertEquals(1, channel1.getProducers().size());

        // ojb doesn't update the collections of the child in the relationship on save, despite auto-update...
        // so I'm forced to load it again
        mockProducer1 = (NotificationProducer) businessObjectDao.findById(NotificationProducer.class, mockProducer1.getId());
        assertEquals(1, mockProducer1.getChannels().size());

        channel2.getProducers().add(mockProducer1);	
        businessObjectDao.save(channel2);

        assertEquals(1, channel2.getProducers().size());

        mockProducer1 = (NotificationProducer) businessObjectDao.findById(NotificationProducer.class, mockProducer1.getId());

        assertEquals(2, mockProducer1.getChannels().size());
    }
}
