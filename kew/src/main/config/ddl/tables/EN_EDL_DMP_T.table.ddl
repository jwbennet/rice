CREATE TABLE EN_EDL_DMP_T (
	DOC_HDR_ID NUMBER(14) NOT NULL,
	DOC_TYP_NM VARCHAR2(255) NOT NULL,
	DOC_RTE_STAT_CD CHAR(1) NOT NULL,
	DOC_MDFN_DT DATE NOT NULL,
	DOC_CRTE_DT DATE NOT NULL,
	DOC_TTL VARCHAR2(255) NULL,
	DOC_INITR_ID VARCHAR2(30) NOT NULL,
	DOC_CRNT_NODE_NM VARCHAR2(30) NOT NULL,
	DB_LOCK_VER_NBR	NUMBER(8) DEFAULT 0,
	CONSTRAINT EN_EDL_DMP_PK PRIMARY KEY (DOC_HDR_ID)
)
/