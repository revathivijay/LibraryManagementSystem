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
   result = execute_query(query, con)
   print(result)

def insert_issued(bid, sid, datee, lid):
    date_str = "\"" + datee + "\""
    query = "insert into issued values({}, {}, {}, {}, {}, {})".format(lid, sid, bid, "\"The immortals of Meluha\"",date_str, 0)
    print("insert into issued values(lid={}, sid={}, bid={}, bname={},  is_returned={})".format(lid, sid, bid, "\"The immortals of Meluha\"", 0))
    result = execute_query(query, con)
    con.commit()
    print(result)

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


@app.route('/getQuery', methods=['GET', 'POST'])
def getQuery():
    print('##################')
    print(request.form['query'])
    return jsonify({})