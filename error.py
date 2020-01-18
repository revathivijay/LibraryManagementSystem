#checking valid student ID in first form
import pymysql

con = pymysql.connect(
  host="phpmyadmin.c6cmsjq5pr1d.ap-south-1.rds.amazonaws.com",
  user="admin",
  passwd="password",
  database="lms",
  port=3306
)

def execute_query(query, con):
    c = con.cursor()
    c.execute(query=query)
    result = c.fetchall()
    return result

def check_sid(sid):
    query = "SELECT COUNT({}) from student".format(sid)
    q_count = execute_query(query, con)
    count = q_count[0][0]
    if count<=0:
        return("Error! This is Invalid")

def check_bid(bid):
    query = "SELECT COUNT({}) from books".format(bid)
    q_count = execute_query(query, con)
    count = q_count[0][0]
    if count<=0:
        return("Error! This is Invalid")

#while returning the book, check if book has been issued
def check_if_book_issued(bid):
    query = "SELECT COUNT({}) from issued".format(bid)
    q_count = execute_query(query, con)
    count = q_count[0][0]
    if count<=0:
        return("This book was not issued")
