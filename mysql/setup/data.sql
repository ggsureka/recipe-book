use recipeBook;

create table if not exists recipe(id int AUTO_INCREMENT not null primary key,
 name varchar(255),
 creationDate datetime default current_timestamp,
 vegetarian varchar(6),
 noOfPersons int,
 instructions varchar(6200));


insert into recipe (name, creationDate, vegetarian, noOfPersons, instructions) values
  ('Pasta', null , 'yes','2','Less spicy, made with fresh ingredients');
insert into recipe (name, creationDate, vegetarian, noOfPersons, instructions) values
  ('Pizza margherita', null, 'yes','2','Spread Tomato sauce on dough');


create table if not exists ingredient(id int AUTO_INCREMENT not null primary key,
 name varchar(255) , amount double);

insert into ingredient (name, amount) values('Flour', '1.0');
insert into ingredient (name, amount) values('Salt', '0.05');
