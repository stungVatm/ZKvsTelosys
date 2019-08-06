/*
   H2 test database schema creation 
 */

/* Create table for entity Admin */
CREATE TABLE admin (
id IDENTITY AUTO_INCREMENT NOT NULL,
nameAd VARCHAR ,
userAd VARCHAR ,
passAd VARCHAR ,
PRIMARY KEY(id)
);

