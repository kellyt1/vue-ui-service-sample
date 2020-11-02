DROP TABLE address;
CREATE TABLE address (id CHARACTER VARYING(36) NOT NULL, street_address CHARACTER VARYING(75) NOT NULL, city CHARACTER VARYING(50) NOT NULL, state CHARACTER VARYING(2) NOT NULL, postal_code CHARACTER VARYING(10) NOT NULL, address_type CHARACTER VARYING(10) NOT NULL, application_id CHARACTER VARYING(36), preferred BOOLEAN, CONSTRAINT pk_address PRIMARY KEY (id));
COMMENT ON TABLE address IS 'Addresses table';
COMMENT ON COLUMN address.id IS 'Id for name';
COMMENT ON COLUMN address.street_address IS 'Address of applicant';
COMMENT ON COLUMN address.city IS 'Address city of applicant';
COMMENT ON COLUMN address.state IS 'Address state of applicant';
COMMENT ON COLUMN address.postal_code IS 'ZIP of applicant';
COMMENT ON COLUMN address.address_type IS 'Type of address';
COMMENT ON COLUMN address.application_id IS 'Foreign key to application table, indicates id of application';
DROP TABLE application;
CREATE TABLE application (id CHARACTER VARYING NOT NULL, license_id CHARACTER VARYING NOT NULL, application_type CHARACTER VARYING, apply_method CHARACTER VARYING, last_name CHARACTER VARYING, first_name CHARACTER VARYING, middle_name CHARACTER VARYING, personal_phone CHARACTER VARYING, work_phone CHARACTER VARYING, email_address CHARACTER VARYING, supervised_est_name CHARACTER VARYING, supervised_est_full_address CHARACTER VARYING, supervised_est_phone_number CHARACTER VARYING, supervised_est_website CHARACTER VARYING, supervised_est_hours CHARACTER VARYING, additional_information CHARACTER VARYING, mobile_phone CHARACTER VARYING(15), adverse_action_taken BOOLEAN, adverse_action_text TEXT, conviction BOOLEAN, conviction_text TEXT, received_date DATE, status CHARACTER VARYING(20) DEFAULT 'PENDING'::character varying NOT NULL, CONSTRAINT pk_application PRIMARY KEY (id));
COMMENT ON TABLE application IS 'Application table';
COMMENT ON COLUMN application.id IS 'Id for application';
COMMENT ON COLUMN application.license_id IS 'Foreign key to license table, indicates id of license';
COMMENT ON COLUMN application.application_type IS 'Type of application';
COMMENT ON COLUMN application.apply_method IS 'Method used to apply for application';
COMMENT ON COLUMN application.last_name IS 'Last name of applicant';
COMMENT ON COLUMN application.first_name IS 'First name of applicant';
COMMENT ON COLUMN application.middle_name IS 'Middle name of applicant';
COMMENT ON COLUMN application.personal_phone IS 'Personal phone number of applicant';
COMMENT ON COLUMN application.work_phone IS 'Work phone number of applicant';
COMMENT ON COLUMN application.email_address IS 'Email of applicant';
COMMENT ON COLUMN application.supervised_est_name IS 'Supervised establishment name';
COMMENT ON COLUMN application.supervised_est_full_address IS 'Supervised establishment address';
COMMENT ON COLUMN application.supervised_est_phone_number IS 'Supervised establishment phone number';
COMMENT ON COLUMN application.supervised_est_website IS 'Supervised establishment website url';
COMMENT ON COLUMN application.supervised_est_hours IS 'Supervised establishment hours';
COMMENT ON COLUMN application.additional_information IS 'Additional information';
COMMENT ON COLUMN application.mobile_phone IS 'Mobile phone number of applicant';
COMMENT ON COLUMN application.adverse_action_taken IS 'Renewals - indicates if any adverse action was taken in past two years.';
COMMENT ON COLUMN application.adverse_action_text IS 'Renewals - describes any adverse action that was taken.';
COMMENT ON COLUMN application.conviction IS 'Renewals - indicates if applicant was charged with any conviction in past two years.';
COMMENT ON COLUMN application.conviction_text IS 'Renewals - describes any conviction applicant was charged with.';
COMMENT ON COLUMN application.received_date IS 'The date the application was received';
COMMENT ON COLUMN application.status IS 'The status of the application';
DROP TABLE application_property;
CREATE TABLE application_property (key CHARACTER VARYING(100) NOT NULL, value CHARACTER VARYING(4000) NOT NULL, CONSTRAINT pk_application_property PRIMARY KEY (key));
COMMENT ON TABLE application_property IS 'App properties';
COMMENT ON COLUMN application_property.key IS 'Key for property';
COMMENT ON COLUMN application_property.value IS 'Value for property';
DROP TABLE attachment;
CREATE TABLE attachment (id CHARACTER VARYING(36) NOT NULL, application_id CHARACTER VARYING(36), attachment_type CHARACTER VARYING(20) NOT NULL, other_attachment_type CHARACTER VARYING(75), file_name CHARACTER VARYING, url CHARACTER VARYING, course_date DATE, training_presenter CHARACTER VARYING(100), CONSTRAINT pk_attachment PRIMARY KEY (id));
COMMENT ON COLUMN attachment.course_date IS 'Renewals - continuing education: course date associated with attachment.';
COMMENT ON COLUMN attachment.training_presenter IS 'Renewals - continuing education: trainer associated with attachment.';
DROP TABLE databasechangelog;
CREATE TABLE databasechangelog (id CHARACTER VARYING(255) NOT NULL, author CHARACTER VARYING(255) NOT NULL, filename CHARACTER VARYING(255) NOT NULL, dateexecuted TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, orderexecuted INTEGER NOT NULL, exectype CHARACTER VARYING(10) NOT NULL, md5sum CHARACTER VARYING(35), description CHARACTER VARYING(255), comments CHARACTER VARYING(255), tag CHARACTER VARYING(255), liquibase CHARACTER VARYING(20), contexts CHARACTER VARYING(255), labels CHARACTER VARYING(255), deployment_id CHARACTER VARYING(10));
DROP TABLE databasechangeloglock;
CREATE TABLE databasechangeloglock (id INTEGER NOT NULL, locked BOOLEAN NOT NULL, lockgranted TIMESTAMP(6) WITHOUT TIME ZONE, lockedby CHARACTER VARYING(255), CONSTRAINT pk_databasechangeloglock PRIMARY KEY (id));
DROP TABLE deposit;
CREATE TABLE deposit (id CHARACTER VARYING NOT NULL, date TIMESTAMP(6) WITHOUT TIME ZONE, CONSTRAINT pk_deposit PRIMARY KEY (id));
COMMENT ON TABLE deposit IS 'Deposit table';
COMMENT ON COLUMN deposit.id IS 'Id column for deposit';
COMMENT ON COLUMN deposit.date IS 'Date deposit was made';
DROP TABLE employer;
CREATE TABLE employer (id CHARACTER VARYING NOT NULL, application_id CHARACTER VARYING NOT NULL, name CHARACTER VARYING, street_address CHARACTER VARYING, city CHARACTER VARYING, state CHARACTER VARYING(2), postal_code CHARACTER VARYING, phone CHARACTER VARYING, email_address CHARACTER VARYING, start_date TIMESTAMP(6) WITHOUT TIME ZONE, end_date TIMESTAMP(6) WITHOUT TIME ZONE, CONSTRAINT pk_employer PRIMARY KEY (id));
COMMENT ON TABLE employer IS 'Employer table';
COMMENT ON COLUMN employer.id IS 'Id for employer';
COMMENT ON COLUMN employer.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN employer.name IS 'Name of employer';
COMMENT ON COLUMN employer.street_address IS 'Address of employer';
COMMENT ON COLUMN employer.city IS 'City employer is located in';
COMMENT ON COLUMN employer.state IS 'State employer is located in';
COMMENT ON COLUMN employer.postal_code IS 'ZIP employer is located in';
COMMENT ON COLUMN employer.phone IS 'Phone number of employer';
COMMENT ON COLUMN employer.email_address IS 'Email address of employer';
COMMENT ON COLUMN employer.start_date IS 'Date which employment starts';
COMMENT ON COLUMN employer.end_date IS 'Date which employment ends';
DROP TABLE license;
CREATE TABLE license (id CHARACTER VARYING NOT NULL, license_type CHARACTER VARYING, status CHARACTER VARYING, effective_date TIMESTAMP(6) WITHOUT TIME ZONE, expiration_date TIMESTAMP(6) WITHOUT TIME ZONE, date_of_birth TIMESTAMP(6) WITHOUT TIME ZONE, social_security_number CHARACTER VARYING, number CHARACTER VARYING(20), renewal_token CHARACTER VARYING(256), user_id CHARACTER VARYING(36), license_category CHARACTER VARYING(20), license_sub_category CHARACTER VARYING(20), pending_application BOOLEAN, CONSTRAINT pk_license PRIMARY KEY (id));
COMMENT ON TABLE license IS 'License table';
COMMENT ON COLUMN license.id IS 'Id column for license';
COMMENT ON COLUMN license.license_type IS 'Type of license';
COMMENT ON COLUMN license.status IS 'Status for license';
COMMENT ON COLUMN license.effective_date IS 'Date which license is effective on';
COMMENT ON COLUMN license.expiration_date IS 'Date which license expires on';
COMMENT ON COLUMN license.date_of_birth IS 'Date of birth of applicant';
COMMENT ON COLUMN license.social_security_number IS 'SSN of applicant';
COMMENT ON COLUMN license.number IS 'The number assigned to the license';
COMMENT ON COLUMN license.renewal_token IS 'The unique token generated by the system for renewals';
COMMENT ON COLUMN license.user_id IS 'The Keycloak User Id who owns the license';
COMMENT ON COLUMN license.license_category IS 'The category of license.  Currently only Technican is supported.';
COMMENT ON COLUMN license.license_sub_category IS 'The further categorizatin of the license. (eg: Full, Temporary, Guest)';
COMMENT ON COLUMN license.pending_application IS 'Indicates if a license as a pending application associated with it.';
DROP TABLE mn_supervisor;
CREATE TABLE mn_supervisor (id CHARACTER VARYING(36) NOT NULL, application_id CHARACTER VARYING(36) NOT NULL, name CHARACTER VARYING(75), license_nbr CHARACTER VARYING(10));
DROP TABLE other_license;
CREATE TABLE other_license (id CHARACTER VARYING(36) NOT NULL, application_id CHARACTER VARYING(36) NOT NULL, state CHARACTER VARYING(2) NOT NULL, status CHARACTER VARYING(20) NOT NULL, issue_date DATE NOT NULL, license_number CHARACTER VARYING(50) NOT NULL, CONSTRAINT pk_other_license PRIMARY KEY (id));
COMMENT ON TABLE other_license IS 'Other Licenses issued by other state/jurisdiction';
COMMENT ON COLUMN other_license.id IS 'Primary key - system generated';
COMMENT ON COLUMN other_license.application_id IS 'Renewal application that reported this other license';
COMMENT ON COLUMN other_license.state IS 'State issuing the license';
COMMENT ON COLUMN other_license.status IS 'The status of the license at the time of renewal';
COMMENT ON COLUMN other_license.issue_date IS 'The issue date of the license';
COMMENT ON COLUMN other_license.license_number IS 'The license number/id of the license';
DROP TABLE other_names;
CREATE TABLE other_names (id CHARACTER VARYING(36) NOT NULL, application_id CHARACTER VARYING(36) NOT NULL, last_name CHARACTER VARYING(50) NOT NULL, first_name CHARACTER VARYING(50) NOT NULL, middle_name CHARACTER VARYING(50) NOT NULL, CONSTRAINT pk_names PRIMARY KEY (id));
DROP TABLE payment;
CREATE TABLE payment (id CHARACTER VARYING NOT NULL, payment_type CHARACTER VARYING, date TIMESTAMP(6) WITHOUT TIME ZONE, status CHARACTER VARYING, tx_number INTEGER, fee_type CHARACTER VARYING, amount NUMERIC, deposit_id CHARACTER VARYING, application_id CHARACTER VARYING, confirmation_number CHARACTER VARYING(50), CONSTRAINT pk_payment PRIMARY KEY (id));
COMMENT ON TABLE payment IS 'Payment table';
COMMENT ON COLUMN payment.id IS 'Id column for payment';
COMMENT ON COLUMN payment.payment_type IS 'Payment type';
COMMENT ON COLUMN payment.date IS 'Date payment was made';
COMMENT ON COLUMN payment.status IS 'Payment status';
COMMENT ON COLUMN payment.tx_number IS 'System generated transaction id for payment';
COMMENT ON COLUMN payment.fee_type IS 'Indicates type of fee (initial, renewal, late) associated with payment amount.';
COMMENT ON COLUMN payment.amount IS 'Payment ammount';
COMMENT ON COLUMN payment.deposit_id IS 'Foreign key to deposit table, indicates id of deposit';
COMMENT ON COLUMN payment.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN payment.confirmation_number IS 'US Bank confirmation of successful transaction.';
DROP TABLE question;
CREATE TABLE question (code CHARACTER VARYING(36) NOT NULL, question_text CHARACTER VARYING(500) NOT NULL, trigger_addl_response CHARACTER VARYING(10), addl_response_text CHARACTER VARYING(500), CONSTRAINT pk_question PRIMARY KEY (code));
DROP TABLE question_response;
CREATE TABLE question_response (id CHARACTER VARYING(36) NOT NULL, question_code CHARACTER VARYING(36) NOT NULL, application_id CHARACTER VARYING(36) NOT NULL, response CHARACTER VARYING(250), additional_response CHARACTER VARYING(250), CONSTRAINT pk_question_response PRIMARY KEY (id));
ALTER TABLE "address" ADD CONSTRAINT fk_address_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "application" ADD CONSTRAINT fk_application_license FOREIGN KEY ("license_id") REFERENCES "license" ("id");
ALTER TABLE "attachment" ADD CONSTRAINT fk_attachment_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "employer" ADD CONSTRAINT fk_employer_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "other_license" ADD CONSTRAINT fk_other_license_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "other_names" ADD CONSTRAINT fk_names_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "payment" ADD CONSTRAINT fk_payment_deposit FOREIGN KEY ("deposit_id") REFERENCES "deposit" ("id");
ALTER TABLE "payment" ADD CONSTRAINT fk_payment_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
ALTER TABLE "question_response" ADD CONSTRAINT fk_question_response_question FOREIGN KEY ("question_code") REFERENCES "question" ("code");
ALTER TABLE "question_response" ADD CONSTRAINT fk_question_response_application FOREIGN KEY ("application_id") REFERENCES "application" ("id");
DROP VIEW license_current;
CREATE VIEW license_current (id, number, license_type, status, effective_date, expiration_date, date_of_birth, renewal_token, first_name, middle_name, last_name, street_address, city, state, postal_code, address_type, preferred, personal_phone, work_phone, mobile_phone, email_address, user_id, license_category, license_sub_category, pending_application) AS  SELECT a.id,
    a.number,
    a.license_type,
    a.status,
    a.effective_date,
    a.expiration_date,
    a.date_of_birth,
    a.renewal_token,
    b.first_name,
    b.middle_name,
    b.last_name,
    c.street_address,
    c.city,
    c.state,
    c.postal_code,
    c.address_type,
    c.preferred,
    b.personal_phone,
    b.work_phone,
    b.mobile_phone,
    b.email_address,
    a.user_id,
    a.license_category,
    a.license_sub_category,
    a.pending_application
   FROM ((license a
     LEFT JOIN application b ON ((((b.license_id)::text = (a.id)::text) AND ((b.status)::text = 'APPROVED'::text) AND (b.received_date = ( SELECT max(b1.received_date) AS max
           FROM application b1
          WHERE (((b1.license_id)::text = (a.id)::text) AND ((b1.status)::text = 'APPROVED'::text)))))))
     LEFT JOIN address c ON ((((b.id)::text = (c.application_id)::text) AND (c.preferred = true))));
COMMENT ON TABLE license_current IS 'Renders the current demographic information associated with a license - prepopulate renewal form';
