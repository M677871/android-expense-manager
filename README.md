# Expense Manager (SQLite) — Android (Java)

An Android expense tracker built with **Java + SQLite** using **two tables** (categories & expenses).  
Supports **Add / View (RecyclerView) / Update / Delete** expenses with a clean, course-style structure.

## Features
- SQLite database with **2 tables**: `categories` and `expenses`
- Auto-insert default categories on first launch: **General, Food, Transport**
- Add a new expense (amount, description, date, category)
- Display all expenses in a **RecyclerView**
- Tap an expense to edit:
  - **Update**: amount + description only
  - **Delete**: with confirmation dialog
- Uses a **JOIN query** to show category name with each expense

## Tech Stack
- Language: **Java**
- Database: **SQLite (SQLiteOpenHelper)**
- UI: **XML layouts + RecyclerView**
- IDE: **Android Studio**

## Database Schema

### categories
- `cid` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
- `cname` (TEXT, NOT NULL, UNIQUE)

### expenses
- `eid` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
- `amount` (REAL, NOT NULL)
- `description` (TEXT, NOT NULL)
- `date` (TEXT, NOT NULL)
- `category_id` (INTEGER, NOT NULL, FK -> categories.cid)

SQL (reference):
```sql
CREATE TABLE categories (
  cid INTEGER PRIMARY KEY AUTOINCREMENT,
  cname TEXT NOT NULL UNIQUE
);

CREATE TABLE expenses (
  eid INTEGER PRIMARY KEY AUTOINCREMENT,
  amount REAL NOT NULL,
  description TEXT NOT NULL,
  date TEXT NOT NULL,
  category_id INTEGER NOT NULL,
  FOREIGN KEY(category_id) REFERENCES categories(cid)
);
```

## Screens / Flow
1. **MainActivity**
   - Add New Expense
   - Display Expenses
2. **AddExpenseActivity**
   - Input: amount, description, date, category (Spinner)
3. **ViewExpensesActivity**
   - RecyclerView list
4. **EditExpenseActivity**
   - Update (amount + description)
   - Delete (confirmation)

## How to Run
1. Clone the repo:
   ```bash
   git clone https://github.com/<your-username>/<repo-name>.git
   ```
2. Open in **Android Studio**
3. Let Gradle sync
4. Run on an emulator or a real device

> If you changed DB structure and the app doesn’t refresh tables:
> - Uninstall the app, or increase DB version in `DBHandler`.

## Project Structure (example)
```
app/src/main/java/.../
  DBHandler.java
  MainActivity.java
  AddExpenseActivity.java
  ViewExpensesActivity.java
  EditExpenseActivity.java
  ExpenseRVAdapter.java
  ExpenseModal.java
  CategoryModal.java

app/src/main/res/layout/
  activity_main.xml
  activity_add_expense.xml
  activity_view_expenses.xml
  activity_edit_expense.xml
  item_expense.xml
```

## Future Improvements
- Filter expenses by category/date
- Monthly total + charts
- Validate date format using DatePicker
- Export to CSV / PDF

## License
MIT

## Author
- Miled issa — 
