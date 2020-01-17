
from bs4 import BeautifulSoup
from selenium import webdriver
from bs4 import NavigableString, Tag
import pymysql
# from temp import get_name

def get_url_to_openlib(bname, fake_author):
    name = bname
    name = bname.strip()
    name = name.replace(' ', "%20")
    print(name)
    url = "https://www.google.dz/search?q="+name+"%20open%20library"
    print("Searching on google..", url)
    driver = webdriver.Chrome('/Users/revathi/chromedriver')
    driver.get(url)
    content = driver.page_source
    soup = BeautifulSoup(content, "lxml")
    link = soup.find("div", attrs={'class':'r'})
    a = link.find('a')

    new_url = a.get('href')
    # driver.close()
    print("needed url", new_url)
    driver.close()
    return new_url, bname
# driver.get(new_url)

def scrape_actual_info(url):
    driver = webdriver.Chrome('/Users/revathi/chromedriver')
    driver.get(url)
    content = driver.page_source
    soup = BeautifulSoup(content, "lxml")
    author = soup.find('a', attrs={'itemprop':"author"}).get_text()

    div_with_class = soup.find('div', attrs={'class':'section link-box'})
    div = div_with_class.find('div')
    span = div.find('span')
    genres = []
    # print("Span: ", span)
    for a_tag in span:
        # print("A TAG", a_tag)
        if isinstance(a_tag, NavigableString):
            continue
        if isinstance(a_tag, Tag):
            # print(a_tag.text)
            genres.append(a_tag.text)

    result = {'author':author, 'genres':genres}
    print(result)
    return result

def add_genre_to_database(bname, bid, result, conn):
    author = result['author']
    genres = result['genres']
    bname = '"' + bname + '"'
    for genre in genres:
        genre_ = "\"" + genre + "\""
        author_ = "\"" + author + "\""
        query = "INSERT INTO genre values({}, {}, {}, {})".format(bid, bname, author_, genre_)
        print("query", query)
        c = conn.cursor()
        c.execute(query=query)
        result = c.fetchall()
        conn.commit()
        print(result)

# res = {'author': 'Amish Tripathi', 'genres': ['India Mythology', 'In library', 'Siva (Hindu deity)', 'Fiction']}
conn = con = pymysql.connect(
  host="phpmyadmin.c6cmsjq5pr1d.ap-south-1.rds.amazonaws.com",
  user="admin",
  passwd="password",
  database="lms",
  port=3306
)

url, name= get_url_to_openlib("Introduction to Algorithms", "f")
res  = scrape_actual_info(url)
add_genre_to_database(name, 20, res, conn=conn)
