CREATE TABLE "cliente"(
 "id" BigSerial NOT NULL,
 "nome" Character varying(300) NOT NULL,
 "telefone" Character varying(11) NOT NULL,
 "documento" Character varying(11) NOT NULL
);

ALTER TABLE "cliente" ADD CONSTRAINT "PK_cliente" PRIMARY KEY ("id");

INSERT INTO "cliente" ("nome", "telefone", "documento") values
	('Cristian Elder Urbainski', '49991021372', '74426136083'),
	('Joeli Vivian Urbainski', '49991223302', '83747906010'),
	('Felipe Luis Urbainski', '49991314681', '81044115009'),
	('Luis Urbainski', '49991145632', '02649374063'),
	('Marelize Weschenfelder Urbainski', '49991459855', '64605823000');