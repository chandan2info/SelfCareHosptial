CREATE Database Medical;
GO

USE Medical;


--------------------------------------------------
--Medicine inventory
--------------------------------------------------

--**Create table Medicine inventory**
IF OBJECT_ID ('dbo.Medicine_inventory','U') IS NOT NULL
	DROP TABLE Medicine_inventory;

	CREATE TABLE Medicine_inventory (
			id INT IDENTITY(1,1)
			, medicine_name varchar(255)
			, sell_med_qantity INT NOT NULL
			, avail_med_quantity INT NOT NULL
			, CONSTRAINT pk_combination PRIMARY KEY (medicine_name)
			)