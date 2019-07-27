CREATE TABLE "reserva"(
 "id" BigSerial NOT NULL,
 "id_cliente" Bigint NOT NULL,
 "data_check_in" Timestamp NOT NULL,
 "data_check_out" Timestamp,
 "valor" Numeric(10,2) NOT NULL,
 "adicional_veiculo" Boolean NOT NULL
);

-- Create indexes for table reserva

CREATE INDEX "idx_reserva_cliente_idcliente" ON "reserva" ("id_cliente");

-- Add keys for table reserva

ALTER TABLE "reserva" ADD CONSTRAINT "PK_reserva" PRIMARY KEY ("id");

-- Add foreign key

ALTER TABLE "reserva" ADD CONSTRAINT "reserva_cliente_idcliente" FOREIGN KEY ("id_cliente") REFERENCES "cliente" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;