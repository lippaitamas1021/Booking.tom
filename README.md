***** Booking.tom *****

A vizsgaremekem címe Booking.tom, ami egy háromrétegű webszolgáltatás, egy szobafoglalási rendszer, amiben vendégeket és 
szobákat lehet nyilvántartani. A jelenlegi állapotában szállodák recepcióján a vendégek érkeztetésekor a 
recepciósok vehetik hasznát.

Spring Boot segítségével generáltam a projekt vázát, ami MAVEN struktúra, a program pedig a JAVA
16-os verziójával készült.

** Függőségek: **
1) ModelMapper, ami az entitások és a DTO-k közötti konvertálást végzi
2) LOMBOK, amivel elimináltam a boilerplate kódokat
3) Problem (Zalando), ami az RFC 7807-es szabvány alapján elvégzi a hibakezelést
4) Flyway, ami az adatbázis séma generálását végzi a megadott SQL utasítások alapján
5) MariaDb, ami a relációs adatbázist kezeli, ami egyébként Docker konténerben fut
6) Spring Data JPA, ami a beépített interfész metódusaival egyszerűsíti az adatbázis műveletek kiadását
7) Validation, ami a bevitt adatok validációját végzi
8) REST Template, ami a HTTP kérések kiadását teszi lehetővé
9) SwaggerUI, ami az alkalmazás dokumentációját szolgáltatja, illetve grafikus felületen teszi lehetővé
   a HTTP kérések kiadását

** Entitások: **
1) Guest, azaz a vendég, aminek a tulajdonságai a következők:
   - id, egyedi azonosító, amit az adatbázis oszt ki részére
   - name, név
   - Room, szoba
2) Room, azaz a szoba, aminek a tulajdonságai a következők:
   - id, egyedi azonosító, amit az adatbázis oszt ki részére
   - roomNumber, szobaszám
   - guests, vendégek listája

** Kapcsolat: **
A két entitás kétirányú, egy-több kapcsolatban áll egymással, hiszen egy szobához több vendég is
tartozhat. A vendégek adattáblája külső kulcsként tartalmazza a szoba egyedi azonosítóját.

** Felépítés: **
3 rétegből épül fel az alkalmazás:
    1) Repository réteg, ezt a GuestRepository és a RoomRepository interfészek reprezentálják, melyek
       kiterjesztik a JpaRepository interfészt, amely a beépített metódusaival egyszerűsíti az
       adatbázis műveletek elvégzését. A háttérben egy relációs adatbázis gondoskodik a tárolásról,
       ennek az adatbázis sémáját a Flyway inicializálja.
    2) Service réteg, ezt a GuestService és RoomService osztályok képvielik. Ezen osztályok metódusai
       olyan műveletekethajtanak végre, mint a mentés, módosítás, lekérdezés és törlés. Ezen metódusok
       továbbhívják a Repository interfészek metódusait.
    3) Controller réteg, ezt a GuestController és RoomControler osztályokban valósítottam meg, ezekben
       deklaráltam a REST végpontokat, melyek a következő funkciókat testesítik meg:

** Funkciók: **
1) Guest entitás:
   - összes vendég lekérdezése
   - vendégek lekérdezése névtöredék alapján
   - egy vendég lekérdezése egyedi azonosító alapján
   - új vendég felvétele
   - szoba hozzárendelése egy már felvett vendéghez, ilyenkor automatikusan hozzárendelem a vendég
     nevét a szobához
   - már felvett vendég adatainak módosítása, ilyenkor az egyedi azonosító alapján megkeresem az adatbázisban,
     majd ezt követően elvégzem rajta a szükséges UPDATE műveletet
   - egy adott vendég törlése ID alapján
   - összes vendég törlése
2) Room entitás:
   - összes szoba lekérdezése
   - szobák lekérdezése a szobanév töredéke alapján
   - egy szoba lekérdezése az egyedi azonosító alapján
   - új szoba felvétele
   - meglévő szobához új vendég felvétele
   - meglévő szobához meglévő vendég felvétele
   - meglévő szoba tulajdonságainak módosítása
   - egy adott szoba törlése egyedi azonosító alapján
   - összes szoba törlése

** Tesztelés: **
Az integrációs tesztek a GuestControllerRestIT és a RoomControllerRestIT osztályokban találhatók, ezek
Spring Boot tesztek, melyek a teljes alkalmazást tesztelik valós konténerben.
A SwaggerUI felhasználói felületén (http://localhost:8080/swagger-ui.html) lehetőség nyílik az alkalmazás
REST webszolgáltatásainak tesztelésére.

                            ***********************************
                            Ha a hotelben ott a Booking.tom,
                            akkor Neked jobb lesz, mint otthon!
                            ***********************************