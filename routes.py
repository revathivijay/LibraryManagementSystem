from flask import render_template, url_for, flash, redirect, request, jsonify
# from Main.models import Student
# from Main.forms import LoginForm, AddStudents, UpdateAccountForm, RequestResetForm, ResetPasswordForm
from Main import app, bcrypt, mail
import pymysql
import json

from flask_login import login_user, current_user, logout_user, login_required
import secrets, os
from PIL import Image
from flask_mail import Message


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

def auto_fill_book_details(bid):
   #query for displaying bname and author automatically from bid, given bid=bid
   query = "select bname from books where bid={}".format(bid)
   result = execute_query(query=query, con=con)
   return(result)


#query for displaying book details when entered in return page
def fetch_issued_book_details(bid):
   query = "select bid, sid, bname, date_of_issue from issued where bid={}".format(bid)
   print(query)
   result = execute_query(query, con)
   return(result)

def insert_issued(bid, sid, datee, lid):
    date_str = "\"" + datee + "\""
    bname = auto_fill_book_details(bid)


    print("bname",bname[0][0])
    name = "\"" + bname[0][0] + "\""
    print(name)
    query = "insert into issued values({}, {}, {}, {}, {}, {})".format(lid, sid, bid, name,date_str, 0)
    print("insert into issued values(lid={}, sid={}, bid={}, bname={},  is_returned={})".format(lid, sid, bid,auto_fill_book_details(bid) , 0))
    # result = execute_query(query, con)
    c1 = con.cursor()
    c1.execute(query=query)
    result = c1.fetchall()
    con.commit()
    query2 = "update books set isAvailable=0 where bid={}".format(bid)
    c2 = con.cursor()
    c2.execute(query=query2)
    result2 = c2.fetchall()
    con.commit()
    print(result, result2)

@app.route("/")
@app.route("/home")
def home():

    return render_template('home.html')


@app.route("/index",methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        # data = request.get_json()
        # print(data)
        print('Entered AJAX')
        arr = (request.form.getlist('arr[]'))
        sid = (request.form['sid'])
        numb = (request.form['rev'])
        lid = (request.form['lid'])
        dateee = (request.form['dateee'])
        for x in range(len(arr)):
            print(arr[x])
            insert_issued(sid=sid, bid=arr[x], datee=dateee, lid=lid)


        # print(json.loads(request.form.getlist('arr')))
        # print(request.form['rev'])
        return jsonify({'bname': 'bname'})
    return jsonify({'Error': 'Missing Data'})


@app.route('/getBname', methods=['GET', 'POST'])
def getBname():
    if request.method=='POST':
        print(request.form['bid'])
        bid = request.form['bid']
        bnameid = request.form['bnameid']
        bname = auto_fill_book_details(bid)
        print(bname)
        print(bname[0][0])
        return jsonify({"bname":bname[0][0], "bnameid":bnameid})

def extract_book_from_db(search_text):
    query = "select bid, author, bname  from books where bname like '%{}%' OR author like '%{}%'".format(search_text, search_text)
    result = execute_query(query=query, con=con)
    return(result)

def count_no_of_books(search_text):
    query = "select count(bid) from books where bname like '%{}%'".format(search_text)
    result = execute_query(query, con)
    # print(result[0][0])
    return result[0][0]


@app.route('/getQuery', methods=['GET', 'POST'])
def getQuery():
    print('##################')
    print(request.form['query'])
    result = extract_book_from_db(request.form['query'])
    count = count_no_of_books(request.form['query'])
    # print(count)
    json = []
    # print(result)
    # print(result[1])
    for i in range(count):
        json.append(result[i])
        print("This is",json)
    print(json)
    return jsonify(json)



@app.route('/returnb', methods=['GET', 'POST'])
def returnb():
    print('##################')
    print(request.form['rev'])
    result = fetch_issued_book_details(request.form['rev'][0])
    print(result[0][1])
    print(result[0][3])
    import datetime
    from datetime import date
    date_str = str(result[0][3])  # The date - 29 Dec 2017
    format_str = '%Y-%m-%d'  # The format
    datetime_obj = datetime.datetime.strptime(date_str, format_str)
    end_date = str((datetime_obj + datetime.timedelta(days=7)).strftime(format_str))
    print(end_date)
    print((datetime.datetime.now()-datetime_obj).days)
    if((datetime.datetime.now()-datetime_obj).days>7):
        lf = (datetime.datetime.now()-datetime_obj).days * 2-14

    else:
        lf=0
    return jsonify({"rev": result[0][0], "sid":result[0][1], "bname": result[0][2], "doi": str(result[0][3]), "edd": end_date, "lf": lf})