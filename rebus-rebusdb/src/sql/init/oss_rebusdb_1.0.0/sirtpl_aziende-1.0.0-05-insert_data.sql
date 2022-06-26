INSERT INTO sirtpla_d_natura_giuridica (id_natura_giuridica,cod_natura_giuridica,desc_natura_giuridica,desc_breve_natura_giuridica,flg_patrimonio) VALUES
	 (1,'AF','ALTRE FORME',NULL,NULL),
	 (2,'AC','ASSOCIAZIONE',NULL,NULL),
	 (4,'PA','ASSOCIAZIONE IN PARTECIPAZIONE',NULL,NULL),
	 (5,'AT','AZIENDA AUTONOMA STATALE',NULL,NULL),
	 (6,'AM','AZIENDA MUNICIPALE',NULL,NULL),
	 (7,'AP','AZIENDA PROVINCIALE',NULL,NULL),
	 (8,'AR','AZIENDA REGIONALE',NULL,NULL),
	 (11,'AL','AZIENDA SPECIALE DI ENTE LOCALE',NULL,NULL),
	 (16,'CR','CONSORZIO INTERCOMUNALE',NULL,NULL),
	 (17,'CM','CONSORZIO MUNICIPALE',NULL,NULL);
INSERT INTO sirtpla_d_natura_giuridica (id_natura_giuridica,cod_natura_giuridica,desc_natura_giuridica,desc_breve_natura_giuridica,flg_patrimonio) VALUES
	 (20,'EN','ENTE',NULL,NULL),
	 (30,'IF','IMPRESA FAMILIARE',NULL,NULL),
	 (43,'CN','SOCIETA'' CONSORTILE',NULL,NULL),
	 (47,'AN','SOCIETA'' CONSORTILE IN NOME COLLETTIVO',NULL,NULL),
	 (48,'SO','SOCIETA'' CONSORTILE PER AZIONI',NULL,NULL),
	 (49,'CI','SOCIETA'' COOPERATIVA A RESPONSABILITA ILLIMITATA',NULL,NULL),
	 (50,'CL','SOCIETA'' COOPERATIVA A RESPONSABILITA LIMITATA',NULL,NULL),
	 (51,'OC','SOCIETA'' COOPERATIVA CONSORTILE',NULL,NULL),
	 (55,'AA','SOCIETA'' IN ACCOMANDITA PER AZIONI',NULL,NULL),
	 (59,'SZ','SOCIETA'' NON PREVISTA DALLA LEGISLAZIONE ITALIANA',NULL,NULL);
INSERT INTO sirtpla_d_natura_giuridica (id_natura_giuridica,cod_natura_giuridica,desc_natura_giuridica,desc_breve_natura_giuridica,flg_patrimonio) VALUES
	 (61,'SE','SOCIETA'' SEMPLICE',NULL,NULL),
	 (68,'ED','ENTE DIRITTO PUBBLICO',NULL,NULL),
	 (31,'DI','IMPRESA INDIVIDUALE','IMPR. INDIV.',NULL),
	 (45,'OS','SOCIETA'' CONSORTILE COOPERATIVA A RESPONSABILITA'' LIMITATA','',NULL),
	 (62,'ST','SOGGETTO ESTERO','SOGG. ESTERO',NULL),
	 (46,'AE','SOCIETA'' CONSORTILE IN ACCOMANDITA SEMPLICE',NULL,'C'),
	 (44,'SL','SOCIETA'' CONSORTILE A RESPONSABILITA'' LIMITATA','SCARL','C'),
	 (39,'SC','SOCIETA'' COOPERATIVA','SOC.COOPERATIVA','C'),
	 (40,'SR','SOCIETA'' A RESPONSABILITA LIMITATA','SRL','C'),
	 (56,'AS','SOCIETA'' IN ACCOMANDITA SEMPLICE','SAS','C');
INSERT INTO sirtpla_d_natura_giuridica (id_natura_giuridica,cod_natura_giuridica,desc_natura_giuridica,desc_breve_natura_giuridica,flg_patrimonio) VALUES
	 (57,'SN','SOCIETA'' IN NOME COLLETTIVO','SNC','C'),
	 (60,'SP','SOCIETA'' PER AZIONI','SPA','C'),
	 (41,'SU','SOCIETA'' A RESPONSABILITA LIMITATA CON UNICO SOCIO','SRL UNICO SOCIO','C'),
	 (67,'AU','SOCIETA'' PER AZIONI CON SOCIO UNICO','SPA UNICO SOCIO','C'),
	 (13,'CO','CONSORZIO','CONSORZIO','F'),
	 (19,'OO','COOPERATIVA SOCIALE','COOP. SOCIALE','F');



INSERT INTO sirtpla_d_ruolo_sog_giuridico (id_ruolo_sog_giuridico,cod_ruolo_sog_giuridico,desc_ruolo_sog_giuridico) VALUES
	 (1,'C','Committente'),
	 (2,'E','Esecutore');



INSERT INTO sirtpla_d_tipo_ente (id_tipo_ente,desc_tipo_ente) VALUES
	 (1,'Comune'),
	 (2,'Unione montana'),
	 (3,'Unione di comuni'),
	 (5,'Agenzia regionale'),
	 (6,'Ministero'),
	 (7,'Regione'),
	 (4,'In-house regionale');



INSERT INTO sirtpla_d_tipo_sog_giuridico (id_tipo_sog_giuridico,cod_tipo_sog_giuridico,desc_tipo_sog_giuridico,valido_per_sog_giurid,id_ruolo_sog_giuridico) VALUES
	 (3,'R','Raggruppamento',true,NULL),
	 (4,'E','Ente',false,NULL),
	 (5,'E','Ente (esecutore)',true,2),
	 (1,'E','Ente (committente)',true,1),
	 (2,'A','Azienda',true,2);



INSERT INTO sirtpla_d_tipo_unione_sg (id_tipo_unione_sg,desc_tipo_unione_sg) VALUES
	 (1,'Fusione'),
	 (2,'Accorpamento');
