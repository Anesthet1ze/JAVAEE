-- add initial employee data
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Mr.','Bigshot','Smartypants','(555)555-5551','bs@abc.com');
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Mrs.','Penny','Pincher','(555)555-5551','pp@abc.com');
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Mr.','Smoke','Andmirrors','(555)555-5552','sa@abc.com');
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Mr.','Sam','Slick','(555)555-5552','ng@abc.com');
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Mr.','Sloppy','Joe','(555)555-5553','sj@abc.com');
INSERT INTO Employee (Title,FirstName,LastName,PhoneNo,Email)
 VALUES ('Captain','Kristina','Vanderboog','(555)555-5554','kv@abc.com');
-- add expense categories
INSERT INTO Expense_Category (Id, Description) VALUES ('BSM','Business Meetings');
INSERT INTO Expense_Category (Id, Description) VALUES ('ENT','Entertainment');
INSERT INTO Expense_Category (Id, Description) VALUES ('PARK','Parking');
INSERT INTO Expense_Category (Id, Description) VALUES ('LDG','Lodging');
INSERT INTO Expense_Category (Id, Description) VALUES ('TRAV','Travel');
INSERT INTO Expense_Category (Id, Description) VALUES ('MEAL','Meals');
INSERT INTO Expense_Category (Id, Description) VALUES ('TUI','Tuition');
INSERT INTO Expense_Category (Id, Description) VALUES ('MISC','Miscealleous');
INSERT INTO Expense_Category (Id, Description) VALUES ('OTH','OTHER');
-- add some expenses to seed the table
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (1,'MEAL','Taco Allowance',true,'2020-04-13',19.99);
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (1,'MISC','10 boxes of swords',true,'2020-04-15',219.99);
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (1,'OTH','Extreneous puppy petting',true,'2020-04-13',39.99);
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (2,'TUI','Tuition for Cobol course',true,'2020-05-19',29.99);
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (2,'MISC','Bought widgets for the office',true,'2020-05-20',19.99);
INSERT INTO Expense (EmployeeId,CategoryId,Description,Receipt,DateIncurred,Amount)
 VALUES (2,'OTH','Donation for Cancer Society',true,'2020-05-21',20.00);
