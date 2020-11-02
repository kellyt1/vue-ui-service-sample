--create tables
--changelog tables
DROP TABLE  databasechangelog;
CREATE TABLE
    databasechangelog
    (
        id CHARACTER VARYING(255) NOT NULL,
        author CHARACTER VARYING(255) NOT NULL,
        filename CHARACTER VARYING(255) NOT NULL,
        dateexecuted TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        orderexecuted INTEGER NOT NULL,
        exectype CHARACTER VARYING(10) NOT NULL,
        md5sum CHARACTER VARYING(35),
        description CHARACTER VARYING(255),
        comments CHARACTER VARYING(255),
        tag CHARACTER VARYING(255),
        liquibase CHARACTER VARYING(20),
        contexts CHARACTER VARYING(255),
        labels CHARACTER VARYING(255),
        deployment_id CHARACTER VARYING(10)
    );

DROP TABLE  databasechangeloglock;
CREATE TABLE
    databasechangeloglock
    (
        id INTEGER NOT NULL,
        locked BOOLEAN NOT NULL,
        lockgranted TIMESTAMP(6) WITHOUT TIME ZONE,
        lockedby CHARACTER VARYING(255),
        CONSTRAINT pk_databasechangeloglock PRIMARY KEY (id)
    );

--functional tables
DROP TABLE IF EXISTS question_response;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS attachment;
DROP TABLE IF EXISTS employer;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS license;
DROP TABLE IF EXISTS deposit;


CREATE TABLE
    license(
        id VARCHAR(36) NOT NULL,
        license_type VARCHAR(20) NOT NULL,
        status VARCHAR(20) NOT NULL,
        effective_date DATE NOT NULL,
        expiration_date DATE NOT NULL,
        date_of_birth DATE,
        social_security_number VARCHAR(11),
        CONSTRAINT pk_license PRIMARY KEY (id)
    );


CREATE TABLE
    deposit(
        id VARCHAR(36) NOT NULL,
        date DATE NOT NULL,
        CONSTRAINT pk_deposit PRIMARY KEY (id)
    );




CREATE TABLE
    question(
      code VARCHAR(36) NOT NULL,
      question_text VARCHAR(500) NOT NULL,
      trigger_addl_response VARCHAR(10),
      addl_response_text VARCHAR(500),
      CONSTRAINT pk_question PRIMARY KEY (code)
    );


CREATE TABLE
    application(
        id VARCHAR(36) NOT NULL,
        license_id VARCHAR(36) NOT NULL,
        application_type VARCHAR(20) NOT NULL,
        apply_method VARCHAR(20) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        first_name VARCHAR(50) NOT NULL,
        middle_name VARCHAR(50) NOT NULL,
        personal_phone VARCHAR(15),
        work_phone VARCHAR(15),
        email_address VARCHAR(75)  NOT NULL,
        supervised_est_name VARCHAR(75),
        supervised_est_full_address VARCHAR(100),
        supervised_est_phone_number VARCHAR(15),
        supervised_est_website VARCHAR(100),
        supervised_est_hours VARCHAR(10),
        additional_information VARCHAR(250),
        CONSTRAINT pk_application PRIMARY KEY (id),
        CONSTRAINT fk_application_license FOREIGN KEY (license_id) REFERENCES license (id)
    );

CREATE TABLE
    address(
        id VARCHAR(36),
        street_address VARCHAR(75)  NOT NULL,
        city VARCHAR(50)  NOT NULL,
        state VARCHAR(2)  NOT NULL,
        postal_code varchar(10)  NOT NULL,
        address_type VARCHAR(10) NOT NULL,
        preferred BOOLEAN,
        application_id VARCHAR(36),
        CONSTRAINT pk_address PRIMARY KEY (id),
        CONSTRAINT fk_address_application FOREIGN KEY (application_id) REFERENCES application (id)
    );

CREATE TABLE
    payment(
        id VARCHAR(36) NOT NULL,
        payment_type VARCHAR(20) NOT NULL,
        date DATE NOT NULL,
        status VARCHAR(20) NOT NULL,
        number VARCHAR(20),
        name VARCHAR(75),
        amount DECIMAL NOT NULL,
        deposit_id VARCHAR(36),
        application_id VARCHAR(36),
        CONSTRAINT pk_payment PRIMARY KEY (id),
        CONSTRAINT fk_payment_deposit FOREIGN  KEY (deposit_id) REFERENCES deposit (id),
        CONSTRAINT fk_payment_application FOREIGN KEY (application_id) REFERENCES application (id)
    );


CREATE TABLE
    question_response(
      id VARCHAR(36) NOT NULL,
      question_code VARCHAR(36) NOT NULL,
      application_id VARCHAR(36) NOT NULL,
      response VARCHAR(250),
      additional_response VARCHAR(250),
      CONSTRAINT pk_question_response PRIMARY KEY (id),
      CONSTRAINT fk_question_response_question FOREIGN  KEY (question_code) REFERENCES question (code),
      CONSTRAINT fk_question_response_application FOREIGN KEY (application_id) REFERENCES application (id)
    );


CREATE TABLE
    attachment(
        id VARCHAR(36) NOT NULL,
        application_id VARCHAR(36),
        attachment_type VARCHAR(20) NOT NULL,
        other_attachment_type VARCHAR(75),
        file_name VARCHAR,
        url VARCHAR,
        --will be more cols?  specific to aws
        CONSTRAINT pk_attachment PRIMARY KEY (id),
        CONSTRAINT fk_attachment_application FOREIGN KEY (application_id) REFERENCES application (id)
    );


CREATE TABLE
    employer(
      id VARCHAR(36) NOT NULL,
      application_id VARCHAR(36) NOT NULL,
      name VARCHAR(75) NOT NULL,
      street_address VARCHAR(75) NOT NULL,
      city VARCHAR(50) NOT NULL,
      state VARCHAR(2) NOT NULL,
      postal_code VARCHAR(10) NOT NULL,
      phone VARCHAR(15),
      email_address VARCHAR(75),
      start_date DATE NOT NULL,
      end_date DATE,
      CONSTRAINT pk_employer PRIMARY KEY (id),
      CONSTRAINT fk_employer_application FOREIGN KEY (application_id) REFERENCES application (id)
    );

    CREATE TABLE
    other_names(
        id VARCHAR(36) NOT NULL,
        application_id VARCHAR(36) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        first_name VARCHAR(50) NOT NULL,
        middle_name VARCHAR(50) NOT NULL,
        CONSTRAINT pk_names PRIMARY KEY (id),
        CONSTRAINT fk_names_application FOREIGN KEY (application_id) REFERENCES application (id)
    );

    CREATE TABLE
    mn_supervisor(
        id VARCHAR(36) NOT NULL,
        application_id VARCHAR(36) NOT NULL,
        name VARCHAR(75),
        license_nbr VARCHAR(10),
        CONSTRAINT pk_mn_supervisor PRIMARY KEY (id)
    );

    CREATE TABLE
    application_property(
        key CHARACTER VARYING(100) NOT NULL,
        value CHARACTER VARYING(4000) NOT NULL,
        CONSTRAINT pk_application_property PRIMARY KEY (key)
    );


--comments for tables
COMMENT ON TABLE license IS 'License table';
COMMENT ON COLUMN license.id IS 'Id column for license';
COMMENT ON COLUMN license.license_type IS 'Type of license';
COMMENT ON COLUMN license.status IS 'Status for license';
COMMENT ON COLUMN license.effective_date IS 'Date which license is effective on';
COMMENT ON COLUMN license.expiration_date IS 'Date which license expires on';
COMMENT ON COLUMN license.date_of_birth IS 'Date of birth of applicant';
COMMENT ON COLUMN license.social_security_number IS 'SSN of applicant';

COMMENT ON TABLE deposit IS 'Deposit table';
COMMENT ON COLUMN deposit.id IS 'Id column for deposit';
COMMENT ON COLUMN deposit.date IS 'Date deposit was made';

COMMENT ON TABLE payment IS 'Payment table';
COMMENT ON COLUMN payment.id IS 'Id column for payment';
COMMENT ON COLUMN payment.payment_type IS 'Payment type';
COMMENT ON COLUMN payment.date IS 'Date payment was made';
COMMENT ON COLUMN payment.status IS 'Payment status';
COMMENT ON COLUMN payment.number IS 'Payment number';
COMMENT ON COLUMN payment.name IS 'Name associated with payment';
COMMENT ON COLUMN payment.amount IS 'Payment amount';

COMMENT ON TABLE question IS 'Questions table';
COMMENT ON COLUMN question.code IS 'Identitfying code for question';
COMMENT ON COLUMN question.question_text IS 'Text of question';
COMMENT ON COLUMN question.trigger_addl_response IS 'Whether question triggers and addtional response';
COMMENT ON COLUMN question.addl_response_text IS 'Text of additional response if exists';

COMMENT ON TABLE question_response IS 'Question responses table';
COMMENT ON COLUMN question_response.id IS 'Id for question response';
COMMENT ON COLUMN question_response.question_code IS 'Foreign key to question table, indicates id code of question';
COMMENT ON COLUMN question_response.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN question_response.response IS 'Response to question';
COMMENT ON COLUMN question_response.additional_response IS 'Response to additional question if exists';

COMMENT ON TABLE attachment IS 'Attachments table';
COMMENT ON COLUMN attachment.id IS 'Id for attachment';
COMMENT ON COLUMN attachment.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN attachment.attachment_type IS 'Type of attachment';
COMMENT ON COLUMN attachment.other_attachment_type IS 'Other type of attachment';
COMMENT ON COLUMN attachment.file_name IS 'Name of attachment file';
COMMENT ON COLUMN attachment.url IS 'Url of attachment';

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

COMMENT ON TABLE address IS 'Addresses table';
COMMENT ON COLUMN address.id IS 'Id for name';
COMMENT ON COLUMN address.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN address.street_address IS 'Address of applicant';
COMMENT ON COLUMN address.city IS 'Address city of applicant';
COMMENT ON COLUMN address.state IS 'Address state of applicant';
COMMENT ON COLUMN address.postal_code IS 'ZIP of applicant';
COMMENT ON COLUMN address.address_type IS 'Type of address';
COMMENT ON COLUMN address.preferred IS 'If address preferred address for contact';

COMMENT ON TABLE other_names IS 'Other Names table';
COMMENT ON COLUMN other_names.id IS 'Id for name';
COMMENT ON COLUMN other_names.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN other_names.last_name IS 'Alternate last name of applicant';
COMMENT ON COLUMN other_names.first_name IS 'Alternate first name of applicant';
COMMENT ON COLUMN other_names.middle_name IS 'Alternate middle name of applicant';

COMMENT ON TABLE mn_supervisor IS 'MN Supervisor table';
COMMENT ON COLUMN mn_supervisor.id IS 'Id for supervisor';
COMMENT ON COLUMN mn_supervisor.application_id IS 'Foreign key to application table, indicates id of application';
COMMENT ON COLUMN mn_supervisor.name IS 'Name of MN Supervisor';
COMMENT ON COLUMN mn_supervisor.license_nbr IS 'License number of MN Supervisor';

COMMENT ON TABLE application_property IS 'App properties';
COMMENT ON COLUMN application_property.key IS 'Key for property';
COMMENT ON COLUMN application_property.value IS 'Value for property';

