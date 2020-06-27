DROP TABLE IF EXISTS Medicine_inventory;
--------------------------------------------------
--Medicine inventory
--------------------------------------------------
	CREATE TABLE Medicine_inventory (
			id INT AUTO_INCREMENT
			, medicine_name varchar(255) PRIMARY KEY
			, quantity INT NOT NULL
			, price INT NOT NULL
			, sell_med_qantity INT
			, avail_med_quantity INT
			);
INSERT INTO Medicine_inventory (medicine_name, quantity, price, sell_med_qantity, avail_med_quantity) VALUES
  ('Sipla', 5000, 2000,200,4800),
  ('SBL', 100, 5000, 10, 90),
  ('Vicks',20, 1000, 5, 15);