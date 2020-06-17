## Pig Latin (Language game)

#### What?
This application allows you to translate a text in English to a Pig Latin :)

#### How?
To get executable jar file run the following command from the root project directory:

Linux:
``./mvnw clean package``

Windows:
``mvnw.cmd clean package``

After jar is built, to run the application execute the following command from the root project directory 
and add an argument = text you want to be translated, see example below

``java -jar target/pig-latin-1.0-SNAPSHOT.jar 'I want this text to be translated yey'``