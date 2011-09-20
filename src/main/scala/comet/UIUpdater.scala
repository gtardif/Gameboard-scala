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
  def registerWith = gameServer

  override def lowPriority = {
    case (move: (Int, Side.Side)) :: _ => partialUpdate(jsMove(move))
    case (message: String) :: rest => partialUpdate(Alert("" + message))
    case other => throw new RuntimeException("Could not update UI with " + other);
  }
  
  override def lifespan = Full(1 minutes)

  def render = {
    val moves = if (gameServer.game.moves isEmpty) List() else gameServer.game.moves.reverse.tail
    val jsInit: JsCmd = Call("P4.start")
    ((jsInit /: moves.map(m => jsMove(m)))(_ & _))
  }

  private def gameServer = GameServer.game(name openOr "default")
  private def jsMove(m: (Int, Side.Side)) = Call("P4.addChip", m._1, m._2.toString.toLowerCase)
}