import mysql.connector
from mysql.connector import Error

# Define the database connection parameters
db_config = {
    'host': 'localhost',
    'port': 3306,
    'database': 'cinema_manager',
    'user': 'root',  # Replace with your MySQL username
    'password': '',  # Replace with your MySQL password
    'use_unicode': True,
    'charset': 'utf8',
    'time_zone': 'Asia/Shanghai'
}

def connect_to_db():
    connection = None  # Initialize connection as None
    try:
        # Establishing the connection to the database
        connection = mysql.connector.connect(**db_config)

        if connection.is_connected():
            print("Connection to the database was successful!")

            # Create a cursor object to interact with the database
            cursor = connection.cursor()

            # Sample query to test
            cursor.execute("SELECT DATABASE();")
            db_info = cursor.fetchone()
            print(f"You're connected to the database: {db_info[0]}")

            # Run the count query on sys_comment table
            cursor.execute("SELECT count(0) FROM sys_comment;")
            row_count = cursor.fetchone()[0]
            print(f"Total rows in 'sys_comment' table: {row_count}")

    except Error as e:
        print(f"Error while connecting to MySQL: {e}")
    finally:
        if connection is not None and connection.is_connected():
            cursor.close()
            connection.close()
            print("MySQL connection is closed.")

if __name__ == '__main__':
    connect_to_db()
