DROP VIEW iowniwant.v_goals;
DROP TABLE iowniwant.goals;
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

CREATE TABLE iowniwant.goals (
  goals_id 			bigserial  NOT NULL,
  title 				varchar(255) NOT NULL,
  cost 					real DEFAULT 0,
  description 	varchar(255),
  pubdate 			date,
  notes 				varchar(255),
  user_id				INT,
  CONSTRAINT goals_id_pk PRIMARY KEY(goals_id),
  CONSTRAINT goals_fk FOREIGN KEY (user_id)
  REFERENCES iowniwant.user(user_id)
);

CREATE OR REPLACE VIEW iowniwant.v_goals AS SELECT goals.goals_id,
    goals.title,
    goals.cost,
    goals.description,
    goals.pubdate,
    goals.notes,
    goals.user_id,
    row_number() OVER (PARTITION BY goals.user_id ORDER BY goals.goals_id) AS v_goals_id
   FROM iowniwant.goals;

INSERT INTO iowniwant.user (first_name, last_name, nick_name, user_password, email)
values ('admin', 'admin', 'admin', 'admin', 'admin@gmail.com');
INSERT INTO iowniwant.user (first_name, last_name, nick_name, user_password, email)
values ('user', 'qwerty', 'user', 'admin', 'user@gmail.com');

INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes, user_id)
values ('Title Sample 1', 100.00, 'Description Sample 1', '22/01/12', 'Notes Sample 1', 1);
INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes, user_id)
values ('Title Sample 2', 500.00, 'Description Sample 2', '13/05/14', 'Notes Sample 2', 2);
