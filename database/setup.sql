CREATE TABLE CLAN (Id int(10) NOT NULL AUTO_INCREMENT, Name varchar(25), Score int(10), ManagerId int(10) NOT NULL, PRIMARY KEY (Id));
CREATE TABLE CLAN_ACCOUNT (AccountId int(10) NOT NULL, ClanId int(10) NOT NULL, Accepted smallint(1));
CREATE TABLE ACCOUNT (Id int(10) NOT NULL AUTO_INCREMENT, Name varchar(255), Email varchar(255), PRIMARY KEY (Id));
ALTER TABLE CLAN_ACCOUNT ADD INDEX FKCLAN_ACCOU221121 (AccountId), ADD CONSTRAINT FKCLAN_ACCOU221121 FOREIGN KEY (AccountId) REFERENCES ACCOUNT (Id);
ALTER TABLE CLAN_ACCOUNT ADD INDEX FKCLAN_ACCOU141682 (ClanId), ADD CONSTRAINT FKCLAN_ACCOU141682 FOREIGN KEY (ClanId) REFERENCES CLAN (Id);
ALTER TABLE CLAN ADD INDEX FKCLAN4375 (ManagerId), ADD CONSTRAINT FKCLAN4375 FOREIGN KEY (ManagerId) REFERENCES ACCOUNT (Id);