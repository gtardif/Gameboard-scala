package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._
import gameboard.Side
import net.liftweb.common.Box
import scala.xml.NodeSeq

class PlayerView extends CometActor with CometListener {
  private var msgs: List[String] = List() // private state

  def registerWith = {
    GameServer.game(name openOr "default")
  }

  override def lowPriority = {
    case list: List[AnyRef] => {
      msgs = list.map(display(_)); reRender()
    }
  }

  private def display(x: AnyRef): String = x match {
    case s: String => s
    case (column: Int, side: Side.Side) => side + " plays " + column
    case _ => throw new RuntimeException("bug")
  }

  def render = <ul>
                 { msgs.flatMap(m => <li>{ m }</li>) }
               </ul>
}
