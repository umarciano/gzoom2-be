Per caricare i dati all'interno di ofbiz, si devono eseguire dei target ant, ognuno dei quali
carica un particolare dataset.

I target previsti sono i seguenti:
---------------------------------
    1 - run-install-seed
        è l'unico obbligatorio: carica i cataloghi, il nodo root, l'azienda Memelabs con relativa
        sede e l'utente gnadmin
    2 - run-install-poc
        carica i dati di poc. Per le specifiche di tali dati si veda:
        http://192.168.9.25:8080/share/page/site/greennebula/wiki-page?title=PoC_Data
    3 - run-install-test
        carica alcuni dati necessari per eseguire i test unitari del client ofbiz
    4 - run-install-beta-test
        carica i dati delle aziende che hanno partecipato al beta test
        (rfi, alfarec, tecnologie d'impresa, enel distribuzione, enel green power)
    5 - run-install-load-test
        carica i dati necessari per l'esecuzione dei test di carico.

Alcuni scenari di utilizzo con l'indicazione dei targets da eseguire:
--------------------------------------------------------------------
    - ambiente di produzione:
      eseguire run-install-seed
    - ambiente di test:
      eseguire run-install-seed / run-install-poc
    - prima dell'esecuzione dei test unitari:
      eseguire run-install-seed / run-install-poc / run-install-test
    - prima dell'esecuzione dei test di carico:
      1- estrarre i files contenuti nel file loadtestdata.zip che si
         trova nella cartella loadtestdata
      2- eseguire run-install-seed / run-install-poc / run-install-load-test

Per eseguire i target ant da linea di comando:
---------------------------------------------
    1 - posizionarsi all'interno della cartella di ofbiz (ad esempio: cd /home/gn/apache-ofbiz-11.04.01)
    2 - eseguire: ant -f ./hot-deploy/gn/run-install.xml <nome target>