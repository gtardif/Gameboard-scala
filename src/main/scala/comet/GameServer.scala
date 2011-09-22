package gameboard.comet

import net.liftweb._
import http._
import actor._
import gameboard._
import scala.collection.mutable

class GameServer(val name: String) {
  private var players : List[Player] = List()
  private var startedGame: Option[P4Game] = None

  def game = startedGame getOrElse (throw new IllegalArgumentException("game not yet started ; missing " + (players.size - 2) + " players"))

  def started = startedGame isDefined

  def join(): WebPlayer = join(side => new WebPlayer(side))
  
  def join[T <: Player](newPlayer : (Side.Side) => T): T = players match {
    case List() => {
      val player1 = newPlayer(Side.RED)
      players = List(player1)
      player1
    }
    case player1 :: Nil => {
      val player2 = newPlayer(Side.YELLOW)
      players = player2 :: players
      startedGame = Some(new P4Game(List(player1, player2)))
      player1.newMessage("player YELLOW joined the game")
      startedGame.get.start()
      player2
      
    }
    case _ => throw new IllegalArgumentException("This game is already started")
  }

}

class WebPlayer(val side: Side.Side) extends Player {
  class Updater extends LiftActor with ListenerManager {
    def createUpdate = msgs
    def update() = updateListeners()
  }
  
  var msgs: List[AnyRef] = List()
  val updater = new Updater()

  override def updateBoard(board: P4Board, game: P4Game) {
    if (game.moves.isEmpty) return
    val lastMove = game.moves.last
    msgs = lastMove +: msgs;
    updater.update()
  }

  override def newMessage(message: String) {
    msgs = message +: msgs;
    updater.update()
  }
}

object GameServer extends LiftActor with ListenerManager {
  var games: mutable.Map[String, GameServer] = mutable.Map()

  def createUpdate = {
    games.values
  }

  def game(name: String): GameServer = {
    games.get(name) getOrElse { throw new IllegalArgumentException("game " + name + " does not exist") }
  }

  def newGame(name: String) = {
    require(!games.contains(name), "game " + name + " already exist")
    val newGame = new GameServer(name)
    games.put(name, newGame)
    updateListeners()
    newGame
  }
}
