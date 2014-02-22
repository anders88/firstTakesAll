# First Takes All

The server code used to host a first takes all game

## WARNING
If you are participating in a First Takes All game looking at the code in this project will ruin your experience. Head over to https://github.com/anders88/firstTakesAllSolver to check out a startingpoint.

# Introduction
This is a game, where the participants (players) must implement the solution of several problems (known as categories). Players must ask for questions (this is performed using a http get).
They are then given a list of questions, and must answer these (using a http post). You have 8 seconds to answer. If you answer late - zero points for you. You (obiously) get zero points if you answer wrong. If someone else has
answered the category before you - again zero points (First Takes All :)) There are no negative points in the game, so you are not punished for trying.

# Hosting a game
To host a game on your machine you just run the WebServer class. This will startup an embedded jetty server that can be used as a game server.

# Admin page
The admin page can be reach by pointing your browser to <server-address>/login. The default password is "secret". Here you can, among other things, choose which categories that sould be availible.

## License
Distributed under the Eclipse Public License (http://www.eclipse.org/legal/epl-v10.html)