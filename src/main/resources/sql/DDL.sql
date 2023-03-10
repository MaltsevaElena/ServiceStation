CREATE TABLE positions
(
    id          int8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description varchar(255) NOT NULL,
    name        varchar(255) NOT NULL,
    CONSTRAINT positions_pkey PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id          int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description varchar(255),
    name        varchar(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE service_stations
(
    id           int8         NOT NULL,
    created_by   varchar(255),
    created_when timestamp,
    address      varchar(255) NOT NULL,
    "name"       varchar(255) NOT NULL,
    phone        varchar(255) NOT NULL,
    CONSTRAINT service_stations_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 int8         NOT NULL,
    created_by         varchar(255),
    created_when       timestamp,
    address            varchar(255),
    back_up_email      varchar(255) NOT NULL,
    date_birth         date         NOT NULL,
    employee_chief_id  int8,
    first_name         varchar(255) NOT NULL,
    last_name          varchar(255) NOT NULL,
    login              varchar(255) NOT NULL,
    middle_name        varchar(255),
    password           varchar(255) NOT NULL,
    phone              varchar(255) NOT NULL,
    position_id        int8,
    role_id            int8         NOT NULL,
    service_station_id int8,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT fk_employees_service_stations FOREIGN KEY (service_station_id) REFERENCES service_stations (id),
    CONSTRAINT fk_users_positions FOREIGN KEY (position_id) REFERENCES positions (id),
    CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_users_users FOREIGN KEY (employee_chief_id) REFERENCES users (id)
);

CREATE TABLE cars
(
    id                  int8         NOT NULL,
    created_by          varchar(255),
    created_when        timestamp,
    mileage             int4         NOT NULL,
    model               varchar(255) NOT NULL,
    owner_car           varchar(255),
    registration_number varchar(255) NOT NULL,
    user_id             int8,
    vin                 varchar(255) NOT NULL,
    year                int4         NOT NULL,
    CONSTRAINT cars_pkey PRIMARY KEY (id),
    CONSTRAINT unique_vin_car UNIQUE (vin),
    CONSTRAINT fk_cars_users FOREIGN KEY (user_id) REFERENCES users (id) on delete set null
);

CREATE TABLE services
(
    id                 int8         NOT NULL,
    created_by         varchar(255),
    created_when       timestamp,
    code               varchar(255) NOT NULL,
    "name"             varchar(255) NOT NULL,
    rate_hour          float8       NOT NULL,
    service_station_id int8         NOT NULL,
    CONSTRAINT services_pkey PRIMARY KEY (id),
    CONSTRAINT unique_code_service UNIQUE (code),
    CONSTRAINT unique_name_service UNIQUE (name),
    CONSTRAINT fk_services_service_stations FOREIGN KEY (service_station_id) REFERENCES service_stations (id)
);

CREATE TABLE tariffs
(
    id                 int8 NOT NULL,
    created_by         varchar(255),
    created_when       timestamp,
    end_date           date,
    price              int4 NOT NULL,
    start_date         date NOT NULL,
    service_station_id int8 NOT NULL,
    CONSTRAINT tariffs_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tariff_service_stations FOREIGN KEY (service_station_id) REFERENCES service_stations (id)
);

CREATE TABLE warehouses
(
    id                 int8         NOT NULL,
    created_by         varchar(255),
    created_when       timestamp,
    "name"             varchar(255) NOT NULL,
    service_station_id int8         NOT NULL,
    CONSTRAINT unique_name_warehouse UNIQUE (name),
    CONSTRAINT warehouses_pkey PRIMARY KEY (id),
    CONSTRAINT fk_warehouses_service_stations FOREIGN KEY (service_station_id) REFERENCES service_stations (id)
);

CREATE TABLE spare_parts
(
    id           int8         NOT NULL,
    created_by   varchar(255),
    created_when timestamp,
    amount       int4         NOT NULL,
    code         varchar(255) NOT NULL,
    "name"       varchar(255) NOT NULL,
    price        float8       NOT NULL,
    unit         int4,
    warehouse_id int8         NOT NULL,
    CONSTRAINT spare_parts_pkey PRIMARY KEY (id),
    CONSTRAINT fk_warehouses_spare_parts FOREIGN KEY (warehouse_id) REFERENCES warehouses (id)
);

create table diagnostic_sheets
(
    id                bigint not null primary key,
    created_by        varchar(255),
    created_when      timestamp,
    diagnostic_result varchar(255),
    repair_date       date   not null,
    car_id            bigint not null
        constraint fk_diagnostic_sheet_cars references cars,
    user_id           bigint
        constraint fk_diagnostic_sheet_employer references users
);

create table service_books
(
    id                bigint       not null primary key,
    created_by        varchar(255),
    created_when      timestamp,
    amount_spare_part integer      not null,
    code_spare_part   varchar(255),
    mileage_car       integer      not null,
    name_spare_part   varchar(255) not null,
    repair_date       date         not null,
    unit_spare_part   integer      not null,
    car_id            bigint       not null
        constraint fk_books_cars references cars
);