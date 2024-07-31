create extension if not exists "uuid-ossp";

create table if not exists course (
    id uuid primary key,
    name varchar(60),
    description varchar(150),
    credit int
);

create table if not exists assessment (
    course_id uuid,
    foreign key (course_id) references course (id),
    content varchar(30)
);

create table if not exists rating (
    id uuid,
    course_id uuid,
    number int check ( number between 0 and 5),
    foreign key (course_id) references course (id)
);

create table if not exists author (
    id uuid primary key,
    name varchar(30),
    email varchar(30),
    birthdate date
);

create table if not exists course_author(
    course_id uuid,
    author_id uuid,
    primary key (course_id, author_id),
    foreign key (course_id) references course (id),
    foreign key (author_id) references author (id)
);
