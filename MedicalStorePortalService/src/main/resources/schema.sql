DROP TABLE IF EXISTS Medicine_inventory;
--------------------------------------------------
-- ***  Medicine inventory  ******
--------------------------------------------------
CREATE TABLE Medicine_inventory
(
   id INT AUTO_INCREMENT,
   medicine_name varchar (255) PRIMARY KEY,
   quantity INT NOT NULL,
   price INT NOT NULL,
   sell_med_qantity INT,
   avail_med_quantity INT
);