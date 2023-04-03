CREATE TABLE IF NOT EXISTS SELECTORS_TBL (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    is_parent BOOLEAN NOT NULL DEFAULT 0,
    parent_selector_id INTEGER NULL,
      foreign key (parent_selector_id) REFERENCES SELECTORS_TBL(id)
);

INSERT INTO SELECTORS_TBL (id,title, is_parent) VALUES
   (default,'Manufacturing', 1),
    (default,'Other', 1),
    (default,'Service', 1);

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Construction materials' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Electronics and Optics' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Food and Beverage' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Bakery &amp; confectionery products' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Beverages' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Fish &amp; fish products' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Meat &amp; meat products' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Milk &amp; dairy products' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Other' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Sweets &amp; snack food' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Food and Beverage'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Furniture' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Bathroom/sauna' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Bedroom' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Children’s room' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Kitchen' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Living room' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Office' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Other (Furniture)' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Outdoor' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Project furniture' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Furniture'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Machinery' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Machinery components' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Machinery equipment/tools' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Manufacture of machinery' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Maritime' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Aluminium and steel workboats' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Maritime'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Boat/Yacht building' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Maritime'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Ship repair and conversion' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Maritime'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Metal structures' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Other' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Repair and maintenance service' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Machinery'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Metalworking' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Construction of metal structures' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metalworking'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Houses and buildings' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metalworking'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Metal products' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metalworking'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Metal works' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metalworking'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'CNC-machining' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metal works'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Forgings, Fasteners' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metal works'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Gas, Plasma, Laser cutting' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metal works'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'MIG, TIG, Aluminum welding' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Metal works'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Plastic and Rubber' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Packaging' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic and Rubber'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Plastic goods' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic and Rubber'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Plastic processing technology' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic and Rubber'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Blowing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic processing technology'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Moulding' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic processing technology'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Plastics welding and processing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic processing technology'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Plastic profiles' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Plastic and Rubber'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Printing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Labelling and packaging printing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Printing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Book/Periodicals printing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Printing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Advertising' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Printing'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Textile and Clothing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Clothing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Textile and Clothing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Textile' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Textile and Clothing'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Wood' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Manufacturing'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Other (Wood)' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Wood'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Wooden building materials' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Wood'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Wooden houses' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Wood'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Creative industries' , 2);
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Energy technology' , 2);
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Environment' , 2);

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Business services' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Engineering' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Information Technology and Telecommunications' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Data processing, Web portals, E-marketing' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Programming, Consultancy' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Software, Hardware' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Telecommunications' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Tourism' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Translation services' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Transport and Logistics' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Service'));

INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Air' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Transport and Logistics'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Rail' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Transport and Logistics'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Road' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Transport and Logistics'));
INSERT INTO SELECTORS_TBL (id,title, parent_selector_id) VALUES (default,'Water' , (SELECT id FROM SELECTORS_TBL WHERE title = 'Transport and Logistics'));