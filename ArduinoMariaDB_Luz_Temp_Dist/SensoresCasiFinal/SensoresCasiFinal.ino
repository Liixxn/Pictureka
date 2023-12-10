#if ! (ESP8266 || ESP32 )
  #error This code is intended to run on the ESP8266/ESP32 platform! Please check your Tools->Board setting
#endif



#include "Credentials.h"
#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define _MYSQL_LOGLEVEL_      1
#include <MySQL_Generic.h>

//libreria que maneja el sensor de temperatura
#include <DHT.h>
#include <DHT_U.h>
//Pin D2 para el de temp
#define dht_pin 4


#define USING_HOST_NAME     false

#if USING_HOST_NAME
  // Optional using hostname, and Ethernet built-in DNS lookup
  char server[] = "your_account.ddns.net"; // change to your server's hostname/URL
#else
  IPAddress server(195,235,211,197);  //IP del servidor
#endif



uint16_t server_port = 3306;    //3306;  //Puerto a traves del cual se conecta

char default_database[] = "priPictureka";    //Base de datos     
char default_table[]    = "SENSORES";        //Tabla en la que se desea insertar/modificar etc  
char default_historial[] = "HISTORIAL";      

char default_identificador[] = "*";

DHT dht(dht_pin, DHT11);    //se  crea un objeto tipo DHT

float temp, hum;
float dataLdr;
float datatemp;
const int ldrPin = A0;   //Entrada analogica

//////////////Ultrasonico//////////

const int trigPin = 12; //D6
const int echoPin = 14; //D5
const int trigPin_dos = 13; //D7
const int echoPin_dos = 15; //D8
int sala = 1;


float tiempo;
float dataDist_uno;
float dataDist_Dos;  

//Tiempo con el que se quiere coger los datos de cada sensor
const int ldrInterval = 60000;
const int ultrasonicInterval = 10000;
const int tempInterval = 60000;

unsigned long lastLdrTime = 0;
unsigned long lastUltrasonicTime =  0;
unsigned long lastTempTime = 0;


//Tiempo con el que se quiere coger los datos de cada sensor para otra sala
const int ldrInterval2 = 60000;
const int ultrasonicInterval2 = 10000;
const int tempInterval2 = 60000;

unsigned long lastLdrTime2 = 0;
unsigned long lastUltrasonicTime2 =  0;
unsigned long lastTempTime2 = 0;

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;

MySQL_Query sql_query = MySQL_Query(&conn);



/////////////SETUP/////////////
void setup()
{
  Serial.begin(115200);
  pinMode(ldrPin, INPUT);

  dht.begin();
  
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(trigPin_dos, OUTPUT);
  pinMode(echoPin_dos, INPUT);

  
  while (!Serial && millis() < 5000); // wait for serial port to connect

  MYSQL_DISPLAY1("\nStarting Basic_Insert_ESP on", ARDUINO_BOARD);
  MYSQL_DISPLAY(MYSQL_MARIADB_GENERIC_VERSION);

  // Begin WiFi section
  MYSQL_DISPLAY1("Connecting to", ssid);
  
  WiFi.begin(ssid, pass);
  
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    MYSQL_DISPLAY0(".");
  }

  // print out info about the connection:
  MYSQL_DISPLAY1("Connected to network. My IP address is:", WiFi.localIP());

  MYSQL_DISPLAY3("Connecting to SQL Server @", server, ", Port =", server_port);
  MYSQL_DISPLAY5("User =", user, ", PW =", password, ", DB =", default_database);
}


////////////////////////////////////////////////////////////////////////////////////7


String historialLuz (float SensorData){ 

   String INSERT_SQL = String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES ("+runQuery("Luz", sala, 1)+ ", " +SensorData+ ");";
   
  return INSERT_SQL;
}


String historialTemp (float SensorData){
  String INSERT_SQL = String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES (" +runQuery("Temperatura", sala, 1)+ ", " + SensorData + ");";
                 
  return INSERT_SQL;
}

String historialDist (){
  String INSERT_SQL = String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES (" +runQuery("Distancia", sala, 1)+ ", " + dataDist_uno + ");";

 INSERT_SQL += String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES (" +runQuery("Distancia", sala, 2)+ ", " + dataDist_Dos + ");";
                 
 INSERT_SQL += String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES (" +runQuery("Distancia", sala, 3)+ ", " + (dataDist_uno+5) + ");";
                 
 INSERT_SQL += String("INSERT INTO ") + default_database + "." + default_historial + "(TipoSensor, Lectura)"
                 + " VALUES (" +runQuery("Distancia", sala, 4)+ ", " + (dataDist_Dos+5) + ");";                               
                
               return INSERT_SQL;
}


//Funcion que recoge los datos de la tabla sensores y devuelve el identificador
long runQuery(String Tipo, int ID_Sala, int Posicion)
{
  
   String query = String("SELECT ") + default_identificador + " FROM " + default_database + "." + default_table 
                 + " WHERE(SENSORES.ID_sala = '"+ID_Sala+"' AND SENSORES.Posicion = '"+Posicion+ "' AND SENSORES.Tipo = '"+Tipo+"');";
                 
  /*String query = String("SELECT ") + default_identificador + " FROM " + default_database + "." + default_table+ ";";*/
                 
  row_values *row = NULL;
  long identificador = 0;

  MYSQL_DISPLAY("1) Demonstrating using a dynamically allocated query.");
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);

  //if (conn.connected())
  
  // Execute the query
  MYSQL_DISPLAY(query);

  // Execute the query
  // KH, check if valid before fetching
  if ( !query_mem.execute(query.c_str()) )
  {
    MYSQL_DISPLAY("Querying error");
    return -1;
  }
  
  // Fetch the columns (required) but we don't use them.
  //column_names *columns = query_mem.get_columns();
  query_mem.get_columns();

  // Read the row (we are only expecting the one)
  do 
  {
    row = query_mem.get_next_row();
    
    if (row != NULL) 
    {
      identificador = atol(row->values[0]);;
    }
  } while (row != NULL);

  // Show the result
  MYSQL_DISPLAY1("ID_Sensor = ", identificador);

  //delay(500);
  // Now we close the cursor to free any memory
  sql_query.close();

  return identificador;
}


/////////////////////////////////////////////

//funciones que insertan a la tabla historial
void runInsertLuzHistorial(float data)
{
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    // MYSQL_DISPLAY(INSERT_SQL);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(historialLuz(data).c_str()) )
    {
      MYSQL_DISPLAY("Insert error Luz");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}


void runInsertDistHistorial()
{
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);
  
  if (conn.connected())
  {
    // MYSQL_DISPLAY(INSERT_SQL);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(historialDist().c_str()) )
    {
      MYSQL_DISPLAY("Insert error Dist");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}



void runInsertTempHistorial(float data)
{
  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    // MYSQL_DISPLAY(INSERT_SQL);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(historialTemp(data).c_str()) )
    {
      MYSQL_DISPLAY("Insert error Temp");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}

////////////////////////////////////////////////////////////////////////



//funcion que recoge el dato del sensor de luz
float sensorLuz()
{

  float rawData = analogRead(ldrPin);
  Serial.println(rawData);

  return rawData;
}

//funcion que recoge el dato del sensor de temperatura
float sensorTemp()
{

  temp = dht.readTemperature();
  Serial.println(temp);

  return temp;
}

float sensorDist(int trigger, int echo)
{
float distancia;

     digitalWrite(trigger, LOW);  //para generar un pulso limpio ponemos a LOW 4us
     delayMicroseconds(4);
     
     digitalWrite(trigger, HIGH);  //generamos Trigger (disparo) de 10us
     delayMicroseconds(10);
     digitalWrite(trigger, LOW);
     
     tiempo = pulseIn(echo, HIGH);
     distancia = (tiempo*0.034)/ 2;  
   
   Serial.println(distancia);


   return distancia;
}


////////////////////////////////////////////////////////////////////////

void loop()
{
  MYSQL_DISPLAY("Connecting...");
  //Inserta las medidas de los sensores en la sala 2
    sala = 2;
  //establece la conexión con el servidor
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    //compruba que hayan pasado 10s para insertar el de distancia
     if (millis() - lastUltrasonicTime >= ultrasonicInterval) {

      dataDist_uno = sensorDist(trigPin,echoPin);
      dataDist_Dos = sensorDist(trigPin_dos, echoPin_dos);
      runInsertDistHistorial();
      lastUltrasonicTime = millis(); //assigns to new millis()
     }
  conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  
  /*
  Nueva conexión con el servidor para insertar los valores de temperatura y luz
  es una nueva conexión porque si se utiliza la anterior esta se cierra antes de que se 
  pueda realizar la conexión

  */
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    //compruba que hayan pasado 60s para insertar el de luz
    if (millis() - lastLdrTime >= ldrInterval) {
      dataLdr = sensorLuz();
      runInsertLuzHistorial(dataLdr);
      lastLdrTime = millis();
      
    }
  //compruba que hayan pasado 60s para insertar el de temperatura
     if (millis() - lastTempTime >= tempInterval) {
      datatemp = sensorTemp();
      runInsertTempHistorial(datatemp);
      lastTempTime = millis(); 
     }
    conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("\nSleeping...");
  MYSQL_DISPLAY("================================================");

  //10 s de espera
  delay(5000);


//finaliza la insercion de los datos en la sala 2

////////////////////////////////////////////////////////////////////////////////////////////////////////////


  //Inserta las medidas de los sensores en la sala 3
  sala = 3;
  MYSQL_DISPLAY("Connecting...");
  
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    //comprueba que hayan pasado 10s para insertar el de distancia
     if (millis() - lastUltrasonicTime2 >= ultrasonicInterval2) {

      dataDist_uno = sensorDist(trigPin,echoPin);
      dataDist_Dos = sensorDist(trigPin_dos, echoPin_dos);
      runInsertDistHistorial();
      lastUltrasonicTime2 = millis(); //assigns to new millis()
     }
  conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  /*
  Nueva conexión con el servidor para insertar los valores de temperatura y luz
  es una nueva conexión porque si se utiliza la anterior esta se cierra antes de que se 
  pueda realizar la conexión

  */

  
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
  //compruba que hayan pasado 60s para insertar el de luz
    if (millis() - lastLdrTime2 >= ldrInterval2) {
      dataLdr = sensorLuz();
      runInsertLuzHistorial(dataLdr);
      lastLdrTime2 = millis();
      
    }
  

//compruba que hayan pasado 60s para insertar el de temperatura
     if (millis() - lastTempTime2 >= tempInterval2) {
      datatemp = sensorTemp();
      runInsertTempHistorial(datatemp);

      lastTempTime2 = millis(); 
     }

    
    conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("\nSleeping...");
  MYSQL_DISPLAY("================================================");

  //10 s de espera
  delay(5000);
}
