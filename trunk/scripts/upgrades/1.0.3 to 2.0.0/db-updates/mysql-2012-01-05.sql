--
-- Copyright 2005-2012 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

ALTER TABLE KRMS_CNTXT_VLD_ACTN_T DROP FOREIGN KEY KRMS_CNTXT_VLD_ACTN_FK1;
DROP INDEX KRMS_CNTXT_VLD_ACTN_TI1 ON KRMS_CNTXT_VLD_ACTN_T;
RENAME TABLE KRMS_CNTXT_VLD_ACTN_S TO KRMS_CNTXT_VLD_ACTN_TYP_S;
RENAME TABLE KRMS_CNTXT_VLD_ACTN_T TO KRMS_CNTXT_VLD_ACTN_TYP_T;
CREATE INDEX KRMS_CNTXT_VLD_ACTN_TYP_TI1 ON KRMS_CNTXT_VLD_ACTN_TYP_T (CNTXT_ID);
ALTER TABLE KRMS_CNTXT_VLD_ACTN_TYP_T ADD CONSTRAINT KRMS_CNTXT_VLD_ACTN_TYP_FK1
  FOREIGN KEY (CNTXT_ID) REFERENCES KRMS_CNTXT_T (CNTXT_ID);

ALTER TABLE KRMS_CNTXT_VLD_AGENDA_T DROP FOREIGN KEY KRMS_CNTXT_VLD_AGENDA_FK1;
DROP INDEX KRMS_CNTXT_VLD_AGENDA_TI1 ON KRMS_CNTXT_VLD_AGENDA_T;
RENAME TABLE KRMS_CNTXT_VLD_AGENDA_S TO KRMS_CNTXT_VLD_AGENDA_TYP_S;
RENAME TABLE KRMS_CNTXT_VLD_AGENDA_T TO KRMS_CNTXT_VLD_AGENDA_TYP_T;
CREATE INDEX KRMS_CNTXT_VLD_AGENDA_TYP_TI1 ON KRMS_CNTXT_VLD_AGENDA_TYP_T (CNTXT_ID);
ALTER TABLE KRMS_CNTXT_VLD_AGENDA_TYP_T ADD CONSTRAINT KRMS_CNTXT_VLD_AGENDA_TYP_FK1
  FOREIGN KEY (CNTXT_ID) REFERENCES KRMS_CNTXT_T (CNTXT_ID);

ALTER TABLE KRMS_CNTXT_VLD_RULE_T DROP FOREIGN KEY KRMS_CNTXT_VLD_RULE_FK1;
DROP INDEX KRMS_CNTXT_VLD_RULE_TI1 ON KRMS_CNTXT_VLD_RULE_T;
RENAME TABLE KRMS_CNTXT_VLD_RULE_S TO KRMS_CNTXT_VLD_RULE_TYP_S;
RENAME TABLE KRMS_CNTXT_VLD_RULE_T TO KRMS_CNTXT_VLD_RULE_TYP_T;
CREATE INDEX KRMS_CNTXT_VLD_RULE_TYP_TI1 ON KRMS_CNTXT_VLD_RULE_TYP_T (CNTXT_ID);
ALTER TABLE KRMS_CNTXT_VLD_RULE_TYP_T ADD CONSTRAINT KRMS_CNTXT_VLD_RULE_TYP_FK1
  FOREIGN KEY (CNTXT_ID) REFERENCES KRMS_CNTXT_T (CNTXT_ID);

ALTER TABLE KRMS_CNTXT_VLD_RULE_TYP_T CHANGE RULE_ID RULE_TYP_ID VARCHAR(40) NOT NULL;

UPDATE KRMS_AGENDA_T SET TYP_ID = NULL WHERE TYP_ID = 'T5';
UPDATE KRMS_RULE_T SET TYP_ID = NULL;
DELETE FROM KRMS_CNTXT_VLD_RULE_TYP_T;