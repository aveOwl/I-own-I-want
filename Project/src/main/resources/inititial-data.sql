DROP TABLE iowniwant.user;

CREATE TABLE iowniwant.user (
	user_id					bigserial NOT NULL,
	first_name			varchar(60) NOT NULL,
	last_name				varchar(60) NOT NULL,
	nick_name				varchar(60) NOT NULL UNIQUE,
	user_password		varchar(60) NOT NULL,
	email						varchar(60),
	month_salary		real DEFAULT 0,
CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);