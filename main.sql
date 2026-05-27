-- ============================================================
-- Grace's Bank App - Full SQL Database
-- Based on Main.java banking application
-- ============================================================

-- REQUIREMENT 1: Database creation script (must be first)
CREATE DATABASE graces_bank;
USE graces_bank;

-- ============================================================
-- REQUIREMENT 2: Minimum 5 related tables
-- Tables: customers, accounts, transactions, branches, loans
-- ============================================================

-- ============================================================
-- TABLE 1: branches
-- a bank has branches, accounts belong to a branch
-- ============================================================
CREATE TABLE branches (
    branch_id     INT PRIMARY KEY AUTO_INCREMENT,         
    branch_name   VARCHAR(100) NOT NULL,                  
    branch_city   VARCHAR(100) NOT NULL,                  
    branch_phone  VARCHAR(20) NOT NULL,                   
    branch_email  VARCHAR(100) UNIQUE NOT NULL            
);


-- ============================================================
-- TABLE 2: customers
-- matches customer_name[], customer_email[], customer_ids[]
-- ============================================================
CREATE TABLE customers (
    customer_id    INT PRIMARY KEY AUTO_INCREMENT,        -- primary key, matches next_customer_id starting at 1
    customer_name  VARCHAR(255) NOT NULL,                 -- matches customer_name[] — NOT NULL
    customer_email VARCHAR(255) UNIQUE NOT NULL,          -- matches customer_email[] — UNIQUE + NOT NULL
    customer_phone VARCHAR(20) NOT NULL,                  -- extra customer detail
    branch_id      INT NOT NULL,                          -- which branch they belong to
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,    -- auto timestamp

    FOREIGN KEY (branch_id) REFERENCES branches(branch_id)  -- REQUIREMENT 4: foreign key
);

-- ============================================================
-- TABLE 3: accounts
-- matches acc_number[], acc_type[], acc_balances[], acc_open[], acc_owner_index[]
-- ============================================================
CREATE TABLE accounts (
    acc_number    INT PRIMARY KEY AUTO_INCREMENT,         -- primary key, matches next_acc_number
    acc_type      VARCHAR(50) NOT NULL,                   -- matches acc_type[] — "savings" or "checking"
    acc_balance   DOUBLE NOT NULL DEFAULT 0.0,            -- matches acc_balances[] — NOT NULL + DEFAULT
    acc_open      BOOLEAN NOT NULL DEFAULT TRUE,          -- matches acc_open[] — TRUE=open, FALSE=closed
    customer_id   INT NOT NULL,                           -- matches acc_owner_index[] — which customer owns account
    branch_id     INT NOT NULL,                           -- which branch this account is at
    opened_date   DATETIME DEFAULT CURRENT_TIMESTAMP,     -- when account was created

    CONSTRAINT chk_acc_type CHECK (acc_type IN ('savings', 'checking')),  --  CHECK constraint — only allows savings or checking
    CONSTRAINT chk_balance CHECK (acc_balance >= 0),                      -- CHECK constraint — balance can't go below 0
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),          -- foreign key to customers
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id)                -- foreign key to branches
);

-- set account number to start at 1001 — matches next_acc_number = 1001 in Java
ALTER TABLE accounts AUTO_INCREMENT = 1001;

-- ============================================================
-- TABLE 4: transactions
-- records every deposit, withdrawal, and transfer from the app
-- matches case 5 (deposit), case 6 (withdraw), case 7 (transfer)
-- ============================================================
CREATE TABLE transactions (
    transaction_id    INT PRIMARY KEY AUTO_INCREMENT,     -- primary key
    acc_number        INT NOT NULL,                       -- which account this transaction is on
    transaction_type  VARCHAR(20) NOT NULL,               -- "deposit", "withdrawal", "transfer_out", "transfer_in"
    amount            DOUBLE NOT NULL,                    -- how much money
    balance_after     DOUBLE NOT NULL,                    -- balance after the transaction
    transaction_date  DATETIME DEFAULT CURRENT_TIMESTAMP, -- when it happened
    
    CONSTRAINT chk_amount CHECK (amount > 0),             -- CHECK — amount must be positive
    CONSTRAINT chk_type CHECK (transaction_type IN ('deposit', 'withdrawal', 'transfer_out', 'transfer_in')),
    FOREIGN KEY (acc_number) REFERENCES accounts(acc_number)  --foreign key to accounts
);

-- ============================================================
-- TABLE 5: loans
-- extra banking table — customers can have loans
-- ============================================================
CREATE TABLE loans (
    loan_id         INT PRIMARY KEY AUTO_INCREMENT,       -- primary key
    customer_id     INT NOT NULL,                         -- who took the loan
    acc_number      INT NOT NULL,                         -- which account the loan is linked to
    loan_amount     DOUBLE NOT NULL,                      -- total loan amount
    amount_paid     DOUBLE NOT NULL DEFAULT 0.0,          -- how much has been paid back
    loan_status     VARCHAR(20) NOT NULL DEFAULT 'active',-- "active" or "paid"
    loan_date       DATETIME DEFAULT CURRENT_TIMESTAMP,   -- when loan was issued
    CONSTRAINT chk_loan_amount CHECK (loan_amount > 0),   -- CHECK — loan must be positive
    CONSTRAINT chk_loan_status CHECK (loan_status IN ('active', 'paid')),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),  -- foreign key
    FOREIGN KEY (acc_number) REFERENCES accounts(acc_number)      -- foreign key
);



-- INSERT branches (table 1)
INSERT INTO branches (branch_name, branch_city, branch_phone, branch_email) VALUES
('Grace Bank Sandton',      'Johannesburg', '0111001001', 'sandton@gracebank.co.za'),
('Grace Bank Cape Town CBD','Cape Town',    '0212002002', 'capetown@gracebank.co.za'),
('Grace Bank Durban North', 'Durban',       '0313003003', 'durban@gracebank.co.za'),
('Grace Bank Pretoria',     'Pretoria',     '0514004004', 'pretoria@gracebank.co.za'),
('Grace Bank Port Elizabeth','Gqeberha',   '0415005005', 'pe@gracebank.co.za');

-- INSERT customers (table 2) — matches option 1 in the Java app
INSERT INTO customers (customer_name, customer_email, customer_phone, branch_id) VALUES
('Grace Dlamini',   'grace@gmail.com',   '0670759635', 1),
('John Mokoena',    'john@gmail.com',    '+2789632151', 1),
('Mary Nkosi',      'mary@gmail.com',    '0736258413', 2),
('Sipho Zulu',      'sipho@gmail.com',   '07404404444', 3),
('Fatima Patel',    'fatima@gmail.com',  '0752232547', 4),
('Thabo Sithole',   'thabo@gmail.com',   '07645716666', 5),
('Lerato Molefe',   'lerato@gmail.com',  '0778887777', 2);

-- INSERT accounts (table 3) — matches option 2 in the Java app
-- acc_number auto starts at 1001
INSERT INTO accounts (acc_type, acc_balance, acc_open, customer_id, branch_id) VALUES
('savings',  1500.00, TRUE,  1, 1),   -- acc 1001 — Grace, savings
('checking',  800.00, TRUE,  2, 1),   -- acc 1002 — John, checking
('savings',  2000.00, TRUE,  3, 2),   -- acc 1003 — Mary, savings
('checking', 3500.00, FALSE, 3, 2),   -- acc 1004 — Mary, checking (closed)
('savings',  5000.00, TRUE,  4, 3),   -- acc 1005 — Sipho, savings
('checking', 1200.00, TRUE,  5, 4),   -- acc 1006 — Fatima, checking
('savings',   750.00, TRUE,  6, 5),   -- acc 1007 — Thabo, savings
('savings',  9900.00, TRUE,  7, 2);   -- acc 1008 — Lerato, savings

-- INSERT transactions (table 4) — matches options 5, 6, 7 in Java app
INSERT INTO transactions (acc_number, transaction_type, amount, balance_after) VALUES
(1001, 'deposit',      500.00,  2000.00),   -- Grace deposited R500
(1001, 'withdrawal',   200.00,  1800.00),   -- Grace withdrew R200
(1002, 'deposit',      300.00,  1100.00),   -- John deposited R300
(1003, 'deposit',     1000.00,  3000.00),   -- Mary deposited R1000
(1005, 'deposit',      500.00,  5500.00),   -- Sipho deposited R500
(1001, 'transfer_out', 300.00,  1500.00),   -- Grace sent R300 to John
(1002, 'transfer_in',  300.00,  1400.00),   -- John received R300 from Grace
(1006, 'withdrawal',   200.00,  1000.00),   -- Fatima withdrew R200
(1007, 'deposit',      250.00,  1000.00),   -- Thabo deposited R250
(1008, 'withdrawal',  100.00,   9800.00);   -- Lerato withdrew R100

-- INSERT loans (table 5)
INSERT INTO loans (customer_id, acc_number, loan_amount, amount_paid, loan_status) VALUES
(1, 1001, 10000.00, 2000.00, 'active'),   -- Grace has a loan of R10000, paid R2000
(2, 1002,  5000.00, 5000.00, 'paid'),     -- John fully paid his loan
(3, 1003, 20000.00,    0.00, 'active'),   -- Mary has a new loan
(4, 1005, 15000.00, 7500.00, 'active'),   -- Sipho halfway through loan
(6, 1007,  8000.00, 1000.00, 'active');   -- Thabo has a small loan



-- SELECT 1: view a specific account (matches case 3 in Java — view account)
SELECT a.acc_number, a.acc_type, a.acc_balance,
       CASE WHEN a.acc_open = TRUE THEN 'Open' ELSE 'Closed' END AS status,
       c.customer_name AS owner
FROM accounts a
JOIN customers c ON a.customer_id = c.customer_id
WHERE a.acc_number = 1001;



-- SELECT 2: view a specific customer (matches case 9 in Java — view customer info)
SELECT customer_id, customer_name, customer_email, customer_phone
FROM customers
WHERE customer_id = 1;



-- SELECT 3: find all open accounts only
SELECT a.acc_number, a.acc_type, a.acc_balance, c.customer_name
FROM accounts a
JOIN customers c ON a.customer_id = c.customer_id
WHERE a.acc_open = TRUE;



-- SELECT 4: view all transactions for one account (transaction history)
SELECT transaction_id, transaction_type, amount, balance_after, transaction_date
FROM transactions
WHERE acc_number = 1001
ORDER BY transaction_date DESC;



-- SELECT 5: find all customers at a specific branch
SELECT c.customer_id, c.customer_name, c.customer_email, b.branch_name
FROM customers c
JOIN branches b ON c.branch_id = b.branch_id
WHERE c.branch_id = 1;


-- SELECT 6: find all savings accounts with balance over R1000
SELECT a.acc_number, a.acc_balance, c.customer_name
FROM accounts a
JOIN customers c ON a.customer_id = c.customer_id
WHERE a.acc_type = 'savings' AND a.acc_balance > 1000.00;


-- SELECT 7: find all active loans
SELECT l.loan_id, c.customer_name, l.loan_amount, l.amount_paid,
       (l.loan_amount - l.amount_paid) AS remaining
FROM loans l
JOIN customers c ON l.customer_id = c.customer_id
WHERE l.loan_status = 'active';



-- UPDATE 1: deposit money into account (matches case 5 in Java — deposit)
UPDATE accounts
SET acc_balance = acc_balance + 500.00
WHERE acc_number = 1001;


-- UPDATE 2: withdraw money from account (matches case 6 in Java — withdraw)
UPDATE accounts
SET acc_balance = acc_balance - 200.00
WHERE acc_number = 1002 AND acc_open = TRUE;


-- UPDATE 3: close an account (matches case 8 in Java — close account)
UPDATE accounts
SET acc_open = FALSE
WHERE acc_number = 1003;


-- UPDATE 4: update customer info (matches case 4 in Java — update customer)
UPDATE customers
SET customer_name = 'Grace Dlamini-Mokoena', customer_email = 'grace.updated@gmail.com'
WHERE customer_id = 1;


-- UPDATE 5: transfer money — take from one account, add to another (matches case 7 in Java)
UPDATE accounts SET acc_balance = acc_balance - 100.00 WHERE acc_number = 1001;
UPDATE accounts SET acc_balance = acc_balance + 100.00 WHERE acc_number = 1002;


-- DELETE 1: delete a loan that was fully paid off
DELETE FROM loans
WHERE loan_status = 'paid' AND customer_id = 2;


-- DELETE 2: delete a customer's transaction history for a closed account
DELETE FROM transactions
WHERE acc_number = 1004;

