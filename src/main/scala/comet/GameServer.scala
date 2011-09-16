package gameboard.comet

import net.liftweb._
import http._
import actor._
import gameboard._
import scala.collection.mutable

class GameServer(name : String) extends LiftActor with ListenerManager {
  private var msgs : List[AnyRef] = List("Red starts playing in game " + name) // private state
  
  class WebPlayer extends Player {
    val side = Side.RED

    override def updateBoard(board: P4Board, game: P4Game) {
      if (game.moves.isEmpty) return
      val lastMove = game.moves.last
      sendMove(lastMove)
    }

    override def newMessage(message: String) {
      sendMessage(message)
    }
  }

  val  game = {
    val webPlayer = new WebPlayer()
    val botPlayer = new P4Bot(Side.YELLOW)
    val game = new P4Game(List(webPlayer, botPlayer))
    game.start()

    game
  }

  def createUpdate = msgs

  private def sendMessage(newMessage: java.lang.String): Unit = {
    msgs = newMessage +: msgs;
    updateListeners()
  }

  private def sendMove(move : (Int, Side.Side)): Unit = {
    msgs  = move +: msgs;
    updateListeners()
  }
}

object GameServer {
  var games : mutable.Map[String, GameServer] = mutable.Map()
  
  def game(name : String) : GameServer = {
    games.get(name) getOrElse newGame(name)
  }
  
  private def newGame(name : String) = {
    val newGame = new GameServer(name)
    games.put(name, newGame)
    newGame
  }
}
