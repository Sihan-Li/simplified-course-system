Drop table if exists Student, Faculty, Classroom, Course,CourseStudent;
create table Student(
	id integer not null,
	name varchar(30) not null,
	password varchar(30) not null,
	primary key (id)
);

create table Faculty(
	id integer not null,
	name varchar(30) not null,
	password varchar(30) not null,
	primary key (id)
);

create table Classroom(
	id integer not null,
	location varchar(30),
	primary key (id)
);

create table Course(
	courseID int,
    Name varchar(30),
    time varchar(20),
    facultyID integer,
    ClassroomID integer,
    prerequisite int,
    primary key (courseID),
    foreign key (facultyID) references Faculty(id),
    foreign key (ClassroomID) references Classroom(id)
);

create table CourseStudent(
	id int,
	CourseID int,
	StudentID int,
	primary key (id),
	foreign key (CourseID) references Course(id),
	foreign key (StudentID) references Student(id)
);

create table CourseFaculty(
	id int,
	CourseID int,
	FacultyID int,
	primary key (id),
	foreign key (CourseID) references Course(id),
	foreign key (FacultyID) references Faculty(id)
)