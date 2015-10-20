use master
go

drop database Library
go

create database Library
go

use Library
go

create table LoaiSach (
	MaLoai nvarchar(10) primary key,
	Tenloai nvarchar(50)
)
go

create table NhaXB(
	MaNXB nvarchar(10)primary key,
	TenNXB nvarchar(50),
	Diachi nvarchar(50),
	SDT nvarchar(50),
	Email nvarchar(50)
	
)
go

create table Tacgia(
	MaTG nvarchar(10) primary key,
	Tentacgia nvarchar(50),
	Diachi nvarchar(50),
	SDT nvarchar(50),
	Email nvarchar(50)
)

create table Sach (
	MaSach nvarchar(10) primary key,
	MaLoai nvarchar(10) foreign key references LoaiSach,
	Tensach nvarchar(50),
	Tomtat nvarchar(50),
	MaTG nvarchar(10) foreign key references Tacgia,
	MaNXB nvarchar(10) foreign key references NhaXB
)
go

insert into LoaiSach values ('LS1','Khoa hoc')
insert into LoaiSach values ('LS2','Sinh vat hoc')
insert into LoaiSach values ('LS3','Toan hoc')
insert into LoaiSach values ('LS4','Van hoc')
go

insert into NhaXB values ('NXB1','Kim Dong','Ha Noi','0987654321','kimdong@gmail.com')
insert into NhaXB values ('NXB2','Giao duc','Ha Noi','0987654321','giaoduc@gmail.com')
insert into NhaXB values ('NXB3','Da Nang','Da Nang','0987654321','danang@gmail.com')
insert into NhaXB values ('NXB4','GTVT','Ho Chi Minh','0987654321','gtvt@gmail.com')
go

insert into Tacgia values ('TG1','Nguyen Thai Binh','Kim Nguu','078381723','ntb@gmail.com')
insert into Tacgia values ('TG2','Nguyen Thanh Son','Trung Hoa','078381723','nts@gmail.com')
insert into Tacgia values ('TG3','Le Ba Lam','Thanh Hoa','3432432432','lbl@gmail.com')
insert into Tacgia values ('TG4','Tran Thi Tuyet','Nghe An','565465466546','ttt@gmail.com')
go

insert into Sach values('S1','LS2','Tim hieu sinh vat hoc','Cho meo ga vit','TG1','NXB1')
insert into Sach values('S2','LS1','Bau troi rat cao','Quan sat thien van','TG2','NXB2')
insert into Sach values('S3','LS3','Dai so','Cac dang toan dai so','TG3','NXB3')
insert into Sach values('S4','LS4','Chieu qua pha Hau Giang','Loi bai hat','TG4','NXB4')
go

create view ThongtinSach as
select Sach.MaSach, Sach.Tensach, Sach.Tomtat, LoaiSach.Tenloai, Tacgia.Tentacgia, NhaXB.TenNXB
from Sach
left join LoaiSach
on Sach.MaLoai=LoaiSach.MaLoai
left join Tacgia
on Sach.MaTG = Tacgia.MaTG
left join NhaXB
on Sach.MaNXB = NhaXB.MaNXB