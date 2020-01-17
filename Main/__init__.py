import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
from flask_login import LoginManager
from flask_mail import Mail


app = Flask(__name__)
app.config['SECRET_KEY'] = 'c99d2943ab28da8b956686f2dfc53531'

bcrypt = Bcrypt(app)
login_manager = LoginManager(app)
login_manager.login_view = 'studentlogin'
login_manager.login_message_category = 'info'
app.config['MAIL_SERVER'] = 'smtp.googlemail.com'
app.config['MAIL_PORT'] = 587
app.config['MAIL_USERNAME'] = os.environ.get('EMAIL_USER')
app.config['MAIL_PASSWORD'] =  os.environ.get('EMAIL_PASS')
app.config['MAIL_USE_TLS'] = True
mail = Mail(app)

from Main import routes