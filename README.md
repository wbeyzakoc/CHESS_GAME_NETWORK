CHESS GAME - NETWORK PROGRAMMING PROJECT
This project is a two-player chess game developed in Java. The user interface
was created with Java Swing, and the network communication was implemented with
TCP sockets. The server is a console application and can be run locally or on
AWS EC2.
PROJECT STRUCTURE
Main files:
- Chess.java
  Starts the application.
- enter.java
  Opens the first start screen.
- ready.java
  Shows the connection screen. The user enters the server IP address, port
  number, and room code here.
- game.java
  Contains the main chess board, pieces, turn control, move handling, online
  play behavior, and bot takeover behavior.
- GameLogic.java
  Contains chess rule validation such as legal moves, check, checkmate,
  stalemate, castling, and pawn promotion.
- Piece.java
  Represents a chess piece with type, color, position, and movement status.
- ClientConnection.java
  Manages the socket connection between the client and the server.
- Server.java
  Console-based server application. It matches players, manages rooms, and
  forwards moves between clients.
- AssetLoader.java
  Loads image assets from the project resources.
- EndScreen.java
  Shows the game result screen.
REQUIREMENTS
- Java 17 or newer
- NetBeans IDE
- Maven
- AWS EC2 instance for online play
LOCAL TESTING
Local testing is done on the same computer using 127.0.0.1.
Local server information:
Server IP address: 127.0.0.1
Server port: 5003
Room code: automatic
To run the server locally:
1. Open Terminal.
2. Go to the project folder:
   cd /Users/beyzamacbook/NetBeansProjects/chess
3. Build the project from NetBeans using Clean and Build.
4. Start the local server:
   java -cp target/classes com.mycompany.chess.client.Server 5003
5. Run the client application twice from NetBeans.
6. Enter the following information in both clients:
   Server IP address: 127.0.0.1
   Server port: 5003
   Room code: automatic
The first two clients will be matched in room1. The third and fourth clients
will be matched in room2.
AWS ONLINE TESTING
For online play, the server runs on AWS EC2. The clients connect to the AWS
server using its Public IPv4 address.
AWS server information used in this project:
Server IP address: 51.20.82.77
Server port: 8080
Room code: automatic
Important:
- The server is run only on AWS.
- Client computers do not run Server.java.
- Players do not have to be on the same Wi-Fi network.
- If the EC2 instance is stopped and started again, the Public IPv4 address may
  change. In that case, the new IP address must be entered in the client.
AWS SECURITY GROUP SETTINGS
The AWS Security Group must allow incoming TCP traffic on port 8080.
Inbound rules:
Type: SSH
Protocol: TCP
Port: 22
Source: 0.0.0.0/0
Type: Custom TCP
Protocol: TCP
Port: 8080
Source: 0.0.0.0/0
CONNECTING TO AWS
From the local computer, connect to AWS with:
ssh -i ~/.ssh/chess-key.pem ec2-user@51.20.82.77
After connecting successfully, the terminal should look similar to this:
[ec2-user@ip-172-31-20-238 ~]$
UPLOADING UPDATED SERVER CODE TO AWS
From the local project folder:
cd /Users/beyzamacbook/NetBeansProjects/chess
Upload the updated Java files:
scp -i ~/.ssh/chess-key.pem -r src/main/java/com ec2-user@51.20.82.77:~
Then connect to AWS:
ssh -i ~/.ssh/chess-key.pem ec2-user@51.20.82.77
Compile the server on AWS:
javac com/mycompany/chess/client/Server.java
RUNNING THE SERVER ON AWS
Start the AWS server on port 8080:
nohup java com.mycompany.chess.client.Server 8080 > server.log 2>&1 &
Check if the server is listening:
ss -ltnp | grep 8080
Check the server log:
cat server.log
Expected output:
Chess server started on port 8080
RESTARTING THE AWS SERVER
If the connection is lost or the server must be restarted:
pkill -f com.mycompany.chess.client.Server
nohup java com.mycompany.chess.client.Server 8080 > server.log 2>&1 &
ss -ltnp | grep 8080
cat server.log
TESTING AWS CONNECTION FROM LOCAL COMPUTER
Open a normal local Terminal and run:
nc -G 5 -vz 51.20.82.77 8080
If the connection is successful, the output should include:
succeeded
ROOM SYSTEM
The project supports a room system. Players who enter the same room code are
matched in the same game.
If the room code is left as "automatic", the server automatically places players
into rooms in pairs:
1st player -> room1
2nd player -> room1, match starts
3rd player -> room2
4th player -> room2, match starts
5th player -> room3
This allows multiple two-player matches to run at the same time.
PLAYER DISCONNECTION AND BOT TAKEOVER
If one player leaves during an active match, the server informs the remaining
player. The remaining game continues against the computer.
The bot takes over the disconnected player's pieces. The bot first checks if it
can capture an opponent's piece. If it can, it chooses the most valuable capture.
If no capture is available, it chooses one of the legal moves.
Bot piece values:
Queen: 9
Rook: 5
Bishop: 3
Knight: 3
Pawn: 1
PORT SUMMARY
Local testing:
Server IP address: 127.0.0.1
Server port: 5003
AWS online play:
Server IP address: 51.20.82.77
Server port: 8080
NOTES
- Use port 5003 only for local testing.
- Use port 8080 for AWS online play.
- Both client computers must run the updated project version.
- The AWS server must also use the updated Server.java file.
- If the bot feature does not work, make sure both the client code and the AWS
  Server.java file are updated and rebuilt.
