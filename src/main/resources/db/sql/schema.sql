SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX material_idx ON material;
DROP INDEX material_batch_idx ON material_batch;
DROP INDEX material_type_idx ON material_type;
DROP INDEX patient_info_idx ON patient_info;
DROP INDEX prescription_idx ON prescription;
DROP INDEX role_idx_1 ON role;
DROP INDEX session_idx ON session;
DROP INDEX session_detail_idx ON session_detail;
DROP INDEX system_idx ON system_parameter;
DROP INDEX user_idx_1 ON user;



/* Drop Tables */

DROP TABLE IF EXISTS material_batch;
DROP TABLE IF EXISTS prescription;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS material_type;
DROP TABLE IF EXISTS session_detail;
DROP TABLE IF EXISTS session;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS patient_info;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS system_parameter;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE material
(
	id bigint NOT NULL AUTO_INCREMENT,
	material_type_id bigint NOT NULL,
	code varchar(45) NOT NULL,
	name varchar(100) NOT NULL,
	composition varchar(500),
	suggest varchar(75),
	unit varchar(20) NOT NULL,
	total int,
	price decimal(13,2) DEFAULT 0.00,
	sale decimal(13,2) DEFAULT 0.00,
	PRIMARY KEY (id)
);


CREATE TABLE material_batch
(
	id bigint NOT NULL AUTO_INCREMENT,
	material_id bigint NOT NULL,
	code varchar(45) NOT NULL,
	unit varchar(20) NOT NULL,
	amount int,
	receipt_date date,
	expired_date date,
	price decimal(13,2),
	sales decimal(13,2),
	created_by varchar(45),
	created_date datetime,
	modified_by varchar(45),
	modified_date datetime,
	PRIMARY KEY (id)
);


CREATE TABLE material_type
(
	id bigint NOT NULL AUTO_INCREMENT,
	code varchar(25) NOT NULL,
	name varchar(75) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE patient
(
	id bigint NOT NULL AUTO_INCREMENT,
	patient_info_id bigint NOT NULL,
	code varchar(25) NOT NULL,
	name varchar(75),
	dob date,
	gender varchar(10),
	phone varchar(15),
	address varchar(255),
	PRIMARY KEY (id)
);


CREATE TABLE patient_info
(
	id bigint NOT NULL AUTO_INCREMENT,
	f_name varchar(45),
	f_phone varchar(15),
	m_name varchar(45),
	m_phone varchar(15),
	note varchar(255),
	PRIMARY KEY (id)
);


CREATE TABLE prescription
(
	id bigint NOT NULL AUTO_INCREMENT,
	material_id bigint NOT NULL,
	session_id bigint NOT NULL,
	dosage varchar(500) NOT NULL,
	amount int NOT NULL,
	unit varchar(25),
	note varchar(255),
	PRIMARY KEY (id)
);


CREATE TABLE role
(
	id bigint NOT NULL AUTO_INCREMENT,
	description varchar(255) NOT NULL,
	name varchar(75) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE session
(
	id bigint NOT NULL AUTO_INCREMENT,
	patient_id bigint NOT NULL,
	code varchar(45) NOT NULL,
	diagnosis varchar(100),
	symptom varchar(255),
	treatment_plan varchar(255),
	note varchar(255),
	status tinyint(1),
	created_by varchar(75),
	created_date datetime,
	modified_by varchar(75),
	modified_date datetime,
	next_time date,
	total_price decimal(10,2),
	PRIMARY KEY (id)
);


CREATE TABLE session_detail
(
	id bigint NOT NULL AUTO_INCREMENT,
	session_id bigint NOT NULL,
	blood_pressure varchar(45),
	weight decimal(5,2),
	heartbeat int,
	left_eye decimal(5,2),
	right_eye decimal(5,2),
	left_sph varchar(25),
	right_sph varchar(25),
	left_cyl varchar(25),
	right_cyl varchar(25),
	left_axis varchar(25),
	right_axis varchar(25),
	left_add varchar(25),
	right_add varchar(25),
	left_pd varchar(25),
	right_pd varchar(25),
	PRIMARY KEY (id)
);


CREATE TABLE system_parameter
(
	id bigint NOT NULL AUTO_INCREMENT,
	param_name varchar(100) NOT NULL,
	param_value varchar(100),
	description varchar(255),
	type varchar(25),
	PRIMARY KEY (id)
);


CREATE TABLE user
(
	id bigint NOT NULL AUTO_INCREMENT,
	username varchar(75) NOT NULL,
	password varchar(125) NOT NULL,
	email varchar(100),
	fullname varchar(100),
	created_by varchar(75),
	created_date datetime,
	modified_by varchar(75),
	modified_date datetime,
	enabled varchar(25),
	PRIMARY KEY (id)
);


CREATE TABLE user_role
(
	user_id bigint NOT NULL,
	role_id bigint NOT NULL
);



/* Create Foreign Keys */

ALTER TABLE material_batch
	ADD FOREIGN KEY (material_id)
	REFERENCES material (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE prescription
	ADD FOREIGN KEY (material_id)
	REFERENCES material (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE material
	ADD FOREIGN KEY (material_type_id)
	REFERENCES material_type (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE session
	ADD FOREIGN KEY (patient_id)
	REFERENCES patient (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE patient
	ADD FOREIGN KEY (patient_info_id)
	REFERENCES patient_info (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE user_role
	ADD FOREIGN KEY (role_id)
	REFERENCES role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE prescription
	ADD FOREIGN KEY (session_id)
	REFERENCES session (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE session_detail
	ADD FOREIGN KEY (session_id)
	REFERENCES session (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE user_role
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX material_idx ON material (id DESC, code DESC, name DESC);
CREATE INDEX material_batch_idx ON material_batch (id DESC);
CREATE INDEX material_type_idx ON material_type (id DESC);
CREATE INDEX patient_info_idx ON patient_info (id DESC);
CREATE INDEX prescription_idx ON prescription (id DESC);
CREATE INDEX role_idx_1 ON role (id DESC);
CREATE INDEX session_idx ON session (id DESC, code DESC);
CREATE INDEX session_detail_idx ON session_detail (id DESC);
CREATE INDEX system_idx ON system_parameter (id DESC);
CREATE INDEX user_idx_1 ON user (id DESC);



