-- provinces
delete from provinces;

insert into provinces (name, code, region, created_at) values
('Agrigento', 'AG', 'Sicilia', current_timestamp),
('Alessandria', 'AL', 'Piemonte', current_timestamp),
('Ancona', 'AN', 'Marche', current_timestamp),
('Arezzo', 'AR', 'Toscana', current_timestamp),
('Ascoli Piceno', 'AP', 'Marche', current_timestamp),
('Asti', 'AT', 'Piemonte', current_timestamp),
('Avellino', 'AV', 'Campania', current_timestamp),
('Bari', 'BA', 'Puglia', current_timestamp),
('Barletta-Andria-Trani', 'BT', 'Puglia', current_timestamp),
('Belluno', 'BL', 'Veneto', current_timestamp),
('Benevento', 'BN', 'Campania', current_timestamp),
('Bergamo', 'BG', 'Lombardia', current_timestamp),
('Biella', 'BI', 'Piemonte', current_timestamp),
('Bologna', 'BO', 'Emilia-Romagna', current_timestamp),
('Bolzano/Bozen', 'BZ', 'Trentino-Alto Adige/Südtirol', current_timestamp),
('Brescia', 'BS', 'Lombardia', current_timestamp),
('Brindisi', 'BR', 'Puglia', current_timestamp),
('Cagliari', 'CA', 'Sardegna', current_timestamp),
('Caltanissetta', 'CL', 'Sicilia', current_timestamp),
('Campobasso', 'CB', 'Molise', current_timestamp),
('Carbonia-Iglesias', 'CI', 'Sardegna', current_timestamp),
('Caserta', 'CE', 'Campania', current_timestamp),
('Catania', 'CT', 'Sicilia', current_timestamp),
('Catanzaro', 'CZ', 'Calabria', current_timestamp),
('Chieti', 'CH', 'Abruzzo', current_timestamp),
('Como', 'CO', 'Lombardia', current_timestamp),
('Cosenza', 'CS', 'Calabria', current_timestamp),
('Cremona', 'CR', 'Lombardia', current_timestamp),
('Crotone', 'KR', 'Calabria', current_timestamp),
('Cuneo', 'CN', 'Piemonte', current_timestamp),
('Enna', 'EN', 'Sicilia', current_timestamp),
('Fermo', 'FM', 'Marche', current_timestamp),
('Ferrara', 'FE', 'Emilia-Romagna', current_timestamp),
('Firenze', 'FI', 'Toscana', current_timestamp),
('Foggia', 'FG', 'Puglia', current_timestamp),
('Forlì-Cesena', 'FC', 'Emilia-Romagna', current_timestamp),
('Frosinone', 'FR', 'Lazio', current_timestamp),
('Genova', 'GE', 'Liguria', current_timestamp),
('Gorizia', 'GO', 'Friuli-Venezia Giulia', current_timestamp),
('Grosseto', 'GR', 'Toscana', current_timestamp),
('Imperia', 'IM', 'Liguria', current_timestamp),
('Isernia', 'IS', 'Molise', current_timestamp),
('L''Aquila', 'AQ', 'Abruzzo', current_timestamp),
('La Spezia', 'SP', 'Liguria', current_timestamp),
('Latina', 'LT', 'Lazio', current_timestamp),
('Lecce', 'LE', 'Puglia', current_timestamp),
('Lecco', 'LC', 'Lombardia', current_timestamp),
('Livorno', 'LI', 'Toscana', current_timestamp),
('Lodi', 'LO', 'Lombardia', current_timestamp),
('Lucca', 'LU', 'Toscana', current_timestamp),
('Macerata', 'MC', 'Marche', current_timestamp),
('Mantova', 'MN', 'Lombardia', current_timestamp),
('Massa-Carrara', 'MS', 'Toscana', current_timestamp),
('Matera', 'MT', 'Basilicata', current_timestamp),
('Medio Campidano', 'VS', 'Sardegna', current_timestamp),
('Messina', 'ME', 'Sicilia', current_timestamp),
('Milano', 'MI', 'Lombardia', current_timestamp),
('Modena', 'MO', 'Emilia-Romagna', current_timestamp),
('Monza e della Brianza', 'MB', 'Lombardia', current_timestamp),
('Napoli', 'NA', 'Campania', current_timestamp),
('Novara', 'NO', 'Piemonte', current_timestamp),
('Nuoro', 'NU', 'Sardegna', current_timestamp),
('Ogliastra', 'OG', 'Sardegna', current_timestamp),
('Olbia-Tempio', 'OT', 'Sardegna', current_timestamp),
('Oristano', 'OR', 'Sardegna', current_timestamp),
('Padova', 'PD', 'Veneto', current_timestamp),
('Palermo', 'PA', 'Sicilia', current_timestamp),
('Parma', 'PR', 'Emilia-Romagna', current_timestamp),
('Pavia', 'PV', 'Lombardia', current_timestamp),
('Perugia', 'PG', 'Umbria', current_timestamp),
('Pesaro e Urbino', 'PU', 'Marche', current_timestamp),
('Pescara', 'PE', 'Abruzzo', current_timestamp),
('Piacenza', 'PC', 'Emilia-Romagna', current_timestamp),
('Pisa', 'PI', 'Toscana', current_timestamp),
('Pistoia', 'PT', 'Toscana', current_timestamp),
('Pordenone', 'PN', 'Friuli-Venezia Giulia', current_timestamp),
('Potenza', 'PZ', 'Basilicata', current_timestamp),
('Prato', 'PO', 'Toscana', current_timestamp),
('Ragusa', 'RG', 'Sicilia', current_timestamp),
('Ravenna', 'RA', 'Emilia-Romagna', current_timestamp),
('Reggio di Calabria', 'RC', 'Calabria', current_timestamp),
('Reggio nell''Emilia', 'RE', 'Emilia-Romagna', current_timestamp),
('Rieti', 'RI', 'Lazio', current_timestamp),
('Rimini', 'RN', 'Emilia-Romagna', current_timestamp),
('Roma', 'RM', 'Lazio', current_timestamp),
('Rovigo', 'RO', 'Veneto', current_timestamp),
('Salerno', 'SA', 'Campania', current_timestamp),
('Sassari', 'SS', 'Sardegna', current_timestamp),
('Savona', 'SV', 'Liguria', current_timestamp),
('Siena', 'SI', 'Toscana', current_timestamp),
('Siracusa', 'SR', 'Sicilia', current_timestamp),
('Sondrio', 'SO', 'Lombardia', current_timestamp),
('Taranto', 'TA', 'Puglia', current_timestamp),
('Teramo', 'TE', 'Abruzzo', current_timestamp),
('Terni', 'TR', 'Umbria', current_timestamp),
('Torino', 'TO', 'Piemonte', current_timestamp),
('Trapani', 'TP', 'Sicilia', current_timestamp),
('Trento', 'TN', 'Trentino-Alto Adige/Südtirol', current_timestamp),
('Treviso', 'TV', 'Veneto', current_timestamp),
('Trieste', 'TS', 'Friuli-Venezia Giulia', current_timestamp),
('Udine', 'UD', 'Friuli-Venezia Giulia', current_timestamp),
('Valle d''Aosta/Vallée d''Aoste', 'AO', 'Valle d''Aosta/Vallée d''Aoste', current_timestamp),
('Varese', 'VA', 'Lombardia', current_timestamp),
('Venezia', 'VE', 'Veneto', current_timestamp),
('Verbano-Cusio-Ossola', 'VB', 'Piemonte', current_timestamp),
('Vercelli', 'VC', 'Piemonte', current_timestamp),
('Verona', 'VR', 'Veneto', current_timestamp),
('Vibo Valentia', 'VV', 'Calabria', current_timestamp),
('Vicenza', 'VI', 'Veneto', current_timestamp),
('Viterbo', 'VT', 'Lazio', current_timestamp);

-- collection points
delete from collection_points;

insert into collection_points (name, province_id, active, notes, created_by, created_at) values
('PD-01', 'PD', true, 'Spazio Stria', 'init_script', current_timestamp),
('PD-02', 'PD', false, 'Quadrato Meticcio', 'init_script', current_timestamp),
('BO-01', 'BO', true, 'Notes', 'init_script', current_timestamp);

delete from categories;
-- Insert root categories
insert into categories (name, parent_id, created_by, created_at) values ('Perishables', null, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Equipment', null, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Supplies', null, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Medicines', null, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Others', null, 'init_script', current_timestamp);

-- Insert sub-categories under 'Perishables' (assuming 'Perishables' has id = 1)
insert into categories (name, parent_id, created_by, created_at) values ('Food', 1, 'init_script', current_timestamp);

-- Insert sub-sub-categories under 'Food' (assuming 'Food' has id = 6)
insert into categories (name, parent_id, created_by, created_at) values ('Pasta', 6, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Baby food', 6, 'init_script', current_timestamp);

-- Insert sub-categories under 'Equipment' (assuming 'Equipment' has id = 2)
insert into categories (name, parent_id, created_by, created_at) values ('Clothing', 2, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Diapers', 2, 'init_script', current_timestamp);

-- Insert sub-categories under 'Clothing' (assuming 'Clothing' has id = 9)
insert into categories (name, parent_id, created_by, created_at) values ('Adult clothes', 9, 'init_script', current_timestamp);
insert into categories (name, parent_id, created_by, created_at) values ('Baby clothes', 9, 'init_script', current_timestamp);