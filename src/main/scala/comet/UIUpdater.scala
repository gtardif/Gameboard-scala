package gameboard.comet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import util._ 
import gameboard.Side

class UIUpdater extends CometActor with CometListener{
  def registerWith = GameServer.game(name openOr "default")

  override def lowPriority = {
    case (column: Int, side : Side.Side)::_ => partialUpdate(Call("P4.addChip", column, side.toString.toLowerCase)) 
    case (message:String) :: rest=>       partialUpdate(Alert( "" + message))
    case other => throw new RuntimeException("Could not update UI with " + other);
  }

  def render = new RenderOut(<div/>, Call("P4.start"))
}