package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._
import gameboard.Side

class PlayerView extends CometActor with CometListener {
  private var msgs: List[String] = List() // private state

  def registerWith = GameServer.startGameServer

  override def lowPriority = {
    case list: List[AnyRef] => msgs = list.map(display(_)); reRender()
  }
  
  private def display(x : AnyRef) : String = x match {
    case s : String => s
    case (column : Int, side : Side.Side) => side + " plays " + column
    case _ => throw new RuntimeException("bug")
  }

  def render = "li *" #> msgs & ClearClearable
}
