#drop table if exists student;
#drop table if exists course;
#drop table if exists selection;

create table student (
  id integer auto_increment primary key,
  username varchar(25) not null,
  department varchar(25) not null,
  grade integer not null,
  password varchar(100) not null,
  userrole varchar(25) not null
);

create table course (
  id integer auto_increment primary key,
  subject varchar(100) not null,
  capacity integer not null,
  ordered integer not null
);

create table selection (
  id integer auto_increment primary key,
  student integer,
  course integer
);
