from flask_wtf import FlaskForm
from flask_wtf.file import FileField, FileAllowed
from wtforms import StringField, PasswordField, SubmitField, BooleanField, IntegerField, TextAreaField, DateField
from wtforms.validators import DataRequired,  ValidationError, Email, EqualTo

from flask_login import current_user




class IssueBook(FlaskForm):

    sid = IntegerField('Student ID', validators=[DataRequired()])
    lid = IntegerField('Librarian ID', validators=[DataRequired()])
    doi = DateField('Date of Issue', validators=[DataRequired()])
    no = IntegerField('Number of Books', validators=[DataRequired()])

    submit = SubmitField("Login")


class AddStudents(FlaskForm):

    id = IntegerField('ID', validators=[DataRequired()])
    name = StringField('Name', validators=[DataRequired()])
    email = StringField('Email', validators = [DataRequired(),Email()])
    submit = SubmitField("Submit")
    def validate_id(self, id):
        student = Student.query.filter_by(id = id.data).first()
        if student:
            raise ValidationError("This ID is taken choose another one")

    def validate_email(self, email):
        student = Student.query.filter_by(email = email.data).first()
        if student:
            raise ValidationError("This Email is taken choose another one")



class UpdateAccountForm(FlaskForm):

    name = StringField('Name', validators=[])
    #email = StringField('Email', validators=[Email()])
    picture = FileField('Update Profile Picture', validators=[FileAllowed(['jpg', 'png', 'jpeg'])])
    submit = SubmitField('Update')

class PostForm(FlaskForm):
    title = StringField('Title', validators=[DataRequired()])
    content = TextAreaField('Content', validators=[DataRequired()])
    submit = SubmitField('Post')


class RequestResetForm(FlaskForm):
    email = StringField('Email', validators=[DataRequired(), Email()])
    submit = SubmitField('Request Password Reset')
    def validate_email(self, email):
        student = Student.query.filter_by(email = email.data).first()
        if student is None:
            raise ValidationError("There is no account with that email or you have entered the email incorrectly")


class ResetPasswordForm(FlaskForm):
    password = PasswordField('Password', validators=[DataRequired()])
    confirm_password  = PasswordField('Password', validators=[DataRequired(), EqualTo('password')])
    submit = SubmitField('Reset Password')