*****   Booking.tom   *****

A Booking.tom egy 3-rétegű webszolgáltatás, egy szobafoglalási rendszer, melyben vendégeket 
és szobákat lehet nyilvántartani. Jelenlegi állapotában szállodák recepcióin a vendégek érkeztetésekor a 
recepciósok vehetik hasznát.

Spring Boot segítségével generáltam a projekt vázát, ami MAVEN struktúra. a program pedig 16-os 
verziójú JAVA-ban íródott. 

**Függőségek:**
1) ModelMapper, ami az entitások és a Data Transfer Object-ek közötti konvertálást végzi
2) LOMBOK, amivel elimináltam a boilerplate kódokat
3) Problem (Zalando), ami az RFC 7807-es szabvány alapján elvégzi a hibakezelést
4) Flyway, ami az adatbázis séma legenerálásását végzi a megadott SQL utasítások alapján
5) MariaDb, ami a relációs adatbázist kezeli, ami egyébként Docker konténerben fut
6) Spring Data JPA, ami a beépített interfész metódusaival egyszerűsíti az adatbázis műveleteket
7) Validation, ami a bevitt adatok validációját végzi
8) REST Template, ami a Http kérések kiadását teszi lehetővé
9) SwaggerUI, ami az alkalmazás tesztelését teszi biztosítja Http kérések grafikus felületen való kiadásával

**Entitások:**
1) Guest, azaz vendég, akinek tulajdonságai a következők:
   - id, ami egy egyedi azonosító, amit az adatbázis oszt ki részére.
   - name, név 
   - Room, szoba
2) Room, azaz szoba, ami az alábbi tulajdonsággal rendelkezik:
    - id, ami egy egyedi azonosító, amit az adatbázis oszt ki részére.
    - roomNumber, azaz szobaszám
    - guests, azaz a szobában tartózkodó vendégek listája
    
**Kapcsolat:**
A két entitás kétirányú egy-több kapcsolatban áll egymással, hiszen egy szobához több vendég is tartozhat.
A vendégek adattáblája külső kulcsként tartalmazza a szoba egyedi azonosítóját.

**Felépítés**: 
3-rétegből épül fel az alkalmazás:
    1) A Repository rétegeket a GuestRepository és a RoomRepository interfészek reprezentálják,
    melyek kiterjesztik a JpaRepository interfészt, amely beépített interfész metódusokkal egyszerűsíti
    az adatbázis műveletek elvégzését. A háttérben egy relációs adatbázis gondoskodik a tárolásról,
    mely adatbázis sémáját a Flyway inicializálja.
    2) A Service réteget a GuestService és a RoomService osztályok képviselik. Ezen osztályok metódusai
    olyan műveleteket hajtanak végre, mint a mentés, módosítás, lekérdezés és a törlés. Ezen metódusok
    továbbhívják a Repository interfész metódusokat.
    3) A Controller réteget a GuestController és a RoomController osztályokban valósítottam meg, ezekben
    deklaráltam REST végpontokat, melyek az alábbi funkciókat testesítik meg.

**Funkciók:**
1) Guest entitás:
    - összes vendég lekérdezése,
    - egy vendég lekérdezése névtöredék alapján
    - egy vendég lekérdezése egyedi azonosító alapján
    - új vendég felvétele, akár csak a neve megadásával
    - szoba hozzárendelése egy már felvett vendéghez, ilyenkor automatikusan hozzárendelem a vendég nevét
      a szobához
    - elmentett vendég tulajdonságainak módosítása, ilyenkor az egyedi azonosító alapján megkereem
      az adatbázisban, majd ezt követően elvégzem rajta a szükséges UPDATE műveletet
    - egy adott vendég törlése egyedi azonosító alapján
    - összes vendég törlése
    
2) Room entitás:
    - összes szoba lekérdezése
    - egy szoba lekérdezése az egyedi azonosítója alapján
    - új szoba felvétele
    - meglévő szoba tulajdonságainak módosítása
    - egy adott szoba törlése egyedi azonosító alapján
    - összes szoba törlése

**Tesztelés:**
Az integrációs tesztek a GuestControllerRestIT és RoomControllerRestIT osztályokban találhatók
Ezek Spring Boot tesztek, melyek a teljes alkalmazást tesztelik valós konténerben.
A SwaggerUI felhasználói felületén (http://localhost:8080/swagger-ui.html) lehetőség nyílik az alkalmazás 
REST webszolgáltatásainak tesztelésére.

*******************************************
    Ha a hotelben ott a Booking.tom,
    akkor Neked jobb lesz mint otthon!
******************************************