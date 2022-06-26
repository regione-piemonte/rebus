INSERT INTO sirtplc_d_ambito_servizio (id_ambito_servizio,desc_ambito_servizio) VALUES
	 (1,'Urbano e suburbano'),
	 (2,'Extraurbano'),
	 (3,'Metropolitane'),
	 (4,'Regionali'),
	 (5,'Servizi speciali'),
	 (6,'Non applicabile');



INSERT INTO sirtplc_d_bacino (id_bacino,denom_bacino,desc_bacino,data_inizio_validita,data_fine_validita) VALUES
	 (2,'Nord-Est','Province di Biella, Novara, Verbano Cusio Ossola e Vercelli','2020-01-01',NULL),
	 (1,'Metropolitano','Città metropolitana di Torino','2020-01-01',NULL),
	 (4,'Sud-Est','Province di Alessandria e Asti','2020-01-01',NULL),
	 (3,'Sud-Ovest','Provincia di Cuneo','2020-01-01',NULL),
	 (5,'Ferroviario','Intero Piemonte','2020-01-01',NULL);



INSERT INTO sirtplc_d_modalita_affidamento (id_modalita_affidamento,desc_modalita_affidamento) VALUES
	 (1,'mediante gara'),
	 (2,'affidamento diretto'),
	 (3,'in-house');



INSERT INTO sirtplc_d_tipo_affidamento (id_tipo_affidamento,desc_tipo_affidamento) VALUES
	 (1,'Atto di concessione'),
	 (2,'Contratto / convenzione di concessione in ambito Project Financing'),
	 (3,'Contratto di servizio'),
	 (4,'In proprio da parte dell''ente committente'),
	 (5,'Atto d''obbligo');



INSERT INTO sirtplc_d_tipo_raggruppamento (id_tipo_raggruppamento,desc_tipo_raggruppamento) VALUES
	 (1,'Consorzio'),
	 (2,'ATI'),
	 (3,'RTI');



INSERT INTO sirtplc_d_tipo_sostituzione (id_tipo_sostituzione,desc_tipo_sostituzione) VALUES
	 (1,'Subaffidamento'),
	 (2,'Subentro'),
	 (3,'Somministrazione di personale');



INSERT INTO sirtplc_d_tipologia_servizio (id_tipologia_servizio,desc_tipologia_servizio) VALUES
	 (1,'Autolinee TPL'),
	 (2,'Filovie'),
	 (3,'Tramvie'),
	 (4,'Metropolitane'),
	 (5,'Ferrovie'),
	 (6,'Impianti a fune'),
	 (7,'Navigazione interna'),
	 (8,'Autolinee sostitutive e/o integrative ferrov.'),
	 (9,'Servizi a chiamata'),
	 (10,'Altro');



INSERT INTO sirtplc_r_amb_tip_servizio (id_amb_tip_servizio,id_ambito_servizio,id_tipologia_servizio) VALUES
	 (1,1,1),
	 (2,2,1),
	 (3,1,2),
	 (4,2,2),
	 (5,1,3),
	 (6,2,3),
	 (7,1,4),
	 (8,5,6),
	 (9,5,7),
	 (10,3,5);
INSERT INTO sirtplc_r_amb_tip_servizio (id_amb_tip_servizio,id_ambito_servizio,id_tipologia_servizio) VALUES
	 (11,4,5),
	 (12,6,8),
	 (13,6,9),
	 (14,1,10),
	 (15,2,10);
