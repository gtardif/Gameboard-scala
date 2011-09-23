package gameboard

import scala.collection.mutable

trait  GameServer {
  var games: mutable.Map[String, P4Game] = mutable.Map()
  def update(): Unit
  
  def game(name: String): P4Game = {
    games.get(name) getOrElse { throw new IllegalArgumentException("game " + name + " does not exist") }
  }
  
  def gameEnded(game : P4Game) = {
    games -= game.name
    update()
  }

  def newGame(name: String) = {
    println("games :" + games)
    require(!games.contains(name), "game " + name + " already exist")
    val newGame = new P4Game(name)
    games.put(name, newGame)
    update()
    newGame
  }
}