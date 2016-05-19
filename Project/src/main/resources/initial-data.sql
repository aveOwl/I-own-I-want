DROP VIEW iowniwant.v_goal;
DROP VIEW iowniwant.v_task;
DROP TABLE iowniwant.task;
DROP TABLE iowniwant.goal;
DROP TABLE iowniwant.user;

CREATE TABLE iowniwant.user (
	user_id         BIGSERIAL NOT NULL,
	first_name      VARCHAR(60) NOT NULL,
	last_name       VARCHAR(60) NOT NULL,
	nick_name       VARCHAR(60) NOT NULL UNIQUE,
	user_password   VARCHAR(60) NOT NULL,
	email           VARCHAR(60),
	month_salary    REAL DEFAULT 0,
	CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);

CREATE TABLE iowniwant.goal (
	goal_id       BIGSERIAL  NOT NULL,
	title         VARCHAR(255) NOT NULL,
	cost          REAL DEFAULT 0,
	description   VARCHAR(255),
	pubdate       DATE,
	notes         VARCHAR(255),
	user_id       INT,
	CONSTRAINT goal_id_pk PRIMARY KEY(goal_id),
	CONSTRAINT goal_fk    FOREIGN KEY (user_id)
	REFERENCES iowniwant.user(user_id)
);

CREATE TABLE iowniwant.task (
	task_id           BIGSERIAL NOT NULL,
	description       VARCHAR(255) NOT NULL,
	goal_id           INT,
	CONSTRAINT task_id_pk PRIMARY KEY (task_id),
	CONSTRAINT task_fk    FOREIGN KEY (goal_id)
	REFERENCES iowniwant.goal(goal_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE OR REPLACE VIEW iowniwant.v_goal AS SELECT goal.goal_id,
																						 goal.title,
																						 goal.cost,
																						 goal.description,
																						 goal.pubdate,
																						 goal.notes,
																						 goal.user_id,
																						 row_number() OVER (PARTITION BY goal.user_id ORDER BY goal.goal_id) AS v_goal_id
																					 FROM iowniwant.goal;

CREATE OR REPLACE VIEW iowniwant.v_task AS SELECT task.task_id,
																						 task.description,
																						 task.goal_id,
																						 row_number() OVER (PARTITION BY task.goal_id ORDER BY task.task_id) AS v_task_id
																					 FROM iowniwant.task;

INSERT INTO iowniwant.user (first_name, last_name, nick_name, user_password, email)
values ('admin', 'admin', 'admin', 'admin', 'admin@gmail.com');
INSERT INTO iowniwant.user (first_name, last_name, nick_name, user_password, email)
values ('user', 'qwerty', 'user', 'admin', 'user@gmail.com');

/*INSERT INTO iowniwant.goal (title, cost, description, pubdate, notes, user_id)
values ('Title Sample 1', 100.00, 'Description Sample 1', '22/01/12', 'Notes Sample 1', 1);
INSERT INTO iowniwant.goal (title, cost, description, pubdate, notes, user_id)
values ('Title Sample 2', 500.00, 'Description Sample 2', '13/05/14', 'Notes Sample 2', 2);
INSERT INTO iowniwant.task (description, goal_id)
values ('Description Task Sample 1', 1);
INSERT INTO iowniwant.task (description, goal_id)
values ('Description Task Sample 2', 2);
INSERT INTO iowniwant.task (description, goal_id)
values ('Description Task Sample 3', 1);
INSERT INTO iowniwant.task (description, goal_id)
values ('Description Task Sample 4', 2);*/


/*
представление не является самостоятельной частью набора данных, хранящегося в базе

row_number - нумерует строки, возвращаемые запросом

Over - задает порядок нумерации строк

PARTITION - определяет группы в которые будут собираться значения для задания нового порядка независимой нумерации

ORDER BY - набор строк будет отсортирован по значению заданного столбца
*/



