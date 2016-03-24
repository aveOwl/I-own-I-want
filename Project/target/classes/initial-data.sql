/*create table users.Users (
name CHARACTER VARYING(30),
salary DOUBLE PRECISION
);*/

/*INSERT INTO users.Users (name, salary) values ('Sam',160.56);
INSERT INTO users.Users (name, salary) values ('Thomas',93.56);
INSERT INTO users.Users (name, salary) values ('Amy',75.56);*/

/*
CREATE TABLE users.goals
(
  id bigserial NOT NULL,
  title character varying(255),
  cost DOUBLE PRECISION,
  description CHARACTER VARYING(255),
  pubdate CHARACTER VARYING(255),
  notes CHARACTER VARYING(255)
);*/

INSERT INTO users.goals (title, cost,description,pubdate,notes)
values ('PC Computer',164.69,'THIS TEXT REPRESENTS AN ARTICLE FOR WHATEVEN YOU DID ADD','22-13-62'
  ,'Gently my mind escapes into the relaxing mode of pleasure A pleasure that will take my mind off the reality of life ');
INSERT INTO users.goals (title, cost,description,pubdate,notes)
values ('Waschmachine',500.56,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','23-13-62'
  ,'My past life Life as I know it know And whatever may come it slowly disappears');
INSERT INTO users.goals (title, cost,description,pubdate,notes)
values ('Vacuum Triode',5124.78,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','24-13-62'
  ,'to somewhere in the back of my mind It will remain there until I wish to retrieve it ');

-- DROP TABLE users.goals;
