# HORBot - RecSys

HorBot è un chat bot sviuluppato con le API di Telegram utilizzato come Recommender System che mediante i dati raccolti da Myrror (attraverso le sue API) e dei questionari è in grado di raccomandare un'attività all'utente che utilizza il chatbot.
Il seguente repository è stato creato per lo sviluppo del lavoro di tesi di Accesso Intelligente all'informazione ed Elaborazione del Linguaggo Naturale dell'università degli Studi di Bari.
Per sviluppare il sistema è stato fatto uso delle seguenti **tecnologie**:
* [Maven](https://maven.apache.org/)
* [Log4j](https://logging.apache.org/log4j/2.x/)
* [IntelliJ](https://www.jetbrains.com/idea/)
* [Telegram API](https://core.telegram.org/)

Per quanto riguarda la pipeline di raccomandazione, è la seguente:
1. Chiedere username Myrror tramite comando Telegram e salvarlo per indicizzare la raccomandazione in base all'utente
2. Caricare collection (tutte o alcune) pertinenti alla raccomandazione tramite richieste http che utilizzano le API sviluppate su Myrror e salvarle su opportune strutture dati
3. Effettuare un questionario all'utente che verrà utilizzato per la raccomandazione content based per quell'utente. Il questionario verrà effettuato una volta
4. Effettuare un secondo questionario che verrà svolto solo una volta e che verrà utilizzato per creare le regole e vincoli sulle collection di Myrror e per modificare lo score della raccomandazione classica e creare feature contestuali
5. Controllare prima di effettuare le inferenze che si disponga di tutti i dati e che le risposte dei questionari siano salvate su repository esterni per mantenere la persistenza dei dati
6. Costruire un recsys classico in base al primo questionario - integrare con na migliore descrizione del recsys (articolo ultima mail)
7. Modificare lo score del recsys classico attraverso regole o apponendo dei vincoli che si sono creati col secondo questionario - descrivere come (articolo HOR)
8. Eventuali domande all'utente per risolvere alcune incertezze dell'inferenza sulle regole 
9. Preparare parametri per interrogazione in base al DB o Service scelto
10. Interrogare POI(DB, Google, Foursquare)
11. Esporre Result Set all'utente o solo un valore di raccomandazione
