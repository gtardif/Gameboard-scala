package gameboard.comet

import net.liftweb._
import http._
import actor._
import gameboard._

object GameServer extends LiftActor with ListenerManager {
  private var msgs = Vector("Red starts playing") // private state

  class WebPlayer extends Player {
    val side = Side.RED

    override def updateBoard(board: P4Board, game: P4Game) {
      if (game.moves.isEmpty) return
      val lastMove = game.moves.last
      newMessage(lastMove._2.toString() + " plays " + lastMove._1)
    }

    override def newMessage(message: String) {
      GameServer.newMessage(message)
    }
  }

  def newGame = {
    val webPlayer = new WebPlayer()
    val botPlayer = new P4Bot(Side.YELLOW)
    val game = new P4Game(List(webPlayer, botPlayer))
    game.start()

    game
  }

  def createUpdate = msgs

  private def newMessage(newMessage: java.lang.String): Unit = {
    msgs :+= newMessage;
    updateListeners()
  }
}
