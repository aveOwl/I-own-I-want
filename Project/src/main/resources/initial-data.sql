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

INSERT INTO iowniwant.user (first_name, last_name, nick_name, user_password, email)
values ('admin', 'admin', 'admin', 'admin', 'admin@gmail.com');

INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes, user_id)
values ('PC Computer',164.69,'THIS TEXT REPRESENTS AN ARTICLE FOR WHATEVER YOU DID ADD','22/01/12'
	,'Gently my mind escapes into the relaxing mode of pleasure A pleasure that will take my mind off the reality of life ', 1);
INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes, user_id)
values ('Waschmachine',500.56,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','13/05/14'
	,'My past life Life as I know it know And whatever may come it slowly disappears', 1);
INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes, user_id)
values ('Vacuum Triode',5124.78,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','05/04/16'
	,'to somewhere in the back of my mind It will remain there until I wish to retrieve it ', 1);


/*
представление не является самостоятельной частью набора данных, хранящегося в базе

row_number - нумерует строки, возвращаемые запросом

Over - задает порядок нумерации строк

PARTITION - определяет группы в которые будут собираться значения для задания нового порядка независимой нумерации

ORDER BY - набор строк будет отсортирован по значению заданного столбца
*/

CREATE OR REPLACE VIEW iowniwant.v_goals
AS SELECT goals_id, title, cost, description, pubdate, notes,user_id,
		 row_number() OVER (PARTITION BY user_id ORDER BY goals_id) AS v_goals_id FROM iowniwant.goals;
