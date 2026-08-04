CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100),
    salary DECIMAL(10,2),
    department_id INT,
    CONSTRAINT fk_department
    FOREIGN KEY(department_id)
    REFERENCES departments(id)
);