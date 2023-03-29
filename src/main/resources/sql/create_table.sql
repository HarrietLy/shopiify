
create table product (
  	product_ID  SERIAL PRIMARY KEY,
	product_name varchar(50),
	description varchar(200),
	product_status_id int,
  	category_id int,
  	units varchar(20),
  	stock int,
  	produce decimal(10,2)
);
