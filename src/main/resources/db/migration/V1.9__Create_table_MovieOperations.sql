CREATE TABLE movie_operations (
id bigint generated by default as identity,
type varchar(10),
issued_to varchar(255),
movie_id bigint,
primary key (id),
foreign key(movie_id) references movie(id)
);