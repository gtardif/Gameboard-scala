package gameboard.comet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import util._
import gameboard.Side
import net.liftweb.common._
import Helpers._

class UIUpdater extends CometActor with CometListener {
  private var player : WebPlayer = null
  
  def registerWith = {
    player = gameServer.join()
    println("register " + player.side)
    player.updater
  }

  override def lowPriority = {
    case (move: (Int, Side.Side)) :: _ => partialUpdate(jsMove(move))
    case (message: String) :: rest => {
      if (message contains "joined the game") partialUpdate(Alert("" + message) & startGame) //TODO improve design...
      else partialUpdate(Alert("" + message))
    }
    case other => println("Could not update UI with " + other);
  }

  override def lifespan = Full(1 minutes)

  def render: RenderOut = {
    println("render " + player.side)
    if (!gameServer.started) return <p>Waiting for another player to join the game...</p>
    else startGame
  }

  private def gameServer = GameServer.game(name openOr "default")
  private def jsMove(m: (Int, Side.Side)) = Call("P4.addChip", m._1, m._2.toString.toLowerCase)

  private def startGame: JsCmd = {
    println("start game for player " + player.side)
    val moves = if (gameServer.game.moves isEmpty) List() else gameServer.game.moves.reverse.tail
    val jsInit: JsCmd = SetExp(JsVar("mySide"), player.side.toString) & Call("P4.start")
    ((jsInit /: moves.map(m => jsMove(m)))(_ & _))
  }
}