CREATE TABLE USER (
   ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
   NAME VARCHAR(50) NOT NULL,
   EMAIL VARCHAR(50) NOT NULL,
   PASSWORD VARCHAR(50),
   CREATED DATE,
   MODIFIED DATE
);

INSERT INTO USER (NAME, EMAIL, PASSWORD, CREATED, MODIFIED) VALUES ('DIEGO', 'DIEGODUARTE98@GMAIL.COM', '12313245', CURRENT_DATE, CURRENT_DATE);

CREATE TABLE PHONE (
   ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
   DDD INTEGER NOT NULL,
   NUMBER INTEGER NOT NULL,
   USER_ID INTEGER NOT NULL,
   FOREIGN KEY (USER_ID) REFERENCES USER(ID)
);

INSERT INTO PHONE (DDD, NUMBER, USER_ID) VALUES (11, 957995397, 1);