package gameboard.comet

import net.liftweb._
import http._
import actor._
import gameboard._

class GameServer extends LiftActor with ListenerManager {
  private var msgs : List[AnyRef] = List("Red starts playing") // private state
  val id = GameServer.newId
  
  println("creating game " + id)

  class WebPlayer extends Player {
    val side = Side.RED

    override def updateBoard(board: P4Board, game: P4Game) {
      println("new board for " + id)
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
  var lastCreated : GameServer = new GameServer
  var id : Int= 0
  
  def startGameServer = {
    lastCreated
  }
  
  def lastCreatedGame = lastCreated
  
  def newId = { id += 1; id}
  
}
