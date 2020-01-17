from selenium import webdriver
from bs4 import BeautifulSoup
import pandas as pd
import requests
import urllib.request

driver = webdriver.Chrome("/Users/revathi/chromedriver")
subjects = ['accounting-finance', 'human-resources', 'management', 'marketing', 'computer-science-information-systems', 'economics', 'education',
            'engineering', 'humanities', 'arts', 'history', 'languages', 'linguistics', 'literature-rhetoric-and-poetry', 'philosophy',
            'applied', 'pure', 'journalism-media-studies-communications', 'law', 'medicine', 'biology',
            'chemistry', 'earth-sciences', 'physics', 'psychology', 'sociology', 'student-success']

books = []
authors = []
description = []

for book_type in subjects:
    URL = 'https://open.umn.edu/opentextbooks/subjects/' + book_type


    driver.get(URL)

    content = driver.page_source
    soup = BeautifulSoup(content, 'html.parser')


    div_tag = soup.find('div', attrs={'class': 'twothird'})
    h2s = soup.find_all("h2")
    for h2 in h2s:
        books.append(h2.a.text.strip())

df = pd.DataFrame(books)
df.to_csv('/Users/revathi/PycharmProjects/scraping_textbook_data/books.csv')

# for tag in div_tag:
#     print(tag)
# print(div_tag.find('p').get_text())
