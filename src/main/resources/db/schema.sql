#drop table if exists student;
#drop table if exists course;
#drop table if exists selection;

create table student (
  id integer auto_increment primary key,
  name varchar(25) not null,
  department varchar(25) not null,
  grade integer not null
);

create table course (
  id integer auto_increment primary key,
  name varchar(100) not null,
  capacity integer not null,
  orderednum integer not null
);

create table selection (
  id integer auto_increment primary key,
  student integer,
  course integer,
  foreign key (student) references student(id),
  foreign key (course) references course(id)
);
