# HORBot - RecSys

Italian chat bot developed with Telegram API and used as Recommender System of point of interests with Myrror facets (through its API) and surveys.

The following **technologies** have been used to develop the system:
* [IntelliJ](https://www.jetbrains.com/idea/)
* [Telegram API](https://core.telegram.org/)
* [Maven](https://maven.apache.org/)
* [Heroku](https://devcenter.heroku.com/categories/deployment)
* [Log4j](https://logging.apache.org/log4j/2.x/)
* [Jackson](https://github.com/FasterXML/jackson-databind)
* [Lucene](https://lucene.apache.org/core/)

## Configuration

Add a file _horbot.properties_ to the _resources_ directory and set it as follows:

```
# HORBot properties
username=<YOUR_CHATBOT_NAME>
token=<YOUR_CHATBOT_TOKEN>
```

## Deploy

The following [guide](https://devcenter.heroku.com/articles/run-non-web-java-processes-on-heroku) has been used for deploying, the commands used are:

```
git init
git add .
git commit -m "Ready to deploy"
heroku create
git push heroku master
```

## Telegram commands

The following is a list of the commands used by the chat bot:

```
/start - Inizia ad usare il RecSys
/login - Effettua il login per Myrror
/survey - Inizia il questionario
/setlocation - Invia la posizione
/setcontexts - Scegli i contesti
/showanswer - Visualizza le risposte del questionario
/showcontexts - Visualizza le attività scelte
/resetanswer - Reimposta le risposte del questionario
/resetcontexts - Reimposta i contesti
/getrecommend - Ottieni un suggerimento di attività da svolgere
/help - Informazioni sui comandi
```