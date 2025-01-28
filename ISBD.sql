CREATE TABLE
    person (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR NOT NULL,
        last_name VARCHAR NOT NULL,
        email VARCHAR UNIQUE NOT NULL,
        tel_number VARCHAR UNIQUE NOT NULL,
        username VARCHAR UNIQUE NOT NULL password VARCHAR NOT NULL
    );

CREATE TABLE
    client (
        id SERIAL PRIMARY KEY,
        person_id INTEGER UNIQUE REFERENCES person (id),
        address VARCHAR NOT NULL
    );

CREATE TABLE
    repairman (
        id SERIAL PRIMARY KEY,
        person_id INTEGER UNIQUE REFERENCES person (id),
        rating REAL DEFAULT 0,
        photo VARCHAR UNIQUE,
        experience SMALLINT NOT NULL,
        qualification VARCHAR NOT NULL,
        CHECK (
            rating >= 0
            AND rating <= 5
            AND experience >= 0
        )
    );

CREATE TABLE
    DAY (
        id SERIAL PRIMARY KEY,
        full_name VARCHAR UNIQUE NOT NULL,
        short_name VARCHAR UNIQUE NOT NULL
    );

CREATE TABLE
    schedule (
        repairman_id INTEGER REFERENCES repairman (id),
        day_id INTEGER REFERENCES DAY (id),
        PRIMARY KEY (repairman_id, day_id)
    );

CREATE TABLE
    appliance (
        id SERIAL PRIMARY KEY,
        type_id INTEGER NOT NULL REFERENCES appliance_type (id),
        name VARCHAR NOT NULL,
        purchase_date DATE NOT NULL,
        client_id INTEGER REFERENCES client (id)
    );

CREATE TABLE
    appliance_type (
        id serial PRIMARY KEY,
        name VARCHAR UNIQUE NOT NULL
    );


CREATE TABLE
    feedback (
        id serial PRIMARY KEY,
        description text NOT NULL,
        rating REAL NOT NULL,
        publish_date DATE NOT NULL
    );

CREATE TABLE
    payment_type (id serial PRIMARY KEY, name text UNIQUE NOT NULL);

CREATE TABLE
    order_status (id serial PRIMARY KEY, name text UNIQUE NOT NULL);

CREATE TABLE
    orders (
        id serial PRIMARY KEY,
        order_date DATE,
        cost INT,
        appliance_id REFERENCES appliance (id),
        
        client_id REFERENCES client (id),
        repairman_id REFERENCES repairman (id),
        
        payment_type_id REFERENCES payment_type (id),
        order_status_id REFERENCES order_status (id),
        feedback_id REFERENCES feedback (id),
        
    );

CREATE TABLE
    subscription (
        id serial PRIMARY KEY,
        name VARCHAR UNIQUE NOT NULL,
        price SMALLINT NOT NULL
    );

CREATE TABLE
    subscriber (
        client_id INTEGER REFERENCES client (id),
        subscription_id INTEGER UNIQUE REFERENCES subscription (id),
        start_date DATE NOT NULL,
        end_date NOT NULL,
        CHECK (start_date < end_date),
        PRIMARY KEY (client_id, subscription_id)
    );

-- creation_date TIMESTAMP
-- last_access_date TIMESTAMP
-- updated_at TIMESTAMP
-- enabled BOOLEAN

CREATE OR REPLACE FUNCTION set_order_status_tr()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.active = true AND NEW.repairman_id IS NOT NULL THEN
        NEW.order_status_id = (SELECT id FROM order_status WHERE name = 'Выполняется');
    ELSIF NEW.active = true THEN
        NEW.order_status_id = (SELECT id FROM order_status WHERE name = 'Поиск мастера');
    ELSIF NEW.repairman_id IS NOT NULL THEN
        NEW.order_status_id = (SELECT id FROM order_status WHERE name = 'Завершён');
    ELSE
        NEW.order_status_id = (SELECT id FROM order_status WHERE name = 'Отменён');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_order_status
    BEFORE INSERT OR UPDATE ON orders
    FOR EACH ROW EXECUTE FUNCTION set_order_status_tr();

insert into order_status(name) values
('Поиск мастера'),
('Выполняется'),
('Отменён'),
('Завершён');

insert into payment_type(name) values
('Подписка'),
('Безналичная');

insert into day(full_name, short_name) values
    ('Понедельник', 'Пн'),
    ('Вторник', 'Вт'),
    ('Среда', 'Ср'),
    ('Четверг', 'Чт'),
    ('Пятница', 'Пт'),
    ('Суббота', 'Сб'),
    ('Воскресенье', 'Вс');

insert into subscription_plan(name, price) values
    ('Кухня (основная)', 1000),
    ('Кухня (дополнительная)', 600),
    ('Ванная', 800),
    ('Общее', 1000);

insert into appliance_type(name, subscription_plan_id) values 
    ('Холодильник', 1),
    ('Кухонная плита', 1),
    ('Посудомоечная машина', 1),
    ('Микроволновая печь', 1),
    ('Мультиварка', 2),
    ('Тостер', 2),
    ('Чайник', 2),
    ('Блендер', 2),
    ('Кофемашина', 2),
    ('Мясорубка', 2),
    ('Хлебопечка', 2),
    ('Электрогриль', 2),
    ('Соковыжималка', 2),
    ('Миксер', 2),
    ('Стиральная машина', 3),
    ('Фен', 3),
    ('Электросушилка', 3),
    ('Утюг', 3),
    ('Кондиционер', 4),
    ('Вентилятор', 4),
    ('Очиститель воздуха', 4),
    ('Пылесос', 4),
    ('Телевизор', 4),
    ('Домашний телефон', 4),
    ('Роутер', 4),
    ('Сетевой фильтр', 4);