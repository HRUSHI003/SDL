create database lms;

use lms;

create table book ( bookCode int primary key, bookName varchar(100), authorName varchar(100), publisher varchar(100),price int,status varchar(100));

create table student ( studentName varchar(100), studId varchar(100) primary key, password varchar(100), email varchar(100),noOfBook varchar(100));

create table issueBook ( bookCode int , studId varchar(100), issueDate date, returnDate date,FOREIGN KEY bookCode(bookCode) REFERENCES book(bookCode) ON UPDATE CASCADE ON DELETE RESTRICT, FOREIGN KEY studId(studId) REFERENCES student(studId) ON UPDATE CASCADE ON DELETE RESTRICT);
