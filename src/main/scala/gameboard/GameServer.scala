package gameboard

import scala.collection.mutable

trait  GameServer {
  var games: mutable.Map[String, P4Game] = mutable.Map()
  def update(): Unit

  def game(name: String): P4Game = {
    games.get(name) getOrElse { throw new IllegalArgumentException("game " + name + " does not exist") }
  }

  def newGame(name: String) = {
    require(!games.contains(name), "game " + name + " already exist")
    val newGame = new P4Game(name)
    games.put(name, newGame)
    update()
    newGame
  }
}