-- Drop table

DROP TABLE report_activity;

CREATE TABLE report_activity (
  activity_id varchar(255) NOT NULL,
  status varchar(255) NOT NULL,
  resumed CHAR,
  report_data text NULL, /* json */
  error TEXT NULL,
  template_name VARCHAR(255) NOT NULL,
  report_name VARCHAR(255) NOT NULL,
  completed_stamp datetime NULL,
  LAST_UPDATED_STAMP datetime DEFAULT NULL,
  LAST_UPDATED_TX_STAMP datetime DEFAULT NULL,
  CREATED_STAMP datetime DEFAULT NULL,
  CREATED_TX_STAMP datetime DEFAULT NULL,

  PRIMARY KEY (activity_id)
)
;
