INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)VALUES ('123 Maple
St','London','On', 'N1N-1N1','(555)555-5555','Trusted','ABC Supply Co.','abc@supply.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('543
Sycamore Ave','Toronto','On', 'N1P-1N1','(999)555-5555','Trusted','Big Bills
Depot','bb@depot.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Untrusted','Shady Sams','ss@underthetable.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)
VALUES ('10 Fifteen Mile Rd','Denfield','On','N0M-1P0','(123)456-789','Trusted','KrissyRules','k_vanderboog@fanshaweonline.ca');

--add products to seed the table
INSERT INTO Product (VendorId, Id, Name, CostPrice, MSRP,ROP,EOQ,QOH,QOO,QRCode, QRCodeTxt)
VALUES (1, '5xYtz', 'Coffee', 10.99, 15.99,8,2,0,10,'','');
INSERT INTO Product (VendorId, Id, Name, CostPrice, MSRP,ROP,EOQ,QOH,QOO,QRCode, QRCodeTxt)
VALUES (1, '5xYtX', 'Bread', 1.99, 5.99,6,15,25,0,'','');
INSERT INTO Product (VendorId, Id, Name, CostPrice, MSRP,ROP,EOQ,QOH,QOO,QRCode, QRCodeTxt)
VALUES (1, 'Hy76FG', 'Eggs', 7.99, 4.99,15,22,50,15,'','');
INSERT INTO Product (VendorId, Id, Name, CostPrice, MSRP,ROP,EOQ,QOH,QOO,QRCode, QRCodeTxt)
VALUES (1, 'L3GtyS', 'Milk', 6.99, 5.99,5,9,15,60,'','');
INSERT INTO Product (VendorId, Id, Name, CostPrice, MSRP,ROP,EOQ,QOH,QOO,QRCode, QRCodeTxt)
VALUES (1, 'TyZhDk', 'Juice', 12.49, 15.99,5,1,0,4,'','');
